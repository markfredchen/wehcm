package com.mcworkshop.wehcm.integration.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by markfredchen on 7/16/15.
 */
@Controller
public class EmailSyncTrigger {
    @Autowired
    EmailMessageReceiver receiver;

    @RequestMapping(value = "/email/trigger", method = RequestMethod.GET)
    public void triggerEmailSync() throws Exception {
        receiver.synchronizeEmailMessages();
    }
}
