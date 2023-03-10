package com.example.SpringBootPostgresCRUD.controller;

import com.example.SpringBootPostgresCRUD.entity.Message;
import com.example.SpringBootPostgresCRUD.service.MessageService;
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
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/SignUpMessage")
    public String signUpUser(@ModelAttribute("message") String message, Model model) {
        Message art = new Message();
        model.addAttribute("art", art);
        model.addAttribute("message", message);

        return "signUpMessage";
    }

    @GetMapping({ "/viewMessages/{email2}" })
    public String viewMessages(@PathVariable("email2") String email2, @ModelAttribute("message") String message,
            Model model) {
        String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Message> mensajes = messageService.findByPreviousChat(email1, email2);
        model.addAttribute("mensajes", mensajes);
        model.addAttribute("message", message);
        return "ViewMessage";
    }

    @GetMapping("/addMessage")
    public String newMessage(@ModelAttribute("message") String message, Model model) {
        Message art = new Message();
        model.addAttribute("art", art);
        model.addAttribute("message", message);

        return "AddMessage";
    }

    @PostMapping("/saveMessage")
    public String saveMessage(Message art, RedirectAttributes redirectAttributes) {
        if (messageService.saveOrUpdateMessage(art)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewMessages";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addMessage";
    }

    @GetMapping("/editMessage/{id}")
    public String editMessage(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Message art = messageService.getMessageById(id);
        model.addAttribute("art", art);
        model.addAttribute("message", message);

        return "EditMessage";
    }

    @GetMapping("/perfilMessage/{id}")
    public String perfilMessage(@PathVariable Long id, @ModelAttribute("message") String message, Model model) {
        Message art = messageService.getMessageById(id);
        model.addAttribute("art", art);
        model.addAttribute("message", message);
        // model.addAttribute("nombreArtistitico", art.getNombre_artistico());
        return "PerfilMessage";
    }

    @PostMapping("/editSaveMessage")
    public String editSaveMessage(@ModelAttribute("art") Message art, RedirectAttributes redirectAttributes) {
        if (messageService.saveOrUpdateMessage(art)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewMessages";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editMessage/" + art.getId();
    }

    @GetMapping("/deleteMessage/{id}")
    public String deleteMessage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (messageService.deleteMessage(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewMessages";
        }

        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewMessages";
    }

}
