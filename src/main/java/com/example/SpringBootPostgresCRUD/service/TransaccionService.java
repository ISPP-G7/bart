package com.example.SpringBootPostgresCRUD.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import com.example.SpringBootPostgresCRUD.entity.Transaccion;

@Service
public class TransaccionService {

    @Value("${stripe.key.secret}")
    String secretKey;

    public PaymentIntent paymentIntent(Transaccion transaccion) {

    }
}
