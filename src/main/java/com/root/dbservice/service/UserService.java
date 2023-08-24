package com.root.dbservice.service;

import com.root.dbservice.vo.UserVO;

public interface UserService {

    UserVO getUserByEmail(String email);

}
