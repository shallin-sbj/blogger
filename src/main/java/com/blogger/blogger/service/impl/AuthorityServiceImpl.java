package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.Authority;
import com.blogger.blogger.repository.AuthorityRepository;
import com.blogger.blogger.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id) {
        Optional<Authority> optAuthority = authorityRepository.findById(id);
        if (optAuthority.isPresent()) {
            return optAuthority.get();
        }
        return new Authority();
    }
}
