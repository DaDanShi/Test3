package com.example.demo.dao;

import com.example.demo.model.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<Users, Integer> {
    /**
     * 使用 JPA 自带的查询方法 findByUserName 通过 userName(用户名) 获取用户信息
     * @param username
     * @return
     */
    @Query(value = "select * from t_user where username = ?1", nativeQuery = true)
    Users findByUsername(String username);
}
