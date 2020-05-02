package com.memoko.integrated.imageanalysis.service;


import java.io.IOException;
import java.util.ArrayList;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.memoko.integrated.imageanalysis.vo.InputFoodVO;

public class MainForTest {
	
//	public static void main(String[] args) throws IOException {
		String projectId = "my-project0228-269601";
		String computeRegion = "asia-east1";
		String productSetId = "MEMOKO-FOODS-77";
//		String productSetDisplayName = "FOODS";
		String productCategory = "general-v1";
		
//		GoogleProductSetManagement.removeProductFromProductSet(projectId, computeRegion, "무파마1", productSetId);
		/*
		GoogleProductSetManagement.createProductSet(
				projectId
				, computeRegion
				, productSetId
				, productSetDisplayName);
		

		ArrayList<InputFoodVO> list = new ArrayList<>();
		
		list.add(new InputFoodVO("닭고기","닭고기"));
		list.add(new InputFoodVO("돼지고기","돼지고기"));
		list.add(new InputFoodVO("양파","양파"));
		list.add(new InputFoodVO("파프리카","파프리카"));
		list.add(new InputFoodVO("무","무"));
		list.add(new InputFoodVO("토마토","토마토"));
		list.add(new InputFoodVO("딸기","딸기"));
		list.add(new InputFoodVO("버섯","버섯"));
		list.add(new InputFoodVO("사과","사과"));
		list.add(new InputFoodVO("당근","당근"));
		list.add(new InputFoodVO("감자","감자"));
		list.add(new InputFoodVO("고구마","고구마"));
		list.add(new InputFoodVO("고추","고추"));
		list.add(new InputFoodVO("김치","김치"));
		list.add(new InputFoodVO("단호박","단호박"));
		list.add(new InputFoodVO("새우","새우"));
		list.add(new InputFoodVO("연어","연어"));
		list.add(new InputFoodVO("옥수수","옥수수"));
		
		System.out.println("list.size() : " + list.size());
		int cnt = 1;
		for(InputFoodVO input : list) {
			System.out.print(cnt + " : ");
			cnt++;
			String productId = input.getEng_name();
	        String productDisplayName = input.getKor_name(); 
	        

			GoogleProductSetManagement.createProduct(
					projectId
					, computeRegion
					, productId
					, productDisplayName
					, productCategory
					);			

			GoogleProductSetManagement.addProductToProductSet(
					projectId
					, computeRegion
					, productId
					, productSetId);
		 
		
			for(int i=1; i<=20; i++) {
				try {
					String referenceImageId = productId + (i>9 ? i: "0"+i);
					String gcsUri = 
							"gs://my-project0228-269601/foods/" + productId + "/" + referenceImageId + ".jpg";
					GoogleProductSetManagement.createReferenceImage(
							projectId
							, computeRegion
							, productId
							, referenceImageId
							, gcsUri);					
				} catch(IOException e) {
					continue;
				}	
			}
			
		}

		Storage storage = StorageOptions.getDefaultInstance().getService();

	    // The name of a bucket, e.g. "my-bucket"
	    // String bucketName = "my-bucket";

	    // Select all fields
	    // Fields can be selected individually e.g. Storage.BucketField.NAME
	    Bucket bucket = storage.get("my-project0228-269601" 
	    		, BucketGetOption.fields(Storage.BucketField.values()));	

	    // Print bucket metadata
	 
	    System.out.println("BucketName: " + bucket.getName());
	    System.out.println("Id: " + bucket.getGeneratedId());
	    System.out.println("IndexPage: " + bucket.getIndexPage());
	    System.out.println("Location: " + bucket.getLocation());
	    System.out.println("Metageneration: " + bucket.getMetageneration());
	    System.out.println("NotFoundPage: " + bucket.getNotFoundPage());
	    System.out.println("RequesterPays: " + bucket.requesterPays());
	    System.out.println("SelfLink: " + bucket.getSelfLink());
	    System.out.println("StorageClass: " + bucket.getStorageClass().name());
	    System.out.println("TimeCreated: " + bucket.getCreateTime());
	    System.out.println("VersioningEnabled: " + bucket.versioningEnabled());
	    if (bucket.getLabels() != null) {
	      System.out.println("\n\n\nLabels:");
	      for (Map.Entry<String, String> label : bucket.getLabels().entrySet()) {
	        System.out.println(label.getKey() + "=" + label.getValue());
	      }
	    };
	    */
//	}

/*		public static void main(String[] args) {
			 // Instantiates a client
		    Storage storage = StorageOptions.getDefaultInstance().getService();

		    // The name for the new bucket
		    String bucketName = "my-new-bucket";  // "my-new-bucket";

		    // Creates the new bucket
		    Bucket bucket = storage.create(BucketInfo.of(bucketName));

		    System.out.printf("Bucket %s created.%n", bucket.getName());
		}
	*/	
	public static void createSubProduct() {
		String projectId = "my-project0228-269601";
		String computeRegion = "asia-east1";
		String productSetId = "MEMOKO-FOODS-77";
//		String productSetDisplayName = "FOODS";
		String productCategory = "general-v1";
		ArrayList<InputFoodVO> list = new ArrayList<>();
				
		list.add(new InputFoodVO("닭고기1","닭고기"));
		list.add(new InputFoodVO("돼지고기1","돼지고기"));
		list.add(new InputFoodVO("양파1","양파"));
		list.add(new InputFoodVO("파프리카1","파프리카"));
		list.add(new InputFoodVO("무1","무"));
		list.add(new InputFoodVO("토마토1","토마토"));
		list.add(new InputFoodVO("딸기1","딸기"));
		list.add(new InputFoodVO("버섯1","버섯"));
		list.add(new InputFoodVO("사과1","사과"));
		list.add(new InputFoodVO("당근1","당근"));
		list.add(new InputFoodVO("감자1","감자"));
		list.add(new InputFoodVO("고구마1","고구마"));
		list.add(new InputFoodVO("고추1","고추"));
		list.add(new InputFoodVO("김치1","김치"));
		list.add(new InputFoodVO("단호박1","단호박"));
		list.add(new InputFoodVO("새우1","새우"));
		list.add(new InputFoodVO("연어1","연어"));
		list.add(new InputFoodVO("옥수수1","옥수수"));
		
		for(InputFoodVO vo : list) {
			String productId = vo.getEng_name();
			String productDisplayName = vo.getKor_name();
			try {
				GoogleProductSetManagement.createProduct(
						projectId
						, computeRegion
						, productId
						, productDisplayName
						, productCategory
				);
			 
				GoogleProductSetManagement.addProductToProductSet(
						projectId
						, computeRegion
						, productId
						, productSetId);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
