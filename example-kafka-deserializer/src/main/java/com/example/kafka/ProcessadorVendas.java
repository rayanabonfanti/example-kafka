package com.example.kafka;

import com.example.kafka.model.Venda;
import com.example.kafka.serializer.VendaDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class ProcessadorVendas {

    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VendaDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-processamento");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (KafkaConsumer<String, Venda> kafkaConsumer = new KafkaConsumer<>(properties)){
            kafkaConsumer.subscribe(List.of("vendas-topic"));
            while (true){
                ConsumerRecords<String, Venda> vendas = kafkaConsumer.poll(Duration.ofMillis(200));
                for(ConsumerRecord<String, Venda> record : vendas){
                    Venda venda = record.value();
                    if(new Random().nextBoolean()) {
                        venda.setStatus("APROVADA");
                    } else {
                        venda.setStatus("REPROVADA");
                    }
                    Thread.sleep(500);
                    System.out.println("Venda processada: " + venda);
                }
            }
        }
    }

}