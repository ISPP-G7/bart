package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("Arrendador")
@Entity
@Table(name = "arrendadores")
@Getter
@Setter
public class Arrendador extends User {
    @NotBlank(message = "La dirección es obligatoria")
    private String nombreLocal;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    @URL(message = "Tiene que ser una URL válida")
    @NotBlank(message = "La url de una imagen es obligatoria")
    private String urlImagen;

    private String links;

    public Arrendador() {

    }

    public Arrendador(Long id, String password, String firstName, String lastName, String email, String dob,
            String gender, String nombreLocal, String direccion, String urlImagen, String links) {
        super(id, password, firstName, lastName, email, dob, gender);
        this.nombreLocal = nombreLocal;
        this.direccion = direccion;
        this.urlImagen = urlImagen;
        this.links = links;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

}
