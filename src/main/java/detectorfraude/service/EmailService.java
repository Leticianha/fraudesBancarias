package detectorfraude.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    private static final String REMETENTE = "letici4cr1s@gmail.com"; // seu e-mail de envio
    private static final String SENHA = "masr ssfo qzxs yyge";   // senha de app (Gmail)

    public static void enviarEmail(String destinatario, String assunto, String mensagemTexto) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(REMETENTE));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensagem.setSubject(assunto);
            mensagem.setText(mensagemTexto);

            Transport.send(mensagem);
            System.out.println("📧 E-mail enviado com sucesso para " + destinatario);

        } catch (MessagingException e) {
            System.err.println("❌ Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
