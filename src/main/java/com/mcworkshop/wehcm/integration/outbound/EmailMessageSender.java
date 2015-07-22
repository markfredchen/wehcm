package com.mcworkshop.wehcm.integration.outbound;

import com.mcworkshop.wehcm.config.EmailSettings;
import com.mcworkshop.wehcm.constant.WeHCMConstants;
import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.exception.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by markfredchen on 6/30/15.
 */
@Service
public class EmailMessageSender implements MessageSender {

    private static Log log = LogFactory.getLog(EmailMessageSender.class);

    @Autowired
    EmailSettings emailSettings;

    @Override
    public void sendPassiveMessage(Account account, JSONObject result) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(emailSettings.getHost());
            email.setAuthenticator(new DefaultAuthenticator(emailSettings.getUser(), emailSettings.getPassword()));
            email.setSslSmtpPort(emailSettings.getPort());
            email.setSSLOnConnect(true);
            email.setFrom(emailSettings.getFrom(), emailSettings.getFromDisplayName());
            email.setSubject(result.getString(WeHCMConstants.MESSAGE_KEY_MESSAGE_OID));
            email.setMsg(result.toString());
            email.addTo(new JSONObject(account.getTarget()).getString(WeHCMConstants.EMAIL_CONFIG_TARGET_EMAIL_ADDRESS));
            email.send();
            if(log.isDebugEnabled()) {
                log.debug("Passive Message[" + result.getString(WeHCMConstants.MESSAGE_KEY_MESSAGE_OID) + "] Result Email Sent");
            }
        } catch (EmailException e) {
            throw new SystemException(e);
        }
    }

    @Override
    public void sendFormFlowMessage(Account account, JSONObject result) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(emailSettings.getHost());
            email.setAuthenticator(new DefaultAuthenticator(emailSettings.getUser(), emailSettings.getPassword()));
            email.setSslSmtpPort(emailSettings.getPort());
            email.setSSLOnConnect(true);
            email.setFrom(emailSettings.getFrom(), emailSettings.getFromDisplayName());
            email.setSubject(result.getString(WeHCMConstants.MESSAGE_KEY_MESSAGE_OID));
            email.setMsg(result.toString());
            email.addTo(new JSONObject(account.getTarget()).getString(WeHCMConstants.EMAIL_CONFIG_TARGET_EMAIL_ADDRESS));
            email.send();
            System.out.println(result.getString(WeHCMConstants.MESSAGE_KEY_MESSAGE_OID));
            if(log.isDebugEnabled()) {
                log.debug("Form Flow Message[" + result.getString(WeHCMConstants.MESSAGE_KEY_MESSAGE_OID) + "] Result Email Sent");
            }
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }
}
