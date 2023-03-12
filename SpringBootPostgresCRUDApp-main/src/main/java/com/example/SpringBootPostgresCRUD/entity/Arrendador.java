package com.example.SpringBootPostgresCRUD.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@DiscriminatorValue("Arrendador")  
@Entity
@Table(name="arrendadores")
public class Arrendador extends User {

    private String nombreLocal;
    private String direccion;
    
    public Arrendador() {

    }

    public Arrendador(Long id,String password, String firstName, String lastName, String email, String dob, String gender, String nombreLocal, String direccion) {
        super(id,password, firstName, lastName, email, dob, gender);
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

    @OneToOne
    //@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role roles;

   

}
