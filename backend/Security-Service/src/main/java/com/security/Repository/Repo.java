package com.security.Repository;

import com.security.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<Users,Long> {
    Users findByUserName(String username);
}
