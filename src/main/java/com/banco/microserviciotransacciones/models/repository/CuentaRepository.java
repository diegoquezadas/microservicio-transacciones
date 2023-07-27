/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.models.repository;

import com.banco.microserviciotransacciones.models.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author diegoquezada
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
    
}
