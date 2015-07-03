package com.mcworkshop.wehcm.core.service;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.User;

import java.util.UUID;

/**
 * Created by markfredchen on 6/23/15.
 */
public interface AccountService {

    UUID createUser(User user);

    UUID updateUser(UUID userOID, User user);

    User getUser(UUID userOID);

    UUID deleteUser(UUID userOID);

    UUID createAccount(Account account);

    UUID updateAccount(UUID accountOID, Account account);

    Account getAccount(UUID accountOID);

    UUID deleteAccount(UUID accountOID);

}
