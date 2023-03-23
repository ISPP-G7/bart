package com.example.SpringBootPostgresCRUD.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.SpringBootPostgresCRUD.service.AnuncioArtistaService;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;
import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;
import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AnuncioArtistaController {
    @Autowired
    UserService userService;
    @Autowired
    ArrendadorService arrendadorService;
    @Autowired
    AnuncioArtistaService anuncioArtistaService;
    @Autowired
    ArtistaService artistaService;

    // working on arrendador acepta artista
    @GetMapping("/aceptarAnuncioArtista/{id}")
    public String aceptarAnuncioArtista(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
        Long arrendador_accept_id = arrendador.getId();
        AnuncioArtista anar = anuncioArtistaService.getAnuncioArtistaById(id);

        Boolean is_logged = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged = true;
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);

        model.addAttribute("arrendador_id", arrendador_accept_id);
        model.addAttribute("anuncio", anar);
        return "Transaccion";
    }

    @GetMapping({ "/viewAnunciosArtistaParaArrendadores" })
    public String viewAnunciosArtistaParaArrendadores(@ModelAttribute("message") String message, Model model) {
        Boolean is_logged = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);
        List<AnuncioArtista> anuList = anuncioArtistaService.getAllAnunciosArrendadorNoAceptados();

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "viewAnunciosArtistaParaArrendadores";
    }

    @GetMapping({ "/viewAnunciosArtista" })
    public String viewAnuncioArtista(@ModelAttribute("message") String message, Model model) {
        Boolean is_logged = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);
        List<AnuncioArtista> anuList = anuncioArtistaService.getAllAnunciosArtista();

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "ViewAnuncioArtista";
    }

    @GetMapping("/addAnuncioArtista")
    public String newAnuncioArtista(@ModelAttribute("message") String message, Model model) {
        Boolean is_logged = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
        }
        model.addAttribute("isLogged", is_logged);
        AnuncioArtista anu = new AnuncioArtista();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Artista artista = artistaService.getArtistaByMailArtista(email); // Con esto cogemos el artista logueado
        model.addAttribute("artista", artista);
        model.addAttribute("anu", anu);
        model.addAttribute("message", message);
        model.addAttribute("nombreUsuario", email);

        return "AddAnuncioArtista";
    }

    @PostMapping("/saveAnuncioArtista")
    public String saveAnuncioArtista(AnuncioArtista anu, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Artista artista = artistaService.getArtistaByMailArtista(email); // Con esto cogemos el artista logueado
        if (anuncioArtistaService.saveOrUpdateAnuncioArtista(anu, artista.getId())) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewAnunciosArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addAnuncioArtista";
    }

    @GetMapping("/deleteAnuncioArtista/{id}")
    public String deleteAnuncioArtista(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.deleteAnuncioArtista(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewAnunciosArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnunciosArtista";
    }

    @GetMapping("/editAnuncioArtista/{id}")
    public String editArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model,
            RedirectAttributes redirectAttributes) {
        Long IDaux = 0l;

        Boolean is_logged = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            IDaux = usr.getId();

        }
        AnuncioArtista ann = anuncioArtistaService.getAnuncioArtistaById(id);

        if (IDaux.equals(ann.getArtista().getId())) {

            model.addAttribute("isLogged", is_logged);

            model.addAttribute("anu", ann);
            model.addAttribute("message", message);

            return "EditAnuncioArtista";
        } else {
            redirectAttributes.addFlashAttribute("message", "No tienes permiso para editar este anuncio.");
            return "redirect:/viewAnunciosArtista";

        }
    }

    @PostMapping("/editSaveAnuncioArtista")
    public String editSaveAnuncioArtista(@ModelAttribute("anu") AnuncioArtista anu,
            RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.saveOrUpdateAnuncioArtista(anu, anu.getArtista().getId())) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewAnunciosArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editAnunciosArtista/" + anu.getId();
    }

    @GetMapping("/anuncioArtista/{id}")
    public String viewAnuncioArtista(@PathVariable Long id, Model model) {
        Boolean is_logged = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
        }
        AnuncioArtista anuncio = anuncioArtistaService.getAnuncioArtistaById(id);
        model.addAttribute("isLogged", is_logged);
        model.addAttribute("anuncio", anuncio);
        return "AnuncioArtistaInfo";
    }

}
