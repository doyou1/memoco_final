package com.memoko.integrated.storagetest;


import org.springframework.stereotype.Service;


@Service
public class ImageReferenceService {

	
	
	public void addFood_list(String[] food) {
		
	}

	public void addFilPath_list(String[] filePath) {
		
	}
		
	
    
	
	
	
	

}
	/*
		Storage storage = StorageOptions.getDefaultInstance().getService();
		String ingrdName = "양파";
		String filePath = "\\C:\\imageUpload\\2028463776.jpg";
		Page<Blob> blobs = storage.list("my-project0228-269601"
				,BlobListOption.currentDirectory()
				,BlobListOption.prefix("foods/"));
		
		for(Blob blob : blobs.iterateAll()) {
			try {
				String productId = blob.getName().split("/")[1];
				
				if(ingrdName.equals(productId)) {
					System.out.println(productId + "있음");
					String subProductId = productId+"1";
					//이 음식재료1 (양파1)이라는 폴더에 추가 그리고 참조
					byte[] imageInByte;
					BufferedImage paintedImg = ImageIO.read(new File(filePath));
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(paintedImg, "png", baos);
					baos.flush();
					 
					imageInByte = baos.toByteArray();
					String referenceImageId = filePath.split("\\\\")[filePath.split("\\\\").length-1];
					String imagePath =  "foods/"+productId+"1/"+filePath.split("\\\\")[filePath.split("\\\\").length-1];
					BlobId blobId = BlobId.of("my-project0228-269601",imagePath);
				    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
				    Blob blob1 = storage.create(blobInfo, imageInByte);
				    System.out.println(blob1.getSelfLink());
				    
				    String gcsUri = 
							"gs://my-project0228-269601/"+imagePath;
		*///		    System.out.println(gcsUri);
		/*		    GoogleProductSetManagement.createReferenceImage(
							projectId
							, computeRegion
							, subProductId
							, referenceImageId
							, gcsUri);	
		*/		
				
		/*	} catch(Exception e) {
				System.out.println("Exception 발생");
				break;
			}
								
		}*/
		
		//BufferedImage paintedImg = ImageIO.read(new File("\\C:\\Users\\user\\Desktop\\carrot08.jpg"));
//		BlobId blobId = BlobId.of("my-project0228-269601", "asd/asdasd.jpg");
//	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
	    //Blob blob = storage.createAcl(blobInfo, paintedImg);
		
	   //System.out.println(blob.toString());
		/*
		Page<Blob> blobs =
		        storage.list(
		            "my-project0228-269601", BlobListOption.currentDirectory(), BlobListOption.prefix("foods/�뼇�뙆/"));
		    if(!blobs.hasNextPage()) {
		    	System.out.println("page�뾾�쓬");
		    }
		
		 for (Blob blob : blobs.iterateAll()) { System.out.println("11"); }
	*/


