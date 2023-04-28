package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.Valoracion;
import com.example.SpringBootPostgresCRUD.repo.ValoracionRepository;

@Service
public class ValoracionService {

    @Autowired
    ValoracionRepository valoracionRepository;

    public List<Valoracion> getAllValoraciones() {
        List<Valoracion> list = new ArrayList<>();
        valoracionRepository.findAll().forEach(list::add);

        return list;
    }

    public Valoracion getValoracionById(Long id) {
        Optional<Valoracion> optionalVal = valoracionRepository.findById(id);
        if (optionalVal.isPresent()) {
            return optionalVal.get();
        } else {
            throw new NoSuchElementException("No se encontró AnuncioArrendador con id " + id);
        }
    }

    public boolean saveorUpdateValoracion(Valoracion valoracion) {
        Valoracion v = valoracionRepository.save(valoracion);
        boolean res = false;
        if (valoracionRepository.findById(v.getId()).isPresent()) {
            res = true;
        }
        return res;
    }

    public boolean deleteValoracion(Long id) {
        valoracionRepository.deleteById(id);
        boolean res = false;
        if (valoracionRepository.findById(id).isPresent()) {
            res = true;
        }
        return res;
    }

    public List<Valoracion> findByReceiver(String email) {
        return valoracionRepository.findAllValoracionesByReceiver(email);
    }

    public List<Valoracion> findBySender(String email) {
        return valoracionRepository.findAllValoracionesBySender(email);
    }

    public Double findAverageNota(String email) {
        return valoracionRepository.findAverageNotaByReceiver(email);
    }
    
}
