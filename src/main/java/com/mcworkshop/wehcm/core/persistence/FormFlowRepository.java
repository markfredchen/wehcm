package com.mcworkshop.wehcm.core.persistence;

import com.mcworkshop.wehcm.core.domain.flow.FormFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * Created by markfredchen on 6/24/15.
 */
public interface FormFlowRepository extends JpaRepository<FormFlow, Long> {

    @Query("SELECT ff FROM FormFlow ff WHERE ff.account.accountOID = ?1 and ff.name = ?2")
    FormFlow findOneByAccountOIDAndName(UUID accountOID, String name);
}
