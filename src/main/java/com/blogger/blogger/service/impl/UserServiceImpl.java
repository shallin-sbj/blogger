package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.User;
import com.blogger.blogger.repository.UserRepository;
import com.blogger.blogger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public User saveOrUpdateUser(User user) {
        if (user.getId() == null) {
            user.setEncodePassword(user.getPassword()); // 加密密码
        } else {
            // 判断密码是否做了变更
            User originalUser = userService.queryUserById(user.getId());
            String rawPassword = originalUser.getPassword();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePasswd = encoder.encode(user.getPassword());
            boolean isMatch = encoder.matches(rawPassword, encodePasswd);
            if (!isMatch) {
                user.setEncodePassword(user.getPassword());
            } else {
                user.setPassword(user.getPassword());
            }
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User queryUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get();
        }
        return new User(null,null,null,null);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> listUsersByNameLike(String username, Pageable pageable) {
        username = "%" + username +"%";
        return userRepository.findByNameLike(username, pageable);
    }

    @Override
    public List<User> queryAllUser() {
        return userRepository.findAll();
    }

    /**
     * 重写安全认证方法，在secuitiyConfig中使用
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user.getId()== null) {
            return null;
        }
//        logger.info("userDetails Size is :" + user.toString());
        return (UserDetails)user;
    }
}
