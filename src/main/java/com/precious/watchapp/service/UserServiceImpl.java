package com.precious.watchapp.service;

import com.precious.watchapp.model.User;
import com.precious.watchapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User register(User user) {
        User existing = repo.findByEmail(user.getEmail());

        if (existing != null) return null;

        return repo.save(user);
    }

    @Override
    public User login(String email, String password) {

        User user = repo.findByEmail(email);

        if (user == null) return null;

        if (!user.getPassword().equals(password)) return null;

        return user;
    }
}
