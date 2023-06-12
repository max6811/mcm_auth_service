package com.maxcmart.mcm_auth_service.controllers;

import com.maxcmart.mcm_auth_service.exceptions.ResourceNotFoundException;
import com.maxcmart.mcm_auth_service.models.User;
import com.maxcmart.mcm_auth_service.security.CurrentUser;
import com.maxcmart.mcm_auth_service.security.UserPrincipal;
import com.maxcmart.mcm_auth_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
//    @PreAuthorize("hasRole('USER')")
    public User getUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getById(userPrincipal.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userPrincipal.getId())
        );
    }

    @PostMapping
    public ResponseEntity<User>  saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }
}
