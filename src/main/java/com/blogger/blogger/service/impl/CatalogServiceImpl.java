package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.Catalog;
import com.blogger.blogger.domain.User;
import com.blogger.blogger.repository.CatalogRepository;
import com.blogger.blogger.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }

    @Override
    public List<Catalog> findByUserAndName(User user, String name) {
        return catalogRepository.findByUserAndName(user, name);
    }

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        List<Catalog> catalogList = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (!CollectionUtils.isEmpty(catalogList)) {
            throw new IllegalArgumentException("该分类已经存在");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public Catalog getCatalogById(Long catalogId) {
        Optional<Catalog> catalogOptional = catalogRepository.findById(catalogId);
        if (catalogOptional.isPresent()) {
            return catalogRepository.findById(catalogId).get();
        }
        return new Catalog(null, null);
    }


}
