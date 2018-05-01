package com.blogger.blogger.repository;

import com.blogger.blogger.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 *  用户Dao
 */
@Component
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户姓名分页查询
     * @param name
     * @param pageable
     * @return
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据用户号码进行查询
     */
   User findByUsername(String username);

    /**
     * 根据名称列表查询
     * @param usernames
     * @return
     */
    List<User> findByUsernameIn(Collection<String> usernames);

}
