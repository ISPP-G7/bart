package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;
import com.example.SpringBootPostgresCRUD.repo.AnuncioArrendadorRepository;

@Service
public class AnuncioArrendadorService {

    @Autowired
    private AnuncioArrendadorRepository anuncioArrendadorRepository;

    public List<AnuncioArrendador> getAllAnunciosArrendador() {
        List<AnuncioArrendador> AnuncioArrendadorList = new ArrayList<>();
        anuncioArrendadorRepository.findAll()
                .forEach(AnuncioArrendador -> AnuncioArrendadorList.add(AnuncioArrendador));

        return AnuncioArrendadorList;
    }

    public boolean saveOrUpdateAnuncioArrendador(AnuncioArrendador anuncioArrendador) {
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
