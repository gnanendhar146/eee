package com.chp.BookStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.chp.BookStore.entity.User;
import com.chp.BookStore.enums.UserRole;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findFirstByEmail(String email);

	User findByUserRole(UserRole userRole);

}
