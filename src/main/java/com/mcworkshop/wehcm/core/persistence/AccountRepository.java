package com.mcworkshop.wehcm.core.persistence;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.enumeration.IntegrationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by markfredchen on 6/23/15.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a.accountOID FROM Account a WHERE a.domain = ?1")
    UUID getAccountOIDByDomain(String domain);

    Account findOneByDomain(String domain);

    Account findOneByAccountOID(UUID accountOID);

    List<Account> findByIntegrationType(IntegrationType integrationType);


}
