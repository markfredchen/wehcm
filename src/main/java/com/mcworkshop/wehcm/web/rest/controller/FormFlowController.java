package com.mcworkshop.wehcm.web.rest.controller;

import com.mcworkshop.wehcm.core.domain.User;
import com.mcworkshop.wehcm.core.domain.flow.FormField;
import com.mcworkshop.wehcm.core.domain.flow.FormFlow;
import com.mcworkshop.wehcm.core.persistence.FormFlowRepository;
import com.mcworkshop.wehcm.core.persistence.UserRepository;
import com.mcworkshop.wehcm.web.rest.resource.FieldResource;
import com.mcworkshop.wehcm.web.rest.resource.FormFlowResource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by markfredchen on 6/24/15.
 */
@Controller
@PropertySource("classpath:config/application.yml")
public class FormFlowController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FormFlowRepository formFlowRepository;

    @Value("${wehcm.account.path}")
    private String accountPath;

    @RequestMapping(value = "/form/flow", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public FormFlowResource getFlow(@RequestParam("userOID") UUID userOID, @RequestParam("flowOID") UUID flowOID) {
        User user = userRepository.findOneByUserOID(userOID);
        FormFlow formFlow = formFlowRepository.findOneByFlowOID(flowOID);
        FormFlowResource result = new FormFlowResource();
        result.setAccountOID(user.getAccount().getAccountOID().toString());
        result.setUserOID(user.getUserOID().toString());
        result.setUsername(user.getUsername());
        result.setFlowOID(formFlow.getFlowOID().toString());
        result.setFlowName(formFlow.getName());
        result.setFields(new ArrayList<>());
        for(FormField field: formFlow.getFields()) {
            FieldResource f = new FieldResource();
            f.setName(field.getName());
            f.setLabelKey(field.getLabelKey());
            f.setInputType(field.getInputType());
            f.setValueType(field.getValueType());
            f.setSequence(field.getSequence());
            f.setDefaultValue(field.getDefaultValue());
            f.setIsReadonly(field.isReadonly());
            f.setOptions(field.getOptions());
            result.getFields().add(f);
        }
        result.setHasCSS(new File(accountPath + user.getAccount().getAccountOID().toString() + "/default.css").exists());
        result.setMessageOID(UUID.randomUUID().toString());
        return result;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/form/flow", method = RequestMethod.POST)
    public void handleFormFlow(@RequestParam("data") String data) {
        JSONObject json = new JSONObject(data);
        System.out.println(json.toString());
    }

}
