package com.edutech.microservicios;
// MODIFICAR PARA TESTS JUNIT
import com.edutech.microservicios.model.Cliente;
import com.edutech.microservicios.repository.ClienteRepository;
import com.edutech.microservicios.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// Para que funcione testFindAll
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
    }

    @Test
    public void testFindById() {
        long id = 1;
        Cliente cliente = new Cliente(id,"ashe12", "Juan", "Perez",
                                    new Date(1999,12,19), "juan@correo.com");
        when(clienteRepo.findById(id)).thenReturn(Optional.of(cliente));

        Cliente found = clienteServ.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {
        Cliente cliente = new Cliente(1, "ashe12", "Juan", "Perez",
                                    new Date(1999,12,19), "juan@correo.com");
        when(clienteRepo.save(cliente)).thenReturn(cliente);

        Cliente saved = clienteServ.save(cliente);
        assertNotNull(saved);
        assertEquals("Juan", saved.getNombres());
    }

    @Test
    public void testDelete() {
        long id = 1;
        doNothing().when(clienteRepo).deleteById(id);

        clienteServ.delete(id);
        verify(clienteRepo, times(1)).deleteById(id);
    }
}
