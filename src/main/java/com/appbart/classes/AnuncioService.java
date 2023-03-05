package com.appbart.classes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService
{
    @Autowired
    private AnuncioRepository anuncioRepository;

    // métodos de servicio que utilizan artistaRepository
     // Constructor

     public Anuncio buscarPorTitulo(String titulo) {
        Optional<Anuncio> optionalAnuncio = anuncioRepository.findByTitle(titulo);
        return optionalAnuncio.orElse(null);
    }

    public Anuncio crearAnuncio(Anuncio anuncio) {
        return anuncioRepository.save(anuncio);
    }
    public Anuncio getAnuncioById(Long id) {
        return anuncioRepository.findById(id).orElse(null);
    }

    public Iterable<Anuncio> getAllAnuncios() {
        return  anuncioRepository.findAll();
    }

    public void deleteAnuncioById(Long id) {
        anuncioRepository.deleteById(id);
    }
}
