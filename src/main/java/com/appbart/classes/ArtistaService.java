package com.appbart.classes;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class ArtistaService {
    @Autowired
    private ArtistaRepository artistaRepository;

    // métodos de servicio que utilizan artistaRepository
     // Constructor

     public Artista buscarPorUsername(String username) {
        Optional<Artista> optionalArtista = artistaRepository.findByUsername(username);
        return optionalArtista.orElse(null);
    }
    //find all heredados de los repo
  
    public Artista crearArtista(Artista artista) {
    
        return artistaRepository.save(artista);
    }
    
    public Artista getArtistaById(Long id) {
        return artistaRepository.findById(id).orElse(null);
    }

    public Iterable<Artista> getAllArtistas() {
        return artistaRepository.findAll();
    }

    public void deleteArtistaById(Long id) {
        artistaRepository.deleteById(id);
    }
}


