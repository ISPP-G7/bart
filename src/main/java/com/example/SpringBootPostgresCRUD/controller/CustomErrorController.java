package com.example.SpringBootPostgresCRUD.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        
        // Obtener el código de estado y la descripción del error
        int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        
        // Obtener una descripción más detallada del error (opcional)
        String detailedErrorMessage = null;
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            detailedErrorMessage = sw.toString();
        }
        
        // Agregar los atributos al modelo de vista
        modelAndView.addObject("statusCode", statusCode);
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.addObject("detailedErrorMessage", detailedErrorMessage);
        
        return modelAndView;
    }

    
    public String getErrorPath() {
        return "/error";
    }
}
