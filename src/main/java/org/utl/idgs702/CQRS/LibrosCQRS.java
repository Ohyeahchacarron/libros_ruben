package org.utl.idgs702.CQRS;

import org.utl.idgs702.DAOLibros.LibroDAO;
import org.utl.idgs702.Entities.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class LibrosCQRS {

    @Autowired
    private LibroDAO libroDAO;

    public boolean validarNombreLibro(String nombreLibro) {
        if (nombreLibro == null || nombreLibro.length() < 5 || nombreLibro.length() > 100) {
            System.out.println("Error: El nombre del libro debe tener entre 5 y 100 caracteres.");
            return false;
        }
        return true;
    }

    public boolean validarCategoria(String categoria) {
        if (categoria == null || categoria.length() < 5 || categoria.length() > 30) {
            System.out.println("Error: La categoría debe tener entre 5 y 30 caracteres.");
            return false;
        }
        return true;
    }

    public void registrarLibro(Libro libro, MultipartFile archivo) {
  
        if (validarNombreLibro(libro.getNombreLibro()) && validarCategoria(libro.getGenero())) {
            try {
                libroDAO.crearLibro(libro.getNombreLibro(), libro.getAutor(), libro.getGenero(), libro.getEstatus(), archivo);
                System.out.println("Libro registrado exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al procesar el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Error: No se pudo registrar el libro debido a datos inválidos.");
        }
    }

    public void actualizarLibro(Libro libro, MultipartFile archivo) {
        if (validarNombreLibro(libro.getNombreLibro()) && validarCategoria(libro.getGenero())) {
            try {
                libroDAO.actualizarLibro(libro.getIdLibro(), libro.getNombreLibro(), libro.getAutor(), libro.getGenero(), libro.getEstatus(), archivo);
                System.out.println("Libro actualizado exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al procesar el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Error: No se pudo actualizar el libro debido a datos inválidos.");
        }
    }
}
