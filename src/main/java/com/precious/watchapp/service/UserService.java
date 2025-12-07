package com.precious.watchapp.service;

import com.precious.watchapp.model.User;

public interface UserService {

    User register(User user);

    User login(String email, String password);
}
