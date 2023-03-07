package com.example.SpringBootPostgresCRUD.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @GetMapping({"/", "/home"})

    public String home(@ModelAttribute("message") String message, Model model) {
        
        return "Home";
    }
}