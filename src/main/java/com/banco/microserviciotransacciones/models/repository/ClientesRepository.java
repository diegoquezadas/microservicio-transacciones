/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.models.repository;

import com.banco.microserviciotransacciones.models.entity.Clientes;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author diegoquezada
 */
public interface ClientesRepository extends CrudRepository<Clientes, Integer>  {
    
}
