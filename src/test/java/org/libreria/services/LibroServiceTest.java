package org.libreria.services;

import org.aspectj.lang.annotation.Before;
import org.libreria.models.Libro;
import org.libreria.repositories.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibroServiceTest {

  @Mock
  private LibroRepository libroRepository;

  @InjectMocks
  private LibroService libroService;

  private Libro libro;

  @BeforeEach
  public void setUp() {
    libro = new Libro();
    libro.setId(1L);
    libro.setTitulo("Libro con Titulo");
    libro.setAutor("Autor con Apellidos");
    libro.setGenero("Genero");
    libro.setPrecio(19.99);
    libro.setPublicacion(2001);
  }

  @Test
  void testGuardarLibro() {
    when(libroRepository.save(any(Libro.class))).thenReturn(libro);
    Libro guardado = libroService.guardarLibro(new Libro());
    assertNotNull(guardado);
    assertEquals("Libro con Titulo", guardado.getTitulo());
    verify(libroRepository, times(1)).save(any(Libro.class));
  }

  @Test
  void testObtenerTodos() {
    when(libroRepository.findAll()).thenReturn(List.of(libro));

    List<Libro> lista = libroService.obtenerTodos();

    assertFalse(lista.isEmpty());
    assertEquals(1, lista.size());
    verify(libroRepository, times(1)).findAll();
  }
}
