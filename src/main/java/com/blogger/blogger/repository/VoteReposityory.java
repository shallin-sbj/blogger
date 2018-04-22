package com.blogger.blogger.repository;

import com.blogger.blogger.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteReposityory extends JpaRepository<Vote,Long> {
}
