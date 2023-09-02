package com.root.dbservice.service;



import com.root.commondependencies.vo.DefaultResponseVO;
import com.root.commondependencies.vo.DelRequestVO;
import com.root.commondependencies.vo.UserVO;


public interface UserService {

    UserVO getUserByEmail(String email);
    DefaultResponseVO createUser(UserVO requestVO);
    DefaultResponseVO deleteUser(DelRequestVO userVO);


}
