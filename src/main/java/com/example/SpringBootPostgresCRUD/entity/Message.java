package com.example.SpringBootPostgresCRUD.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String messageBody;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "userSender")
    private User userSender;

    @ManyToOne
    @JoinColumn(name = "userReceiver")
    private User userReceiver;

}
