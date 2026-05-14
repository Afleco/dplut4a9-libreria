package org.libreria.services;

import org.libreria.exceptions.ResourceNotFoundException;
import org.libreria.models.Libro;
import org.libreria.repositories.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
    libro.setTitulo("El Quijote");
    libro.setAutor("Miguel de Cervantes");
    libro.setGenero("Novela");
    libro.setPrecio(20.0);
    libro.setPublicacion(1605);
  }

  @Test
  void testGuardarLibro() {
    when(libroRepository.save(any(Libro.class))).thenReturn(libro);
    Libro guardado = libroService.guardarLibro(new Libro());
    assertNotNull(guardado);
    assertEquals("Para que falle", guardado.getTitulo());
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

  // --- NUEVOS TESTS ---

  @Test
  void testActualizarLibro_Exito() {
    Libro detallesNuevos = new Libro();
    detallesNuevos.setTitulo("El Quijote Actualizado");

    // Simulamos que encuentra el libro
    when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
    when(libroRepository.save(any(Libro.class))).thenReturn(libro);

    Libro actualizado = libroService.actualizarLibro(1L, detallesNuevos);

    assertNotNull(actualizado);
    assertEquals("El Quijote Actualizado", actualizado.getTitulo());
    verify(libroRepository, times(1)).findById(1L);
    verify(libroRepository, times(1)).save(libro);
  }

  @Test
  void testActualizarLibro_NoEncontrado() {
    // Simulamos que NO encuentra el libro
    when(libroRepository.findById(99L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> {
      libroService.actualizarLibro(99L, new Libro());
    });

    verify(libroRepository, never()).save(any(Libro.class));
  }

  @Test
  void testEliminarLibro_Exito() {
    when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
    doNothing().when(libroRepository).delete(libro);

    libroService.eliminarLibro(1L);

    verify(libroRepository, times(1)).findById(1L);
    verify(libroRepository, times(1)).delete(libro);
  }
}