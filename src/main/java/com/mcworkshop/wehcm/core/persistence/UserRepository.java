package com.mcworkshop.wehcm.core.persistence;

import com.mcworkshop.wehcm.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * Created by markfredchen on 6/24/15.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUserOID(UUID userOID);

    User findOneByWxUserID(String wxUserID);

    @Query("SELECT u FROM User u WHERE u.account.accountOID = ?1 and u.wxUserID = ?2")
    User findUserByAccountOIDAndWxUserID(UUID accountOID, String wxUserID);

}
