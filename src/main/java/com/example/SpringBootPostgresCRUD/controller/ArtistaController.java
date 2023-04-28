package com.example.SpringBootPostgresCRUD.controller;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.Foto;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.FotoService;
import com.example.SpringBootPostgresCRUD.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    ArrendadorService arrendadorService;
    @Autowired
    FotoService fotoService;

    String anonymousUser = "anonymousUser";

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
        
        setUserIfLogged(model);

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
    public String editArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model,RedirectAttributes redirectAttributes) {
        Long IDaux=0l;
        Boolean isLogged=false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("isLogged",  isLogged);
            model.addAttribute("nombreUsuario",email);
            if(usr.getEsArtista()){
                Artista artista = artService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
                IDaux=artista.getId();
            }

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
    @Transactional
    public String perfilArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        
        setUserIfLogged(model);

        Artista art = artService.getArtistaById(id);
        List<Foto> fotos= fotoService.getFotosByUser(art.getEmail());
        model.addAttribute("fotos", fotos);
        model.addAttribute("art", art);
        model.addAttribute("message", message);
        model.addAttribute("nombreArtistitico", art.getNombre_artistico());
        return "PerfilArtista";
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

    //Comprueba si el usuario está logueado y setea los valores correspondientes
    public void setUserIfLogged(Model model){
        Boolean isLogged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);
    }
}
