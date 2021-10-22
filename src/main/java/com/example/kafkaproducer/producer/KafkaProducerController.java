package com.example.kafkaproducer.producer;

import com.example.kafkaproducer.domain.Mensagem;
import com.example.kafkaproducer.domain.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaProducerController {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, Mensagem> kafkaTemplateMensagem;

    @Autowired
    KafkaTemplate<String, Sample> kafkaTemplateSample;

    @GetMapping("/{message}")
    public String publishEndpoint(@PathVariable String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test-topic", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("FAILED TO SEND MESSAGE: " + message + "\n Caused by: " + throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                System.out.println("MESSAGE: " + message + " SENT SUCCESSFULLY");
            }
        });

        return "Puslibhesh";
    }

    @PostMapping("/")
    public String publishEndpoint(@RequestBody Mensagem message) {
        ListenableFuture<SendResult<String, Mensagem>> future =
                kafkaTemplateMensagem.send("custom-test-topic", "chave", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Mensagem>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("FAILED TO SEND CUSTOM MESSAGE: " + message + "\n Caused by: " + throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Mensagem> stringStringSendResult) {
                System.out.println("CUSTOM MESSAGE: " + message + " SENT SUCCESSFULLY");
            }
        });

        return "Custom Puslibhesh";
    }

    @PostMapping("/avro")
    public String publishEndpoint(@RequestBody Sample sample) {
        ListenableFuture<SendResult<String, Sample>> future =
                kafkaTemplateSample.send("avro-test-topic", "chavro", sample);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Sample>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("FAILED TO SEND CUSTOM MESSAGE: " + sample + "\n Caused by: " + throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Sample> stringStringSendResult) {
                System.out.println("CUSTOM MESSAGE: " + sample + " SENT SUCCESSFULLY");
            }
        });

        return "Custom Avro Puslibhesh";
    }
}
