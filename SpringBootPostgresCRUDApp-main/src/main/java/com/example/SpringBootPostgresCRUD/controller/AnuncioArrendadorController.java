package com.example.SpringBootPostgresCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/addAnuncioArrendador")
    public String newAnuncioArrendador(@ModelAttribute("message") String message, Model model) {
        AnuncioArrendador anu = new AnuncioArrendador();
        List<Arrendador> arrList = arrendadorService.getAllArrendadores();

        model.addAttribute("anu", anu);
        model.addAttribute("message", message);
        model.addAttribute("arrendadoresDisponibles",arrList);

        return "AddAnuncioArrendador";
    }

    @PostMapping("/saveAnuncioArrendador")
    public String saveAnuncioArrendador(AnuncioArrendador anu, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        if (anuncioArrendadorService.saveOrUpdateAnuncioArrendador(anu,Long.parseLong(request.getParameter("arrendadores")))) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/Home";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/Home";
    }

    @PostMapping("/editSaveAnuncioArrendador")
    public String editSaveAnuncioArrendador(@ModelAttribute("anu") AnuncioArrendador anu,HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
                if (anuncioArrendadorService.saveOrUpdateAnuncioArrendador(anu,Long.parseLong(request.getParameter("arrendadores")))) {
                    redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewAnuncioArrendadors";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editAnuncioArrendador/" + anu.getId();
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

}