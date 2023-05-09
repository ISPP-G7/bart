package com.example.SpringBootPostgresCRUD.controller;

import java.io.Console;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SpringBootPostgresCRUD.entity.AnuncioArrendador;
import com.example.SpringBootPostgresCRUD.entity.AnuncioArtista;
import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.TransaccionPaypal;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.AnuncioArrendadorService;
import com.example.SpringBootPostgresCRUD.service.AnuncioArtistaService;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.PayPalService;
import com.example.SpringBootPostgresCRUD.service.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PayPalController {

    @Autowired
    PayPalService paypalService;

    @Autowired
    UserService userService;

    @Autowired
    ArtistaService artistaService;

    @Autowired
    ArrendadorService arrendadorService;

    @Autowired
    AnuncioArtistaService anuncioArtistaService;

    @Autowired
    AnuncioArrendadorService anuncioArrendadorService;

    public static final String BASE_URL = "bartdeploy4.azurewebsites.net";
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    public static Long id_aux = 0L;
    public static String tipo_anuncio = "";

    @GetMapping("/viewAllAnunciosAceptados")
    public String anunciosAceptados(Model model) {
        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("artista", usr);
            model.addAttribute("arrendador", usr);

            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);

        String emailArrendador = SecurityContextHolder.getContext().getAuthentication().getName();
        Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(emailArrendador);
        List<AnuncioArtista> anunciosArtistaAceptados = anuncioArtistaService.getAllAnunciosArtistaAceptados();
        List<AnuncioArrendador> anunciosArrendadorAceptados = anuncioArrendadorService
                .getAllAnunciosArrendadorAceptados();
        List<AnuncioArtista> auxArtista = new ArrayList<>();
        List<Pair<AnuncioArrendador, Artista>> auxArrendador = new ArrayList<>();

        for (AnuncioArtista anuncioArtista : anunciosArtistaAceptados) {
            if (anuncioArtista.getArrendador_accept_id() == arrendador.getId()) {
                auxArtista.add(anuncioArtista);
            }
        }

        for (AnuncioArrendador anuncioArrendador : anunciosArrendadorAceptados) {
            if (anuncioArrendador.getArrendador().getId() == arrendador.getId()) {
                Artista artista = artistaService.getArtistaById(anuncioArrendador.getArtista_accept_id());
                Pair<AnuncioArrendador, Artista> aux = Pair.of(anuncioArrendador, artista);
                auxArrendador.add(aux);
            }
        }
        model.addAttribute("anunciosArtista", auxArtista);
        model.addAttribute("anunciosArrendador", auxArrendador);
        return "ViewAllAnunciosAceptados";
    }

    @GetMapping("/pagarAnuncioArtista/{id}")
    public String pagarAnuncioArtista(@PathVariable("id") Long id, Model model) {
        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("artista", usr);
            model.addAttribute("arrendador", usr);
            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);

        AnuncioArtista anuncio = anuncioArtistaService.getAnuncioArtistaById(id);
        id_aux = id;
        tipo_anuncio = "artista";
        model.addAttribute("tipo", "artista");
        model.addAttribute("anuncio", anuncio);
        return "Transaccion";
    }

    @GetMapping("/pagarAnuncioArrendador/{id}")
    public String pagarAnuncioArrendador(@PathVariable("id") Long id, Model model) {
        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);

        AnuncioArrendador anuncio = anuncioArrendadorService.getAnuncioArrendadorById(id);
        Artista artista = artistaService.getArtistaById(anuncio.getArtista_accept_id());
        id_aux = id;
        tipo_anuncio = "arrendador";
        model.addAttribute("tipo", "arrendador");
        model.addAttribute("artista", artista);
        model.addAttribute("anuncio", anuncio);
        return "Transaccion";
    }

    @GetMapping("/paypal")
    public String home(Model model) {
        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);
        return "home";
    }

    @PostMapping("/pay")
    public String handlePayment(@ModelAttribute("transaction") TransaccionPaypal transaccion, Model model) {
        try {

            Payment payment = paypalService.createPayment(
                    transaccion.getTotal(),
                    transaccion.getCurrency(),
                    transaccion.getMethod(),
                    transaccion.getIntent(),
                    transaccion.getDescription(),
                    BASE_URL + CANCEL_URL,
                    BASE_URL + SUCCESS_URL);

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay(Model model) {

        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
        }
        model.addAttribute("isLogged", is_logged);

        return "PayFail";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId, Model model) {

        Boolean is_logged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            model.addAttribute("artista", usr);
            model.addAttribute("arrendador", usr);
        }
        model.addAttribute("isLogged", is_logged);

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                if (tipo_anuncio.equals("artista")) {
                    AnuncioArtista anuncio = anuncioArtistaService.getAnuncioArtistaById(id_aux);
                    anuncio.setEstaPagado(true);
                    anuncioArtistaService.saveOrUpdateAnuncioArtista(anuncio, anuncio.getArtista().getId());
                } else {
                    AnuncioArrendador anuncio = anuncioArrendadorService.getAnuncioArrendadorById(id_aux);
                    anuncio.setEstaPagado(true);
                    anuncioArrendadorService.saveOrUpdateAnuncioArrendador(anuncio, anuncio.getArrendador().getId());
                }
                return "PaySuccess";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}