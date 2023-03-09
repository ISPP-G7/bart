package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;
import com.example.SpringBootPostgresCRUD.repo.AnuncioArrendadorRepository;
import com.example.SpringBootPostgresCRUD.repo.ArrendadorRepository;

@Service
public class AnuncioArrendadorService {

    @Autowired
    private AnuncioArrendadorRepository anuncioArrendadorRepository;

    @Autowired
    private ArrendadorRepository arrendadorRepository;

    public List<AnuncioArrendador> getAllAnunciosArrendador() {
        List<AnuncioArrendador> AnuncioArrendadorList = new ArrayList<>();
        anuncioArrendadorRepository.findAll()
                .forEach(AnuncioArrendador -> AnuncioArrendadorList.add(AnuncioArrendador));

        return AnuncioArrendadorList;
    }

    public boolean saveOrUpdateAnuncioArrendador(AnuncioArrendador anuncioArrendador) {
        anuncioArrendador.setArrendador_id(arrendadorRepository.getById(3L));// Esto hay que hacer que coja el id del
                                                                             // artista automáticamente.
        AnuncioArrendador anu = anuncioArrendadorRepository.save(anuncioArrendador);
        if (anuncioArrendadorRepository.findById(anu.getId()) != null) {
            return true;
        }
        return false;
    }

    public AnuncioArrendador getAnuncioArrendadorById(Long id) {
        return anuncioArrendadorRepository.findById(id).get();
    }

    public boolean deleteAnuncioArrendador(Long id) {
        anuncioArrendadorRepository.deleteById(id);
        if (anuncioArrendadorRepository.findById(id) != null) {
            return true;
        }
        return false;
    }

}
