package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("Arrendador")  
@Entity
@Table(name="arrendadores")
@Getter
@Setter
public class Arrendador extends User {

    private String nombreLocal;
    private String direccion;
    private String urlImagen;
    
    public Arrendador() {

    }

    public Arrendador(Long id,String password, String firstName, String lastName, String email, String dob, String gender, String nombreLocal, String direccion,String urlImagen) {
        super(id,password, firstName, lastName, email, dob, gender);
        this.nombreLocal = nombreLocal;
        this.direccion = direccion;
        this.urlImagen = urlImagen;
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

}
