package com.msgrJava.controller;


import com.msgrJava.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/edit_profile")
public class ControllerUser {

        @Autowired
        private ServiceUser userService;

}
