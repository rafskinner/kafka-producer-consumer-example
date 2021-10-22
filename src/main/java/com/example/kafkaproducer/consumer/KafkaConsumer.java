package com.example.kafkaproducer.consumer;

import com.example.kafkaproducer.domain.Mensagem;
import com.example.kafkaproducer.domain.Sample;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "test-topic", groupId = "group-1")
    public void listenGroup1(String message) {
        System.out.println("Consumed Message from test-topic: " + message);
    }

    @KafkaListener(topics = "custom-test-topic", groupId = "group-1", containerFactory = "mensagemKafkaListenerContainerFactory")
    public void listenGroup1(Mensagem message) {
        System.out.println("Consumed Custom Message from custom-test-topic: " + message);
    }

    @KafkaListener(topics = "avro-test-topic", groupId = "group-1", containerFactory = "sampleKafkaListenerContainerFactory")
    public void listenGroup1(ConsumerRecord<String, Sample> sample) {
        System.out.println("Consumed Avro Sample Message from avro-test-topic: " + sample.value());
    }
}
