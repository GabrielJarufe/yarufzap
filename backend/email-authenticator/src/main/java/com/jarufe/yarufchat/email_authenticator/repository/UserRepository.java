package com.jarufe.yarufchat.email_authenticator.repository;

import com.jarufe.yarufchat.email_authenticator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
