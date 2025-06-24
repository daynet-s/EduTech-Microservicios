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

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteModelAssembler assembler;

    @Autowired
    private ClienteService clienteServ;


    // Listar todos los clientes con HATEOAS
    @GetMapping
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
    public ResponseEntity<EntityModel<Cliente>> searchCliente(@PathVariable Integer id) {
        Cliente cliente = clienteServ.findById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(cliente));
    }

    // Guardar un nuevo cliente (retornando HATEOAS)
    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> saveCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteServ.save(cliente);
        EntityModel<Cliente> entityModel = assembler.toModel(newCliente);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    // Actualizar cliente (retornando HATEOAS)
    @PutMapping("/{id}")
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
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id) {
        try {
            clienteServ.delete(Long.valueOf(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

