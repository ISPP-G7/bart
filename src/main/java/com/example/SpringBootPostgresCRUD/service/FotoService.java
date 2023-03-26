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

    public List<Foto> getFotosByUser(String user){
        return fotoRepository.findByUser(user);
    }

}
