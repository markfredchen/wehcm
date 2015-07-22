package com.mcworkshop.wehcm.core.service;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.enumeration.IntegrationType;
import com.mcworkshop.wehcm.core.domain.message.FormFlowMessage;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.integration.outbound.EmailMessageSender;
import com.mcworkshop.wehcm.integration.outbound.MessageSender;
import com.mcworkshop.wehcm.integration.outbound.WebServiceMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by markfredchen on 7/3/15.
 */

@Service
public class FormFlowMessageService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmailMessageSender emailMessageSender;

    @Autowired
    WebServiceMessageSender webServiceMessageSender;

    public void handleFormFlowMessage(FormFlowMessage message) {
        Account account = accountRepository.findOneByAccountOID(message.getAccountOID());
        MessageSender sender = getMessageSender(account.getIntegrationType());
        sender.sendFormFlowMessage(account, message.toJSONObject());
    }

    private MessageSender getMessageSender(IntegrationType integrationType) {
        if (integrationType.equals(IntegrationType.EMAIL)) {
            return emailMessageSender;
        } else {
            return webServiceMessageSender;
        }
    }
}
