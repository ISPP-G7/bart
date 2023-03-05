package com.appbart.ispp.classes;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
public interface ArtistaRepository extends CrudRepository<Artista, Long> {
    Optional<Artista> findByUsername(String nombreUsuario);

}