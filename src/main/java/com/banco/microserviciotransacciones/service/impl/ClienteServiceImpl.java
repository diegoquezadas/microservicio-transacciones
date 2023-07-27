/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service.impl;

import com.banco.microserviciotransacciones.models.entity.Cliente;
import com.banco.microserviciotransacciones.models.repository.ClienteRepository;
import com.banco.microserviciotransacciones.service.ClienteService;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author diegoquezada
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository repository;
    
    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);

    }
}
