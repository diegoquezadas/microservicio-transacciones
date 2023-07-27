/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.controllers;

import com.banco.microserviciotransacciones.exceptions.ResourceNotFoundException;
import com.banco.microserviciotransacciones.models.entity.Cuenta;
import com.banco.microserviciotransacciones.service.CuentaService;

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
@RequestMapping("/cuentas")
public class CuentaController {

    private CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Integer id) {
        Optional<Cuenta> o = service.findById(id);
        if (o.isPresent()) {

            return ResponseEntity.ok().body(o.get());
        } else {
            throw new ResourceNotFoundException("Cuenta con ID " + id + " no encontrado.");
        }

    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cuenta cuenta) {
        Cuenta cuentaDb = service.save(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaDb);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Cuenta cuenta, @PathVariable Integer id) {
        Optional<Cuenta> o = service.findById(id);
        if (o.isPresent()) {
            Cuenta cuentaDb = o.get();
            cuentaDb.setCliente(cuenta.getCliente());
            cuentaDb.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDb.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDb.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDb.setEstado(cuenta.getEstado());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cuentaDb));
        } else {
            throw new ResourceNotFoundException("Cuenta con ID " + id + " no encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
