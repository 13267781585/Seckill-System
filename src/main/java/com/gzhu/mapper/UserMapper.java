package com.gzhu.mapper;

import com.gzhu.pojo.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> queryAll();

    @Insert("INSERT INTO `seckill`.`user`(`name`, `password`, `salt`, `token`, `token_expire_date`) VALUES (#{name}, #{password}, #{salt}, #{token}, #{tokenExpireDate});")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(User user);

    @Select("select * from user where name=#{name}")
    List<User> selectUserByName(String name);

    @Update("UPDATE `seckill`.`user` SET `name` = #{name}, `password` = #{password}, `salt` = #{salt}, `token` = #{token}, `token_expire_date` = #{tokenExpireDate} WHERE `id` = #{id};")
    int save(User user);

    @Select("select * from user where token=#{loginToken}")
    List<User> selectUserByToken(String loginToken);
}
