package com.itboyst.facedemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itboyst.facedemo.domain.UserFaceInfo;

public interface UserFaceInfoRepository extends JpaRepository<UserFaceInfo, Integer> {
	List<UserFaceInfo> findAllByGroupId(Integer groupId);

	UserFaceInfo getOneByUserId(Long userId);

	UserFaceInfo getOneByFaceId(Integer faceId);
	// @Query(value = "SELECT b.username,b.password FROM user_face_info a "
	// + "LEFT JOIN user b ON a.user_id=b.id " + "WHERE a.face_id=:pkid",
	// nativeQuery = true)
	// List<Object[]> selectByPkid(@Param("pkid") BigDecimal pkid);
}
