package com.backend.jwt.reposirtory;

import com.backend.jwt.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userDetailsRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
}
