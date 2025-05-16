package com.edutech.microservicios.controller;

import com.edutech.microservicios.model.Cliente;
import com.edutech.microservicios.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteServ;

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> showList() {
        List<Cliente> clientes = clienteServ.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    // Buscar a cliente por Id
    @GetMapping("/{id}")
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
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteServ.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
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
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            clienteServ.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
