package com.edutech.microservicios.assembler;

import com.edutech.microservicios.controller.ClienteController;
import com.edutech.microservicios.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).searchCliente(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).showList()).withRel("clientes")
        );
    }
}
