package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
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

    // @NotBlank
    // @Min(10)
    // @Max(50)
    private String pseudonimoArtista;
    private Long arrendador_accept_id;

    // @NotBlank
    /*@Min(5)
     @Max(60)
     */
    private String ubicacion;

    /*
    @NotNull
    @Positive
     */
    private Float precio;

    // @NotBlank
    // @Min(15)
    // @Max(300)
    private String descripcionArtista;
    private boolean requiereMicrofono;
    private boolean requiereInstrumentos;
    private boolean requiereIluminacion;
    private boolean requiereAltavoces;
    private boolean requiereMesaDeMezclas;
    private boolean requierePortatil;
    private String  requiereOtrasEspecificaciones;
    // @NotNull
    @Enumerated(EnumType.STRING)
    private Estilo estilo;

    @OneToOne
    private Artista artista;
    private boolean estaAceptado = false;

    public AnuncioArtista() {

    }

    public AnuncioArtista(Long id, String pseudonimoArtista, String ubicacion,
        Float precio, String descripcionArtista, Estilo estilo, Artista artista,
        boolean requiereMicrofono, boolean requiereInstrumentos, boolean requiereIluminacion,
        boolean requiereAltavoces, boolean requiereMesaDeMezclas, boolean requierePortatil,String requiereOtrasEspecificaciones ) {
        this.id = id;
        this.pseudonimoArtista = pseudonimoArtista;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.descripcionArtista = descripcionArtista;
        this.estilo = estilo;
        this.artista= artista;
        this.requiereMicrofono = requiereMicrofono;
        this.requiereInstrumentos = requiereInstrumentos;
        this.requiereIluminacion = requiereIluminacion;
        this.requiereAltavoces = requiereAltavoces;
        this.requiereMesaDeMezclas = requiereMesaDeMezclas;
        this.requierePortatil = requierePortatil;
        this.requiereOtrasEspecificaciones=requiereOtrasEspecificaciones;
    }
   
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
    public boolean getEstaAceptado() {
        return estaAceptado;
    }
    
    public void setEstaAceptado(boolean estaAceptado) {
        this.estaAceptado = estaAceptado;
    }

	public void setArtista(Artista byId) {
		// TODO Auto-generated method stub
		this.artista = byId;
		
	}
    public Long getArrendador_accept_id() {
        return arrendador_accept_id;
    }
    public void setArrendador_accept_id(Long arrendador_accept_id) {
        this.arrendador_accept_id = arrendador_accept_id;
    }
    // otros getters y setters específicos de Artista
}
