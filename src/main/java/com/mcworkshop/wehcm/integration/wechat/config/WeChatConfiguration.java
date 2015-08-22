package com.mcworkshop.wehcm.integration.wechat.config;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.flow.FormFlow;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.core.persistence.FormFlowRepository;
import com.mcworkshop.wehcm.core.persistence.UserRepository;
import com.mcworkshop.wehcm.core.util.MessageUtil;
import com.mcworkshop.wehcm.integration.wechat.config.wrapper.WeChatServiceWrapper;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.*;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutNewsMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by markfredchen on 8/15/15.
 */
@Component
@Scope(value = "singleton")
public class WeChatConfiguration {

    private Map<String, WeChatServiceWrapper> configMap = new HashMap<>();

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    FormFlowRepository formFlowRepository;

    public WeChatServiceWrapper getWeChatServicer(String domain) {
        if (configMap.size() == 0) {
            List<Account> accounts = accountRepository.findAll();
            for (Account account : accounts) {
                WeChatServiceWrapper wrapper = new WeChatServiceWrapper();
                WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
                config.setCorpId(account.getCorpID());
                config.setCorpSecret(account.getCorpSecret());
                config.setAgentId(account.getAgentID());
                config.setToken(account.getWxToken());
                config.setAesKey(account.getWxAESKey());
                WxCpService service = new WxCpServiceImpl();
                service.setWxCpConfigStorage(config);
                WxCpCryptUtil wxCpCryptUtil = new WxCpCryptUtil(config);
                wrapper.setService(service);
                wrapper.setConfig(config);
                wrapper.setCryptUtil(wxCpCryptUtil);
                WxCpMessageRouter router = new WxCpMessageRouter(service);
                WxCpMessageInterceptor authorizeInterceptor = (wxMessage, context, wxCpService, sessionManager) -> {
                    System.out.println("Entering Auth Interceptor");
                    return userRepository.findUserByAccountOIDAndWxUserID(account.getAccountOID(), wxMessage.getFromUserName()) == null;
                };
                WxCpMessageHandler authorizeHandler = new WxCpMessageHandler() {
                    @Override
                    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) throws WxErrorException {
                        System.out.println("entering auth handler");
                        return null;
                    }
                };
                WxCpMessageHandler handler = (wxMessage, context, wxCpService, sessionManager) -> {
                    System.out.println("Entering Evnet handler");
                    String flowName = wxMessage.getEventKey();
                    FormFlow formFlow = formFlowRepository.findOneByAccountOIDAndName(account.getAccountOID(), flowName);
                    if (formFlow != null) {
                        WxCpXmlOutNewsMessage.Item item = new WxCpXmlOutNewsMessage.Item();
                        item.setTitle(MessageUtil.getMessage("wechat." + flowName + ".title", messageSource));
                        item.setDescription(MessageUtil.getMessage("wechat." + flowName + ".description", messageSource));
                        item.setUrl(buildFormFlowURL(account, wxMessage.getFromUserName(), flowName));
                        return WxCpXmlOutMessage.NEWS().toUser(wxMessage.getFromUserName()).fromUser(wxMessage.getToUserName()).addArticle(item).build();
                    } else {
                        return null;
                    }
                };
                WxCpMessageHandler viewEventHandler = (wxMessage, context, wxCpService, sessionManager) -> {
                    System.out.println("Entering View Event Handler");
                    System.out.println(wxMessage.getFromUserName());
                    return null;
                };
                WxCpMessageHandler exceptionHandler = (wxMessage, context, wxCpService, sessionManager) -> {
                    System.out.println("entering exception handler");
                    // Return Warning Message
                    return WxCpXmlOutMessage.TEXT().toUser(wxMessage.getFromUserName()).content(MessageUtil.getMessage("wechat.error.message", messageSource)).build();
                };
                router
                    .rule()
                        .async(false)
                        .agentId(Integer.valueOf(account.getAgentID()))
                        .interceptor(authorizeInterceptor)
                        .handler(authorizeHandler)
                    .next()
                    .rule()
                        .async(false)
                        .msgType(WxConsts.XML_MSG_EVENT)
                        .event("click")
                        .handler(handler)
                    .end()
                    .rule()
                        .async(false)
                        .handler(exceptionHandler).
                    end();
                wrapper.setRouter(router);
                configMap.put(account.getDomain(), wrapper);
            }
        }
        return configMap.get(domain);
    }

    private String buildFormFlowURL(Account account, String userID, String flowName) {
        StringBuilder s = new StringBuilder();
        s.append("http://");
        s.append(account.getDomain());
        s.append(":8081/#/flow?userOID=78f5c39a-f52e-3315-8074-b3ae7141b769&flowName=");
        s.append(flowName);
        return s.toString();
    }
}
