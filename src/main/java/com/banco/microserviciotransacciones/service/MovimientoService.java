/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service;

import com.banco.microserviciotransacciones.models.entity.Cuenta;
import com.banco.microserviciotransacciones.models.entity.Movimiento;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author diegoquezada
 */
public interface MovimientoService {


    Optional<Movimiento> findById(Integer id);

    Movimiento save(Movimiento movimiento);

    void deleteById(Integer id);

    List<Movimiento> findByCuenta(Cuenta cuenta);

}
