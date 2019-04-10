package com.itboyst.facedemo.service.impl;

import com.itboyst.facedemo.domain.Vote;

/**
 * Vote 服务接口.
 * 
 * @since 1.0.0 2017年4月9日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface VoteService {
	/**
	 * 根据id获取 Vote
	 * 
	 * @param id
	 * @return
	 */
	Vote getVoteById(Long id);

	/**
	 * 删除Vote
	 * 
	 * @param id
	 * @return
	 */
	void removeVote(Long id);
}
