package com.maxcmart.mcm_auth_service.services;

import com.maxcmart.mcm_auth_service.exceptions.ResourceNotFoundException;
import com.maxcmart.mcm_auth_service.models.User;
import com.maxcmart.mcm_auth_service.repositories.UserRepository;
import com.maxcmart.mcm_auth_service.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

//    @Autowired
    private final UserRepository userRepository;

    public Optional<User> getById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }

    public User save(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(
                userfind -> {
                    throw new IllegalStateException("Error when to save new User " + userfind);
                }
        );

        return userRepository.insert(user);
    }
}
