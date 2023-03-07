package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.*;

@DiscriminatorValue("Arrendador")  
@Entity
@Table(name="arrendadores")
public class Arrendador extends User {

    private String nombreLocal;
    private String direccion;
    
    public Arrendador() {

    }

    public Arrendador(Long id, String firstName, String lastName, String email, String dob, String gender, String nombreLocal, String direccion) {
        super(id, firstName, lastName, email, dob, gender);
        this.nombreLocal = nombreLocal;
        this.direccion = direccion;
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
