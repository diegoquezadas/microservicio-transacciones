/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service;

import com.banco.microserviciotransacciones.models.entity.Cuenta;
import java.util.Optional;

/**
 *
 * @author diegoquezada
 */
public interface CuentaService {


    Optional<Cuenta> findById(Integer id);

    Cuenta save(Cuenta cuenta);

    void deleteById(Integer id);
}
