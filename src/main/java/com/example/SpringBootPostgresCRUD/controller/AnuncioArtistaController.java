package com.example.SpringBootPostgresCRUD.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.SpringBootPostgresCRUD.service.AnuncioArtistaService;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;
import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;
import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AnuncioArtistaController {
    @Autowired
    UserService userService;
    @Autowired
    ArrendadorService arrendadorService;
    @Autowired
    AnuncioArtistaService anuncioArtistaService;
    @Autowired
    ArtistaService artistaService;

    String anonymousUser = "anonymousUser";

    // working on arrendador acepta artista
    @GetMapping("/aceptarAnuncioArtista/{id}")
    public String aceptarAnuncioArtista(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
        Long arrendadorAcceptId = arrendador.getId();
        AnuncioArtista anar = anuncioArtistaService.getAnuncioArtistaById(id);
        if (anuncioArtistaService.aceptarAnuncioArtista(anar, arrendadorAcceptId)) {
            redirectAttributes.addFlashAttribute("message", "Accept Success");
            return "redirect:/viewAnunciosArtistaParaArrendadores";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnunciosArtistaParaArrendadores";
    }

    @GetMapping({ "/viewAnunciosArtistaParaArrendadores" })
    public String viewAnunciosArtistaParaArrendadores(@ModelAttribute("message") String message, Model model,
            @Param("palabraClave") String palabraClave) {

        setUserIfLogged(model);

        List<AnuncioArtista> anuList = anuncioArtistaService.getAllAnunciosArrendadorNoAceptadosFiltrados(palabraClave);

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);
        model.addAttribute("palabraClave", palabraClave);

        return "viewAnunciosArtistaParaArrendadores";
    }

    @GetMapping({ "/viewAnunciosArtista" })
    public String viewAnuncioArtista(@ModelAttribute("message") String message, Model model,
            @Param("palabraClave") String palabraClave) {

        setUserIfLogged(model);

        List<AnuncioArtista> anuList = anuncioArtistaService.getAllAnunciosArtistaFiltrados(palabraClave);

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);
        model.addAttribute("palabraClave", palabraClave);

        return "ViewAnuncioArtista";
    }

    @GetMapping("/addAnuncioArtista")
    public String newAnuncioArtista(@ModelAttribute("message") String message, Model model) {

        setUserIfLogged(model);

        AnuncioArtista anu = new AnuncioArtista();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Artista artista = artistaService.getArtistaByMailArtista(email); // Con esto cogemos el artista logueado
        model.addAttribute("artista", artista);
        model.addAttribute("anu", anu);
        model.addAttribute("message", message);
        model.addAttribute("nombreUsuario", email);

        return "AddAnuncioArtista";
    }

    @PostMapping("/saveAnuncioArtista")
    public String saveAnuncioArtista(AnuncioArtista anu, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Artista artista = artistaService.getArtistaByMailArtista(email); // Con esto cogemos el artista logueado
        if (anuncioArtistaService.saveOrUpdateAnuncioArtista(anu, artista.getId())) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewAnunciosArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addAnuncioArtista";
    }

    @GetMapping("/deleteAnuncioArtista/{id}")
    public String deleteAnuncioArtista(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.deleteAnuncioArtista(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewAnunciosArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnunciosArtista";
    }

    @GetMapping("/editAnuncioArtista/{id}")
    public String editArtista(@PathVariable Long id, @ModelAttribute("message") String message, Model model,
            RedirectAttributes redirectAttributes) {
        Long IDaux = 0l;

        Boolean isLogged = false;
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            IDaux = usr.getId();
            if (usr.getEsArrendador()) {
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if (usr.getEsArtista()) {
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        AnuncioArtista ann = anuncioArtistaService.getAnuncioArtistaById(id);

        if (IDaux.equals(ann.getArtista().getId())) {

            model.addAttribute("isLogged", isLogged);

            model.addAttribute("anu", ann);
            model.addAttribute("message", message);

            return "EditAnuncioArtista";
        } else {
            redirectAttributes.addFlashAttribute("message", "No tienes permiso para editar este anuncio.");
            return "redirect:/viewAnunciosArtista";

        }
    }

    @PostMapping("/editSaveAnuncioArtista")
    public String editSaveAnuncioArtista(@ModelAttribute("anu") AnuncioArtista anu,
            RedirectAttributes redirectAttributes) {
        if (anuncioArtistaService.saveOrUpdateAnuncioArtista(anu, anu.getArtista().getId())) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewAnunciosArtista";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editAnunciosArtista/" + anu.getId();
    }

    @GetMapping("/anuncioArtista/{id}")
    public String viewAnuncioArtista(@PathVariable Long id, Model model) {

        setUserIfLogged(model);

        AnuncioArtista anuncio = anuncioArtistaService.getAnuncioArtistaById(id);
        model.addAttribute("anuncio", anuncio);
        return "AnuncioArtistaInfo";
    }

    // Comprueba si el usuario está logueado y setea los valores correspondientes
    public void setUserIfLogged(Model model) {
        Boolean isLogged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            if (usr.getEsArrendador()) {
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if (usr.getEsArtista()) {
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);
    }
}
