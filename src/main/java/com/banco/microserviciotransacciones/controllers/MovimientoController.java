/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.controllers;

import com.banco.microserviciotransacciones.exceptions.BadRequestException;
import com.banco.microserviciotransacciones.exceptions.ResourceNotFoundException;
import com.banco.microserviciotransacciones.models.entity.Cuenta;
import com.banco.microserviciotransacciones.models.entity.Movimiento;
import com.banco.microserviciotransacciones.service.MovimientoService;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author diegoquezada
 */
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Integer id) {
        Optional<Movimiento> o = service.findById(id);
        if (o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(o.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Movimiento movimiento) {
        Cuenta cuenta = movimiento.getCuenta();
        if (cuenta != null) {
            int saldoAnterior = obtenerSaldoAnterior(cuenta);
            realizarMovimiento(movimiento, saldoAnterior);
            Movimiento movimientoDb = service.save(movimiento);
            return ResponseEntity.status(HttpStatus.OK).body(movimientoDb);
        } else {
            throw new BadRequestException("Falta número de cuenta");
        }

    }

    private int obtenerSaldoAnterior(Cuenta cuenta) {
        List<Movimiento> movimientosAnteriores = service.findByCuenta(cuenta);
        if (movimientosAnteriores.isEmpty()) {
            // Primer movimiento, tomar el saldo inicial
            return cuenta.getSaldoInicial();
        } else {
            // Calcular el saldo anterior sumando todos los valores de los movimientos
            // anteriores
            return movimientosAnteriores.stream()
                    .mapToInt(Movimiento::getValor)
                    .sum();
        }
    }

    private void realizarMovimiento(Movimiento movimiento, int saldoAnterior) {
        String tipoMovimiento = movimiento.getTipoMovimiento();
        int valor = movimiento.getValor();

        if (tipoMovimiento.equals("DEBITO")) {
            if (saldoAnterior == 0) {
                throw new IllegalArgumentException("Saldo no disponible");
            }
            valor = -Math.abs(valor);
        } else if (tipoMovimiento.equals("DEPOSITO")) {
            valor = Math.abs(valor);
        }

        int saldoDisponible = saldoAnterior + valor;
        movimiento.setValor(valor);
        movimiento.setSaldo(saldoDisponible);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Movimiento movimiento, @PathVariable Integer id) {
        Optional<Movimiento> o = service.findById(id);
        if (o.isPresent()) {
            Movimiento movimientoDb = o.get();
            movimientoDb.setCuenta(movimiento.getCuenta());
            movimientoDb.setFecha(movimiento.getFecha());
            movimientoDb.setTipoMovimiento(movimiento.getTipoMovimiento());
            movimientoDb.setValor(movimiento.getValor());
            movimientoDb.setSaldo(movimiento.getSaldo());

            return ResponseEntity.status(HttpStatus.OK).body(service.save(movimientoDb));
        } else {
            throw new ResourceNotFoundException("Movimiento con ID " + id + " no encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
