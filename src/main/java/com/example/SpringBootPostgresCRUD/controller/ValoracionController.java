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
        setUserIfLogged(model);

        List<Valoracion> todasValoraciones = valoracionService.getAllValoraciones();
        model.addAttribute("email", todasValoraciones);
        model.addAttribute("message", message);
        return "ViewAllValoraciones";
    }

    @GetMapping("/viewValoracionesRecibidas/{idReceiver}")
    public String viewValoracionesRecibidas(@PathVariable Long idReceiver, @ModelAttribute("message") String message, 
            Model model) {
        setUserIfLogged(model);

        User receiver = userService.getUserById(idReceiver);
        User sender = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Valoracion> valoraciones = valoracionService.findByReceiver(receiver.getEmail());
        Valoracion tuValoracion = valoracionService.findBySenderAndReceiver(sender.getEmail(), receiver.getEmail());

        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("tuValoracion", tuValoracion);
        model.addAttribute("receiver", receiver);
        model.addAttribute("message", message);
        return "ViewValoracionesRecibidas";
    }

    @GetMapping("/viewValoracionesHechas/{idSender}")
    public String viewValoracionesHechas(@PathVariable Long idSender, @ModelAttribute("message") String message, 
            Model model) {
        setUserIfLogged(model);

        User sender = userService.getUserById(idSender);
        User login = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Valoracion> valoraciones = valoracionService.findBySender(sender.getEmail());

        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("sender", sender);
        model.addAttribute("login", login);
        model.addAttribute("message", message);
        return "ViewValoracionesHechas";
    }

    @GetMapping("/addValoracion/{idReceiver}")
    public String newValoracion(@PathVariable Long idReceiver, @ModelAttribute("message") String message, Model model) {
        User receiver = userService.getUserById(idReceiver);
        User sender = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Boolean validRepetido = valoracionService.validacionValoracionRepetida(sender.getEmail(), receiver.getEmail());

        model.addAttribute("val", new Valoracion());
        model.addAttribute("receiver", receiver);
        model.addAttribute("sender", sender);
        model.addAttribute("validRepetido", validRepetido);

        return "AddValoracion";
    }

    @PostMapping("/saveValoracion/{emailReceiver}")
    public String saveValoracion(Valoracion val, @PathVariable("emailReceiver") String emailReceiver,
            RedirectAttributes redirectAttributes) {
        User sender = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        User receiver = userService.getUserByEmail(emailReceiver);
        Date now = Date.from(Instant.now());
        val.setSender(sender);
        val.setReceiver(receiver);
        val.setFecha(now);

        if (valoracionService.saveorUpdateValoracion(val)) {
            redirectAttributes.addFlashAttribute("valoracion", "Save Success");
        } else {
            redirectAttributes.addFlashAttribute("valoracion", "Save Failure");
        }

        return "redirect:/perfilUsuario/" + val.getReceiver().getId();
    }

    @PostMapping("deleteValoracion/{id}")
    public String deleteValoracion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User receiver = valoracionService.getValoracionById(id).getReceiver();

        if (valoracionService.deleteValoracion(id)) {
            redirectAttributes.addFlashAttribute("valoracion", "Delete Success");
            return "redirect:/perfilUsuario/" + receiver.getId();
        }

        redirectAttributes.addFlashAttribute("valoracion", "Delete Failure");
        return "redirect:/perfilUsuario/" + receiver.getId();
    }

    //Comprueba si el usuario está logueado y setea los valores correspondientes
    public void setUserIfLogged(Model model){
        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", is_logged);
    }
    
}
