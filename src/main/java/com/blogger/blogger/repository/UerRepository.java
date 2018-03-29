package com.blogger.blogger.repository;

import com.blogger.blogger.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户实体
 */
@Component
public interface UerRepository extends CrudRepository<User, Long> {


}
