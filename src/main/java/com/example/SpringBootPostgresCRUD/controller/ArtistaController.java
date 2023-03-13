package com.example.SpringBootPostgresCRUD.controller;

import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ArtistaController {
    @Autowired
    UserService userService;
    @Autowired
    ArtistaService artService;

    @GetMapping("/SignUpArtista")
    public String signUpUser(@ModelAttribute("message") String message, Model model) {
        Artista art = new Artista();
       
        model.addAttribute("art", art);
        model.addAttribute("message", message);
        model.addAttribute("isLogged", false);
        return "signUpArtista";
    }

    @GetMapping({ "/viewArtistas" })
    public String viewArtistas(@ModelAttribute("message") String message, Model model) {
        List<Artista> artList = artService.getAllArtistas();
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("nombreUsuario",email);
            is_logged=true;
        }
        model.addAttribute("isLogged", is_logged);
        model.addAttribute("artList", artList);
        model.addAttribute("message", message);
        return "ViewArtista";
    }

    @GetMapping("/addArtista")
    public String newArtista(@ModelAttribute("message") String message, Model model) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
        }
        Artista art = new Artista();
        model.addAttribute("art", art);
        model.addAttribute("message", message);

        return "AddArtista";
    }

    @PostMapping("/saveArtista")
    public String saveArtista(Artista art, RedirectAttributes redirectAttributes) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
        }
        
        if (artService.saveOrUpdateArtista(art)) {

            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addArtista";
    }

    @GetMapping("/editArtista/{id}")
    public String editArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model,RedirectAttributes redirectAttributes) {
        Long IDaux=0l;
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("isLogged",  is_logged);
            model.addAttribute("nombreUsuario",email);
            IDaux=usr.getId();

        }
        if(IDaux.equals(id)){
        Artista art = artService.getArtistaById(id);
        model.addAttribute("art", art);
        model.addAttribute("message", message);

        return "EditArtista";
    }else{
        redirectAttributes.addFlashAttribute("message", "No tienes permiso para editar este perfil.");
        return "redirect:/viewArtistas";

        
    }
    }
    @GetMapping("/perfilArtista/{id}")
    public String perfilArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
        }
        Artista art = artService.getArtistaById(id);
        model.addAttribute("art", art);
        model.addAttribute("message", message);
        model.addAttribute("nombreArtistitico", art.getNombre_artistico());
        return "PerfilArtista";
    }

    @PostMapping("/editSaveArtista")
    public String editSaveArtista(@ModelAttribute("art") Artista art, RedirectAttributes redirectAttributes) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
        }
        if (artService.saveOrUpdateArtista(art)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editArtista/" + art.getId();
    }

    @GetMapping("/deleteArtista/{id}")
    public String deleteArtista(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
        }
        if (artService.deleteArtista(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewArtistas";
    }

}
