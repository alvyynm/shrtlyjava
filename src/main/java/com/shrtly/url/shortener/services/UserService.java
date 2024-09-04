package com.shrtly.url.shortener.services;

import com.shrtly.url.shortener.dtos.LoginUserDto;
import com.shrtly.url.shortener.dtos.UserSignupDto;
import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(UserSignupDto signupPayload) {
        User newUser = new User();
        System.out.println("Email:" + signupPayload.getEmail());
        newUser.setEmail(signupPayload.getEmail());
        newUser.setPassword(passwordEncoder.encode(signupPayload.getPassword()));
        newUser.setFullName(signupPayload.getFullName());
        newUser.setStatus("active");
        newUser.setUserRole("USER");
        return userRepository.save(newUser);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User deleteUser(Integer id) {
        User user = findById(id);
        return userRepository.save(user);
    }

    public User changeEmail(Integer id, String email) {
        User user = findById(id);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public User changePassword(Integer id, String password) {
        User user = findById(id);
        // TODO: Encrypt password before storage
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User login(LoginUserDto loginPayload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginPayload.getEmail(), loginPayload.getPassword()));

        return userRepository.findByEmail(loginPayload.getEmail()).orElseThrow();
    }
}
