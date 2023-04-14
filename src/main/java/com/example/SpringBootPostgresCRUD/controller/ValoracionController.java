package com.example.SpringBootPostgresCRUD.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.entity.Valoracion;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;
import com.example.SpringBootPostgresCRUD.service.ValoracionService;

@Controller
public class ValoracionController {

    @Autowired
    ValoracionService valoracionService;

    @Autowired
    UserService userService;

    @Autowired
    ArtistaService artistaService;

    @Autowired
    ArrendadorService arrendadorService;

    @GetMapping("/viewAllValoraciones")
    public String viewAllValoraciones(@ModelAttribute("message") String message, Model model) {
        loginCheck(model);

        List<Valoracion> todasValoraciones = valoracionService.getAllValoraciones();
        model.addAttribute("email", todasValoraciones);
        model.addAttribute("message", message);
        return "ViewAllValoraciones";
    }

    @GetMapping("/viewValoracionesRecibidas/{email}")
    public String viewValoracionesRecibidas(@PathVariable("email") String email, @ModelAttribute("message") String message, 
            Model model) {
        loginCheck(model);

        List<Valoracion> valoraciones = valoracionService.findByReceiver(email);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("message", message);
        return "ViewValoracionesRecibidas";
    }

    @GetMapping("/viewValoracionesHechas/{email}")
    public String viewValoracionesHechas(@PathVariable("email") String email, @ModelAttribute("message") String message, 
            Model model) {
        loginCheck(model);

        List<Valoracion> valoraciones = valoracionService.findBySender(email);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("message", message);
        return "ViewValoracionesHechas";
    }

    @GetMapping("/addValoracion/{idReceiver}")
    public String newValoracion(@PathVariable Long idReceiver, @ModelAttribute("message") String message, Model model) {
        String emailReceiver = userService.getUserById(idReceiver).getEmail();
        String nombreReceiver = userService.getUserById(idReceiver).getFirstName();
        model.addAttribute("val", new Valoracion());
        model.addAttribute("emailReceiver", emailReceiver);
        model.addAttribute("nombreReceiver", nombreReceiver);
        return "AddValoracion";
    }

    @PostMapping("/saveValoracion/{emailReceiver}")
    public String saveValoracion(Valoracion val, @PathVariable("emailReceiver") String emailReceiver,
            RedirectAttributes redirectAttributes) {
        
        String emailSender = SecurityContextHolder.getContext().getAuthentication().getName();
        User userSender = userService.getUserByEmail(emailSender);
        User userReceiver = userService.getUserByEmail(emailReceiver);
        Date now = Date.from(Instant.now());
        val.setSender(userSender);
        val.setReceiver(userReceiver);
        val.setFecha(now);

        if (valoracionService.saveorUpdateValoracion(val)) {
            redirectAttributes.addFlashAttribute("valoracion", "Save Success");
        } else {
            redirectAttributes.addFlashAttribute("valoracion", "Save Failure");
        }

        if (val.getReceiver().getEsArtista()) {
            return "redirect:/perfilArtista/" + val.getReceiver().getId();
        } else {
            return "redirect:/perfilArrendador/" + val.getReceiver().getId();
        }
    }

    @GetMapping("deleteValoracion/{id}/{emailReceiver}")
    public String deleteValoracion(@PathVariable Long id, @PathVariable("emailReceiver") String emailReceiver,
            RedirectAttributes redirectAttributes) {
        User receiver = userService.getUserByEmail(emailReceiver);
        String emailSender = SecurityContextHolder.getContext().getAuthentication().getName();
        if (valoracionService.deleteValoracion(id)) {
            redirectAttributes.addFlashAttribute("valoracion", "Delete Success");
            if (receiver.getEsArtista()) {
                return "redirect:/perfilArtista/" + receiver.getId();
            } else {
                return "redirect:/perfilArrendador/" + receiver.getId();
            }
        }
        redirectAttributes.addFlashAttribute("valoracion", "Delete Failure");
        
        if (receiver.getEsArtista()) {
            return "redirect:/perfilArtista/" + receiver.getId();
        } else {
            return "redirect:/perfilArrendador/" + receiver.getId();
        }
    }

    public void loginCheck(Model model) {
        Boolean isLogged = false;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!email.equals("anonymousUser")) {
            isLogged=true;
            User usr = userService.getUserByEmail(email);
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            if (usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if (usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);
    }
    
}
