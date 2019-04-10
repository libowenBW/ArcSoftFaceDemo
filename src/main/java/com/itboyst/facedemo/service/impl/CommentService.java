package com.itboyst.facedemo.service.impl;

import com.itboyst.facedemo.domain.Comment;

/**
 * Comment Service接口.
 * 
 * @since 1.0.0 2017年6月6日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface CommentService {

	/**
	 * 根据id获取 Comment
	 * 
	 * @param id
	 * @return
	 */
	Comment getCommentById(Long id);

	/**
	 * 删除评论
	 * 
	 * @param id
	 * @return
	 */
	void removeComment(Long id);
}
