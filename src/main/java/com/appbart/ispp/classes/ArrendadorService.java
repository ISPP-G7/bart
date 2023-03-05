package com.appbart.ispp.classes;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class ArrendadorService {
    private ArrendadorRepository arrendadorRepository;

    // métodos de servicio que utilizan arrendadorRepository
      // Constructor
      
      public Arrendador buscarPorUsername(String username) {
        Optional<Arrendador> optionalArrendador = arrendadorRepository.findByUsername(username);
        return optionalArrendador.orElse(null);
    }
    //find all heredados de los repo
  
    public Arrendador crearArrendador(Arrendador arrendador) {
    
        return arrendadorRepository.save(arrendador);
    }
    
    public Arrendador getArrendadorById(Long id) {
        return arrendadorRepository.findById(id).orElse(null);
    }

    public Iterable<Arrendador> getAllArrendadors() {
        return arrendadorRepository.findAll();
    }

    public void deleteArrendadorById(Long id) {
        arrendadorRepository.deleteById(id);
    }
}


