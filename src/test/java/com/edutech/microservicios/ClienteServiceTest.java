package com.edutech.microservicios;
// MODIFICAR PARA TESTS JUNIT
import com.edutech.microservicios.model.Cliente;
import com.edutech.microservicios.repository.ClienteRepository;
import com.edutech.microservicios.service.ClienteService;
import org.junit.jupiter.api.Test;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteServ;

    @MockBean
    private ClienteRepository clienteRepo;

    // REVISAR POR QUE LOS METODOS DA ERROR EN COMPARACION A DOC
    /*@Test
    public void testFindAll() {
        when(clienteRepo.findAll()).thenReturn(List.of(new Cliente(1, "ashe12", "Juan", "Perez",
                                                                LocalDate.now(), "juan@correo.com")));
        List<Cliente> clientes = clienteServ.findAll();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
    }*/
}
