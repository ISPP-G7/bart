package com.example.SpringBootPostgresCRUD.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.SpringBootPostgresCRUD.service.AnuncioArrendadorService;
import com.example.SpringBootPostgresCRUD.service.AnuncioArtistaService;
import com.example.SpringBootPostgresCRUD.entity.Anuncio;
import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;
import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AnuncioArtistaController {

    @Autowired
    AnuncioArtistaService anuncioArtistaService;

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
        model.addAttribute("anu", anu);
        model.addAttribute("message", message);

        return "AddAnuncioArtista";
    }

    @PostMapping("/saveAnuncioArtista")
    public String saveAnuncioArtista(AnuncioArtista anu, RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.saveOrUpdateAnuncioArtista(anu)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/Home";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/Home";
    }

    @PostMapping("/editSaveAnuncioArtista")
    public String editSaveAnuncioArtista(@ModelAttribute("anu") AnuncioArtista anu,
            RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.saveOrUpdateAnuncioArtista(anu)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewAnuncioArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editAnuncioArtista/" + anu.getId();
    }

    @GetMapping("/deleteAnuncioArtista/{id}")
    public String deleteAnuncioArtista(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.deleteAnuncioArtista(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewAnuncioArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnuncioArtista";
    }
}
