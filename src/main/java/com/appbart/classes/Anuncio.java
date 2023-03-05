package com.appbart.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "anuncios")
public class Anuncio {
    private Integer id;
    private String  titulo;
    private String  ubicacion;
    private Float precio;
    private String especificaciones;
    private Estilo  estilo;
    //falta subir imagenes y poner bien lo del precio fijo/negociable

    public Anuncio(Integer id, String titulo, String ubicacion,
    Float precio, String especificaciones, Estilo estilo)
    {
        this.id = id;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.especificaciones = especificaciones;
        this.estilo = estilo;
    }

    // otros getters y setters específicos de Artista
}
