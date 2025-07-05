package com.edutech.microservicios;
// MODIFICAR PARA TESTS JUNIT
import com.edutech.microservicios.model.Cliente;
import com.edutech.microservicios.repository.ClienteRepository;
import com.edutech.microservicios.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

// Para que funcione testFindAll
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteServ;

    @MockBean
    private ClienteRepository clienteRepo;


    @Test
    public void testFindAll() {
        when(clienteRepo.findAll()).thenReturn(List.of(new Cliente(1, "ashe12", "Juan", "Perez",
                                                                new Date(1999,12,19), "juan@correo.com")));
        List<Cliente> clientes = clienteServ.findAll();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        System.out.println("Clientes encontrados: " + clientes);
    }
}
