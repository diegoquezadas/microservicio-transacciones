package com.banco.microserviciotransacciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.banco.microserviciotransacciones.models.entity.Cliente;
import com.banco.microserviciotransacciones.models.entity.Cuenta;
import com.banco.microserviciotransacciones.models.entity.Movimiento;
import com.banco.microserviciotransacciones.service.CuentaService;
import com.banco.microserviciotransacciones.service.MovimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MicroservicioTransaccionesApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private MovimientoService movimientoService;

    @Mock
    private CuentaService cuentaService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testEditarClienteExistente() throws Exception {
        // Crear un objeto Cliente que represente el JSON del cliente a editar
        Cliente clienteEditar = new Cliente();
        clienteEditar.setId(1);
        clienteEditar.setNombre("Nuevo Nombre");
        clienteEditar.setGenero("M");
        clienteEditar.setEdad(30);
        clienteEditar.setDireccion("Nueva Dirección");
        clienteEditar.setTelefono("Nuevo Teléfono");

        // Realizar la solicitud PUT al endpoint /clientes/{id}
        mockMvc.perform(put("/clientes/{id}", 1)
                .contentType("application/json")
                .content(mapper.writeValueAsString(clienteEditar)))
                .andExpect(status().isOk()) // Esperamos que la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verificamos que el ID del cliente sea el mismo
                .andExpect(jsonPath("$.nombre").value("Nuevo Nombre")) // Verificamos que el nombre se haya actualizado
                .andExpect(jsonPath("$.genero").value("M")) // Verificamos que el género se haya actualizado
                .andExpect(jsonPath("$.edad").value(30)) // Verificamos que la edad se haya actualizado
                .andExpect(jsonPath("$.direccion").value("Nueva Dirección")) // Verificamos que la dirección se haya
                // actualizado
                .andExpect(jsonPath("$.telefono").value("Nuevo Teléfono")); // Verificamos que el teléfono se haya
        // actualizado
    }

    @Test
    void testEditarClienteNoExistente() throws Exception {
        // Crear un objeto Cliente que represente el JSON del cliente a editar
        Cliente clienteEditar = new Cliente();
        clienteEditar.setId(999); // ID que no existe

        // Realizar la solicitud PUT al endpoint /clientes/{id}
        mockMvc.perform(put("/clientes/{id}", 999)
                .contentType("application/json")
                .content(mapper.writeValueAsString(clienteEditar)))
                .andExpect(status().isNotFound()); // Esperamos que la respuesta sea 404 Not Found
    }

    @Test
    void testEditarMovimientoNoExistente() throws Exception {
        // Crear un objeto movimiento que represente el JSON del movimiento a editar
        Movimiento movimientoEditar = new Movimiento();
        movimientoEditar.setId(999); // ID que no existe

        // Realizar la solicitud PUT al endpoint /clientes/{id}
        mockMvc.perform(put("/movimientos/{id}", 999)
                .contentType("application/json")
                .content(mapper.writeValueAsString(movimientoEditar)))
                .andExpect(status().isNotFound()); // Esperamos que la respuesta sea 404 Not Found
    }

    @Test
    void testEliminarMovimientoNoExistente() throws Exception {
        int movimientoId = 999;

        mockMvc.perform(delete("/movimientos/{id}", movimientoId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Movimiento con ID " + movimientoId + " no encontrada."));
    }

    @Test
    void testVerCuentaExistente() throws Exception {
        int cuentaId = 1;

        mockMvc.perform(get("/cuentas/{id}", cuentaId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cuentaId));
    }

    @Test
    void testVerCuentaNoExistente() throws Exception {
        int cuentaId = 999;

        mockMvc.perform(get("/cuentas/{id}", cuentaId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Cuenta con ID " + cuentaId + " no encontrado."));
    }

    @Test
    void contextLoads() {
    }

}
