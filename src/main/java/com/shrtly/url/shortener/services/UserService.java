package com.shrtly.url.shortener.services;

import com.shrtly.url.shortener.dtos.LoginUserDto;
import com.shrtly.url.shortener.dtos.UserSignupDto;
import com.shrtly.url.shortener.models.User;
import com.shrtly.url.shortener.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(UserSignupDto signupPayload) {
        Optional<User> existingUser = userRepository.findByEmail(signupPayload.getEmail());
        if (existingUser.isPresent()) {
            return null;
        }
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
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginPayload.getEmail(), loginPayload.getPassword());
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginPayload.getEmail(), loginPayload.getPassword()));
        authenticationProvider.authenticate(authentication); // authenticating the user
        return userRepository.findByEmail(loginPayload.getEmail()).orElseThrow();
    }
}
