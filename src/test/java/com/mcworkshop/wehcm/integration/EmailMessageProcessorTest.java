package com.mcworkshop.wehcm.integration;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

import javax.mail.*;
import java.util.Properties;

/**
 * Created by markfredchen on 6/30/15.
 */
public class EmailMessageProcessorTest {


    @Test
    public void testReceiveMails() throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.class", "com.sun.mail.imap.IMAPSSLStore");
        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        store.connect("imap.126.com", 993, "wehcm_system@126.com", "otkpdbcjtickfkdo");
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        Message[] messages = inbox.getMessages();
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            if(message.getSubject().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
                System.out.println("==============================");
                System.out.println("Email #" + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
                message.setFlag(Flags.Flag.DELETED, true);
            }
        }

        inbox.close(false);
        store.close();
    }

    @Test
    public void testSendEmail() throws Exception{
        Email email = new SimpleEmail();
        email.setHostName("smtp.126.com");
        email.setAuthentication("wehcm_system@126.com", "otkpdbcjtickfkdo");
        email.setSslSmtpPort("994");
        email.setSSLOnConnect(true);
        email.setFrom("wehcm_system@126.com", "WeHCM");
        email.setSubject("Hello");
        email.setMsg("Hello World!");
        email.addTo("346120@qq.com");
        email.send();
    }
}
