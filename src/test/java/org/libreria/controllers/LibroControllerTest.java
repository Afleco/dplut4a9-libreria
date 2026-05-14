package org.libreria.controllers;

import org.libreria.models.Libro;
import org.libreria.services.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
public class LibroControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper; // Para convertir objetos a JSON

  @MockitoBean
  private LibroService libroService;

  private Libro libroValido;

  @BeforeEach
  void setUp() {
    libroValido = new Libro();
    libroValido.setId(1L);
    libroValido.setTitulo("Dune");
    libroValido.setAutor("Frank Herbert");
    libroValido.setGenero("Ciencia Ficción");
    libroValido.setPrecio(25.50);
    libroValido.setPublicacion(1965);
  }

  @Test
  public void testListarTodosEndpoint() throws Exception {
    when(libroService.obtenerTodos()).thenReturn(List.of(libroValido));

    mockMvc.perform(get("/api/libros"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].titulo").value("Dune"));
  }

  // --- NUEVOS TESTS ---

  @Test
  public void testCrearLibro_ValidacionFalla() throws Exception {
    Libro libroInvalido = new Libro();
    // No le ponemos título, autor, etc. para forzar el error de validación @NotBlank

    mockMvc.perform(post("/api/libros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(libroInvalido)))
            .andExpect(status().isBadRequest()) // Esperamos un error 400
            .andExpect(jsonPath("$.titulo").exists()) // Comprobamos que el JSON de error avisa del título
            .andExpect(jsonPath("$.autor").exists());
  }

  @Test
  public void testActualizarLibro() throws Exception {
    when(libroService.actualizarLibro(eq(1L), any(Libro.class))).thenReturn(libroValido);

    mockMvc.perform(put("/api/libros/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(libroValido)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Dune"));
  }

  @Test
  public void testEliminarLibro() throws Exception {
    doNothing().when(libroService).eliminarLibro(1L);

    mockMvc.perform(delete("/api/libros/1"))
            .andExpect(status().isNoContent()); // Esperamos un 204 No Content
  }
}