package com.mcworkshop.wehcm.integration.inbound;

import com.mcworkshop.wehcm.core.domain.message.MessageStatus;
import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.core.persistence.PassiveMessageRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static com.mcworkshop.wehcm.constant.WeHCMConstants.*;

/**
 * Created by markfredchen on 7/1/15.
 */
@Controller
public class WebServiceMessageReceiver {

    @Autowired
    PassiveMessageRepository messageRepository;

    @RequestMapping(value = "/sync/message", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void syncPassiveMessage(@RequestParam("data") String data) {
        JSONObject message = new JSONObject(data);
        PassiveMessage pm = new PassiveMessage();
        pm.setMessageOID(UUID.fromString(message.getString(MESSAGE_KEY_MESSAGE_OID)));
        pm.setFlowName(message.getString(MESSAGE_KEY_FLOW_NAME));
        pm.setToUser(message.getString(PASSIVE_MESSAGE_KEY_TO_USER));
        pm.setFromUser(message.getString(MESSAGE_KEY_FROM_USER));
        pm.setData(message.getJSONObject(MESSAGE_KEY_DATA));
        pm.setAccountOID(UUID.fromString(message.getString(MESSAGE_KEY_ACCOUNT_OID)));
        pm.setStatus(MessageStatus.PENDING);
        pm.setActions(message.getJSONArray(PASSIVE_MESSAGE_KEY_ACTIONS));
        messageRepository.save(pm);
    }


    @RequestMapping(value = "/sync/messages", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void syncPassiveMessages(@RequestParam("data") String data) {
        JSONObject msg = new JSONObject(data);
        JSONArray messages = msg.getJSONArray("messages");
        for (int i = 0; i < messages.length(); i++) {
            JSONObject message = messages.getJSONObject(i);
            PassiveMessage pm = new PassiveMessage();
            pm.setMessageOID(UUID.fromString(message.getString(MESSAGE_KEY_MESSAGE_OID)));
            pm.setFlowName(message.getString(MESSAGE_KEY_FLOW_NAME));
            pm.setToUser(message.getString(PASSIVE_MESSAGE_KEY_TO_USER));
            pm.setFromUser(message.getString(MESSAGE_KEY_FROM_USER));
            pm.setData(message.getJSONObject(MESSAGE_KEY_DATA));
            pm.setAccountOID(UUID.fromString(message.getString(MESSAGE_KEY_ACCOUNT_OID)));
            pm.setStatus(MessageStatus.PENDING);
            pm.setActions(message.getJSONArray(PASSIVE_MESSAGE_KEY_ACTIONS));
            messageRepository.save(pm);
        }
    }
}
