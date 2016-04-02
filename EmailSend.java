package com.grad.ids;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Vaibhav
 */
public class EmailSend {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	//****Go to URL : https://www.google.com/settings/security/lesssecureapps and Turn on Setting for Less Secure Apps****//
    	
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("mangudkarabhishek", "@bhImangudkar0311");
                    }
                });

        try {
        	
        	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    		Date date = new Date();
    		
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mangudkarabhishek@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("abhiqk311@gmail.com"));
            message.setSubject("Intrusion Alert at "+dateFormat.format(date));
            message.setText("Dear Admin,"
                    + "\n\nDoctor tried to enter restricted area. Kindly take appropriate action to maintain safety & integrity of the System."+"\n\nRegards,\nIT Security Team");
            
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}


