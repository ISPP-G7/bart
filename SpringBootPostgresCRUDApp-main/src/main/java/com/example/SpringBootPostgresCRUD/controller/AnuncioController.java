package com.example.SpringBootPostgresCRUD.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringBootPostgresCRUD.service.AnuncioService;
import com.example.SpringBootPostgresCRUD.entity.Anuncio;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping(value = "/anuncios")
public class AnuncioController {

    private static final String VISTA_CREAR_ANUNCIO_FORMULARIO = "jsp/CrearAnuncioFormulario";
    private final AnuncioService AnuncioService;

    public AnuncioController(AnuncioService anuncioService) {
        this.AnuncioService = anuncioService;
    }

    @GetMapping(value = "/nuevo")
    public String initCreationForm(Map<String, Object> model) {
        Anuncio anuncio = new Anuncio(null, null, null, null, null, null);
        model.put("anuncio", anuncio);

        // TODO
        return VISTA_CREAR_ANUNCIO_FORMULARIO;
    }

    @PostMapping(value = "/anuncio/nuevo")
    public String processCreationForm(@Valid Anuncio anuncio, BindingResult result) {
        // TODO
        if (result.hasErrors()) {
            return VISTA_CREAR_ANUNCIO_FORMULARIO;
        } else {
            // creating owner, user, and authority
            this.AnuncioService.crearAnuncio(anuncio);
            return "redirect:/";
        }
    }

}