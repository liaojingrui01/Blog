package com.ljr.blog.service;

import com.ljr.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}

