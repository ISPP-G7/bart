package com.example.SpringBootPostgresCRUD.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootPostgresCRUD.entity.Anuncio;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    @Query("SELECT a FROM Anuncio a WHERE a.titulo = :titulo")
    Optional<Anuncio> findByTitle(@Param("titulo") String titulo);

}
