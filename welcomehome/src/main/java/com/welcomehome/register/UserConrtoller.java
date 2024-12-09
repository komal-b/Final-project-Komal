package com.welcomehome.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/welcomehome")
public class UserConrtoller {

    private Logger loggers = LoggerFactory.getLogger(UserConrtoller.class);
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public void registerUser(@RequestBody User user){
        try{
            userService.registerUser(user);
            loggers.info("User Registered Succesfully");
        }
        catch (Exception e){
            loggers.error("Registration Error: ", e);
        }
    }

    @GetMapping("/roles")
    public List<Role> getRoles(){
        List<Role> role = new ArrayList<>();
        try {
            role =  userService.getAllRoles();
        }
        catch (Exception e){
            loggers.error("Registration Error: ", e);
        }
        return role;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody User user) {
        return userService.loginUser(user);
    }

}
