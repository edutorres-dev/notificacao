package com.javanauta.notificacao.infastructure.exception;

// Exceção usada para representar erros relacionados ao envio de e-mail.
public class EmailException extends RuntimeException {

    // Cria a exceção apenas com uma mensagem.
    public EmailException(String mensagem) {
        super(mensagem);
    }

    // Cria a exceção com uma mensagem e a causa original do erro.
    public EmailException(String mensagem, Throwable trowable) {
        super(mensagem, trowable);
    }
}

