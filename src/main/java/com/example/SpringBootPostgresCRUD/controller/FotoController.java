package com.example.SpringBootPostgresCRUD.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.SpringBootPostgresCRUD.entity.Arrendador;
import com.example.SpringBootPostgresCRUD.entity.Artista;
import com.example.SpringBootPostgresCRUD.entity.Foto;
import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.repo.FotoRepository;
import com.example.SpringBootPostgresCRUD.service.ArrendadorService;
import com.example.SpringBootPostgresCRUD.service.ArtistaService;
import com.example.SpringBootPostgresCRUD.service.UserService;

@Controller
public class FotoController {
    @Autowired
    FotoRepository fotoRepository;
    @Autowired
    UserService userService;
    @Autowired
    ArrendadorService arrendadorService;
    @Autowired
    ArtistaService artistaService;
    String anonymousUser = "anonymousUser";

    @GetMapping("/imagenes")
    public String viewFormFotos(Model model){

        setUserIfLogged(model);

        Foto foto = new Foto();
        model.addAttribute("foto", foto);
        return "SendPhotos";
    }
   
    @PostMapping("/imagenes")
    public String subirImagen(@RequestParam("data") MultipartFile archivo) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Foto imagen = new Foto();
        imagen.setUserEmail(email);
        imagen.setData(archivo.getBytes());
        fotoRepository.save(imagen);
        return "redirect:/";
    }
    
    @GetMapping("/imagenes/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Optional<Foto> imagenOptional = fotoRepository.findById(id);
        if (imagenOptional.isPresent()) {
            Foto imagen = imagenOptional.get();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Comprueba si el usuario está logueado y setea los valores correspondientes
    public void setUserIfLogged(Model model){
        Boolean isLogged = false;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(anonymousUser)) {
            isLogged = true;
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User usr = userService.getUserByEmail(email); // Con esto cogemos el artista logueado
            model.addAttribute("usuario", usr);
            model.addAttribute("nombreUsuario", email);
            if(usr.getEsArrendador()){
                Arrendador arrendador = arrendadorService.getArrendadorByMailArrendador(email);
                model.addAttribute("arrendador", arrendador);
            } else if(usr.getEsArtista()){
                Artista artista = artistaService.getArtistaByMailArtista(email);
                model.addAttribute("artista", artista);
            }
        }
        model.addAttribute("isLogged", isLogged);
    }
}
