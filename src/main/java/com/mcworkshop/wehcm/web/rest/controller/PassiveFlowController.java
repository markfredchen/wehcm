package com.mcworkshop.wehcm.web.rest.controller;

import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.core.exception.ValidationError;
import com.mcworkshop.wehcm.core.exception.ValidationException;
import com.mcworkshop.wehcm.core.persistence.PassiveMessageRepository;
import com.mcworkshop.wehcm.core.service.PassiveMessageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.util.UUID;

/**
 * Created by markfredchen on 6/27/15.
 */
@Controller
@PropertySource("classpath:config/application.yml")
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
    public String loadPassiveMessage(@PathVariable("messageOID") UUID messageOID) {
        PassiveMessage passiveMessage = messageRepository.findOneByMessageOID(messageOID);

        JSONObject result = new JSONObject();
        result.put("messageOID", messageOID.toString());
        result.put("accountOID", passiveMessage.getAccountOID().toString());
        result.put("fromUser", passiveMessage.getFromUser());
        result.put("toUser", passiveMessage.getToUser());
        result.put("flowName", passiveMessage.getFlowName());

        JSONArray dataFields = new JSONArray();
        for (String key : passiveMessage.getData().keySet()) {
            JSONObject field = new JSONObject();
            field.put("name", key);
            field.put("value", passiveMessage.getData().get(key));
            dataFields.put(field);
        }
        result.put("data", dataFields);
        result.put("actions", passiveMessage.getActions());
        System.out.println(accountPath + passiveMessage.getAccountOID().toString() + "/default.css");
        result.put("hasCSS", new File(accountPath + passiveMessage.getAccountOID().toString() + "/default.css").exists());
        return result.toString();
    }


    @RequestMapping(value = "/passive/flow/action", method = RequestMethod.POST)
    @ResponseBody
    public String handlePassiveMessageAction(@RequestParam("messageOID") UUID messageOID, @RequestParam("action") String action) {
        passiveMessageService.handlePassiveMessageAction(messageOID, action);

        JSONObject result = new JSONObject();
        result.put("messageOID", messageOID.toString());
        result.put("status", "OK");
        return result.toString();
    }
}
