package com.example.SpringBootPostgresCRUD.controller;

import java.time.Instant;
import java.util.Date;
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

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.Message;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.MessageService;
import com.example.SpringBootPostgresCRUD.service.UserService;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    ArtistaService artistaService;
    @Autowired
    ArrendadorService arrendadorService;

    @GetMapping("/viewAllMessages")
    public String viewAllMessages(@ModelAttribute("message") String message,
            Model model) {
        Boolean isLogged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
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

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Message> todosMensajes = messageService.findBySenderOrReceiverEmail(email);
        Set<String> auxEmails = new HashSet<>();
        for (Message m : todosMensajes) {
            if (m.getUserSender().getEmail().equals(email)) {
                auxEmails.add(m.getUserReceiver().getEmail());
            } else {
                auxEmails.add(m.getUserSender().getEmail());
            }
        }
        Set<User> contactos = new HashSet<>();
        for (String sEmail : auxEmails) {
            User user = messageService.getUserByEmail(sEmail);
            contactos.add(user);
        }
        model.addAttribute("contactos", contactos);
        model.addAttribute("message", message);
        return "ViewAllMessages";
    }

    @GetMapping("/viewMessages/{id}")
    public String viewMessages(@PathVariable("id") Long id, @ModelAttribute("message") String message,
            Model model) {

        Boolean isLogged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
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

        String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
        User userReceiver = userService.getUserById(id);
        List<Message> mensajes = messageService.getByPreviousChat(email1, userReceiver.getEmail());
        model.addAttribute("userReceiver", userReceiver);
        model.addAttribute("mensajes", mensajes);
        model.addAttribute("message", message);
        return "ViewMessage";
    }

    @PostMapping("/saveMessage/{id}")
    public String saveMessage(@PathVariable("id") Long id,
            @RequestParam("bodyMessage") String bodyMessage,
            RedirectAttributes redirectAttributes) {

        Message msg = new Message();
        String emailSender = SecurityContextHolder.getContext().getAuthentication().getName();
        User userSender = userService.getUserByEmail(emailSender);
        User userReceiver = userService.getUserById(id);
        msg.setUserSender(userSender);
        msg.setUserReceiver(userReceiver);
        msg.setMessageBody(bodyMessage);

        Date now = Date.from(Instant.now());
        msg.setDate(now);

        if (messageService.saveOrUpdateMessage(msg)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Save Failure");
        }
        return "redirect:/viewMessages/" + id;
    }

    @GetMapping("/refresh")
    public RedirectView refresh(HttpServletRequest request) {
        String currentUrl = request.getRequestURL().toString();
        return new RedirectView(currentUrl);
    }

}
