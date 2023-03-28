package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.repo.ArrendadorRepository;

@Service
public class ArrendadorService {

    @Autowired
    ArrendadorRepository arrRepository;

    public List<Arrendador> getAllArrendadores() {
        List<Arrendador> arrList = new ArrayList<>();
        arrRepository.findAll().forEach(Arrendador -> arrList.add(Arrendador));

        return arrList;
    }

    public Arrendador getArrendadorById(Long id) {
        return arrRepository.findById(id).get();//aquí habría que comprobar que no es nulo antes de pasarlo, si es nulo pasar excepción. TODO
    }

    public Arrendador getArrendadorByMailArrendador(String mail) {
        return arrRepository.getArrendadorByMailArrendador(mail);
    }

    public boolean saveOrUpdateArrendador(Arrendador arrendador) {
        arrendador.setEsArrendador(true);
        arrendador.setEsArtista(false);
        Arrendador arr = arrRepository.save(arrendador);
        if (arrRepository.findById(arr.getId()).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean deleteArrendador(Long id) {
        arrRepository.deleteById(id);
        if (arrRepository.findById(id).isPresent()) {
            return true;
        }
        return false;
    }

}
