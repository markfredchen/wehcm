package com.mcworkshop.wehcm.core.persistence;

import com.mcworkshop.wehcm.core.domain.message.MessageStatus;
import com.mcworkshop.wehcm.core.domain.message.PassiveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by markfredchen on 6/27/15.
 */
public interface PassiveMessageRepository extends JpaRepository<PassiveMessage, Long>{

    PassiveMessage findOneByMessageOID(UUID messageOID);
}
