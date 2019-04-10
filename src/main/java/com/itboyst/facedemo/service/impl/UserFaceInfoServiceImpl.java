package com.itboyst.facedemo.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itboyst.facedemo.domain.UserFaceInfo;
import com.itboyst.facedemo.repository.UserFaceInfoRepository;

@Service("userFaceInfoService")
public class UserFaceInfoServiceImpl implements UserFaceInfoService {

	@Autowired
	private UserFaceInfoRepository userFaceInfoRepository;

	// @Override
	// public int insertSelective(UserFaceInfo userFaceInfo) {
	// return userFaceInfoMapper.insertSelective(userFaceInfo);
	// }

	@Transactional
	@Override
	public UserFaceInfo insert(UserFaceInfo userFaceInfo) {
		return userFaceInfoRepository.save(userFaceInfo);
	}

	@Transactional
	@Override
	public UserFaceInfo getOneByUserId(Long userId) {
		return userFaceInfoRepository.getOneByUserId(userId);
	}

	@Transactional
	@Override
	public UserFaceInfo getOneByFaceId(int parseInt) {

		return userFaceInfoRepository.getOneByFaceId(parseInt);
	}

	// @Transactional
	// @Override
	// public UserFaceInfo getOneByUserId(Long parseInt) {
	//
	// return userFaceInfoRepository.getOneByUserId(parseInt);
	// }
}
