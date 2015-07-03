package com.mcworkshop.wehcm.integration;

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
        store.connect("imap.163.com", 993, "markfredchen@163.com", "mtcazrwjtrnsagrn");
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
}
