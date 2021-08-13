package com.gzhu.service;

import com.gzhu.mapper.UserMapper;
import com.gzhu.pojo.user.User;
import com.gzhu.util.DateUtils;
import com.gzhu.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    private final static int SALT_LENGTH = 5;

    @Value("${qianduan.fixedsalt}")
    private String FIXED_SALT;

    @Value("${houduan.changesalt}")
    private String CHANGED_SALT;

    @Value("${user.loginTokenExpire}")
    private int USER_TOKEN_EXPIRE_TIME;

    public User userRegister(User user) throws Exception {
        String name = user.getName();
        String password = user.getPassword();
        //判断用户名是否被注册
        List<User> users = userMapper.selectUserByName(name);
        if(!CollectionUtils.isEmpty(users))
        {
            throw new Exception("用户名已被注册!");
        }

        //后端再次加密
        String salt = EncryptUtils.buildSalt(CHANGED_SALT,SALT_LENGTH);
        password = EncryptUtils.MD5(password + salt);

        user.setPassword(password);
        user.setSalt(salt);
        user.setToken(UUID.randomUUID().toString().replace("-",""));
        user.setTokenExpireDate(new Date(System.currentTimeMillis() + USER_TOKEN_EXPIRE_TIME));
        userMapper.insert(user);

        return user;
    }

    public User userLogin(User user) throws Exception {
        String name = user.getName();
        String password = user.getPassword();

        List<User> users = userMapper.selectUserByName(name);
        if(CollectionUtils.isEmpty(users))
        {
            throw new Exception("密码或用户名错误!");
        }
        User user1 = users.get(0);
        String salt = user1.getSalt();
        String password1 = user1.getPassword();

        password = EncryptUtils.MD5(password+salt);

        if(!password1.equals(password)){
            throw new Exception("密码或用户名错误!");
        }
        //更新token
        user1.setToken(UUID.randomUUID().toString().replace("-",""));
        user1.setTokenExpireDate(new Date(System.currentTimeMillis() + USER_TOKEN_EXPIRE_TIME));
        userMapper.save(user1);
        return user1;
    }

    public User tokenLogin(String loginToken) throws Exception {
        List<User> users = userMapper.selectUserByToken(loginToken);
        User user = null;
        if (CollectionUtils.isEmpty(users) || users.size() > 1)
        {
            throw new Exception("token信息错误!");
        }
        user = users.get(0);

        Date date = DateUtils.getNow();
        //判断token信息是否过期
        if(date.after(user.getTokenExpireDate())){
            throw new Exception("token过期");
        }
        user.setToken(UUID.randomUUID().toString().replace("-",""));
        user.setTokenExpireDate(new Date(System.currentTimeMillis() + USER_TOKEN_EXPIRE_TIME));
        userMapper.save(user);
        return user;
    }
}
