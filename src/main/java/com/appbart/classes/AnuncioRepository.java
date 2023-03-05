package com.appbart.classes;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends CrudRepository<Anuncio, Long> {
    @Query("SELECT a FROM Anuncio a WHERE a.titulo = :titulo")
    Optional<Anuncio> findByTitle(@Param("titulo") String titulo);

}
