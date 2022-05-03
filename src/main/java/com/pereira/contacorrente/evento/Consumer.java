package com.pereira.contacorrente.evento;

import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Consumer {

    private static final String TOPICO = "TOPICO";
    private static final String BOOTSTRAP = "localhost:9092";

    public static List<String> consumer() {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList(TOPICO));
        List<String> mesagens = new ArrayList<>();
        var records = consumer.poll(Duration.ofMillis(10000));
        for (ConsumerRecord<String, String> registro : records) {
            System.out.println("------------------------------------------");
            System.out.println("Recebendo nova mensagem");
            System.out.println(registro.key());
            System.out.println(registro.value());
            final String valor = registro.value().replaceAll("º", "");
            mesagens.add(valor);
        }
        return mesagens;
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, Consumer.class.getName());
        return properties;
    }

}
