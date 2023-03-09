package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import lombok.Setter;
import lombok.Getter;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;

@Getter
@Setter
@Entity

@Table(name = "anunciosArtista")
public class AnuncioArtista {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@NotBlank
    //@Min(10)
    //@Max(50)
    private String pseudonimoArtista;

    //@NotBlank
    /*@Min(5)
    @Max(60)*/
    private String ubicacion;

    /*@NotNull
    @Positive*/
    private Float precio;

   // @NotBlank
    //@Min(15)
    //@Max(300)
    private String descripcionArtista;

    //@NotNull
    @Enumerated(EnumType.STRING)
    private Estilo estilo;


    @OneToOne
    private Artista artista_id;

 

    public AnuncioArtista() {

    }

    public AnuncioArtista(Long id, String pseudonimoArtista, String ubicacion,
            Float precio, String descripcionArtista, Estilo estilo) {
        this.id = id;
        this.pseudonimoArtista = pseudonimoArtista;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.descripcionArtista = descripcionArtista;
        this.estilo = estilo;
    }

    public String getPseudonimoArtista() {
        return pseudonimoArtista;
    }

    public void setTitulo(String pseudonimoArtista) {
        this.pseudonimoArtista = pseudonimoArtista;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripicionArtista() {
        return descripcionArtista;
    }

    public void setDescripicionArtista(String especificaciones) {
        this.descripcionArtista = especificaciones;
    }

    // otros getters y setters específicos de Artista
}
