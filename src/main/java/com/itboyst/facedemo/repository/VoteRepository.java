package com.itboyst.facedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itboyst.facedemo.domain.Vote;

/**
 * Vote Repository接口.
 * 
 * @since 1.0.0 2017年6月6日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
}