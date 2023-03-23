package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
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

    /*
     * @NotBlank
     * 
     * @Min(10)
     * 
     * @Max(50)
     */
    
    private Long artista_accept_id;
    private String nombreLocal;

    /*
     * @NotBlank
     * 
     * @Min(5)
     * 
     * @Max(60)
     */
    private String ubicacion;

    /*
     * @NotNull
     * 
     * @Positive
     */
    private Float precio;

    /*
     * @NotBlank
     * 
     * @Min(15)
     * 
     * @Max(300)
     */
    private String descripcionArrendador;

    // @NotNull
    @Enumerated(EnumType.STRING)
    private Estilo estilo;
    private String ofreceMicrofono;
    private String ofreceInstrumentos;
    private String ofreceIluminacion;
    private String ofreceAltavoces;
    private String ofreceMesaDeMezclas;
    private String ofrecePortatil;
    private String ofreceOtrasEspecificaciones;
    @OneToOne
    private Arrendador arrendador;
    private boolean estaAceptado = false;

    public AnuncioArrendador() {

    }

    public AnuncioArrendador(Long id, String nombreLocal, String ubicacion,
            Float precio, String descripcionArrendador, Estilo estilo,String ofreceMicrofono,String ofreceInstrumentos,String ofreceIluminacion,
            String ofreceAltavoces,String ofreceMesaDeMezclas,String ofrecePortatil, String ofreceOtrasEspecificaciones) {
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

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setTitulo(String nombreLocal) {
        this.nombreLocal = nombreLocal;
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

    public String getDescripicionArrendador() {
        return descripcionArrendador;
    }

    public void setDescripicionArrendador(String especificaciones) {
        this.descripcionArrendador = especificaciones;
    }

	public void setArrendador(Arrendador byId) {
		// TODO Auto-generated method stub
		this.arrendador = byId;
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
    public Long getArtista_accept_id() {
        return artista_accept_id;
    }
    public void setArtista_accept_id(Long artista_accept_id) {
        this.artista_accept_id = artista_accept_id;
    }
    // otros getters y setters específicos de Arrendador
}
