package com.example.SpringBootPostgresCRUD.service;

import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.repo.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArtistaService {

    @Autowired
    ArtistaRepository artistaRepository;

    public List<Artista> getAllArtistas() {
        List<Artista> ArtistaList = new ArrayList<>();
        artistaRepository.findAll().forEach(Artista -> ArtistaList.add(Artista));

        return ArtistaList;
    }

    public Artista getArtistaById(Long id) {
        Optional<Artista> optionalArtista = artistaRepository.findById(id);
        if(optionalArtista.isPresent()){
            return optionalArtista.get();
        }else{
            throw new NoSuchElementException("No se encontró AnuncioArrendador con id " + id);
        }
    }
    public Artista getArtistaByMailArtista(String mail){
        return artistaRepository.getArtistaByMailArtista(mail);
    }
  

    public boolean saveOrUpdateArtista(Artista artista) {
        artista.setEsArtista(true);
        artista.setEsArrendador(false);
        Artista ars = artistaRepository.save(artista);
        boolean res = false;
        if (artistaRepository.findById(ars.getId()).isPresent()) {
            res=true;
        }
        return res;
    }

    public boolean deleteArtista(Long id) {
        artistaRepository.deleteById(id);
        boolean res = false;
        if (artistaRepository.findById(id).isPresent()) {
            res=true;
        }
        return res;
    }

}
