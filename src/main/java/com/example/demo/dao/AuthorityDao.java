package com.example.demo.dao;

import com.example.demo.model.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityDao extends JpaRepository<Authority, Integer> {
    /**
     * 通过 userName(用户名)获取用户权限信息
     * @param userName
     * @return
     */
    @Query(value =
            "select a.* from t_user c,t_authority a,t_user_authority ca " +
            "where ca.user_id=c.id and ca.authority_id=a.id and c.username =?1",
            nativeQuery = true)
    List<Authority> getAuthorityListByUserName(String userName);
}
