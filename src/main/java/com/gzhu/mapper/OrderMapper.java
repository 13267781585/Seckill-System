package com.gzhu.mapper;

import com.gzhu.pojo.order.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface OrderMapper {
    @Delete({
        "delete from `order`",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into `order` (id, good_id, ",
        "user_id, status, ",
        "order_uuid, create_time, ",
        "pay_time)",
        "values (#{id,jdbcType=INTEGER}, #{goodId,jdbcType=INTEGER}, ",
        "#{userId,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, ",
        "#{orderUuid,jdbcType=CHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{payTime,jdbcType=TIMESTAMP})"
    })
    int insert(Order record);

    @InsertProvider(type=OrderSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insertSelective(Order record);

    @Select({
        "select",
        "id, good_id, user_id, status, order_uuid, create_time, pay_time",
        "from `order`",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="good_id", property="goodId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="order_uuid", property="orderUuid", jdbcType=JdbcType.CHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="pay_time", property="payTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Order selectByPrimaryKey(Integer id);

    @UpdateProvider(type=OrderSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Order record);

    @Update({
        "update `order`",
        "set good_id = #{goodId,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "order_uuid = #{orderUuid,jdbcType=CHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "pay_time = #{payTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Order record);

    @Select({
            "select",
            "id, good_id, user_id, status, order_uuid, create_time, pay_time",
            "from `order`",
            "where user_id = #{userId,jdbcType=INTEGER} and ",
            "order_uuid = #{uuid,jdbcType=CHAR}"
    })
    List<Order> selectByUserIdAndUUid(@Param("userId") Integer userId, @Param("uuid") String uuid);
}