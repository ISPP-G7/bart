package com.example.SpringBootPostgresCRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;

@Controller
public class ArrendadorController {
    
    @Autowired
    ArrendadorService arrService;

    @GetMapping({"/viewArrendadores"})
    public String viewArrendadores(@ModelAttribute("message") String message, Model model) {
        List<Arrendador> arrList = arrService.getAllArrendadores();

        model.addAttribute("arrList", arrList);
        model.addAttribute("message", message);

        return "ViewArrendador";
    }

    @GetMapping("/addArrendador")
    public String newArrendador(@ModelAttribute("message") String message, Model model) {
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
    public String editArrendador(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Arrendador arr = arrService.getArrendadorById(id);
        model.addAttribute("arr", arr);
        model.addAttribute("message", message);

        return "EditArrendador";
    }
    @GetMapping("/perfilArrendador/{id}")
    public String perfilArrendador(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
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

}