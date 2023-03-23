package com.example.SpringBootPostgresCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SpringBootPostgresCRUD.entity.TransaccionPaypal;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.PayPalService;
import com.example.SpringBootPostgresCRUD.service.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PayPalController {

    @Autowired
    PayPalService service;

    @Autowired
    UserService userService;



    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/paypal")
    public String home(Model model) {
        Boolean is_logged=false;
                if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                    is_logged=true;
                    String email=SecurityContextHolder.getContext().getAuthentication().getName();
                    User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
                    model.addAttribute("usuario",usr);
                    model.addAttribute("nombreUsuario",email);
                }
                model.addAttribute("isLogged", is_logged);
        return "home";
    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") TransaccionPaypal order) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

     @GetMapping(value = CANCEL_URL)
        public String cancelPay() {
            return "cancel";
        }

        @GetMapping(value = SUCCESS_URL)
        public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
            try {
                Payment payment = service.executePayment(paymentId, payerId);
                System.out.println(payment.toJSON());
                if (payment.getState().equals("approved")) {
                    return "success";
                }
            } catch (PayPalRESTException e) {
             System.out.println(e.getMessage());
            }
            return "redirect:/";
        }

}