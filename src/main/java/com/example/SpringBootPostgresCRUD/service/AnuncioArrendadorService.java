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

    public List<AnuncioArrendador> getAllAnunciosArrendadorFiltrados(String palabraClave) {
        List<AnuncioArrendador> AnuncioArrendadorList = new ArrayList<>();
        if (palabraClave != null) {
            anuncioArrendadorRepository.busquedaFiltrada(palabraClave)
                    .forEach(AnuncioArrendador -> AnuncioArrendadorList.add(AnuncioArrendador));
            return anuncioArrendadorRepository.busquedaFiltrada(palabraClave);
        }
        anuncioArrendadorRepository.findAll()
                .forEach(AnuncioArrendador -> AnuncioArrendadorList.add(AnuncioArrendador));
        return AnuncioArrendadorList;
    }

    public List<AnuncioArrendador> getAllAnunciosArrendadorNoAceptados() {
        List<AnuncioArrendador> AnuncioArrendadorList = anuncioArrendadorRepository.findAll();
        List<AnuncioArrendador> AnuncioArrendadorListAux = new ArrayList<>();

        for (AnuncioArrendador anuncioArrendador : AnuncioArrendadorList) {
            if (anuncioArrendador.getEstaAceptado() == false) {
                AnuncioArrendadorListAux.add(anuncioArrendador);
            }
        }

        return AnuncioArrendadorListAux;
    }

    public boolean saveOrUpdateAnuncioArrendador(AnuncioArrendador anuncioArrendador, Long arrendadorIdSeleccionado) {
        anuncioArrendador.setArrendador(arrendadorRepository.getById(arrendadorIdSeleccionado));
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

    public boolean aceptarAnuncioArrendador(AnuncioArrendador anar, Long artista_accept_id) {
        anar.setEstaAceptado(true);
        anar.setArtista_accept_id(artista_accept_id);
        anuncioArrendadorRepository.save(anar);
        if (anuncioArrendadorRepository.findById(anar.getId()) != null) {
            return true;
        }
        return false;
    }

    // Actualizar el estado de la oferta y guardar los cambios en la base de datos

}
