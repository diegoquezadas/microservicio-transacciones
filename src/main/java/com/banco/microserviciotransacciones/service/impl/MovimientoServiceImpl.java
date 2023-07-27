/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service.impl;

import com.banco.microserviciotransacciones.models.entity.Cuenta;
import com.banco.microserviciotransacciones.models.entity.Movimiento;
import com.banco.microserviciotransacciones.models.repository.MovimientoRepository;
import com.banco.microserviciotransacciones.service.MovimientoService;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author diegoquezada
 */
@Service
public class MovimientoServiceImpl implements MovimientoService {

    private MovimientoRepository repository;

    public MovimientoServiceImpl(MovimientoRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Movimiento> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Movimiento save(Movimiento movimiento) {
        return repository.save(movimiento);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);

    }


    @Override
    public List<Movimiento> findByCuenta(Cuenta cuenta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCuenta'");
    }
}
