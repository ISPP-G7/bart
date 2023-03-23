package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;

@Repository
public interface AnuncioArtistaRepository extends JpaRepository<AnuncioArtista, Long> {

}