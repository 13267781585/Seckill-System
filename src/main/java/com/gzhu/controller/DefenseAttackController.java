package com.gzhu.controller;

import com.gzhu.pojo.user.UniqueToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DefenseAttackController {
    @Value("${replay.salt}")
    private String REPLAY_SALT;

    @Value("${replay.expire}")
    private long REPLAY_EXPIRE;

    /**
     * 获取唯一的token,防御重放攻击和重复提交
     * @param request
     * @param response
     */
    @RequestMapping("/getUniqueToken")
    public UniqueToken getUniqueTokenHeader(HttpServletRequest request, HttpServletRequest response)
    {
        UniqueToken uniqueToken = UniqueToken.builder(REPLAY_EXPIRE,REPLAY_SALT);
        return uniqueToken;
    }

}
