
package com.javanauta.notificacao.business;

import com.javanauta.notificacao.business.dto.TarefasDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    // Responsável por criar e enviar mensagens de e-mail.
    private final JavaMailSender javaMailSender;

    // Responsável por processar templates HTML do Thymeleaf.
    private final TemplateEngine templateEngine;

    // Obtém o e-mail do remetente definido no arquivo de configuração.
    @Value("${envio.email.remetente}")
    private String remetente;

    // Obtém o nome do remetente definido no arquivo de configuração.
    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;


    // Monta e envia o e-mail de notificação da tarefa.
    public void enviaEmail(TarefasDTO dto) {

        try {

            // Cria a mensagem de e-mail que será enviada.
            MimeMessage mensagem = javaMailSender.createMimeMessage();

            // Configura a mensagem para aceitar HTML e caracteres UTF-8.
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mensagem,
                    true,
                    StandardCharsets.UTF_8.name()
            );

            // Define o remetente e o nome que aparecerá no e-mail.
            mimeMessageHelper.setFrom(
                    new InternetAddress(remetente, nomeRemetente)
            );

            // Define o e-mail do usuário que receberá a notificação.
            mimeMessageHelper.setTo(
                    InternetAddress.parse(dto.getEmailUsuario())
            );

            // Define o assunto da mensagem.
            mimeMessageHelper.setSubject("Notificação de Tarefa");

            // Cria o contexto com os dados que serão enviados para o template.
            Context context = new Context();
            context.setVariable("nomeTarefa", dto.getNomeTarefa());
            context.setVariable("dataEvento", dto.getDataEvento());
            context.setVariable("descricao", dto.getDescricao());

            // Processa o template HTML usando os dados da tarefa.
            String template = templateEngine.process("notificacao", context);

            // Define o conteúdo HTML da mensagem.
            mimeMessageHelper.setText(template, true);

            // Envia o e-mail através do JavaMailSender.
            javaMailSender.send(mensagem);

        } catch (MessagingException | UnsupportedEncodingException e) {

            // Lança uma exceção caso ocorra erro durante o envio.
            throw new RuntimeException("Erro ao enviar o email", e.getCause());
        }
    }
}

