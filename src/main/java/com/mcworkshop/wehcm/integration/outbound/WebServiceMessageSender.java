package com.mcworkshop.wehcm.integration.outbound;

import com.mcworkshop.wehcm.core.domain.Account;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by markfredchen on 7/1/15.
 */
@Service
public class WebServiceMessageSender implements MessageSender {

    @Override
    public void sendPassiveMessage(Account account, JSONObject result) {

    }

    @Override
    public void sendFormFlowMessage(Account account, JSONObject result) {

    }

}
