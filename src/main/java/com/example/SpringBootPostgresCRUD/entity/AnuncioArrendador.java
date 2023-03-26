package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Valid
@Table(name = "anunciosArrendador")
public class AnuncioArrendador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long artista_accept_id;

    @NotBlank
    private String nombreLocal;

    
    @NotBlank
    private String ubicacion;

    @NotNull
    @Min(0)
    private Float precio;
    
    @NotBlank
    private String descripcionArrendador;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Estilo estilo;
    private String ofreceMicrofono;
    private String ofreceInstrumentos;
    private String ofreceIluminacion;
    private String ofreceAltavoces;
    private String ofreceMesaDeMezclas;
    private String ofrecePortatil;
    private String ofreceOtrasEspecificaciones;
    private Boolean ofreceActuacionPorEntradas;
    @OneToOne
    private Arrendador arrendador;
    private boolean estaAceptado = false;
    private boolean estaPagado = false;

    public AnuncioArrendador() {

    }

    public AnuncioArrendador(Long id, String nombreLocal, String ubicacion,
            Float precio, String descripcionArrendador, Estilo estilo,String ofreceMicrofono,String ofreceInstrumentos,String ofreceIluminacion,
            String ofreceAltavoces,String ofreceMesaDeMezclas,String ofrecePortatil, String ofreceOtrasEspecificaciones,Boolean ofreceActuacionPorEntradas ) {
        this.id = id;
        this.nombreLocal = nombreLocal;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.descripcionArrendador = descripcionArrendador;
        this.estilo = estilo;
        this.ofreceMicrofono=ofreceMicrofono;
        this.ofreceInstrumentos=ofreceInstrumentos;
        this.ofreceIluminacion=ofreceIluminacion;
        this.ofreceAltavoces=ofreceAltavoces;
        this.ofreceMesaDeMezclas=ofreceMesaDeMezclas;
        this.ofrecePortatil=ofrecePortatil;
        this.ofreceOtrasEspecificaciones=ofreceOtrasEspecificaciones;
    }
}
