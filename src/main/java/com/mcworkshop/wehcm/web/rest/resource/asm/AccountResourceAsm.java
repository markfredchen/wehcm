package com.mcworkshop.wehcm.web.rest.resource.asm;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.web.rest.resource.AccountResource;
import org.springframework.hateoas.ResourceAssembler;

/**
 * Created by markfredchen on 6/24/15.
 */
public class AccountResourceAsm implements ResourceAssembler<Account, AccountResource> {


    @Override
    public AccountResource toResource(Account account) {
        AccountResource ar = new AccountResource();

        return null;
    }
}
