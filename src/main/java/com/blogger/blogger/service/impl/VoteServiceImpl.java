package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.Vote;
import com.blogger.blogger.repository.VoteReposityory;
import com.blogger.blogger.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *  VoteService 服务实现.
 */

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteReposityory voteReposityory;

    @Override
    public Vote getVoteById(Long id) {
        Optional<Vote> voteOptional = voteReposityory.findById(id);
        if (voteOptional.isPresent()){
            return voteOptional.get();
        }
        return null;
    }

    @Override
    public void removeVote(Long id) {
        voteReposityory.deleteById(id);
    }
}
