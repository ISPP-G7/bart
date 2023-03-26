package com.example.SpringBootPostgresCRUD.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SpringBootPostgresCRUD.entity.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{

  @Query("SELECT f FROM Foto f WHERE f.userEmail=?1")
  public List<Foto> findByUser(String user);
    
}
