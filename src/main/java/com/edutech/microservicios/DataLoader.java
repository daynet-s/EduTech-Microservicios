package com.edutech.microservicios;

import com.edutech.microservicios.model.*;
import com.edutech.microservicios.repository.*;
import net.datafaker.Faker;
import net.datafaker.providers.base.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generacion de clientes fake
        for (int i = 0; i < 50; i++) {
            Cliente cliente = new Cliente();
            cliente.setId(i + 1);
            //cliente.setClave(toString(faker.number().numberBetween(10000,99999)));
            cliente.setClave(faker.text().text(15));
            cliente.setNombres(faker.name().firstName());
            cliente.setApellidos(faker.name().lastName());
            cliente.setFechaNacimiento(new Date());
            cliente.setCorreo(faker.internet().emailAddress());
            clienteRepository.save(cliente);
        }

        List<Cliente> clientes = clienteRepository.findAll();
    }




}
