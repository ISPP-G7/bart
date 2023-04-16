package com.example.SpringBootPostgresCRUD.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringBootPostgresCRUD.entity.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long>{

    
    @Query("SELECT v FROM Valoracion v WHERE v.receiver.email= :email ORDER BY v.fecha")
    public List<Valoracion> findAllValoracionesByReceiver(@Param("email") String email);

    @Query("SELECT v FROM Valoracion v WHERE v.sender.email= :email ORDER BY v.fecha")
    public List<Valoracion> findAllValoracionesBySender(@Param("email") String email);

    /* 
    @Query("SELECT v FROM Valoracion v WHERE v.sender.email= :email1 and v.receiver.email= :email2 ORDER BY v.fecha LIMIT 1")
    public Valoracion findValoracionBySenderAndReceiver(@Param("email1") String email1,
                @Param("email2") String email2);
    */
    @Query("SELECT AVG(CAST(v.nota as double)) from Valoracion v WHERE v.receiver.email= :email")
    public Double findAverageNotaByReceiver(@Param("email") String email);
    
}
