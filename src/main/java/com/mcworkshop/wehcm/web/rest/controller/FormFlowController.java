package com.mcworkshop.wehcm.web.rest.controller;

import com.mcworkshop.wehcm.core.domain.User;
import com.mcworkshop.wehcm.core.domain.flow.FormField;
import com.mcworkshop.wehcm.core.domain.flow.FormFlow;
import com.mcworkshop.wehcm.core.domain.message.FormFlowMessage;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.core.persistence.FormFlowRepository;
import com.mcworkshop.wehcm.core.persistence.UserRepository;
import com.mcworkshop.wehcm.core.service.FormFlowMessageService;
import com.mcworkshop.wehcm.integration.wechat.config.WeChatConfiguration;
import com.mcworkshop.wehcm.integration.wechat.config.wrapper.WeChatServiceWrapper;
import com.mcworkshop.wehcm.web.rest.resource.ConstraintsResource;
import com.mcworkshop.wehcm.web.rest.resource.FieldResource;
import com.mcworkshop.wehcm.web.rest.resource.FormFlowResource;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

import static com.mcworkshop.wehcm.constant.WeHCMConstants.*;

/**
 * Created by markfredchen on 6/24/15.
 */
@Controller
public class FormFlowController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FormFlowRepository formFlowRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    FormFlowMessageService formFlowMessageService;

    @Autowired
    WeChatConfiguration weChatConfiguration;

    @RequestMapping(value = "/form/flow", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public FormFlowResource getFlow(HttpServletRequest request, @RequestParam("code") String code, @RequestParam("flowName") String flowName, @RequestParam("accountOID") UUID accountOID) {

        try {
            WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(request.getServerName());
            User user = userRepository.findOneByWxUserID(wrapper.getService().oauth2getUserInfo(code)[0]);
            FormFlow formFlow = formFlowRepository.findOneByAccountOIDAndName(accountOID, flowName);
            FormFlowResource result = new FormFlowResource();
            result.setAccountOID(accountOID.toString());
            result.setUserOID(user.getUserOID().toString());
            result.setUsername(user.getWxUserID());
            result.setFullName(user.getFullName());
            result.setFlowName(formFlow.getName());
            result.setFields(new ArrayList<>());
            for (FormField field : formFlow.getFields()) {
                FieldResource f = new FieldResource();
                f.setName(field.getName());
                f.setLabelKey(field.getLabelKey());
                f.setInputType(field.getInputType());
                f.setValueType(field.getValueType());
                f.setSequence(field.getSequence());
                f.setDefaultValue(field.getDefaultValue());
                f.setIsReadonly(field.isReadonly());
                if (field.getOptions() != null) {
                    f.setOptions(new ArrayList<>());
                    for (int i = 0; i < field.getOptions().length(); i++) {
                        f.getOptions().add(field.getOptions().get(i).toString());
                    }
                }
                if (field.getConstraints() != null) {
                    ConstraintsResource cr = new ConstraintsResource();
                    cr.setRequired(field.getConstraints().getBoolean("required"));
                    f.setConstraints(cr);
                }
                result.getFields().add(f);
            }
            result.setMessageOID(UUID.randomUUID().toString());
            return result;
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/form/flow", method = RequestMethod.POST)
    public void handleFormFlow(@RequestParam("data") String data) {
        JSONObject json = new JSONObject(data);
        FormFlowMessage message = new FormFlowMessage();
        JSONObject messageData = new JSONObject();

        for (String key : json.keySet()) {
            if (key.equals(MESSAGE_KEY_MESSAGE_OID)) {
                message.setMessageOID(UUID.fromString(json.getString(key)));
            } else if (key.equals(MESSAGE_KEY_ACCOUNT_OID)) {
                message.setAccountOID(UUID.fromString(json.getString(key)));
            } else if (key.equals(MESSAGE_KEY_FLOW_NAME)) {
                message.setFlowName(json.getString(key));
            } else if (key.equals(MESSAGE_KEY_FROM_USER)) {
                message.setFromUser(json.getString(key));
            } else {
                messageData.put(key, json.getString(key));
            }
        }
        message.setData(messageData);
        formFlowMessageService.handleFormFlowMessage(message);
    }

}
