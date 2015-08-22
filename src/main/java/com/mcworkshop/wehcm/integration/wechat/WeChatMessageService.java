package com.mcworkshop.wehcm.integration.wechat;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import com.mcworkshop.wehcm.integration.wechat.config.WeChatConfiguration;
import com.mcworkshop.wehcm.integration.wechat.config.wrapper.WeChatServiceWrapper;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.messagebuilder.TextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * Created by markfredchen on 8/15/15.
 */
@Service
public class WeChatMessageService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private WeChatConfiguration weChatConfiguration;

    private static final String PASSIVE_FLOW_NAME_KEY_PREFIX = "passive.message.flow.name.";

    public void sendTextMessage(Account account, PassiveMessage passiveMessage) {
        WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(account.getDomain());
        try {
            TextBuilder textBuilder = WxCpMessage.TEXT();
            textBuilder.toUser(passiveMessage.getToUser());
            textBuilder.agentId(account.getAgentID());
            String flowName = messageSource.getMessage(PASSIVE_FLOW_NAME_KEY_PREFIX + passiveMessage.getFlowName(), new String[]{}, Locale.SIMPLIFIED_CHINESE);
            String flowURL = "http://" + account.getDomain() + ":8081/#/passive/flow?messageOID=" + passiveMessage.getMessageOID().toString();
            String[] messageArgs = {passiveMessage.getToUser(), flowName, flowURL};
            textBuilder.content(messageSource.getMessage("wechat.passive.message.summary.text", messageArgs, Locale.SIMPLIFIED_CHINESE));

            System.out.println(wrapper.getConfig().getAgentId());
            System.out.println(wrapper.getConfig().getAccessToken());
            wrapper.getService().messageSend(textBuilder.build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
