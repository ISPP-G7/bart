package com.example.SpringBootPostgresCRUD.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "transacciones")
public class Transaccion {

	public enum Currency {
		EUR;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private User artista;

	@OneToOne
	private User arrendador;

	private EstadoTransaccion estadoTransaccion;
	private Date fechaCreacionDeTransaccion;
	private Date fechaActuacion;

	private Integer amount;
	private Currency currency;

}