package enviando_email;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class AppTest {

	
	private String userName = "xgamemaster000@gmail.com";
	private String senha = "sejghwwajxzzmblq";


	@Test
	public void testeEmail() {
		try {

			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true"); // autorizacao
			properties.put("mail.smtp.starttls", "true"); // autenticacao
			properties.put("mail.smtp.host", "smtp.gmail.com"); // servidor gmail
			properties.put("mail.smtp.port", "465"); // porta do servidor
			properties.put("mail.smtp.socketFactory.port", "465"); // Especifica porta a ser conectada pelo socket
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");// classe socket de
																								// conexao ao smtp

			Session session = Session.getInstance(properties, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}

			});
			
			Address[] toUser = InternetAddress.parse("airtonnwa2010@gmail.com,airtonnwa2011@gmail.com");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName)); //quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUser); //email de destino
			message.setSubject("Teste de email enviado com java"); // assunto email
			message.setText("Teste corpo de email - vc acaba de finalizar tarefa em java mail");
			
			
			Transport.send(message);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
