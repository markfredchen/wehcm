package com.mcworkshop.wehcm.integration.outbound;

import com.mcworkshop.wehcm.core.domain.Account;
import org.json.JSONObject;

/**
 * Created by markfredchen on 6/30/15.
 */
public interface MessageSender {

    void sendPassiveMessage(Account account, JSONObject result);

    void sendFormFlowMessage(Account account, JSONObject result);

}
