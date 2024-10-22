package org.utl.idgs702.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.utl.idgs702.Entities.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
}
