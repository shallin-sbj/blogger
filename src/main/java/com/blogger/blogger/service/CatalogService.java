package com.blogger.blogger.service;

import com.blogger.blogger.domain.Catalog;
import com.blogger.blogger.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CatalogService {
    /**
     * 根据用户查询
     * @param user
     * @return
     */
    List<Catalog> listCatalogs(User user);

    /**
     * 根据用户查询
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user,String name);

    Catalog saveCatalog(Catalog catalog);

    void  removeCatalog(Long id);

    Catalog getCatalogById(Long catalogId);

}
