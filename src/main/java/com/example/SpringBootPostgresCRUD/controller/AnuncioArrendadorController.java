package com.example.SpringBootPostgresCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.SpringBootPostgresCRUD.service.AnuncioArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;

import javax.servlet.http.HttpServletRequest;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;
import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AnuncioArrendadorController {
    @Autowired
    UserService userService;
    @Autowired
    ArtistaService artistaService;
    @Autowired
    AnuncioArrendadorService anuncioArrendadorService;
    @Autowired
    ArrendadorService arrendadorService;

    @GetMapping({ "/viewAnunciosArrendador" })
    public String viewAnunciosArrendador(@ModelAttribute("message") String message, Model model) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("nombreUsuario",email);
        }
        model.addAttribute("isLogged", is_logged);

        List<AnuncioArrendador> anuList = anuncioArrendadorService.getAllAnunciosArrendador();

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "ViewAnuncioArrendador";
    }
    @GetMapping("/aceptarAnuncioArrendador/{id}")
    public String aceptarAnuncioArrendador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
                
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Artista artista = artistaService.getArtistaByMailArtista(email);
        Long artista_accept_id= artista.getId();
        AnuncioArrendador anar=  anuncioArrendadorService.getAnuncioArrendadorById(id);
        if (anuncioArrendadorService.aceptarAnuncioArrendador(anar,artista_accept_id)) {
            redirectAttributes.addFlashAttribute("message", "Accept Success");
            return "redirect:/viewAnunciosArrendadorParaArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnunciosArrendadorParaArtistas";
    }
    @GetMapping({ "/viewAnunciosArrendadorParaArtistas" })
    public String viewAnunciosArrendadorParaArtistas(@ModelAttribute("message") String message, Model model) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("nombreUsuario",email);
        }
        model.addAttribute("isLogged", is_logged);
        List<AnuncioArrendador> anuList = anuncioArrendadorService.getAllAnunciosArrendadorNoAceptados();
       

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "viewAnunciosArrendadorParaArtistas";
    }

    @GetMapping("/addAnuncioArrendador")
    public String newAnuncioArrendador(@ModelAttribute("message") String message, Model model) {
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("nombreUsuario",email);
        }
        model.addAttribute("isLogged", is_logged);
        AnuncioArrendador anu = new AnuncioArrendador();
        anu.setEstaAceptado(false);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
        model.addAttribute("anu", anu);
        model.addAttribute("message", message);
        model.addAttribute("arrendador", arrendador);

        return "AddAnuncioArrendador";
    }

    @PostMapping("/saveAnuncioArrendador")
    public String saveAnuncioArrendador(AnuncioArrendador anu, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
        if (anuncioArrendadorService.saveOrUpdateAnuncioArrendador(anu, arrendador.getId())) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewAnunciosArrendador";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addAnuncioArrendador";

    }

    @GetMapping("/deleteAnuncioArrendador/{id}")
    public String deleteAnuncioArrendador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (anuncioArrendadorService.deleteAnuncioArrendador(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewAnunciosArrendador";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnunciosArrendador";
    }

    @GetMapping("/editAnuncioArrendador/{id}")
    public String editArrendador(@PathVariable Long id, @ModelAttribute("message") String message, Model model,RedirectAttributes redirectAttributes) {
        Long IDaux = 0l;
        Boolean is_logged=false;
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            is_logged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("nombreUsuario",email);
            IDaux=usr.getId();
        }
        AnuncioArrendador ann = anuncioArrendadorService.getAnuncioArrendadorById(id);

        if(IDaux.equals(ann.getArrendador().getId())){

        model.addAttribute("isLogged", is_logged);

        model.addAttribute("anu", ann);
        model.addAttribute("message", message);

        return "EditAnuncioArrendador";
    }else{
        redirectAttributes.addFlashAttribute("message", "No tienes permiso para editar este anuncio.");
        return "redirect:/viewAnunciosArrendador";

    }
}
    @PostMapping("/editSaveAnuncioArrendador")
    public String editSaveAnuncioArrendador(@ModelAttribute("anu") AnuncioArrendador anu,HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
                
                if (anuncioArrendadorService.updateAnuncioArrendador(anu)) {
                    redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewAnunciosArrendador";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editAnuncioArrendador/" + anu.getId();
    }


}