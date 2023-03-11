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
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;
import com.example.SpringBootPostgresCRUD.entity.Artista;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AnuncioArtistaController {

    @Autowired
    AnuncioArtistaService anuncioArtistaService;
    @Autowired
    ArtistaService artistaService;

    @GetMapping({ "/viewAnunciosArtista" })
    public String viewAnuncioArtista(@ModelAttribute("message") String message, Model model) {
        List<AnuncioArtista> anuList = anuncioArtistaService.getAllAnunciosArtista();

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "ViewAnuncioArtista";
    }

    @GetMapping("/addAnuncioArtista")
    public String newAnuncioArtista(@ModelAttribute("message") String message, Model model) {
        AnuncioArtista anu = new AnuncioArtista();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Artista artista = artistaService.getArtistaByMailArtista(email); // Con esto cogemos el artista logueado
        model.addAttribute("artista", artista);
        model.addAttribute("anu", anu);
        model.addAttribute("message", message);

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
    public String editArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        AnuncioArtista ann = anuncioArtistaService.getAnuncioArtistaById(id);

        model.addAttribute("anu", ann);
        model.addAttribute("message", message);

        return "EditAnuncioArtista";
    }

    @PostMapping("/editSaveAnuncioArtista")
    public String editSaveAnuncioArtista(@ModelAttribute("anu") AnuncioArtista anu,
            RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.updateAnuncioArtista(anu)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewAnunciosArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editAnunciosArtista/" + anu.getId();
    }

}
