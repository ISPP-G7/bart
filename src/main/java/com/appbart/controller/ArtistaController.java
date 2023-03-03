package com.appbart.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appbart.classes.Artista;
import com.appbart.classes.ArtistaService;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {
    
    @Autowired
    private ArtistaService artistaService;
    
    @PostMapping("/")
    public ResponseEntity<Artista> crearArtista(@RequestBody Artista artista) {
        Artista nuevoArtista = artistaService.crearArtista(artista);
        return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED);
    }
    
    @GetMapping("/")
    public ResponseEntity<Iterable<Artista>> obtenerTodosLosArtistas() {
        Iterable<Artista> artistas = artistaService.getAllArtistas();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Artista> obtenerArtistaPorId(@PathVariable("id") Long id) {
        Artista artista = artistaService.getArtistaById(id);
        return new ResponseEntity<>(artista, HttpStatus.OK);
    }
    
/*  @PutMapping("/{id}")
    public ResponseEntity<Artista> actualizarArtista(@PathVariable("id") Long id, @RequestBody Artista artista) {
        Artista artistaActualizado = artistaService.actualizarArtista(id, artista);
        return new ResponseEntity<>(artistaActualizado, HttpStatus.OK);
    }
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArtista(@PathVariable("id") Long id) {
        artistaService.deleteArtistaById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}