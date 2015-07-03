package com.mcworkshop.wehcm.core.persistence;

import com.mcworkshop.wehcm.core.domain.flow.FormFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by markfredchen on 6/24/15.
 */
public interface FormFlowRepository extends JpaRepository<FormFlow, Long> {

    FormFlow findOneByFlowOID(UUID flowOID);
}
