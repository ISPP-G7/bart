package com.appbart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appbart.classes.Arrendador;
import com.appbart.classes.ArrendadorService;

@RestController
@RequestMapping("/arrendadores")
public class ArrendadorController {
    
    @Autowired
    private ArrendadorService arrendadorService;
    
    @PostMapping("/")
    public ResponseEntity<Arrendador> crearArrendador(@RequestBody Arrendador arrendador) {
        Arrendador nuevoArrendador = arrendadorService.crearArrendador(arrendador);
        return new ResponseEntity<>(nuevoArrendador, HttpStatus.CREATED);
    }
    
    @GetMapping("/")
    public ResponseEntity<Iterable<Arrendador>> obtenerTodosLosArrendadores() {
        Iterable<Arrendador> arrendadores = arrendadorService.getAllArrendadors();
        return new ResponseEntity<>(arrendadores, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Arrendador> obtenerArrendadorPorId(@PathVariable("id") Long id) {
        Arrendador arrendador = arrendadorService.getArrendadorById(id);
        return new ResponseEntity<>(arrendador, HttpStatus.OK);
    }
    
/*    @PutMapping("/{id}")
    public ResponseEntity<Arrendador> actualizarArrendador(@PathVariable("id") Long id, @RequestBody Arrendador arrendador) {
        Arrendador arrendadorActualizado = arrendadorService.actualizarArrendador(id, arrendador);
        return new ResponseEntity<>(arrendadorActualizado, HttpStatus.OK);
    }
*/    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArrendador(@PathVariable("id") Long id) {
        arrendadorService.deleteArrendadorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}