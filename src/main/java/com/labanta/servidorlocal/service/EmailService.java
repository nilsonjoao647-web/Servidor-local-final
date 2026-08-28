package com.labanta.servidorlocal.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender, JavaMailSender mailSender1){
        this.mailSender = mailSender;
    }

    public void enviarEmailBoasVindas(String emailDestino, String nomeUtilizador) {

        // Criar um email simples (texto limpo)
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setTo(emailDestino);
        mensagem.setSubject("Bem-vinod ao Marketplace!");
        mensagem.setText("Ola " + nomeUtilizador + "!\n\n" +
                "A tua conta foi criada com sucesso. já podes fazer login " +
                "e explorar os nossos servicos. \n\n" +
                "com os melhores cumprimentos. \nEquipa do Marketplace");

        //Enviar!
        mailSender.send(mensagem);
    }

    public void enviarOrcamentoPorEmail(String emailDestino, String nomeServico, double precoConvertido, String moeda) {

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(emailDestino);
        mensagem.setSubject("orcamento do servico no marketplce");
        String corpo = String.format(
                "Ola!\n\nAqui tens orcamento solicitado para o servico:\n\n" +
                        "Servico: %s\n" +
                        "Preco Final; %.2f %s\n\n" +
                        "Este valor foi calculado com a taxa de câmbio em tempo real.\n" +
                        "Obrigado por usares o nosso Marketplace!",
                nomeServico, precoConvertido, moeda
        );
        mensagem.setText(corpo);
        mailSender.send(mensagem);
    }

    public void enviarAlertaSeguranca(
            String emailDestino,
            String cidade,
            String pais
    ) {

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setTo(emailDestino);
        mensagem.setSubject("⚠️ Alerta de Segurança - Marketplace");

        mensagem.setText(
                "Aviso de Segurança:\n\n" +
                        "Detetámos uma nova atividade na tua conta do Marketplace " +
                        "a partir de " + cidade + ", " + pais + ".\n\n" +
                        "Se não foste tu, altera a tua password imediatamente!"
        );

        mailSender.send(mensagem);
    }
}
