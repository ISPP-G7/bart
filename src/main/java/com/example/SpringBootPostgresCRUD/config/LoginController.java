package com.example.SpringBootPostgresCRUD.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArrendadorService arrService;
    @Autowired
    private ArtistaService artService;
    @RequestMapping("/login")
    public String login(Model model) {
        Boolean isLogged=false;
                if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                    isLogged=true;
                    String email=SecurityContextHolder.getContext().getAuthentication().getName();
                    User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
                    model.addAttribute("usuario",usr);
                    model.addAttribute("nombreUsuario",email);
                    if(usr.getEsArrendador()){
                        Arrendador arrendador = arrService.getArrendadorByMailArrendador(email);
                        model.addAttribute("arrendador", arrendador);
                    } else if(usr.getEsArtista()){
                        Artista artista = artService.getArtistaByMailArtista(email);
                        model.addAttribute("artista", artista);
                    }
                }
                model.addAttribute("isLogged", isLogged);
        return "login";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
