package com.blogger.blogger.service;

import com.blogger.blogger.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    User saveOrUpdateUser(User user);

    User registerUser(User user);

    User queryUserById(Long userId);

    void deleteUserById(Long id);

    /**
     * 获取用户列表
     * @param
     * @return
     */
    List<User> queryAllUser();

    /**
     * 根据用户名模糊查询用户
     *
     * @param username
     * @param pageable
     * @return
     */
    Page<User> listUsersByNameLike(String username, Pageable pageable);


}
