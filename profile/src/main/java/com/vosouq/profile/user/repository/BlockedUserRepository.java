package com.vosouq.profile.user.repository;

import com.vosouq.profile.user.model.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {

    Optional<BlockedUser> findByPhoneNumberAndUdidAndBlockUntilGreaterThanEqual(String phoneNumber, String udid, Date blockUntil);
}
