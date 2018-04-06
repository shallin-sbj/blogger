package com.blogger.blogger.repository;

import com.blogger.blogger.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 权限仓库
 */
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}
