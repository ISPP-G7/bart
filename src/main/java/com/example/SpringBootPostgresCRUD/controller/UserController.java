package com.example.SpringBootPostgresCRUD.controller;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.Foto;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.repo.UserRepository;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.FotoService;
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

import javax.transaction.Transactional;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ArtistaService artistaService;
    @Autowired
    ArrendadorService arrendadorService;
    @Autowired
    FotoService fotoService;

    String anonymousUser = "anonymousUser";

    @GetMapping("/SignUpUser")
    public String signUpUser(@ModelAttribute("message") String message, Model model) {
        setUserIfLogged(model);

        User usr = new User();
        model.addAttribute("usr", usr);
        model.addAttribute("message", message);

        return "signUpUser";
    }
    @GetMapping({"/viewUsers"})
    public String viewUsers(@ModelAttribute("message") String message, Model model) {
        setUserIfLogged(model);

        List<User> userList = userService.getAllUsers();

        model.addAttribute("userList", userList);
        model.addAttribute("message", message);

        return "ViewUser";
    }

    @GetMapping("/addUser")
    public String newUser(@ModelAttribute("message") String message, Model model) {
        setUserIfLogged(model);

        User usr = new User();
        model.addAttribute("usr", usr);
        model.addAttribute("message", message);

        return "AddUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(User usr, RedirectAttributes redirectAttributes) {
        if (userService.saveOrUpdateUser(usr)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewUsers";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addUser";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        setUserIfLogged(model);

        User usr = userService.getUserById(id);
        model.addAttribute("usr", usr);
        model.addAttribute("message", message);

        return "EditUser";
    }

    @PostMapping("/editSaveUser")
    public String editSaveUser(@ModelAttribute("usr") User usr, RedirectAttributes redirectAttributes,Model model) {
        setUserIfLogged(model);

        if (userService.saveOrUpdateUser(usr)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewUsers";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editUser/" + usr.getId();
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
       
        if (userService.deleteUser(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewUsers";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewUsers";
    }

    @GetMapping("/perfilUsuario/{id}")
    @Transactional
    public String perfilUsuario(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        setUserIfLogged(model);

        User user = userService.getUserById(id);
        User valid = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Foto> fotos = fotoService.getFotosByUser(user.getEmail());

        model.addAttribute("user", user);
        model.addAttribute("valid", valid);
        model.addAttribute("fotos", fotos);
        model.addAttribute("message", message);
        if (user.getEsArtista()) {
            Artista art = artistaService.getArtistaById(id);
            model.addAttribute("art", art);
        } else if (user.getEsArrendador()) {
            Arrendador arr = arrendadorService.getArrendadorById(id);
            model.addAttribute("arr", arr);
        }
        return "PerfilUsuario";
    }

    public void setUserIfLogged(Model model){
        Boolean isLogged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", user);
            model.addAttribute("nombreUsuario", email);
            if (user.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if (user.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);
    }

}
