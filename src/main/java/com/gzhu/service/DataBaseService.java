package com.gzhu.service;

import com.gzhu.mapper.UserMapper;
import com.gzhu.pojo.user.User;
import com.gzhu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DataBaseService {
    @Autowired
    private UserMapper userMapper;

    public void autoCreateUser(){
        Set<String> stringSet = new HashSet<>();
        for(int i=0; i<1000; i++){
            String name = StringUtils.getRandomString(10);

            while(stringSet.contains(name)){
                name = StringUtils.getRandomString(10);
            }
            stringSet.add(name);
            User user = new User(name,StringUtils.getRandomString(10));
            userMapper.insert(user);
        }
    }
}
