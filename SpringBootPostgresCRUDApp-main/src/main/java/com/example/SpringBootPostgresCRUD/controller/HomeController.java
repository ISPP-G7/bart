package com.example.SpringBootPostgresCRUD.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import com.example.SpringBootPostgresCRUD.auxiliarClaseMap.Place;
import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class HomeController {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ArrendadorService arrService;

    @GetMapping({"/", "/home"})
    public String home(Model model) throws IOException {
        List<List<String>> coordenadasList=new ArrayList<>();


        List<Arrendador> arrendadoresList = arrService.getAllArrendadores();
        System.out.println("hola");
        System.out.println(arrendadoresList.size());
        int i=0;
        for (Arrendador arrendador : arrendadoresList) {
            List<String> coordenadas= new ArrayList<>();
            String direccion = arrendador.getDireccion().replace(" ", "+");
            String url = "https://nominatim.openstreetmap.org/search?q=" + direccion + "&format=jsonv2&limit=1";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonParser jsonParser = objectMapper.createParser(jsonResponse);
            List<Place> places = objectMapper.readValue(jsonParser, new TypeReference<List<Place>>(){});
         
            coordenadas.add((places.get(i).getLat()));
            coordenadas.add((places.get(i).getLon()));
          
            System.out.println("entro");
            System.out.println(coordenadas);

            coordenadasList.add(coordenadas);
            //latitud,longitud
           // coordenadas.add(places.get(0)Integer.parseInt(.getLat())
           model.addAttribute("coordenadas", coordenadas);
           model.addAttribute("coordenadasList", coordenadasList);
           System.out.println("listam");
           System.out.println(coordenadasList);
       
        } 
       
     
        return "Home";
    }

   
}
