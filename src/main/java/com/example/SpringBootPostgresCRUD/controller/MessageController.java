package com.example.SpringBootPostgresCRUD.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.SpringBootPostgresCRUD.entity.Message;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.MessageService;
import com.example.SpringBootPostgresCRUD.service.UserService;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @GetMapping("/viewAllMessages")
    public String viewAllMessages(@ModelAttribute("message") String message,
            Model model) {

                Boolean is_logged=false;
                if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                    is_logged=true;
                    String email=SecurityContextHolder.getContext().getAuthentication().getName();
                    User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
                    model.addAttribute("usuario",usr);
                    model.addAttribute("nombreUsuario",email);
                }
                model.addAttribute("isLogged", is_logged);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Message> todosMensajes = messageService.findBySenderOrReceiverEmail(email);
        Set<String> contactos = new HashSet<>();
        for (Message m : todosMensajes) {
            if (m.getUserSender().getEmail().equals(email)) {
                contactos.add(m.getUserReceiver().getEmail());
            } else {
                contactos.add(m.getUserSender().getEmail());

            }
        }
        model.addAttribute("contactos", contactos);
        model.addAttribute("message", message);
        return "ViewAllMessages";
    }

    @GetMapping("/viewMessages/{email2}")
    public String viewMessages(@PathVariable("email2") String email2, @ModelAttribute("message") String message,
            Model model) {

                Boolean is_logged=false;
                if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                    is_logged=true;
                    String email=SecurityContextHolder.getContext().getAuthentication().getName();
                    User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
                    model.addAttribute("usuario",usr);
                    model.addAttribute("nombreUsuario",email);
                }
                model.addAttribute("isLogged", is_logged);

        String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Message> mensajes = messageService.getByPreviousChat(email1, email2);
        model.addAttribute("emailReceiverS", email2);
        model.addAttribute("mensajes", mensajes);
        model.addAttribute("message", message);
        return "ViewMessage";
    }

    @PostMapping("/saveMessage/{emailReceiver}")
    public String saveMessage(@PathVariable("emailReceiver") String emailReceiver,
            @RequestParam("bodyMessage") String bodyMessage,
            RedirectAttributes redirectAttributes) {

        Message msg = new Message();
        String emailSender = SecurityContextHolder.getContext().getAuthentication().getName();
        User userSender = userService.getUserByEmail(emailSender);
        User userReceiver = userService.getUserByEmail(emailReceiver);
        msg.setUserSender(userSender);
        msg.setUserReceiver(userReceiver);
        msg.setMessageBody(bodyMessage);

        if (messageService.saveOrUpdateMessage(msg)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Save Failure");
        }
        return "redirect:/viewMessages/" + emailReceiver;
    }

    @GetMapping("/deleteMessage/{id}/{emailReceiver}")
    public String deleteMessage(@PathVariable Long id, @PathVariable("emailReceiver") String emailReceiver,
            RedirectAttributes redirectAttributes) {
        if (messageService.deleteMessage(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
            return "redirect:/viewMessages/" + emailReceiver;
        }
        redirectAttributes.addFlashAttribute("message", "Delete Failure");
        return "redirect:/viewMessages/" + emailReceiver;
    }

    @GetMapping("/refresh")
    public RedirectView refresh(HttpServletRequest request) {
        String currentUrl = request.getRequestURL().toString();
        return new RedirectView(currentUrl);
    }

}
