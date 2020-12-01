package com.flashex.usermicroservice.repository;


import com.flashex.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User findByUsername(String username);
     Boolean existsByUsername(String username);
     Boolean existsByEmail(String email);
}