package com.mcworkshop.wehcm;

import com.mcworkshop.wehcm.constant.WeHCMConstants;
import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.User;
import com.mcworkshop.wehcm.core.domain.enumeration.IntegrationType;
import com.mcworkshop.wehcm.core.domain.flow.FormField;
import com.mcworkshop.wehcm.core.domain.flow.FormFlow;
import com.mcworkshop.wehcm.core.domain.message.MessageStatus;
import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.core.persistence.FormFlowRepository;
import com.mcworkshop.wehcm.core.persistence.PassiveMessageRepository;
import com.mcworkshop.wehcm.core.persistence.UserRepository;
import com.mcworkshop.wehcm.integration.wechat.WeChatMessageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class WehcmApplication implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormFlowRepository formFlowRepository;

    @Autowired
    private PassiveMessageRepository messageRepository;

    public static void main(String[] args) {
        SpringApplication.run(WehcmApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Account account = new Account();
        account.setName("MengNiu");
        account.setDomain("mengniu.wehcm.com");
        account.setIntegrationType(IntegrationType.EMAIL);
        JSONObject emailConfig = new JSONObject();
        emailConfig.put(WeHCMConstants.EMAIL_CONFIG_EMAIL_ADDRESS, "system@wehcm.com");
        emailConfig.put(WeHCMConstants.EMAIL_CONFIG_PROTOCOL_TYPE, "imaps");
        emailConfig.put(WeHCMConstants.EMAIL_CONFIG_HOST, "imap.exmail.qq.com");
        emailConfig.put(WeHCMConstants.EMAIL_CONFIG_POST, "993");
        emailConfig.put(WeHCMConstants.EMAIL_CONFIG_USER, "system@wehcm.com");
        emailConfig.put(WeHCMConstants.EMAIL_CONFIG_PASSWORD, "!QAZxsw2");
        emailConfig.put(WeHCMConstants.EMAIL_CONFIG_TARGET_EMAIL_ADDRESS, "mengniu@wehcm.com");
        account.setTarget(emailConfig.toString());
        account.setAccountOID(UUID.nameUUIDFromBytes("MengNiu".getBytes()));
        account.setWxToken("ZEUSHCM");
        account.setWxAESKey("YsY3st3zVCoCQFC04am4C8nOShirIofGDHJSaDaSPjj");
        account.setCorpID("wxe4aff45f5aba161b");
        account.setAgentID("1");
        account.setCorpSecret("Ib8lN6ZCLoxwjNe3oyoeszwsy-mXyPBRamgXCddytqNfZ6ZRx2LZpVzQGZCwwBlw");
        accountRepository.save(account);

        User markfredchen = new User();
        markfredchen.setUserOID(UUID.nameUUIDFromBytes("markfredchen".getBytes()));
        markfredchen.setAccount(account);
        markfredchen.setWxUserID("markfred.chen");
        markfredchen.setWxUsername("markfredchen");
        markfredchen.setIsDeleted(false);
        markfredchen.setFullName("\u9648\u6D69");
        markfredchen.setMobile("18616808523");
        markfredchen.setEmailAddress("markfred.chen@wehcm.com");
        userRepository.save(markfredchen);

//        User komixu = new User();
//        komixu.setUserOID(UUID.nameUUIDFromBytes("komixu".getBytes()));
//        komixu.setAccount(account);
//        komixu.setUsername("komixu");
//        komixu.setIsDeleted(false);
//        komixu.setWxUserID("13618685130");
//        komixu.setFullName("\u5F90\u96C1");
//        userRepository.save(komixu);

        FormField field1 = new FormField();
        field1.setName("endDate");
        field1.setValueType("Date");
        field1.setInputType("date");
        field1.setLabelKey("endDate");
        field1.setSequence(3);
        field1.setConstraints("{\"required\": true}");
        field1.setIsReadonly(false);


        FormField field2 = new FormField();
        field2.setName("startDate");
        field2.setValueType("Date");
        field2.setInputType("date");
        field2.setLabelKey("startDate");
        field2.setSequence(2);
        field2.setConstraints("{\"required\": true}");
        field2.setIsReadonly(false);

        FormField field3 = new FormField();
        field3.setName("type");
        field3.setValueType("String");
        field3.setInputType("select");
        field3.setLabelKey("type");
        field3.setSequence(1);
        field3.setOptions(new JSONArray(
            Arrays.asList("annual.leave",
                "leave",
                "sick.leave",
                "maternity.leave",
                "nursing.leave",
                "long.sick.leave",
                "injury.leave",
            "bereavement.leave")));
        field3.setConstraints("{\"required\": true}");
        field3.setIsReadonly(false);

        FormFlow flow = new FormFlow();
        flow.setName("PTO");
        flow.setAccount(account);
        flow.setFields(Arrays.asList(field3, field2, field1));
        flow.setMsgKey("pto");

        formFlowRepository.save(flow);

        PassiveMessage message = new PassiveMessage();
        message.setMessageOID(UUID.nameUUIDFromBytes("LEAVE".getBytes()));
        message.setAccountOID(UUID.nameUUIDFromBytes("MengNiu".getBytes()));
        message.setFlowName("PTO");
        message.setFromUser("komixu");
        message.setToUser("markfred.chen");
        message.setData("{\"1|select|type\": \"annual.leave\", \"2|date|startDate\": \"2015/06/02\", \"3|date|endDate\": \"2015/06/04\"}");
        JSONArray actions = new JSONArray();
        actions.put("approve");
        actions.put("decline");
        message.setActions(actions);
        message.setStatus(MessageStatus.PENDING);
        messageRepository.save(message);
    }
}
