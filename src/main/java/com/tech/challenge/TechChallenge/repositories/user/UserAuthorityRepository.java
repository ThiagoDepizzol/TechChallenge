package com.tech.challenge.TechChallenge.repositories.user;

import com.tech.challenge.TechChallenge.domain.user.UserAuthority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true ")
    Page<UserAuthority> findAllActive(Pageable pageable);

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true " +//
                    "  and users.id = :userId " +//
                    "limit 1")
    Optional<UserAuthority> findOneActiveById(@Param("userId") Long userId);
}
