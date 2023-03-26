package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fotos")
@Getter
@Setter
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] data;

    private String userEmail;

    public Foto(Long id,byte[] data, String user){
        this.id=id;
        this.data=data;
        this.userEmail=user;
    }
    public Foto(){

    }
}
