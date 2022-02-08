package se.kth.iv1201.iv1201recruitmentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import se.kth.iv1201.iv1201recruitmentapp.model.Role;
import se.kth.iv1201.iv1201recruitmentapp.model.User;
import se.kth.iv1201.iv1201recruitmentapp.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Argon2PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRoleToAuthority(user.getRoleId())
        );

    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role) {
        return List.of(role::getName);
    }
}
