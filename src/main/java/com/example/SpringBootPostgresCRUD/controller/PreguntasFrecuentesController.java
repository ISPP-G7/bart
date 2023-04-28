package com.example.SpringBootPostgresCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.repo.FotoRepository;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;

@Controller
public class PreguntasFrecuentesController {

    @Autowired
    FotoRepository fotoRepository;
    @Autowired
    UserService userService;
    @Autowired
    ArrendadorService arrendadorService;
    @Autowired
    ArtistaService artistaService;
    String anonymousUser = "anonymousUser";

    @GetMapping("/PreguntasFrecuentes")
    public String PreguntasFrecuentes(Model model) {

        setUserIfLogged(model);
        return "PreguntasFrecuentes";
    }

    // Comprueba si el usuario está logueado y setea los valores correspondientes
    public void setUserIfLogged(Model model) {
        Boolean isLogged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            if (usr.getEsArrendador()) {
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if (usr.getEsArtista()) {
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);
    }

}
