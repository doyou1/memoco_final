package com.memoko.integrated.recipe.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageOptions;
import com.memoko.integrated.imageanalysis.service.GoogleProductSetManagement;
@EnableScheduling
@Component
public class ReferenceImageScheduler {

	//Stroage 객체 생성을 위한 bucket 사용 인증키
//    @Value(value = "classpath:sjh200228.json")
 //   public Resource accountResource;
    //Stroage 객체 생성을 위한 bucket 사용 인증키
//    @Value(value = "classpath:db.properties")
//    public Resource accountResource;
    
	//사용자 입력 image 서브참조를 위한 food와 filePath 리스트
	static ArrayList<String> food_list = new ArrayList<>();
	static ArrayList<String> filePath_list = new ArrayList<>();
	
	@Autowired
	private ApplicationContext ctx;

    
//	@Scheduled//3분에 한번씩 실행
	public void referenceImageSchedule() throws IOException {	
		System.out.println("Image Reference 실행");
		
		ctx = new ClassPathXmlApplicationContext();
		
		Resource accountResource = ctx.getResource("classpath:sjh200228.json");
		System.out.println(accountResource);
		// TODO Auto-generated method stub
		String projectId = "my-project0228-269601";
		String computeRegion = "asia-east1";
		String productSetId = "MEMOKO-FOODS-77";
		String productSetDisplayName = "FOODS";
		String productCategory = "general-v1";

		if(!(food_list.isEmpty()) && !(filePath_list.isEmpty())) {
			//GoogleStorage 접속
			Storage storage = StorageOptions
					   .newBuilder()
		              .setCredentials(ServiceAccountCredentials.fromStream(accountResource.getInputStream()))
		              .build()
		              .getService();
			

			//food_list : food라는 String[]을 담는 ArrayList
			for(int i=0; i<food_list.size(); i++) {
				
				
				//지금까지 저장된 FOODS의 일부		
				String food = food_list.get(i);
				//지금까지 저장된 Food의 이미지 경로의 일부
				String filePath = filePath_list.get(i);
							

				System.out.println("Google Storage 접속 완료");
								
					//PRODUCT SET에 추가돼있는 PRODUCTID
					String productId = food;//파프리카
					
					//GoogleProductSetManagement.getSimilarProductsFile() 메서드 실행시 리턴받을 음식재료명
					String productDisplayName = food;//파프리카
					
					//PRODUCT에 참조할 사용자가 입력한 image의 path
					String productImagePath = filePath;//사용자가 입력한 파프리카 사진
					
					System.out.println(productId);
					System.out.println(productImagePath);
					
					//사용자 업로드 사진 참조
					//앞선 음식재료명과 이미지경로 중 하나라도 null이면 STORAGE 접근 금지
					if(productId == "" || productImagePath == ""
							|| productId == null || productImagePath == null) {
						continue;
					}
					else {

						//STORAGE의 FOODS폴더 안 폴더명을 PRODUCTID LIST로 활용
						Page<Blob> blobs = storage.list(projectId, BlobListOption.currentDirectory(),BlobListOption.prefix("foods/"));
						System.out.println("PRODUCTID LIST GET!");
						
						
						boolean result = false;
						//1. 입력된 재료의 이름이 productId의 List에 있는가
						for(Blob blob : blobs.iterateAll()) {
								//폴더명만 끊어오기 위한 처리
								String folderName = blob.getName().split("/")[1];//   String["foods","새우",""] foods/새우/
								System.out.println(folderName);
								if(productId.equals(folderName)) {
									result = true;
								}
						}	    	
						    
						//입력된 재료의 이름이 productId의 List에 있으면 TRUE, 없으면 FALSE
						System.out.println("ProductId 체크" + result);
					
					//SUBPRODUCTID
					String subProductId = productId + "1";
						
					//PRODUCTID LIST를 통해 확인한 후
					if(result) {
						//1-1. 있다면, 그 productId의 서브(productId1)에 사진을 참조
						//1-1-1 productImagePath를 이용해 GoogleStorage에 업로드
				       
						//GoogleStorage에 이미지 업로드하기 위한 IMAGE의 BYTE화 시작
				        byte[] imageInByte;
						BufferedImage productImage = ImageIO.read(new File(productImagePath));
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(productImage, "jpg", baos);
						baos.flush();
						imageInByte = baos.toByteArray();
						//GoogleStorage에 이미지 업로드하기 위한 IMAGE의 BYTE화 종료
						
						//referenceImageId은 로컬이미지파일명으로
						String referenceImageId = productImagePath.split("\\\\")[productImagePath.split("\\\\").length-1];
						System.out.println("referenceImageId : " + referenceImageId);
						
						//mybucket의 foods라는 폴더안에 특정 subproductId폴더안에 로컬이미지파일명으로 저장. 확장자는 .jpg
						String storageFullPath ="foods/"+subProductId+"/"+referenceImageId;
						System.out.println("storageFullPath : " + storageFullPath);
						
						//앞선 경로로 업로드하기 위해 BlobId 객체 생성
						BlobId blobId = BlobId.of(projectId, storageFullPath);
						//업로드할 contentType을 image으로 설정
						BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
					    //로컬이미지 파일 storage에 저장
						Blob blob = storage.create(blobInfo, imageInByte);
						
						//저장을 확인 하는 Print
				        System.out.println(blobInfo.getName());//foods/파프리카1/ewewe1e123e.jpg

				        //1-1-2 Google Storage에 저장된 image를 SUBPRODUCT의 참조이미지로 활용 
					    String gcsUri = 
								"gs://"+projectId+"/"+storageFullPath;
						GoogleProductSetManagement.createReferenceImage(projectId, computeRegion, subProductId, referenceImageId, gcsUri);
						
					}else {

						try {
							//1-2. 없다면, 그 productId와 subProductId를 만들고, 이미지 subProductId에 참조
							GoogleProductSetManagement.createProduct(
									projectId
									, computeRegion
									, productId //파프리카
									, productDisplayName
									,productCategory);
							System.out.println("//productId 만들기 : " + productId);
							GoogleProductSetManagement.addProductToProductSet(projectId, computeRegion, productId, productSetId);
							System.out.println("만든 product ProductSet에 추가");

						}catch(Exception e) {
							
						}
							
						try {
							
							GoogleProductSetManagement.createProduct(
									projectId
									, computeRegion
									, subProductId //파프리카1
									, productDisplayName
									,productCategory);
							System.out.println("//subProductId 만들기 : " +subProductId  );
							GoogleProductSetManagement.addProductToProductSet(projectId, computeRegion, subProductId, productSetId);
							System.out.println("만든 subProduct ProductSet에 추가");
							
						}catch(Exception e) {
							
						}
						
						
						
						byte[] imageInByte;
						BufferedImage productImage = ImageIO.read(new File(productImagePath));
						//Google Storage에 upload하기 위한 image의 byte화
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(productImage, "jpg", baos);
						baos.flush();
							imageInByte = baos.toByteArray();
						String referenceImageId = productImagePath.split("\\\\")[productImagePath.split("\\\\").length-1];
						System.out.println("referenceImageId : " + referenceImageId);
						
						String storageFullPath ="foods/"+subProductId+"/"+referenceImageId;
						System.out.println("storageFullPath : " + storageFullPath);
						
						BlobId blobId = BlobId.of(projectId, storageFullPath);
					    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
					    Blob blob = storage.create(blobInfo, imageInByte);
						System.out.println("storage의 subproduct에 저장 : " + blob.getSelfLink());
						
						//1-1-2 Google Storage에 저장된 image 참조
					    String gcsUri = 
								"gs://"+projectId+"/"+storageFullPath;
						GoogleProductSetManagement.createReferenceImage(projectId, computeRegion, subProductId, referenceImageId, gcsUri);
					}
					System.out.println("참조 하나 완료");
				}
					System.out.println("작업 하나 사라짐");
				}
		

			
			//참조 성공후 새로운 사용자 입력을 받기 위해 FOOD_LIST와 FILEPATH_LIST를 비움
			food_list.clear();
			filePath_list.clear();	
			
		}

		System.out.println("Image Reference 종료");
	}

}
