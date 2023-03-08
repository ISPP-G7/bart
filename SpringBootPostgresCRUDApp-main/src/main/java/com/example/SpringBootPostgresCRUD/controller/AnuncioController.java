package com.example.SpringBootPostgresCRUD.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.SpringBootPostgresCRUD.service.AnuncioService;
import com.example.SpringBootPostgresCRUD.entity.Anuncio;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AnuncioController {

    @Autowired
    AnuncioService anuncioService;

    @GetMapping({ "/viewAnuncios" })
    public String viewArtistas(@ModelAttribute("message") String message, Model model) {
        List<Anuncio> anuList = anuncioService.getAllAnuncios();

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "ViewArtista";
    }

    @GetMapping("/addAnuncio")
    public String newAnuncio(@ModelAttribute("message") String message, Model model) {
        Anuncio anu = new Anuncio();
        model.addAttribute("anu", anu);
        model.addAttribute("message", message);

        return "AddAnuncio";
    }

    @PostMapping("/saveAnuncio")
    public String saveAnuncio(Anuncio anu, RedirectAttributes redirectAttributes) {
        if (anuncioService.saveOrUpdateAnuncio(anu)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/Home";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/Home";
    }

    @PostMapping("/editSaveAnuncio")
    public String editSaveAnuncio(@ModelAttribute("anu") Anuncio anu, RedirectAttributes redirectAttributes) {
        if (anuncioService.saveOrUpdateAnuncio(anu)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewAnuncios";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editAnuncio/" + anu.getId();
    }

    @GetMapping("/deleteAnuncio/{id}")
    public String deleteAnuncio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (anuncioService.deleteAnuncio(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewAnuncios";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnuncios";
    }

}