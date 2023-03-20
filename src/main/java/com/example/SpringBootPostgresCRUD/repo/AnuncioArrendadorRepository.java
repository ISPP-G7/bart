package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;

@Repository
public interface AnuncioArrendadorRepository extends JpaRepository<AnuncioArrendador, Long> {

    @Query("SELECT a FROM AnuncioArrendador a WHERE" + " CONCAT(a.estilo)"
            + " LIKE %?1%")
    public List<AnuncioArrendador> busquedaFiltrada(String palabraClave);

}