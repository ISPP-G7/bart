package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @ManyToOne
    @JoinColumn(name = "userSender")
    private User userSender;

    @ManyToOne
    @JoinColumn(name = "userReceiver")
    private User userReceiver;

}
