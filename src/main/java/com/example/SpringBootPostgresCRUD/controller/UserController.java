package com.example.SpringBootPostgresCRUD.controller;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.repo.UserRepository;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
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
public class UserController {

    @Autowired
    UserService usrService;
    @Autowired
    UserRepository usrRepo;
    @Autowired
    ArtistaService artistaService;
    @Autowired
    ArrendadorService arrendadorService;

    @GetMapping("/SignUpUser")
    public String signUpUser(@ModelAttribute("message") String message, Model model) {
        Boolean isLogged=false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = usrService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            isLogged=true;
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);

        User usr = new User();
        model.addAttribute("usr", usr);
        model.addAttribute("message", message);

        return "signUpUser";
    }
    @GetMapping({"/viewUsers"})
    public String viewUsers(@ModelAttribute("message") String message, Model model) {
        Boolean isLogged=false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = usrService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("nombreUsuario",email);
            isLogged=true;
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);

        List<User> usrList = usrService.getAllUsers();

        model.addAttribute("usrList", usrList);
        model.addAttribute("message", message);

        return "ViewUser";
    }

    @GetMapping("/addUser")
    public String newUser(@ModelAttribute("message") String message, Model model) {
        Boolean isLogged=false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            isLogged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = usrService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("nombreUsuario",email);
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);

        User usr = new User();
        model.addAttribute("usr", usr);
        model.addAttribute("message", message);

        return "AddUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(User usr, RedirectAttributes redirectAttributes) {
        if (usrService.saveOrUpdateUser(usr)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewUsers";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addUser";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Boolean isLogged=false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = usrService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            isLogged=true;
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);

        User usr = usrService.getUserById(id);
        model.addAttribute("usr", usr);
        model.addAttribute("message", message);

        return "EditUser";
    }

    @PostMapping("/editSaveUser")
    public String editSaveUser(@ModelAttribute("usr") User usr, RedirectAttributes redirectAttributes,Model model) {
        Boolean isLogged=false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User user = usrService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",user);
            isLogged=true;
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);

        if (usrService.saveOrUpdateUser(usr)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewUsers";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editUser/" + usr.getId();
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
       
        if (usrService.deleteUser(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewUsers";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewUsers";
    }

}
