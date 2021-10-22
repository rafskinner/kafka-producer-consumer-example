package com.example.kafkaproducer.configuration;

import com.example.kafkaproducer.domain.Mensagem;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class CustomKafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, Mensagem> mensagemConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Mensagem.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Mensagem> mensagemKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Mensagem> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(mensagemConsumerFactory());
        return factory;
    }
}
