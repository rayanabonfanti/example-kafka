package com.example.kafka.serializer;

import com.example.kafka.model.Venda;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class VendaDeserializer implements Deserializer<Venda> {

    @Override
    public Venda deserialize(String s, byte[] bytes) {
        try {
            return new ObjectMapper().readValue(bytes, Venda.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
