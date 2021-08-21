package com.ace.registeruser.service;

import com.ace.registeruser.vo.create.RegisterUserRequest;
import com.ace.registeruser.vo.create.CreateUserResponse;

public interface RegisterUserService {

	CreateUserResponse createUser(RegisterUserRequest registerUserRequest);
}
