package com.example.kafka.serializer;

import com.example.kafka.model.Venda;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class VendaSerializer implements Serializer<Venda> {

    @Override
    public byte[] serialize(String topic, Venda venda) {
        try {
            return new ObjectMapper().writeValueAsBytes(venda);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing Venda object", e);
        }
    }

}
