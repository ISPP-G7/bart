package com.appbart.classes;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArrendadorRepository extends CrudRepository<Arrendador, Long> {
    Optional<Arrendador> findByUsername(String userName);

}