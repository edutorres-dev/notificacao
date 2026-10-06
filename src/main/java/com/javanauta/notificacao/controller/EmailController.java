package com.javanauta.notificacao.controller;

import com.javanauta.notificacao.business.EmailService;
import com.javanauta.notificacao.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    // Service responsável pela lógica de envio do e-mail.
    private final EmailService emailService;

    // Endpoint responsável por solicitar o envio de um e-mail.
    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefasDTO dto) {

        // Envia os dados da tarefa para o Service montar e enviar o e-mail.
        emailService.enviaEmail(dto);

        // Retorna HTTP 200 indicando que o envio foi solicitado com sucesso.
        return ResponseEntity.ok().build();
    }
}

