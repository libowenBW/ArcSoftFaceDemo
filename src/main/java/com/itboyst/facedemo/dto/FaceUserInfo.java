package com.itboyst.facedemo.dto;

public class FaceUserInfo {

	private Integer faceId;
	private String name;
	private Integer similarValue;
	private byte[] faceFeature;

	public Integer getFaceId() {
		return faceId;
	}

	public void setFaceId(Integer faceId) {
		this.faceId = faceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSimilarValue() {
		return similarValue;
	}

	public void setSimilarValue(Integer similarValue) {
		this.similarValue = similarValue;
	}

	public byte[] getFaceFeature() {
		return faceFeature;
	}

	public void setFaceFeature(byte[] faceFeature) {
		this.faceFeature = faceFeature;
	}

}
