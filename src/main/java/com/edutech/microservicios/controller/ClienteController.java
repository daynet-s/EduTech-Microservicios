package com.edutech.microservicios.controller;

import com.edutech.microservicios.model.Cliente;
import com.edutech.microservicios.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")

public class ClienteController {
    @Autowired
    private ClienteService clienteServ;

    // Listar todos los clientes
    @GetMapping
    @Operation(summary = "Listar todos los cliente", description = "Lista a todos los clientes de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se han listados los clientes."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<List<Cliente>> showList() {
        List<Cliente> clientes = clienteServ.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    // Buscar a cliente por Id
    @GetMapping("/{id}")
    @Operation(summary = "Encontrar un cliente", description = "Busca a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a encontrado un cliente."),
    @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<Cliente> searchCliente(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteServ.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Guardar un nuevo cliente
    @PostMapping
    @Operation(summary = "Guardar un cliente", description = "Guarda a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a Guardado un cliente."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteServ.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente", description = "Actualiza a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a actualizado un cliente."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente cli = clienteServ.findById(id);
            cli.setId(id);
            cli.setClave(cliente.getClave());
            cli.setNombres(cliente.getNombres());
            cli.setApellidos(cliente.getApellidos());
            cli.setFechaNacimiento(cliente.getFechaNacimiento());
            cli.setCorreo(cliente.getCorreo());

            clienteServ.save(cli);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente", description = "Elimina a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a eliminado un cliente."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            clienteServ.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
