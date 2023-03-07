package com.example.SpringBootPostgresCRUD.controller;

import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.repo.ArtistaRepository;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ArtistaController {

    @Autowired
    ArtistaService artService;

    @GetMapping({"/viewArtistas"})
    public String viewArtistas(@ModelAttribute("message") String message, Model model) {
        List<Artista> artList = artService.getAllArtistas();

        model.addAttribute("artList", artList);
        model.addAttribute("message", message);

        return "ViewArtista";
    }

    @GetMapping("/addArtista")
    public String newArtista(@ModelAttribute("message") String message, Model model) {
        Artista art = new Artista();
        model.addAttribute("art", art);
        model.addAttribute("message", message);

        return "AddArtista";
    }

    @PostMapping("/saveArtista")
    public String saveArtista(Artista art, RedirectAttributes redirectAttributes) {
        if (artService.saveOrUpdateArtista(art)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addArtista";
    }

    @GetMapping("/editArtista/{id}")
    public String editArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Artista art = artService.getArtistaById(id);
        model.addAttribute("art", art);
        model.addAttribute("message", message);

        return "EditArtista";
    }

    @PostMapping("/editSaveArtista")
    public String editSaveArtista(@ModelAttribute("art") Artista art, RedirectAttributes redirectAttributes) {
        if (artService.saveOrUpdateArtista(art)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editArtista/" + art.getId();
    }

    @GetMapping("/deleteArtista/{id}")
    public String deleteArtista(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (artService.deleteArtista(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewArtistas";
    }

}
