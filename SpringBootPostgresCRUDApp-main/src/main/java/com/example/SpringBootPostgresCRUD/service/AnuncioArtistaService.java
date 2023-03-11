package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;
import com.example.SpringBootPostgresCRUD.repo.AnuncioArtistaRepository;
import com.example.SpringBootPostgresCRUD.repo.ArtistaRepository;

@Service
public class AnuncioArtistaService {

    @Autowired
    private AnuncioArtistaRepository anuncioArtistaRepository;

    @Autowired
    private ArtistaRepository artistaRepository;



    // métodos de servicio que utilizan artistaRepositorys
    // Constructor

    public List<AnuncioArtista> getAllAnunciosArtista() {
        List<AnuncioArtista> AnuncioArtistaList = new ArrayList<>();
        anuncioArtistaRepository.findAll().forEach(AnuncioArtista -> AnuncioArtistaList.add(AnuncioArtista));

        return AnuncioArtistaList;
    }

    public boolean saveOrUpdateAnuncioArtista(AnuncioArtista anuncioArtista,Long artistaIdSeleccionado) {
        anuncioArtista.setArtista(artistaRepository.getById(artistaIdSeleccionado));//Esto hay que hacer que coja el id del artista automáticamente.
        AnuncioArtista anu = anuncioArtistaRepository.save(anuncioArtista);
        if (anuncioArtistaRepository.findById(anu.getId()) != null) {
            return true;
        }
        return false;
    }

    public boolean updateAnuncioArtista(AnuncioArtista anuncioArtista) {
        AnuncioArtista anu = anuncioArtistaRepository.save(anuncioArtista);
        if (anuncioArtistaRepository.findById(anu.getId()) != null) {
            return true;
        }
        return false;
    }

    public AnuncioArtista getAnuncioArtistaById(Long id) {
        return anuncioArtistaRepository.findById(id).get();
    }

    public boolean deleteAnuncioArtista(Long id) {
        anuncioArtistaRepository.deleteById(id);
        if (anuncioArtistaRepository.findById(id) != null) {
            return true;
        }
        return false;
    }

}
