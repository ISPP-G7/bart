package com.example.SpringBootPostgresCRUD.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransaccionPaypal {

    private BigDecimal total;
    private String currency;
    private String method;
    private String intent;
    private String description;

}