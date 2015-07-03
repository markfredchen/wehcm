package com.mcworkshop.wehcm.integration.inbound;

import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by markfredchen on 7/1/15.
 */
@Controller
public class WebServiceMessageReceiver {

    @RequestMapping(value = "/sync/message", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void syncPassiveMessage(@RequestParam("data") String data) {
        JSONObject message = new JSONObject(data);
        PassiveMessage pm = new PassiveMessage();

    }
}
