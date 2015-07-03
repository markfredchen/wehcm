package com.mcworkshop.wehcm.core.persistence;

import com.mcworkshop.wehcm.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by markfredchen on 6/24/15.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUserOID(UUID userOID);

    User findOneByUsername(String username);

}
