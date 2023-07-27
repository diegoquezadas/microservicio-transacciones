/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.controllers;

import com.banco.microserviciotransacciones.exceptions.ResourceNotFoundException;
import com.banco.microserviciotransacciones.models.entity.Cliente;
import com.banco.microserviciotransacciones.service.ClienteService;

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
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Integer id) {
        Optional<Cliente> o = service.findById(id);
        if (o.isPresent()) {
            return ResponseEntity.ok().body(o.get());
        } else {
            throw new ResourceNotFoundException("Cliente con ID " + id + " no encontrado.");
        }

    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
        Cliente clienteDb = service.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDb);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Cliente cliente, @PathVariable Integer id) {
        Optional<Cliente> o = service.findById(id);
        if (o.isPresent()) {
            Cliente clienteDb = o.get();
            clienteDb.setCedula(cliente.getCedula());
            clienteDb.setNombre(cliente.getNombre());
            clienteDb.setGenero(cliente.getGenero());
            clienteDb.setEdad(cliente.getEdad());
            clienteDb.setDireccion(cliente.getDireccion());
            clienteDb.setTelefono(cliente.getTelefono());
            clienteDb.setContrasenia(cliente.getContrasenia());
            clienteDb.setEstado(cliente.getEstado());

            return ResponseEntity.status(HttpStatus.OK).body(service.save(clienteDb));
        } else {
            throw new ResourceNotFoundException("Cliente con ID " + id + " no encontrado.");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
