/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service;

import java.util.Optional;

import com.banco.microserviciotransacciones.models.entity.Cliente;

/**
 *
 * @author diegoquezada
 */
public interface ClienteService {

    Optional<Cliente> findById(Integer id);

    Cliente save(Cliente cliente);

    void deleteById(Integer id);
}
