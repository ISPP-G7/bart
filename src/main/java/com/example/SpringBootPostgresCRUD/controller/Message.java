package com.example.SpringBootPostgresCRUD.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    private String sender;
    private String receiver;
    private String message;
    private String date;
    private Status status;
}
