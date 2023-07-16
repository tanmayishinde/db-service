package com.root.dbservice.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {

    private String name;
    private String email;
    private String password;
    private String role;

}
