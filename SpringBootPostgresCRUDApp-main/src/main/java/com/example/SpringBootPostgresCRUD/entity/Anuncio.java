package com.example.SpringBootPostgresCRUD.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "anuncios")
public class Anuncio {

    private Integer id;

    @NotBlank
    @Min(10)
    @Max(50)
    private String titulo;

    @NotBlank
    @Min(5)
    @Max(60)
    private String ubicacion;

    @NotNull
    @Positive
    private Float precio;

    @NotBlank
    @Min(15)
    @Max(300)
    private String especificaciones;

    @NotNull
    private Estilo estilo;

    /*
     * @Valid
     * 
     * @ManyToOne
     * private Artista artista;
     * 
     * @Valid
     * 
     * @ManyToOne
     * private Arrendador arrendador;
     */

    // falta subir imagenes y poner bien lo del precio fijo/negociable

    public Anuncio(Integer id, String titulo, String ubicacion,
            Float precio, String especificaciones, Estilo estilo) {
        this.id = id;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.especificaciones = especificaciones;
        this.estilo = estilo;
    }

    // otros getters y setters específicos de Artista
}
