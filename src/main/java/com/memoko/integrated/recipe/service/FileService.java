package com.memoko.integrated.recipe.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * �뙆�씪 愿��젴 �쑀�떥
 * �뾽濡쒕뱶�븳 �뙆�씪�쓽 ���옣 & �꽌踰꾩뿉 ���옣�맂 �뙆�씪 �궘�젣 �벑�쓽 湲곕뒫 �젣怨�
 */
@Service
public class FileService {

	/**
	 * �뾽濡쒕뱶 �맂 �뙆�씪�쓣 吏��젙�맂 寃쎈줈�뿉 ���옣�븯怨�, ���옣�맂 �뙆�씪紐낆쓣 由ы꽩
	 * @param mfile �뾽濡쒕뱶�맂 �뙆�씪
	 * @param path ���옣�븷 寃쎈줈
	 * @return ���옣�맂 �뙆�씪紐�
	 */
	public static String saveFile(MultipartFile mfile, String uploadPath) {
		//�뾽濡쒕뱶�맂 �뙆�씪�씠 �뾾嫄곕굹 �겕湲곌� 0�씠硫� ���옣�븯吏� �븡怨� null�쓣 由ы꽩
		if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
			return null;
		}
		
		//���옣 �뤃�뜑媛� �뾾�쑝硫� �깮�꽦
		File path = new File(uploadPath);
		if (!path.isDirectory()) {
			path.mkdirs();
		}
		
		//�썝蹂� �뙆�씪紐�
		String originalFilename = mfile.getOriginalFilename();
		
		//���옣�븷 �뙆�씪紐낆쓣 �삤�뒛 �궇吏쒖쓽 �뀈�썡�씪濡� �깮�꽦
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String savedFilename = sdf.format(new Date());
		
		//�썝蹂� �뙆�씪�쓽 �솗�옣�옄
		String ext;
		int lastIndex = originalFilename.lastIndexOf('.');
		//�솗�옣�옄媛� �뾾�뒗 寃쎌슦
		if (lastIndex == -1) {
			ext = "";
		}
		//�솗�옣�옄媛� �엳�뒗 寃쎌슦
		else {
			ext = "." + originalFilename.substring(lastIndex + 1);
		}

		//���옣�븷 �쟾泥� 寃쎈줈瑜� �룷�븿�븳 File 媛앹껜
		File serverFile = null;
		
		//媛숈� �씠由꾩쓽 �뙆�씪�씠 �엳�뒗 寃쎌슦�쓽 泥섎━
		while (true) {
			serverFile = new File(uploadPath + "/" + savedFilename + ext);
			//媛숈� �씠由꾩쓽 �뙆�씪�씠 �뾾�쑝硫� �굹媛�.
			if ( !serverFile.isFile()) break;	
			//媛숈� �씠由꾩쓽 �뙆�씪�씠 �엳�쑝硫� �씠由� �뮘�뿉 long ���엯�쓽 �떆媛꾩젙蹂대�� �뜤遺숈엫.
			savedFilename = savedFilename + new Date().getTime();	
		}		
		
		//�뙆�씪 ���옣
		try {
			mfile.transferTo(serverFile);
		} catch (Exception e) {
			savedFilename = null;
			ext = null;
			e.printStackTrace();
		}
		
		return savedFilename + ext;
	}
	
	/**
	 * �꽌踰꾩뿉 ���옣�맂 �뙆�씪�쓽 �쟾泥� 寃쎈줈瑜� �쟾�떖諛쏆븘, �빐�떦 �뙆�씪�쓣 �궘�젣
	 * @param fullpath �궘�젣�븷 �뙆�씪�쓽 寃쎈줈
	 * @return �궘�젣 �뿬遺�
	 */
	public static boolean deleteFile(String fullpath) {
		//�뙆�씪 �궘�젣 �뿬遺�瑜� 由ы꽩�븷 蹂��닔
		boolean result = false;
		
		//�쟾�떖�맂 �쟾泥� 寃쎈줈濡� File媛앹껜 �깮�꽦
		File delFile = new File(fullpath);
		
		//�빐�떦 �뙆�씪�씠 議댁옱�븯硫� �궘�젣
		if (delFile.isFile()) {
			delFile.delete();
			result = true;
		}
		
		return result;
	}
}
