package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.User;
import com.blogger.blogger.repository.UserRepository;
import com.blogger.blogger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User saveOrUpdateUser(User user) {
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
        return new User();
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
}
