package com.tech.challenge.TechChallenge.repositories.user;

import com.tech.challenge.TechChallenge.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true ")
    Page<User> findAllActive(Pageable pageable);

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true " +//
                    "  and users.id = :userId " +//
                    "limit 1")
    Optional<User> findOneActiveById(@Param("userId") Long userId);

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true " +//
                    "  and lower(users.login) = lower(cast(:login as text)) " +//
                    "limit 1 ")
    Optional<User> findByEmail(@Param("login") String login);

    @Query(nativeQuery = true, //
            value = "select users.* " +//
                    "from usr_users users " +//
                    "where users.active = true " +//
                    "  and lower(users.login) = lower(cast(:login as text)) " +//
                    "  and users.password = :password")
    Optional<User> getByLogin(@Param("login") String login, @Param("password") String password);
}
