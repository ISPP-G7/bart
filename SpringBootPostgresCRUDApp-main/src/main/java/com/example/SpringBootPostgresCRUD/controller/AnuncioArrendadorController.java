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

import javax.servlet.http.HttpServletRequest;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;
import com.example.SpringBootPostgresCRUD.entity.Arrendador;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AnuncioArrendadorController {

    @Autowired
    AnuncioArrendadorService anuncioArrendadorService;
    @Autowired
    ArrendadorService arrendadorService;

    @GetMapping({ "/viewAnunciosArrendador" })
    public String viewAnunciosArrendador(@ModelAttribute("message") String message, Model model) {
        List<AnuncioArrendador> anuList = anuncioArrendadorService.getAllAnunciosArrendador();

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "ViewAnuncioArrendador";
    }
    @GetMapping("/aceptarAnuncioArrendador/{id}")
    public String aceptarAnuncioArrendador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        AnuncioArrendador anar=  anuncioArrendadorService.getAnuncioArrendadorById(id);
        if (anuncioArrendadorService.aceptarAnuncioArrendador(anar)) {
            redirectAttributes.addFlashAttribute("message", "Accept Success");
            return "redirect:/viewAnunciosArrendadorParaArtistas";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewAnunciosArrendadorParaArtistas";
    }
    @GetMapping({ "/viewAnunciosArrendadorParaArtistas" })
    public String viewAnunciosArrendadorParaArtistas(@ModelAttribute("message") String message, Model model) {
        List<AnuncioArrendador> anuList = anuncioArrendadorService.getAllAnunciosArrendadorNoAceptados();
       

        model.addAttribute("anuList", anuList);
        model.addAttribute("message", message);

        return "viewAnunciosArrendadorParaArtistas";
    }

    @GetMapping("/addAnuncioArrendador")
    public String newAnuncioArrendador(@ModelAttribute("message") String message, Model model) {
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
    public String editArrendador(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        AnuncioArrendador ann = anuncioArrendadorService.getAnuncioArrendadorById(id);

        model.addAttribute("anu", ann);
        model.addAttribute("message", message);

        return "EditAnuncioArrendador";
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