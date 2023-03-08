package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.Anuncio;
import com.example.SpringBootPostgresCRUD.repo.AnuncioRepository;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    // métodos de servicio que utilizan artistaRepositorys
    // Constructor

    public List<Anuncio> getAllAnuncios() {
        List<Anuncio> AnuncioList = new ArrayList<>();
        anuncioRepository.findAll().forEach(Anuncio -> AnuncioList.add(Anuncio));

        return AnuncioList;
    }

    public boolean saveOrUpdateAnuncio(Anuncio anuncio) {
        Anuncio anu = anuncioRepository.save(anuncio);
        if (anuncioRepository.findById(anu.getId()) != null) {
            return true;
        }
        return false;
    }

    public Anuncio getAnuncioById(Long id) {
        return anuncioRepository.findById(id).get();
    }

    public boolean deleteAnuncio(Long id) {
        anuncioRepository.deleteById(id);
        if (anuncioRepository.findById(id) != null) {
            return true;
        }
        return false;
    }
}
