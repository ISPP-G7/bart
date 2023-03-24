package com.example.SpringBootPostgresCRUD.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringBootPostgresCRUD.entity.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{

    @Query("SELECT foto FROM Foto foto WHERE foto.user.email = :email and foto.nombre = :nombre")
    public Foto findFotoByUserAndName(@Param("email") String email, @Param("nombre") String nombre);

    @Query("SELECT foto FROM Foto foto WHERE foto.id = :id")
    public Foto findFotoById(@Param("id") Long id);

    @Query("SELECT foto FROM Foto foto WHERE foto.user.email = :email")
    public List<Foto> findAllFotosByUser(@Param("email") String email);
    
}
