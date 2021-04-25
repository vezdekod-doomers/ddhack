package com.alesharik.hack.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LocalUserRepository extends PagingAndSortingRepository<LocalUser, UUID> {
    @Query("SELECT user FROM LocalUser user WHERE user.login = :login")
    Optional<LocalUser> findByLogin(@Param("login") String login);
}
