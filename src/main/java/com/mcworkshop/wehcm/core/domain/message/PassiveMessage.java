package com.mcworkshop.wehcm.core.domain.message;

import com.mcworkshop.wehcm.core.domain.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by markfredchen on 6/16/15.
 */
@Entity
@Table(name = "T_PASSIVE_MESSAGE")
public class PassiveMessage extends Message{

    private String toUser;

    private JSONArray actions;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public JSONArray getActions() {
        return actions;
    }

    public void setActions(JSONArray actions) {
        this.actions = actions;
    }

    public void setActions(String  actions) {
        this.actions = new JSONArray(actions);
    }


    public static String PASSIVE_MESSAGE_KEY_TO_USER = "toUser";
    public static String PASSIVE_MESSAGE_KEY_ACTIONS = "actions";
    public static String PASSIVE_MESSAGE_KEY_ACTION = "action";
    @Override
    public JSONObject toJSONObject() {
        JSONObject message = super.toJSONObject();
        message.put(PASSIVE_MESSAGE_KEY_TO_USER, toUser);
        message.put(PASSIVE_MESSAGE_KEY_ACTIONS, actions);
        return message;
    }
}
