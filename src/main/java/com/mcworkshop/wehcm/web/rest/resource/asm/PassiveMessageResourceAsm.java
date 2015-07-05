package com.mcworkshop.wehcm.web.rest.resource.asm;

import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.web.rest.resource.DataFieldResource;
import com.mcworkshop.wehcm.web.rest.resource.FieldResource;
import com.mcworkshop.wehcm.web.rest.resource.PassiveMessageResource;
import org.json.JSONObject;
import org.springframework.hateoas.ResourceAssembler;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by markfredchen on 7/5/15.
 */
public class PassiveMessageResourceAsm implements ResourceAssembler<PassiveMessage, PassiveMessageResource> {
    @Override
    public PassiveMessageResource toResource(PassiveMessage pm) {
        PassiveMessageResource pmr = new PassiveMessageResource();
        pmr.setMessageOID(pm.getMessageOID());
        pmr.setAccountOID(pm.getAccountOID());
        pmr.setFlowName(pm.getFlowName());
        pmr.setFromUser(pm.getFromUser());
        pmr.setToUser(pm.getToUser());
        JSONObject dataJSON = pm.getData();
        for (String key : dataJSON.keySet()) {
            DataFieldResource field = new DataFieldResource();
            String[] config = key.split("\\|");
            field.setSequence(Integer.valueOf(config[0]));
            field.setName(config[1]);
            field.setValue(dataJSON.getString(key));
            pmr.getData().add(field);
        }
        Collections.sort(pmr.getData(), (o1, o2) -> {
            if (o1.getSequence() > o2.getSequence()) {
                return 1;
            } else if(o1.getSequence() == o2.getSequence()) {
                return 0;
            } else {
                return -1;
            }

        });
        for (int i = 0; i < pm.getActions().length(); i++) {
            pmr.getActions().add(pm.getActions().getString(i));
        }
        return pmr;
    }
}
