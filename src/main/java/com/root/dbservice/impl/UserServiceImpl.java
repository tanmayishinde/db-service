package com.root.dbservice.impl;

import com.root.commondependencies.vo.DelRequestVO;
import com.root.commondependencies.vo.DefaultResponseVO;
import com.root.commondependencies.vo.UserVO;
import com.root.dbservice.entities.UserEntity;
import com.root.dbservice.repo.UserRepo;
import com.root.dbservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserVO getUserByEmail(String email) {
        UserVO userVO = new UserVO();
        Optional<UserEntity> userEntityOptional = userRepo.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            userVO.setName(userEntity.getName());
            userVO.setEmail(userEntity.getEmail());
            userVO.setPassword(userEntity.getPassword());
            userVO.setRole(userEntity.getRole());
            return userVO;
        }
        return userVO;

    }

    @Override
    public DefaultResponseVO createUser(UserVO requestVO) {
        DefaultResponseVO defaultResponse = new DefaultResponseVO();
        defaultResponse.setStatusCode("200");
        defaultResponse.setMessage("SUCCESS");
        UserEntity userEntity = new UserEntity();
        userEntity.setName(requestVO.getName());
        userEntity.setEmail(requestVO.getEmail());
        userEntity.setPassword(requestVO.getPassword());
        userEntity.setRole(requestVO.getRole());
        userRepo.save(userEntity);
        return defaultResponse;
    }

    @Override
    public DefaultResponseVO deleteUser(DelRequestVO userVO) {
        DefaultResponseVO defaultResponse = new DefaultResponseVO();
        defaultResponse.setStatusCode("200");
        defaultResponse.setMessage("SUCCESS");
        userRepo.deleteUserByEmail(userVO.getEmailId());
        return defaultResponse;

    }

}
