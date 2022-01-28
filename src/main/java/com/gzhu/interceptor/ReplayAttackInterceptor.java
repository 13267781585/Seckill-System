package com.gzhu.interceptor;

import com.gzhu.interceptor.interfaces.ReplayAttackDefense;
import com.gzhu.pojo.user.UniqueToken;
import com.gzhu.util.EncryptUtils;
import com.gzhu.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 防御重放攻击
 */
@Component
public class ReplayAttackInterceptor implements HandlerInterceptor {
    @Value("${replay.salt}")
    private String REPLAY_SALT;

    @Value("${replay.expire}")
    private long REPLAY_EXPIRE;

    @Value("${replay.mode}")
    private String REPLAY_MODE;

    @Value("${replay.enable}")
    private String REPLAY_ENABLE;

    @Autowired
    private RedisUtils redisUtils;

    private static final String REPLAY_MODE_SERVER = "SERVER";
    private static final String REPLAY_MODE_CLIENT = "CLIENT";
    private static final String REPLAY_ENABLE_OPEN = "1";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!REPLAY_ENABLE_OPEN.equals(REPLAY_ENABLE)){
            return true;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //查询是否添加注解
            ReplayAttackDefense annotation = method.getAnnotation(ReplayAttackDefense.class);
            if(!Objects.isNull(annotation))
            {
                String uniqueToken = request.getHeader("unique_token");
                long expire = Long.parseLong(request.getHeader("expire_time"));
                String signature = request.getHeader("signature");

                String verifySignature = "";
                //判断是那种方式校验
                //客户端和服务端数字签名的算法不一样
                if(REPLAY_MODE_CLIENT.equals(REPLAY_MODE)){
                    verifySignature = EncryptUtils.MD5(uniqueToken + REPLAY_SALT + REPLAY_SALT + expire);
                }else{
                    verifySignature = EncryptUtils.MD5(uniqueToken + REPLAY_SALT + expire);
                }

                if(!verifySignature.equals(signature))
                {
                    throw new Exception("数据遭受篡改!");
                }

                if((expire - System.currentTimeMillis()) < 0L)
                {
                    throw new Exception("请求已过期!");
                }

                if(redisUtils.hasKey("replay:token:" + uniqueToken))
                {
                    throw new Exception("请求已提交!");
                }

                //数据校验正确且是第一次请求 将token放入redis
                redisUtils.set("replay:token:" + uniqueToken,null,REPLAY_EXPIRE);

                //如果是SERVER模式 需要更新头部中的token
                if(REPLAY_MODE_SERVER.equals(REPLAY_MODE))
                {
                    UniqueToken uniqueToken1 = UniqueToken.builder(REPLAY_EXPIRE,REPLAY_SALT);
                    response.setHeader("unique_token",uniqueToken1.getUniqueToken());
                    response.setHeader("expire_time",String.valueOf(uniqueToken1.getExpireTime()));
                    response.setHeader("signature",uniqueToken1.getSignature());
                }
            }
        }

        return true;
    }

}
