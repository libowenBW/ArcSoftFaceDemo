package com.itboyst.facedemo.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.arcsoft.face.FaceInfo;
import com.itboyst.facedemo.base.ImageInfo;
import com.itboyst.facedemo.dto.FaceUserInfo;
import com.itboyst.facedemo.dto.ProcessInfo;

public interface FaceEngineService {

	void addFaceToCache(Integer groupId, FaceUserInfo userFaceInfo) throws ExecutionException;

	List<FaceInfo> detectFaces(ImageInfo imageInfo);

	List<ProcessInfo> process(ImageInfo imageInfo);

	/**
	 * 人脸特征
	 * 
	 * @param imageInfo
	 * @return
	 */
	byte[] extractFaceFeature(ImageInfo imageInfo) throws InterruptedException;

	/**
	 * 人脸比对
	 * 
	 * @param groupId
	 * @param faceFeature
	 * @return
	 */
	List<FaceUserInfo> compareFaceFeature(byte[] faceFeature, Integer groupId)
			throws InterruptedException, ExecutionException;

}
