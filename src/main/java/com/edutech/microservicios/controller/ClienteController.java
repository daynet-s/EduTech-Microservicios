package com.edutech.microservicios.controller;

import com.edutech.microservicios.assembler.ClienteModelAssembler;
import com.edutech.microservicios.model.Cliente;
import com.edutech.microservicios.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")

public class ClienteController {

    @Autowired
    private ClienteModelAssembler assembler;

    @Autowired
    private ClienteService clienteServ;


    // Listar todos los clientes con HATEOAS
    @GetMapping
    @Operation(summary = "Listar todos los cliente", description = "Lista a todos los clientes de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se han listados los clientes."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> showList() {
        List<EntityModel<Cliente>> clientes = clienteServ.findAll().stream()
                .map(assembler::toModel)
                .toList();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(clientes,
                        linkTo(methodOn(ClienteController.class).showList()).withSelfRel())
        );
    }

    // Obtener cliente por ID con HATEOAS
    @GetMapping("/{id}")
    @Operation(summary = "Encontrar un cliente", description = "Busca a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a encontrado un cliente."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<EntityModel<Cliente>> searchCliente(@PathVariable Integer id) {
        Cliente cliente = clienteServ.findById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(cliente));
    }

    // Guardar un nuevo cliente (retornando HATEOAS)
    @PostMapping
    @Operation(summary = "Guardar un cliente", description = "Guarda a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a Guardado un cliente."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<EntityModel<Cliente>> saveCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteServ.save(cliente);
        EntityModel<Cliente> entityModel = assembler.toModel(newCliente);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    // Actualizar cliente (retornando HATEOAS)
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente", description = "Actualiza a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a actualizado un cliente."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<EntityModel<Cliente>> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Cliente existingCliente = clienteServ.findById(id);
        if (existingCliente == null) {
            return ResponseEntity.notFound().build();
        }
        // Actualiza campos
        existingCliente.setClave(cliente.getClave());
        existingCliente.setNombres(cliente.getNombres());
        existingCliente.setApellidos(cliente.getApellidos());
        existingCliente.setFechaNacimiento(cliente.getFechaNacimiento());
        existingCliente.setCorreo(cliente.getCorreo());

        Cliente updatedCliente = clienteServ.save(existingCliente);
        EntityModel<Cliente> entityModel = assembler.toModel(updatedCliente);

        return ResponseEntity.ok(entityModel);
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente", description = "Elimina a un cliente de la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operación exitosa, se a eliminado un cliente."),
            @ApiResponse(responseCode = "404", description = "Operación fallida, no se encontrado el cliente")})
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id) {
        try {
            clienteServ.delete(Long.valueOf(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}


