package com.mcworkshop.wehcm.web.rest.controller;

import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.core.persistence.PassiveMessageRepository;
import com.mcworkshop.wehcm.core.service.PassiveMessageService;
import com.mcworkshop.wehcm.web.rest.resource.PassiveMessageResource;
import com.mcworkshop.wehcm.web.rest.resource.asm.PassiveMessageResourceAsm;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.mcworkshop.wehcm.core.domain.message.Message.MESSAGE_KEY_MESSAGE_OID;

/**
 * Created by markfredchen on 6/27/15.
 */
@Controller
@PropertySource("classpath:application.properties")
public class PassiveFlowController {

    @Value("${wehcm.account.path}")
    private String accountPath;

    @Autowired
    PassiveMessageRepository messageRepository;

    @Autowired
    PassiveMessageService passiveMessageService;

    @Transactional
    @RequestMapping(value = "/passive/flow/{messageOID}", method = RequestMethod.GET)
    @ResponseBody
    public PassiveMessageResource loadPassiveMessage(@PathVariable("messageOID") UUID messageOID) {
        PassiveMessage passiveMessage = messageRepository.findOneByMessageOID(messageOID);
        return new PassiveMessageResourceAsm().toResource(passiveMessage);
    }


    @RequestMapping(value = "/passive/flow/action", method = RequestMethod.POST)
    @ResponseBody
    public String handlePassiveMessageAction(@RequestBody String data) {
        JSONObject dataJSON = new JSONObject(data);
        UUID messageOID = UUID.fromString(dataJSON.getString(MESSAGE_KEY_MESSAGE_OID));
        String action = dataJSON.getString(PassiveMessage.PASSIVE_MESSAGE_KEY_ACTION);
        passiveMessageService.handlePassiveMessageAction(messageOID, action);

        JSONObject result = new JSONObject();
        result.put("messageOID", data);
        result.put("status", "OK");
        return result.toString();
    }
}
