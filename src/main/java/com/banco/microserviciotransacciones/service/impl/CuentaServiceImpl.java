/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service.impl;

import com.banco.microserviciotransacciones.models.entity.Cuenta;
import com.banco.microserviciotransacciones.models.repository.CuentaRepository;
import com.banco.microserviciotransacciones.service.CuentaService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author diegoquezada
 */
@Service
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository repository;

    public CuentaServiceImpl (CuentaRepository repository){
        this.repository=repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuenta> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);

    }
}
