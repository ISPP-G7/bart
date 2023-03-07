package com.example.SpringBootPostgresCRUD.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.Anuncio;
import com.example.SpringBootPostgresCRUD.repo.AnuncioRepository;

@Service
public class AnuncioService {

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
        return anuncioRepository.findAll();
    }

    public void deleteAnuncioById(Long id) {
        anuncioRepository.deleteById(id);
    }

}
