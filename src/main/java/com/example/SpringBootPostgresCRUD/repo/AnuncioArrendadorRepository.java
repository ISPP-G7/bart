package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;

@Repository
public interface AnuncioArrendadorRepository extends JpaRepository<AnuncioArrendador, Long> {

}