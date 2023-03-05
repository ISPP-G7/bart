package com.appbart.ispp.classes;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/* */
@Component
public interface ArrendadorRepository extends CrudRepository<Arrendador, Long> {
    Optional<Arrendador> findByUsername(String userName);

}