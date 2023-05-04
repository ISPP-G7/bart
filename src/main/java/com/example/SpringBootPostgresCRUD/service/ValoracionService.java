package com.example.SpringBootPostgresCRUD.service;

import java.util.ArrayList;
import java.util.List;

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
        return valoracionRepository.findById(id).get();
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
        if (valoracionRepository.findById(id).isPresent()) {
            return true;
        }
        return false;
    }

    public List<Valoracion> findByReceiver(String email) {
        return valoracionRepository.findAllValoracionesByReceiver(email);
    }

    public List<Valoracion> findBySender(String email) {
        return valoracionRepository.findAllValoracionesBySender(email);
    }

    public Valoracion findBySenderAndReceiver(String emailSender, String emailReceiver) {
        List<Valoracion> list = valoracionRepository.findAllValoracionesBySenderAndReceiver(emailSender, emailReceiver);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Boolean validacionValoracionRepetida(String emailSender, String emailReceiver) {
        return valoracionRepository.findAllValoracionesBySenderAndReceiver(emailSender, emailReceiver).size() > 0;
    }

    public Double findAverageNota(String email) {
        return valoracionRepository.findAverageNotaByReceiver(email);
    }
    
}
