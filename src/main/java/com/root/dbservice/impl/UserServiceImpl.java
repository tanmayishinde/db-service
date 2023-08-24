package com.root.dbservice.impl;

import com.root.dbservice.entities.UserEntity;
import com.root.dbservice.repo.UserRepo;
import com.root.dbservice.service.UserService;
import com.root.dbservice.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserVO getUserByEmail(String email){
        UserVO userVO = new UserVO();
        Optional<UserEntity> userEntityOptional = userRepo.findByEmail(email);
        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            userVO.setName(userEntity.getName());
            userVO.setEmail(userEntity.getEmail());
            userVO.setPassword(userEntity.getPassword());
            userVO.setRole(userEntity.getRole());
            return userVO;
        }
        return userVO;

    }
}
