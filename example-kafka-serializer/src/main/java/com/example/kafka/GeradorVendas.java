package com.example.kafka;

import com.example.kafka.model.Venda;
import com.example.kafka.serializer.VendaSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Random;

public class GeradorVendas {

    private static Random random = new Random();
    private static long operacao = 0;
    private static BigDecimal valorIngresso = BigDecimal.valueOf(500);

    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VendaSerializer.class.getName());
        ProducerRecord<String, Venda> record = null;

        try (KafkaProducer<String, Venda> kafkaProducer = new KafkaProducer<>(properties)){
            while (true) {
                Venda venda = gerarVenda();
                record = new ProducerRecord<>("vendas-topic", venda);
                kafkaProducer.send(record);
                System.out.println("Venda gerada e enviada: " + venda);
                Thread.sleep(200);
            }
        }
    }

    private static Venda gerarVenda() {
        long cliente = random.nextLong();
        int quantidadeIngressos = random.nextInt(10);
        return Venda.builder()
                .operacao(operacao++)
                .cliente(cliente)
                .quantidadeIngressos(quantidadeIngressos)
                .valorTotal(valorIngresso.multiply(BigDecimal.valueOf(quantidadeIngressos)))
                .build();
    }
}