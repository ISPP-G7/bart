package com.appbart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

   
    @GetMapping("/error")
    public String handleError() {
   // Aquí puedes agregar el código para manejar el error
   return "error"; // nombre de la vista para mostrar la página de error
    }
}