package com.lc.dao;

import com.lc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//@Mapper
public interface UserDao {

//    @Select("select * from user")
    Map<String,Object> selectAllUser();


   // @Select("select * from price p1 inner join price_order p2 on p1.p_id = p2.p_id where p2.open_id = #{open_id} and p1.p_condition=#{num}")
    List<Map<String,Object>> selectPriceInnerJoinPriceOrder(Map<String, Object> map);

    void insertUsers(@Param("user") List user);

    User findUserByAccountAndPassword(@Param("username") String username, @Param("password") String password);

    User findUserByUserId(@Param("id") String id);

}
