package com.edutech.microservicios.service;

import com.edutech.microservicios.model.Cliente;
import com.edutech.microservicios.repository.ClienteRepository;
import jakarta.transaction.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepo;

    // Encontrar todas las entidades
    public List<Cliente> findAll() {
        return clienteRepo.findAll();
    }

    // Encontrar por Id
    public Cliente findById (int id) {
        return clienteRepo.findById(id).get();
    }

    // Guardar entidad
    public Cliente save(Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    // Eliminar entidad
    public void delete(int id) {
        clienteRepo.deleteById(id);
    }
}
