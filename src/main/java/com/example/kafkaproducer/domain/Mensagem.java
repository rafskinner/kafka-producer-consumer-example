package com.example.kafkaproducer.domain;

import lombok.Data;

@Data
public class Mensagem {
    private String destinatario;
    private String remetente;
    private String conteudo;
}
