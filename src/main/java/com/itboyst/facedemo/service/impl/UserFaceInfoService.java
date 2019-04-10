package com.itboyst.facedemo.service.impl;

import com.itboyst.facedemo.domain.UserFaceInfo;

public interface UserFaceInfoService {

	// int insertSelective(UserFaceInfo userFaceInfo);

	UserFaceInfo insert(UserFaceInfo userFaceInfo);

	UserFaceInfo getOneByUserId(Long userId);

	UserFaceInfo getOneByFaceId(int parseInt);

	// UserFaceInfo getOneByUserId(int parseInt);

}
