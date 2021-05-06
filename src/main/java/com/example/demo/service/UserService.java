package com.example.demo.service;

import com.example.demo.dao.AuthorityDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.domain.Authority;
import com.example.demo.model.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    private final static String User = "user_";
    private final static String AUTHORITY = "authorities_";
    @Resource
    private UserDao UserDao;
    @Resource
    private AuthorityDao authorityDao;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过 userName(用户名) 获取 User(用户信息)
     * @param username
     * @return
     */
    public Users getUser(String username) {
        Users user = null;
        // 调用 redisTemplate 中的方法 opsForValue().get(key) 获取对应 key 值
        Object o = redisTemplate.opsForValue().get(User + username);
        if (o != null) {
            user = (Users) o;
        } else {
            // 调用 UserDao 的 findByUserName 方法通过 userName(用户名)获取用户信息赋值给 User
            user = UserDao.findByUsername(username);
            System.out.println(user.getPassword());
            if (user != null) {
                // 调用 redisTemplate 中的方法 opsForValue().set(key,value) 新增一个字符串类型的值，key是键，value是值
                redisTemplate.opsForValue().set(User + username, user);
            }
        }
        return user;
    }

    /**
     * 通过 userName(用户名) 获取用户权限信息
     * @param userName
     * @return
     */
    public List<Authority> getUserAuthority(String userName) {
        List<Authority> authorityList = null;
        // 调用 redisTemplate 中的方法 opsForValue().get(key) 获取对应 key 值
        Object o = redisTemplate.opsForValue().get(AUTHORITY + userName);
        if (o != null) {
            authorityList = (List<Authority>) o;
        } else {
            // 调用 AuthorityDao 的 getAuthorityListByUserName 方法通过 userName(用户名)获取权限信息赋值给 authorityList
            authorityList = authorityDao.getAuthorityListByUserName(userName);
            if (authorityList.size() > 0) {
                // 调用 redisTemplate 中的方法 opsForValue().set(key,value) 新增一个字符串类型的值，key是键，value是值
                redisTemplate.opsForValue().set(AUTHORITY + userName, authorityList);
            }
        }
        return authorityList;
    }
}
