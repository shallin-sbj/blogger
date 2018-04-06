package com.blogger.blogger.service;

import com.blogger.blogger.domain.Authority;

/**
 *  权限服务接口
 */
public interface AuthorityService {
    /**
     * 根据ID获取权限
     * @param id
     * @return
     */
    Authority getAuthorityById(Long id);
}
