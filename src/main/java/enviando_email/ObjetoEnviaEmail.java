package enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "xgamemaster000@gmail.com";
	private String senha = "sejghwwajxzzmblq";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHTML) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
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

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // quem está enviando
		message.setRecipients(Message.RecipientType.TO, toUser); // email de destino
		message.setSubject(assuntoEmail); // assunto email

		if (envioHTML) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}

		Transport.send(message);
	}

	public void enviarEmailAnexo(boolean envioHTML) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
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

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // quem está enviando
		message.setRecipients(Message.RecipientType.TO, toUser); // email de destino
		message.setSubject(assuntoEmail); // assunto email

		/**
		 * parte 1 do email - texto e descrição do email
		 */
		MimeBodyPart corpoEmail = new MimeBodyPart();

		if (envioHTML) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}

		/**
		 * criação de lista com vários arquivos pdf
		 */
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);

		
		int index = 0;
		
		for (FileInputStream fileInputStream : arquivos) {

			/*
			 * Parte 2 - anexos em pdf
			 */
			MimeBodyPart anexoEmail = new MimeBodyPart();
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("AnexoEmail"+index+".pdf");

			multipart.addBodyPart(anexoEmail);
			index++;

		}

		message.setContent(multipart);

		Transport.send(message);
	}

	/**
	 * Simulador de pdf ou qualquer arquivo que possa ser enviado por anexo pode ser
	 * base64, byte[]
	 * 
	 * @return retorna um pdf em branco com o texto do paragrafo de exemplo
	 * @throws Exception
	 */
	private FileInputStream simuladorDePdf() throws Exception {
		Document document = new Document();
		File file = new File("Downloads.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do pdf anexo ao email"));
		document.close();

		return new FileInputStream(file);

	}

}
