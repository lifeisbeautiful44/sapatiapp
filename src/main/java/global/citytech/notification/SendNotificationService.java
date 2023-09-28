package global.citytech.notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendNotificationService {

    public static  void sendMail(String from, String to,String subject, String message )
    {


        String host="smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.host", host); //SMTP Host
        props.put("mail.smtp.port", "465"); //ssl Port
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.auth","true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("srijansil.bohara444.lhng@gmail.com","healnghmchupqiwk");
            }
        });
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
            System.out.println("Successfull");

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}

