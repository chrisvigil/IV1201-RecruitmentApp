package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import se.kth.iv1201.iv1201recruitmentapp.model.User;

public interface UserService extends UserDetailsService {

    User findByUsername (String username);
}
