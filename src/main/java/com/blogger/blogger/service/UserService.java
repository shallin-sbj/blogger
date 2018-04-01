package com.blogger.blogger.service;

import com.blogger.blogger.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户服务接口
 */
public interface UserService {

    User saveOrUpdateUser(User user);

    User registerUser(User user);

    User queryUserById(Long userId);

    void deleteUserById(Long id);

    /**
     * 根据用户名模糊查询用户
     *
     * @param userName
     * @param pageable
     * @return
     */
    Page<User> listUsersByNameLike(String userName, Pageable pageable);


}
