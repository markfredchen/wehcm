package com.mcworkshop.wehcm.core.service;

import com.mcworkshop.wehcm.constant.WeHCMConstants;
import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.enumeration.IntegrationType;
import com.mcworkshop.wehcm.core.domain.message.MessageStatus;
import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.core.exception.ValidationError;
import com.mcworkshop.wehcm.core.exception.ValidationException;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.core.persistence.PassiveMessageRepository;
import com.mcworkshop.wehcm.core.util.JSONUtil;
import com.mcworkshop.wehcm.integration.outbound.EmailMessageSender;
import com.mcworkshop.wehcm.integration.outbound.MessageSender;
import com.mcworkshop.wehcm.integration.outbound.WebServiceMessageSender;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by markfredchen on 6/27/15.
 */
@Service
public class PassiveMessageService {

    @Autowired
    PassiveMessageRepository messageRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmailMessageSender emailMessageSender;

    @Autowired
    WebServiceMessageSender webServiceMessageSender;

    @Transactional
    public void handlePassiveMessageAction(UUID messageOID, String action) {
        PassiveMessage passiveMessage = messageRepository.findOneByMessageOID(messageOID);
        // message status shouldn't be processed.
        if (passiveMessage.getStatus().equals(MessageStatus.PROCESSED)) {
            ValidationError error = new ValidationError("passive.message.processed", "Message has been processed.");
            throw new ValidationException(error);
        }
        // validate action
        if (!JSONUtil.convertJSONArray(passiveMessage.getActions()).contains(action)) {
            ValidationError error = new ValidationError("passive.message.action.invalid", "Passive Message Action: action is not valid");
            throw new ValidationException(error);
        }

        // Send Data to integration point.
        Account account = accountRepository.findOneByAccountOID(passiveMessage.getAccountOID());
        MessageSender messageSender = getMessageSender(account.getIntegrationType());
        // Construct Passive Message Response Data
        JSONObject result = new JSONObject();
        result.put(WeHCMConstants.PASSIVE_MESSAGE_KEY_MESSAGE_OID, messageOID.toString());
        result.put(WeHCMConstants.PASSIVE_MESSAGE_KEY_ACTION, action);
        messageSender.sendPassiveMessage(account, result);
        // Update passiveMessage status
        passiveMessage.setStatus(MessageStatus.PROCESSED);
        messageRepository.save(passiveMessage);
    }

    private MessageSender getMessageSender(IntegrationType integrationType) {
        if(integrationType.equals(IntegrationType.EMAIL)) {
            return emailMessageSender;
        } else {
            return webServiceMessageSender;
        }
    }
}
