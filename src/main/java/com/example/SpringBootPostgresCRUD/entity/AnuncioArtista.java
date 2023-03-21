package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.persistence.OneToOne;
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

    @NotBlank
    private String pseudonimoArtista;

    private Long arrendador_accept_id;

    @NotBlank
    private String ubicacion;

    @NotNull
    @PositiveOrZero
    private Float precio;

    @NotBlank
    private String descripcionArtista;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Estilo estilo;

    @OneToOne
    private Artista artista;

    private boolean estaAceptado = false;

    public AnuncioArtista() {

    }

    public AnuncioArtista(Long id, String pseudonimoArtista, String ubicacion,
            Float precio, String descripcionArtista, Estilo estilo, Artista artista) {
        this.id = id;
        this.pseudonimoArtista = pseudonimoArtista;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.descripcionArtista = descripcionArtista;
        this.estilo = estilo;
        this.artista= artista;
    }

    // otros getters y setters específicos de Artista
}
