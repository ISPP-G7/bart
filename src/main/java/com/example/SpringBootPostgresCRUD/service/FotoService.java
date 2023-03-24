package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.Foto;
import com.example.SpringBootPostgresCRUD.repo.FotoRepository;

@Service
public class FotoService {
    
    @Autowired
    FotoRepository fotoRepository;

    public List<Foto> getAllFotos() {
        List<Foto> fotoList = new ArrayList<>();
        fotoRepository.findAll().forEach(fotoList::add);

        return fotoList;
    }

    public Foto getFotoById(Long id) {
        return fotoRepository.findFotoById(id);
    }

    public boolean saveOrUpdateFoto(Foto foto) {
        Foto foto1 = fotoRepository.save(foto);
        boolean res = false;
        if (fotoRepository.findById(foto1.getId()).isPresent()) {
            res = true;
        }
        return res;
    }

    public boolean deleteFoto(Long id) {
        fotoRepository.deleteById(id);
        boolean res = false;
        if (fotoRepository.findById(id).isPresent()) {
            res = true;
        }
        return res;
    }

}
