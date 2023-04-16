package com.example.SpringBootPostgresCRUD.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;


@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@Table(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String firstName;
    
    private String lastName;
    @Email
    private String email;

    private String dob;

    private String gender;

    private String password;

    
    private Boolean es_arrendador;
    private Boolean es_artista;
    private Boolean anuncioNoVisto = false; // valor por defecto


    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String dob, String gender,String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.password=password;
    }
 
    public Boolean getEsArtista(){
        return es_artista;
    }
    public Boolean getEsArrendador(){
        return es_arrendador;
    }
    public void setEsArtista(Boolean es_artista){
        this.es_artista= es_artista;
    }
    public void setEsArrendador(Boolean es_arrendador){
        this.es_arrendador=es_arrendador;
    }

}
