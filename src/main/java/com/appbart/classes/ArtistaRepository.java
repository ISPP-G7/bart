package com.appbart.classes;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ArtistaRepository extends CrudRepository<Artista, Long> {
    Optional<Artista> findByUsername(String nombreUsuario);

}