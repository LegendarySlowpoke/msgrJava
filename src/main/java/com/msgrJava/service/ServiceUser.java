package com.msgrJava.service;


import com.msgrJava.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser {

    @Autowired
    private RepoUser userRepo;

}
