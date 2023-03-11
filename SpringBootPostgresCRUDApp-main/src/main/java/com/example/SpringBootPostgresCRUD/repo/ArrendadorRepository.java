package com.example.SpringBootPostgresCRUD.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;

@Repository
public interface ArrendadorRepository extends JpaRepository<Arrendador, Long> {
    @Query("SELECT a FROM Arrendador a WHERE a.email=?1")
    Arrendador getArrendadorByMailArrendador(String mail);
}