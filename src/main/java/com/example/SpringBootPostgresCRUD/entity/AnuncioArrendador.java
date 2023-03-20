package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.persistence.OneToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "anunciosArrendador")
public class AnuncioArrendador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   
    private Long artista_accept_id;

    @NotBlank
    @Min(10)
    @Max(50)
    private String nombreLocal;

    
    @NotBlank
    @Min(5)
    @Max(60) 
    private String ubicacion;

    @NotNull
    @PositiveOrZero
    private Float precio;
    
    @NotBlank
    @Min(5)
    @Max(300)
    private String descripcionArrendador;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Estilo estilo;

    @OneToOne
    private Arrendador arrendador;
    private boolean estaAceptado = false;

    public AnuncioArrendador() {

    }

    public AnuncioArrendador(Long id, String nombreLocal, String ubicacion,
            Float precio, String descripcionArrendador, Estilo estilo) {
        this.id = id;
        this.nombreLocal = nombreLocal;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.descripcionArrendador = descripcionArrendador;
        this.estilo = estilo;
    }
}
