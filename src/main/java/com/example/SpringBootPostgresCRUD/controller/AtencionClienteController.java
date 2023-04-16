package com.example.SpringBootPostgresCRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AtencionClienteController {
    @RequestMapping("/atencionCliente")
    public String atencionCliente(){
        return "AtencionCliente";
    }
    
}
