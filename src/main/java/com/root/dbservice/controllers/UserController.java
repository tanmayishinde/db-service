package com.root.dbservice.controllers;

import com.root.commondependencies.exception.ValidationException;
import com.root.dbservice.service.UserService;
import com.root.dbservice.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/getUserByEmail")
    public UserVO getUserByEmailId(@RequestParam("email") String email) throws ValidationException {
        return userService.getUserByEmail(email);
    }

}
