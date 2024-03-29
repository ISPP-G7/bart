package com.example.SpringBootPostgresCRUD.controller;

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
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;

@Controller
public class ArrendadorController {
    @Autowired
    UserService userService;
    @Autowired
    ArrendadorService arrService;
    @Autowired
    ArtistaService artistaService;

    String anonymousUser = "anonymousUser";

    @GetMapping("/SignUpArrendador")
    public String signUpUser(@ModelAttribute("message") String message, Model model) {
        
        setUserIfLogged(model);

        Arrendador arr = new Arrendador();
        arr.setEsArrendador(true);
        model.addAttribute("arr", arr);
        model.addAttribute("message", message);

        return "signUpArrendador";
    }
    @GetMapping({"/viewArrendadores"})
    public String viewArrendadores(@ModelAttribute("message") String message, Model model) {

        String emailLogged =  SecurityContextHolder.getContext().getAuthentication().getName();
        setUserIfLogged(model);

        List<Arrendador> arrList = arrService.getAllArrendadores();

        model.addAttribute("arrList", arrList);
        model.addAttribute("message", message);
        model.addAttribute("emailLogged", emailLogged);

        return "ViewArrendador";
    }

    @GetMapping("/addArrendador")
    public String newArrendador(@ModelAttribute("message") String message, Model model) {

        setUserIfLogged(model);

        Arrendador arrendador = new Arrendador();
        model.addAttribute("arr", arrendador);
        model.addAttribute("message", message);

        return "AddArrendador";
    }

    @PostMapping("/saveArrendador")
    public String saveArrendador(Arrendador arr, RedirectAttributes redirectAttributes) {
        
        if (arrService.saveOrUpdateArrendador(arr)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewArrendadores";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addArrendador";
    }

    @GetMapping("/editArrendador/{id}")
    public String editArrendador(@PathVariable Long id, @ModelAttribute("message") String message, Model model,RedirectAttributes redirectAttributes) {
        Long IDaux=0l;
        Boolean isLogged=false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged=true;
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el arrendador logueado
            model.addAttribute("usuario",usr);
            model.addAttribute("isLogged",  isLogged);
            model.addAttribute("nombreUsuario",email);  
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
                IDaux=arrendador.getId();
            }
        }
        
        if(IDaux.equals(id)){
            Arrendador arr = arrService.getArrendadorById(id);
            model.addAttribute("arr", arr);
            model.addAttribute("message", message);
    
            return "EditArrendador";

        }else{
            redirectAttributes.addFlashAttribute("message", "No tienes permiso para editar este perfil.");
            return "redirect:/viewArrendadores";
    
            
        }
    
       
    }
    @GetMapping("/perfilArrendador/{id}")
    public String perfilArrendador(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {

        setUserIfLogged(model);

        Arrendador arr = arrService.getArrendadorById(id);
        model.addAttribute("arr", arr);
        model.addAttribute("message", message);
        model.addAttribute("nombreLocal",arr.getNombreLocal());

        return "PerfilArrendador";
    }
    @PostMapping("/editSaveArrendador")
    public String editSaveArrendador(@ModelAttribute("arr") Arrendador arr, RedirectAttributes redirectAttributes) {
     
        if (arrService.saveOrUpdateArrendador(arr)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewArrendadores";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editArrendador/" + arr.getId();
    }

    @GetMapping("/deleteArrendador/{id}")
    public String deleteArrendador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
               if (arrService.deleteArrendador(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewArrendadores";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewArrendadores";
    }


    //Comprueba si el usuario está logueado y setea los valores correspondientes
    public void setUserIfLogged(Model model){
        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", is_logged);
    }
}