package com.gzhu.mapper;

import com.gzhu.pojo.Goods;
import org.apache.ibatis.jdbc.SQL;

public class GoodsSqlProvider {
    public String insertSelective(Goods record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("goods");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getStock() != null) {
            sql.VALUES("stock", "#{stock,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Goods record) {
        SQL sql = new SQL();
        sql.UPDATE("goods");
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getStock() != null) {
            sql.SET("stock = #{stock,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}