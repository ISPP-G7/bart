package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.SpringBootPostgresCRUD.entity.Anuncio;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

}