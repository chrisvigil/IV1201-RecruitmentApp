package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.kth.iv1201.iv1201recruitmentapp.model.User;


public interface UserRepository extends JpaRepository <User, Long> {

    User findByUsername(String username);
}
