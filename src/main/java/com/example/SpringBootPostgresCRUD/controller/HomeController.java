package com.example.SpringBootPostgresCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.example.SpringBootPostgresCRUD.auxiliarClaseMap.Place;
import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private UserService userService;
    @Autowired
    private ArrendadorService arrService;
    @Autowired
    private ArtistaService artService;

    @GetMapping({ "/", "/home" })
    public String home(Model model,Authentication authentication) throws IOException {
        Boolean is_logged= false;
        List<List<String>> coordenadasList = new ArrayList<>();

        List<Arrendador> arrendadoresList = arrService.getAllArrendadores();

        List<List<String>> arrendadoresNombreLocalMapListAux = new ArrayList<>();

        for (Arrendador arrendador : arrendadoresList) {
            List<String> arrendadoresNombreLocalMap = new ArrayList<>();
            arrendadoresNombreLocalMap.add((arrendador.getDireccion()));
            List<String> coordenadas = new ArrayList<>();
            String direccion = arrendador.getDireccion().replace(" ", "+");
            String url = "https://nominatim.openstreetmap.org/search?q=" + direccion + "&format=jsonv2&limit=1";
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonParser jsonParser = objectMapper.createParser(jsonResponse);
            List<Place> places = objectMapper.readValue(jsonParser, new TypeReference<List<Place>>() {
            });

            if (!places.isEmpty()) {
                coordenadas.add((places.get(0).getLat()));
                coordenadas.add((places.get(0).getLon()));
                arrendadoresNombreLocalMapListAux.add(arrendadoresNombreLocalMap);
                coordenadasList.add(coordenadas);
            }

            model.addAttribute("coordenadas", coordenadas);
            model.addAttribute("coordenadasList", coordenadasList);

        }
        model.addAttribute("arrendadoresNombreLocalMapListAux", arrendadoresNombreLocalMapListAux);
        // Para que aparezca el nombre de usuario
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            is_logged=true;
            model.addAttribute("nombreUsuario", SecurityContextHolder.getContext().getAuthentication().getName());
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); //Con esto cogemos el artista logueado
            model.addAttribute("usuario",usr);
            if(usr.getEsArrendador()){
                Arrendador arrendador= arrService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            }
            else if(usr.getEsArtista()){
                Artista artista = artService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        } else {
            model.addAttribute("nombreUsuario", "");
        }
        model.addAttribute("isLogged", is_logged);


        return "Home";
    }

}
