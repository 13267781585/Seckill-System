package test.lua;

import com.gzhu.SecKillApplication;
import com.gzhu.util.redis.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecKillApplication.class})
public class PrintNum {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtils redisUtils;
    @Test
    public void lock() {
//        String path = "/lua/get.lua";
//        ClassPathResource resource = new ClassPathResource(path);
//        ScriptSource scriptSource = new ResourceScriptSource(resource);
//        DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
//        defaultRedisScript.setScriptSource(scriptSource);
//
//        //设置返回类型
//        defaultRedisScript.setResultType(Long.class);

//        List<Object> keyList = new ArrayList<>();
//        keyList.add("testName");
//
//        Long result = (Long) redisUtils.eval(path,keyList,Long.class,5);
//        System.out.println(result);

        List<String> keyList = new ArrayList<>();
        keyList.add("{seckill}");
        redisUtils.eval("/lua/init.lua",keyList,Long.class,0,30,25,0);
    }
}
