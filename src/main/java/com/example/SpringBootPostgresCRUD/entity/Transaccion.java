package com.example.SpringBootPostgresCRUD.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "transacciones")
@Getter
@Setter
public class Transaccion{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private User artista;
	private User arrendador;
	private EstadoTransaccion estadoTransaccion;
	private Date fechaCreacionDeTransaccion;
	private Date fechaActuacion;

  
}