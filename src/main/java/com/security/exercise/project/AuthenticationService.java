package com.security.exercise.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder encoder;


    @Autowired
    public AuthenticationService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init(){
        repository.save(new User(1L, "user", encoder.encode("pass")));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findUserByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
