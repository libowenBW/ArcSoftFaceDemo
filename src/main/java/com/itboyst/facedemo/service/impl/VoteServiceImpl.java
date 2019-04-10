package com.itboyst.facedemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itboyst.facedemo.domain.Vote;
import com.itboyst.facedemo.repository.VoteRepository;

/**
 * Vote 服务实现.
 * 
 * @since 1.0.0 2017年6月6日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Override
	public Vote getVoteById(Long id) {
		return voteRepository.findOne(id);
	}

	@Override
	public void removeVote(Long id) {
		voteRepository.delete(id);
	}

}
