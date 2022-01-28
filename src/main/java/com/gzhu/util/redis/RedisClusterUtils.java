//package com.gzhu.util.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class RedisClusterUtils implements RedisUtils {
//    @Autowired
//    private JedisCluster jedisCluster;
//    /**
//     * 指定缓存失效时间
//     * @param key 键
//     * @param time 时间(秒)
//     * @return
//     */
//    public boolean expire(String key,int time){
//        try {
//            if(time>0){
//                jedisCluster.expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 根据key 获取过期时间
//     * @param key 键 不能为null
//     * @return 时间(秒) 返回0代表为永久有效
//     */
//    public long getExpire(String key){
//        return jedisCluster.ttl(key);
//    }
//
//    /**
//     * 判断key是否存在
//     * @param key 键
//     * @return true 存在 false不存在
//     */
//    public boolean hasKey(String key){
//        try {
//            return jedisCluster.exists(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 删除缓存
//     * @param key 可以传一个值 或多个
//     */
//    @SuppressWarnings("unchecked")
//    public void del(String ... key){
//        if(key!=null&&key.length>0){
//            jedisCluster.del(key);
//        }
//    }
//
//    //============================String=============================
//    /**
//     * 普通缓存获取
//     * @param key 键
//     * @return 值
//     */
//    public Object get(String key){
//        return key==null?null:jedisCluster.get(key);
//    }
//
//    /**
//     * 普通缓存放入
//     * @param key 键
//     * @param value 值
//     * @return true成功 false失败
//     */
//    public boolean set(String key,String value) {
//        try {
//            jedisCluster.set(key,value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 普通缓存放入并设置时间
//     * @param key 键
//     * @param value 值
//     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
//     * @return true成功 false 失败
//     */
//    public boolean set(String key,String value,long time){
//        try {
//            if(time>0){
//                jedisCluster.psetex(key,time,value);
//            }else{
//                set(key, value);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 递增
//     * @param key 键
//     * @param delta 要增加几(大于0)
//     * @return
//     */
//    public long incr(String key, long delta){
//        if(delta<0){
//            throw new RuntimeException("递增因子必须大于0");
//        }
//        return jedisCluster.incrBy(key,delta);
//    }
//
//    /**
//     * 递减
//     * @param key 键
//     * @param delta 要减少几(小于0)
//     * @return
//     */
//    public long decr(String key, long delta){
//        if(delta<0){
//            throw new RuntimeException("递减因子必须大于0");
//        }
//        return jedisCluster.incrBy(key, -delta);
//    }
//
//    //================================Map=================================
//    /**
//     * HashGet
//     * @param key 键 不能为null
//     * @param item 项 不能为null
//     * @return 值
//     */
//    public Object hget(String key,String item){
//        return jedisCluster.hget(key, item);
//    }
//
//    /**
//     * HashSet
//     * @param key 键
//     * @param map 对应多个键值
//     * @return true 成功 false 失败
//     */
//    public boolean hmset(String key, Map<String,Object> map){
//        try {
//            jedisCluster.hmset(key,map);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * HashSet 并设置时间
//     * @param key 键
//     * @param map 对应多个键值
//     * @param time 时间(秒)
//     * @return true成功 false失败
//     */
//    public boolean hmset(String key, Map<String,String> map, int time){
//        try {
//            jedisCluster.hmset(key,map);
//            if(time>0){
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     * @param key 键
//     * @param item 项
//     * @param value 值
//     * @return true 成功 false失败
//     */
//    public boolean hset(String key,String item,String value) {
//        try {
//            jedisCluster.hset(key,item,value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     * @param key 键
//     * @param item 项
//     * @param value 值
//     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
//     * @return true 成功 false失败
//     */
//    public boolean hset(String key,String item,String value,int time) {
//        try {
//            jedisCluster.hset(key, item, value);
//            if(time>0){
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 删除hash表中的值
//     * @param key 键 不能为null
//     * @param item 项 可以使多个 不能为null
//     */
//    public void hdel(String key, String... item){
//        jedisCluster.hdel(key,item);
//    }
//
//    /**
//     * 判断hash表中是否有该项的值
//     * @param key 键 不能为null
//     * @param item 项 不能为null
//     * @return true 存在 false不存在
//     */
//    public boolean hHasKey(String key, String item){
//        return jedisCluster.hexists(key, item);
//    }
//
//    /**
//     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
//     * @param key 键
//     * @param item 项
//     * @param by 要增加几(大于0)
//     * @return
//     */
//    public double hincr(String key, String item,long by){
//        return jedisCluster.hincrBy(key, item, by);
//    }
//
//    /**
//     * hash递减
//     * @param key 键
//     * @param item 项
//     * @param by 要减少记(小于0)
//     * @return
//     */
//    public double hdecr(String key, String item,long by){
//        return jedisCluster.hincrBy(key, item,-by);
//    }
//
//    //============================set=============================
//    /**
//     * 根据key获取Set中的所有值
//     * @param key 键
//     * @return
//     */
//    public Set<String> sGet(String key){
//        try {
//            return jedisCluster.smembers(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 根据value从一个set中查询,是否存在
//     * @param key 键
//     * @param value 值
//     * @return true 存在 false不存在
//     */
//    public boolean sHasKey(String key,String value){
//        try {
//            return jedisCluster.sismember(key, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将数据放入set缓存
//     * @param key 键
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public long sSet(String key, Object...values) {
//        try {
//            return jedisCluster.opsForSet().add(key, values);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 将set数据放入缓存
//     * @param key 键
//     * @param time 时间(秒)
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public long sSetAndTime(String key,long time,Object...values) {
//        try {
//            Long count = jedisCluster.opsForSet().add(key, values);
//            if(time>0) {
//                expire(key, time);
//            }
//            return count;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 获取set缓存的长度
//     * @param key 键
//     * @return
//     */
//    public long sGetSetSize(String key){
//        try {
//            return jedisCluster.opsForSet().size(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 移除值为value的
//     * @param key 键
//     * @param values 值 可以是多个
//     * @return 移除的个数
//     */
//    public long setRemove(String key, Object ...values) {
//        try {
//            Long count = jedisCluster.opsForSet().remove(key, values);
//            return count;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//    //===============================list=================================
//
//    /**
//     * 获取list缓存的内容
//     * @param key 键
//     * @param start 开始
//     * @param end 结束  0 到 -1代表所有值
//     * @return
//     */
//    public List<Object> lGet(String key, long start, long end){
//        try {
//            return jedisCluster.opsForList().range(key, start, end);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 获取list缓存的长度
//     * @param key 键
//     * @return
//     */
//    public long lGetListSize(String key){
//        try {
//            return jedisCluster.opsForList().size(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 通过索引 获取list中的值
//     * @param key 键
//     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
//     * @return
//     */
//    public Object lGetIndex(String key,long index){
//        try {
//            return jedisCluster.opsForList().index(key, index);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public boolean lSet(String key, Object value) {
//        try {
//            jedisCluster.opsForList().rightPush(key, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     * @param key 键
//     * @param value 值
//     * @param time 时间(秒)
//     * @return
//     */
//    public boolean lSet(String key, Object value, long time) {
//        try {
//            jedisCluster.opsForList().rightPush(key, value);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     * @param key 键
//     * @param value 值
//     * @return
//     */
//    public boolean lSet(String key, List<Object> value) {
//        try {
//            jedisCluster.opsForList().rightPushAll(key, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     * @param key 键
//     * @param value 值
//     * @param time 时间(秒)
//     * @return
//     */
//    public boolean lSet(String key, List<Object> value, long time) {
//        try {
//            jedisCluster.opsForList().rightPushAll(key, value);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 根据索引修改list中的某条数据
//     * @param key 键
//     * @param index 索引
//     * @param value 值
//     * @return
//     */
//    public boolean lUpdateIndex(String key, long index,Object value) {
//        try {
//            jedisCluster.opsForList().set(key, index, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 移除N个值为value
//     * @param key 键
//     * @param count 移除多少个
//     * @param value 值
//     * @return 移除的个数
//     */
//    public long lRemove(String key,long count,Object value) {
//        try {
//            Long remove = jedisCluster.opsForList().remove(key, count, value);
//            return remove;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * setnx
//     * @param key
//     * @param value
//     * @return
//     */
//    public boolean setIfAbsent(String key, Object value){
//        try {
//            return jedisCluster.opsForValue().setIfAbsent(key,value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//}
