 package com.memoko.integrated.imageanalysis.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.memoko.integrated.HomeController;
import com.memoko.integrated.imageanalysis.service.FileService;
import com.memoko.integrated.imageanalysis.service.GoogleProductSetManagement;
import com.memoko.integrated.imageanalysis.service.GoogleVisionAPI;
import com.memoko.integrated.imageanalysis.service.MediaUtils;

@Controller
@RequestMapping(value="/imageanalysis")
public class ImageAnalysisController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageAnalysisController.class);

	private static final String uploadPath = "\\C:\\imageUpload";
	
	private String savedFileName = null;
	private String fullPath = null;
	private ArrayList<HashMap<String,Object>> list = null;
	private int width = 0;
	private int height = 0;

	

	
	@RequestMapping(value="/uploadImageForm",method=RequestMethod.GET)
	public String imagehome() {
		
		logger.info("***Mode1 UploadImageForm***");
		
		logger.info("***Print /imageanalysis/uploadImageForm.jsp***");
		
		return "/imageanalysis/uploadImageForm";
	}

	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(MultipartFile upload, HttpSession session) throws Exception {
		logger.info("***Upload Start***");
		
		if(!upload.isEmpty()) {
			String savedFile = FileService.saveFile(upload, uploadPath);
			savedFileName = savedFile;
			fullPath = "\\"+uploadPath + "\\" + savedFile;
		}
				
		if(fullPath != null) {
			logger.info("Saved Image FullPath : {}", fullPath);

			list = null;
			width = 0;
			height = 0;
			
			BufferedImage doneUploadImage = ImageIO.read(new File(fullPath));
			width = doneUploadImage.getWidth();
			height = doneUploadImage.getHeight();

			logger.info("Saved Image Width : {}", width);
			logger.info("Saved Image Height : {}", height);

			logger.info("***Saved Image Find Objects Start***");
			
			
			//전체 사진의 각각의 object의 값이 담긴 HashMap을 담는 ArrayList가 리턴됨
			list = GoogleVisionAPI.detectLocalizedObjects(fullPath, width, height);
			logger.info("Objects Value : ", list);
			
			for(HashMap<String,Object> map : list) {
				
				int x1 = (int) map.get("x1");
				int y1 = (int) map.get("y1");
				int x2 = (int) map.get("x2");
				int y3 = (int) map.get("y3");

				BufferedImage subimage = doneUploadImage.getSubimage(x1, y1, x2-x1, y3-y1);
				
				//파일명이 중복되지 않기위해서 시작
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String savedFileName = sdf.format(new Date());
				
				File checkIsFile = null;
				while(true) {
					checkIsFile = new File(uploadPath + "\\" + savedFileName + ".jpg");
					if(!checkIsFile.isFile()) break;
					savedFileName = savedFileName + new Date().getTime();
				}
				//파일명이 중복되지 않기위해서 종료
				
				String filePath = uploadPath+"\\"+savedFileName+".jpg";
				ImageIO.write(subimage,"jpg",new File(filePath));
				
				//Product 참조를 위해 쓰일 서브파일의 전체경로
				map.put("filePath", filePath);
				
				String value = GoogleProductSetManagement.getSimilarProductsFile(
						filePath
						);
				map.put("value",value);

			}
		}
		
		return "redirect:/imageanalysis/analysisImageResult";
	}
	
	@RequestMapping(value="/analysisImageResult",method=RequestMethod.GET)
	public String result(HttpSession session, Model model) {
		
		model.addAttribute("list", list);
		model.addAttribute("width", width);
		model.addAttribute("height", height);
		model.addAttribute("fullPath", fullPath);
		model.addAttribute("savedFileName", savedFileName);
		return "/imageanalysis/analysisImageResult";
	}
	
	
}
