package org.utl.idgs702.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.utl.idgs702.Entities.Libro;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
    // Método para buscar libros cuyo nombre contenga una cadena específica
    @Query("SELECT l FROM Libro l WHERE l.nombreLibro LIKE %:nombre%")
    List<Libro> findByNombreContaining(@Param("nombre") String nombre);
}
