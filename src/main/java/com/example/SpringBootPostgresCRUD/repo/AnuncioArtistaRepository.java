package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;

@Repository
public interface AnuncioArtistaRepository extends JpaRepository<AnuncioArtista, Long> {

    @Query("SELECT a FROM AnuncioArtista a WHERE" + " LOWER(CONCAT(a.pseudonimoArtista, a.estilo))"
            + " LIKE LOWER(CONCAT('%', ?1, '%'))")
    public List<AnuncioArtista> busquedaFiltrada(String palabraClave);

}