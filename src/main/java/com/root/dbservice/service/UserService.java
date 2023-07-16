package com.root.dbservice.service;

import com.root.dbservice.vo.UserVO;
import com.root.redis.exception.ValidationException;

public interface UserService {

    UserVO getUserByEmail(String email) throws ValidationException;

}
