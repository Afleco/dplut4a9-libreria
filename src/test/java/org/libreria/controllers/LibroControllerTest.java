package org.libreria.controllers;

import org.libreria.models.Libro;
import org.libreria.services.LibroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LibroController.class)
public class LibroControllerTest {

  @Autowired
    private MockMvc mockMvc;

  @MockitoBean
  private LibroService libroService;

  @Test
  public void testListarTodosEndpoint() throws Exception {
    Libro libro = new Libro();
    libro.setTitulo("Libro con Titulo");

    when(libroService.obtenerTodos()).thenReturn(List.of(libro));

    mockMvc.perform(get("/api/libros"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].titulo").value("Libro con Titulo"));
  }
}















