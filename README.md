

Como aporte al desarrollo del sistema de microservicios de EduTech Innovators SPA, se agregó una prueba unitaria para el microservicio de clientes

Objetivo de la prueba:
Verificar que el método `crearCliente()` del servicio `ClienteService` funcione correctamente, simulando la operación de guardado mediante un mock del repositorio.

 Ubicación del archivo de prueba:
```
src/test/java/com/edutech/clientes/service/ClienteServiceTest.java
```

#Tecnologías utilizadas:
- JUnit 5**: Para la creación de pruebas unitarias.
- Mockito**: Para simular el comportamiento del `ClienteRepository`.
- Spring Boot Test**: Para ejecutar pruebas integradas con el contexto de Spring.

Qué se prueba:
- Que un cliente con nombre y correo válidos puede ser creado correctamente.
- Que la respuesta del servicio es válida y corresponde a los datos entregados.


