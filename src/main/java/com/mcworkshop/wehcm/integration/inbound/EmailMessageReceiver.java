package com.mcworkshop.wehcm.integration.inbound;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.enumeration.IntegrationType;
import com.mcworkshop.wehcm.core.domain.message.MessageStatus;
import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.core.persistence.PassiveMessageRepository;
import com.mcworkshop.wehcm.core.persistence.UserRepository;
import com.mcworkshop.wehcm.integration.wechat.WeChatMessageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static com.mcworkshop.wehcm.constant.WeHCMConstants.*;

/**
 * Created by markfredchen on 7/1/15.
 */
@Service
public class EmailMessageReceiver {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PassiveMessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WeChatMessageService weChatMessageService;

    public void synchronizeEmailMessages() throws Exception {
        List<Account> accounts = accountRepository.findByIntegrationType(IntegrationType.EMAIL);
        for (Account account : accounts) {
            System.out.println(account.getName());
            JSONObject emailConfig = new JSONObject(account.getTarget());
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            props.setProperty("mail.imaps.class", "com.sun.mail.imap.IMAPSSLStore");
            Session session = Session.getDefaultInstance(props);
            System.out.println(emailConfig.getString(EMAIL_CONFIG_PROTOCOL_TYPE));
            Store store = session.getStore(emailConfig.getString(EMAIL_CONFIG_PROTOCOL_TYPE));
            store.connect(emailConfig.getString(EMAIL_CONFIG_HOST),
                emailConfig.getInt(EMAIL_CONFIG_POST),
                emailConfig.getString(EMAIL_CONFIG_USER),
                emailConfig.getString(EMAIL_CONFIG_PASSWORD));
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println(message.getSubject());
                if(message.getSubject().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
                    System.out.println(message.getContent());
                    JSONObject data = new JSONObject(new String(message.getContent().toString()));
                    System.out.println(data.toString());
                    PassiveMessage pm = new PassiveMessage();
                    pm.setMessageOID(UUID.fromString(data.getString(MESSAGE_KEY_MESSAGE_OID)));
                    pm.setAccountOID(account.getAccountOID());
                    pm.setFlowName(data.getString(MESSAGE_KEY_FLOW_NAME));
                    pm.setData(data.getJSONObject(MESSAGE_KEY_DATA));
                    pm.setActions(data.getJSONArray(PASSIVE_MESSAGE_KEY_ACTIONS));
                    pm.setToUser(data.getString(PASSIVE_MESSAGE_KEY_TO_USER));
                    pm.setFromUser(data.getString(MESSAGE_KEY_FROM_USER));
                    pm.setStatus(MessageStatus.PENDING);
                    messageRepository.save(pm);
                    System.out.println("====saved====");
                    weChatMessageService.sendTextMessage(account, pm);
                    message.setFlag(Flags.Flag.DELETED, true);
                }
            }

            inbox.close(false);
            store.close();
        }
    }
}
