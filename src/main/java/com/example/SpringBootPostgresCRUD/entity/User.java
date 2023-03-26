package com.example.SpringBootPostgresCRUD.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@Table(name="users")
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

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<Foto> fotos; */

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
