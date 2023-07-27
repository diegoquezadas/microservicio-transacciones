/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.controllers;

import com.banco.microserviciotransacciones.exceptions.BadRequestException;
import com.banco.microserviciotransacciones.exceptions.ResourceNotFoundException;
import com.banco.microserviciotransacciones.models.entity.Cuenta;
import com.banco.microserviciotransacciones.models.entity.Movimiento;
import com.banco.microserviciotransacciones.service.CuentaService;
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
    private final CuentaService cuentaService;

    public MovimientoController(MovimientoService service, CuentaService cuentaService) {
        this.service = service;
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Integer id) {
        Optional<Movimiento> o = service.findById(id);
        if (o.isPresent()) {
            return ResponseEntity.ok().body(o.get());
        } else {
            throw new ResourceNotFoundException("Movimiento con ID " + id + " no encontrado.");
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Movimiento movimiento) {
        Cuenta cuenta = movimiento.getCuenta();
        if (cuenta != null) {
            Optional<Cuenta> cuentaOptional = cuentaService.findById(cuenta.getId());
            if (cuentaOptional.isPresent()) {
                int saldoAnterior = obtenerSaldoAnterior(cuenta);
                realizarMovimiento(movimiento, saldoAnterior);
                Movimiento movimientoDb = service.save(movimiento);
                return ResponseEntity.status(HttpStatus.OK).body(movimientoDb);
            } else {
                throw new ResourceNotFoundException("No se encontró la cuenta del movimiento");
            }
        } else {
            throw new BadRequestException("Falta número de cuenta");
        }
    }

    private int obtenerSaldoAnterior(Cuenta cuenta) {
        Optional<Cuenta> cuentaOptional = cuentaService.findById(cuenta.getId());
        if (cuentaOptional.isPresent()) {
            Cuenta cuentaDb = cuentaOptional.get();
            int saldoAnterior = cuentaDb.getSaldoInicial() != null ? cuentaDb.getSaldoInicial() : 0;

            List<Movimiento> movimientosAnteriores = service.findByCuenta(cuentaDb);
            if (movimientosAnteriores != null && !movimientosAnteriores.isEmpty()) {
                saldoAnterior += movimientosAnteriores.stream()
                        .mapToInt(Movimiento::getValor)
                        .sum();
            }

            return saldoAnterior;
        } else {
            throw new ResourceNotFoundException("No se encontró la cuenta");
        }
    }

    private void realizarMovimiento(Movimiento movimiento, int saldoAnterior) {
        String tipoMovimiento = movimiento.getTipoMovimiento();
        int valor = movimiento.getValor();

        if ("DEBITO".equals(tipoMovimiento)) {
            if (saldoAnterior < valor) {
                throw new IllegalArgumentException("Saldo no disponible");
            }
            valor = -valor;
        } else if (!"DEPOSITO".equals(tipoMovimiento)) {
            throw new BadRequestException("Tipo de movimiento inválido");
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
        Optional<Movimiento> movimientoOptional = service.findById(id);
        if (movimientoOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Movimiento con ID " + id + " no encontrada.");
        }
    }

}
