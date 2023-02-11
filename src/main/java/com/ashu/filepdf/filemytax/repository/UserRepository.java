package com.ashu.filepdf.filemytax.repository;

import com.ashu.filepdf.filemytax.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    @Query(value = "SELECT * FROM tax_user u WHERE u.user_id = :userId",
            nativeQuery = true)
    User findUser(@Param("userId") String userId);

    @Query(value = "SELECT * FROM tax_user u WHERE u.email = :email LIMIT 1",
            nativeQuery = true)
    User findByEmailUser(@Param("email") String email);
}
