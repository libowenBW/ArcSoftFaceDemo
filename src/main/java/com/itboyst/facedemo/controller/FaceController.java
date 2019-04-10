package com.itboyst.facedemo.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.arcsoft.face.FaceInfo;
import com.itboyst.facedemo.base.ImageInfo;
import com.itboyst.facedemo.base.Result;
import com.itboyst.facedemo.base.Results;
import com.itboyst.facedemo.domain.User;
import com.itboyst.facedemo.domain.UserFaceInfo;
import com.itboyst.facedemo.dto.FaceSearchResDto;
import com.itboyst.facedemo.dto.FaceUserInfo;
import com.itboyst.facedemo.dto.ProcessInfo;
import com.itboyst.facedemo.enums.ErrorCodeEnum;
import com.itboyst.facedemo.service.impl.FaceEngineService;
import com.itboyst.facedemo.service.impl.UserFaceInfoService;
import com.itboyst.facedemo.service.impl.UserService;
import com.itboyst.facedemo.util.FaceAddParam;
import com.itboyst.facedemo.util.ImageUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import sun.misc.BASE64Decoder;

@Component
@RequestMapping
public class FaceController {
	private final String fileName = "/Users/libowen/Desktop/jietu/0.png";
	private final String fileName1 = "/Users/libowen/Desktop/jietu/";

	public final static Logger logger = LoggerFactory.getLogger(FaceController.class);

	@Autowired
	FaceEngineService faceEngineService;
	@Autowired
	UserService userService;
	@Autowired
	UserFaceInfoService userFaceInfoService;
	@Autowired
	HttpServletRequest request;
	// @Autowired
	// UserRepository userRepository;
	// @Autowired
	// UserFaceInfoRepository userFaceInfoRepository;

	@RequestMapping(value = "/demo")
	public ModelAndView demo(ModelAndView modelAndView) {
		modelAndView.setViewName("demo");
		return modelAndView;
	}

	@ResponseBody
	@PostMapping("/faceAdd")
	public Result<Object> faceAdd(FaceAddParam formData) throws IOException {
		Long id = formData.getId();
		Integer groupId = Integer.parseInt(formData.getGroupId().toString());
		String name = formData.getUsername().toString();
		User user = new User();
		user.setId(id);
		user.setUsername(name);
		System.out.print("_________________________" + userService.saveOrUpateUser(user).getPassword());
		UserFaceInfo face = userFaceInfoService.getOneByUserId(id);
		if (id == null || face != null) {
			return Results.newFailedResult("id is null or already exists");
		}
		if (groupId == null) {
			return Results.newFailedResult("groupId is null");
		}
		if (name == null) {
			return Results.newFailedResult("name is null");
		}
		// if (file == null) // 图像数据为空
		// return ;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(formData.getFile());
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		String imgFilePath = "/media/psf/Home/Documents/fice/截图0.jpg";
		OutputStream out = new FileOutputStream(imgFilePath);
		out.write(b);
		out.flush();
		out.close();

		try {
			InputStream inputStream = new FileInputStream(imgFilePath);
			ImageInfo imageInfo = ImageUtil.getRGBData(inputStream);
			// 人脸特征获取
			byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
			if (bytes == null) {
				return Results.newFailedResult(ErrorCodeEnum.NO_FACE_DETECTED);
			}
			if (inputStream != null) {
				inputStream.close();
			}
			faceEngineService.detectFaces(imageInfo);
			UserFaceInfo userFaceInfo = new UserFaceInfo();
			userFaceInfo.setName(name);
			userFaceInfo.setGroupId(groupId);
			userFaceInfo.setFaceFeature(bytes);
			userFaceInfo.setUserId(id);

			// 人脸特征插入到数据库
			UserFaceInfo temp = userFaceInfoService.insert(userFaceInfo);
			FaceUserInfo faceUserInfo = new FaceUserInfo();
			faceUserInfo.setName(name);
			faceUserInfo.setFaceId(temp.getFaceId());
			faceUserInfo.setFaceFeature(bytes);
			// 人脸信息添加到缓存
			faceEngineService.addFaceToCache(groupId, faceUserInfo);
			logger.info("faceAdd:" + name);
			System.out.print(
					"                         ------------------------------------------------------------------------------- ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------ ------------------------------------------------------");
			return Results.newSuccessResult("");
		} catch (Exception e) {
			logger.error("", e);
		}
		return Results.newFailedResult(ErrorCodeEnum.UNKNOWN);
	}

	// final Webcam webcam = Webcam.getDefault();
	// webcam.setViewSize(WebcamResolution.VGA.getSize());
	// WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
	// webcam.close();
	// 这里应该用人的身份信息作为名字，用完之后删除，偷懒没做mark一下
	@ResponseBody
	@PostMapping("/faceSearch")
	public Result<FaceSearchResDto> faceSearch(/* MultipartFile file, */ FaceAddParam formData) throws Exception {

		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(formData.getFile());
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}

		String imgFilePath = "/media/psf/Home/Documents/fice/截图1.jpg";
		OutputStream out = new FileOutputStream(imgFilePath);
		out.write(b);
		out.flush();
		out.close();

		String groupId = formData.getGroupId();
		InputStream inputStream = new FileInputStream(imgFilePath);
		BufferedImage bufImage = ImageIO.read(inputStream);
		ImageInfo imageInfo = ImageUtil.bufferedImage2ImageInfo(bufImage);
		if (inputStream != null) {
			inputStream.close();
		}
		if (groupId == null) {
			return Results.newFailedResult("groupId is null");
		}
		// 人脸特征获取
		byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);

		if (bytes == null) {
			return Results.newFailedResult(ErrorCodeEnum.NO_FACE_DETECTED);
		}
		// 人脸比对，获取比对结果
		List<FaceUserInfo> userFaceInfoList = faceEngineService.compareFaceFeature(bytes, Integer.parseInt(groupId));

		if (CollectionUtil.isNotEmpty(userFaceInfoList)) {
			FaceUserInfo faceUserInfo = userFaceInfoList.get(0);
			FaceSearchResDto faceSearchResDto = new FaceSearchResDto();
			BeanUtil.copyProperties(faceUserInfo, faceSearchResDto);
			List<ProcessInfo> processInfoList = faceEngineService.process(imageInfo);
			if (CollectionUtil.isNotEmpty(processInfoList)) {
				// 人脸检测
				List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);
				int left = faceInfoList.get(0).getRect().getLeft();
				int top = faceInfoList.get(0).getRect().getTop();
				int width = faceInfoList.get(0).getRect().getRight() - left;
				int height = faceInfoList.get(0).getRect().getBottom() - top;

				Graphics2D graphics2D = bufImage.createGraphics();
				graphics2D.setColor(Color.RED);// 红色
				BasicStroke stroke = new BasicStroke(5f);
				graphics2D.setStroke(stroke);
				graphics2D.drawRect(left, top, width, height);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ImageIO.write(bufImage, "jpg", outputStream);
				byte[] bytes1 = outputStream.toByteArray();
				faceSearchResDto.setImage("data:image/jpeg;base64," + Base64Utils.encodeToString(bytes1));
				faceSearchResDto.setAge(processInfoList.get(0).getAge());
				faceSearchResDto.setGender(processInfoList.get(0).getGender().equals(1) ? "女" : "男");
				UserFaceInfo faceEntity = userFaceInfoService
						.getOneByFaceId(Integer.parseInt(faceSearchResDto.getFaceId()));
				User u = userService.getOneById(faceEntity.getUserId());
				faceSearchResDto.setUserName(u.getUsername());
				System.out.print(u.getUsername());
				String password = u.getPassword();
				if (password == null) {
					u.setPassword("000000");
				}
				faceSearchResDto.setPassword(userService.saveOrUpateUser(u).getPassword());
			}

			return Results.newSuccessResult(faceSearchResDto);
		}

		return Results.newFailedResult(ErrorCodeEnum.FACE_DOES_NOT_MATCH);

	}

	@RequestMapping(value = "/detectFaces", method = RequestMethod.POST)
	@ResponseBody
	public List<FaceInfo> detectFaces(String image) throws IOException {
		byte[] bytes = Base64Utils.decodeFromString(image.trim());

		InputStream inputStream = new ByteArrayInputStream(bytes);
		ImageInfo imageInfo = ImageUtil.getRGBData(inputStream);

		if (inputStream != null) {
			inputStream.close();
		}
		List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);

		return faceInfoList;
	}

}
