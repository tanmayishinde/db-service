package com.root.dbservice.controllers;

import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.DefaultResponseVO;
import com.root.commondependencies.vo.DelRequestVO;
import com.root.commondependencies.vo.UserVO;
import com.root.dbservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/getUserByEmail")
    public UserVO getUserByEmailId(@RequestParam("email") String email) throws ValidationException {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/createUser")
    public DefaultResponseVO createUser(@RequestBody UserVO requestVO){
      return  userService.createUser(requestVO);
    }
    @DeleteMapping("/deleteUser")
    public DefaultResponseVO deleteUser(@RequestBody DelRequestVO userVO){
       return userService.deleteUser(userVO);
    }

}
