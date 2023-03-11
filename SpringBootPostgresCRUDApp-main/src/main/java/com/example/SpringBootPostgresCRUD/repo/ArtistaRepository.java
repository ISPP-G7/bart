package com.example.SpringBootPostgresCRUD.repo;

import com.example.SpringBootPostgresCRUD.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    @Query("SELECT a FROM Artista a WHERE a.email=?1")
    Artista getArtistaByMailArtista(String mail);
}
