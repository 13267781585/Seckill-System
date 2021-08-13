package com.gzhu.controller;

import com.gzhu.service.DataBaseService;
import com.gzhu.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    @Autowired
    private DataBaseService dataBaseService;
    @Autowired
    private SecKillService secKillService;

    @RequestMapping("/autoCreateUser")
    public void autoCreateUser(){
        dataBaseService.autoCreateUser();
    }

}
