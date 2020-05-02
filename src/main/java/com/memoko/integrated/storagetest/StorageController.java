package com.memoko.integrated.storagetest;


import org.springframework.stereotype.Controller;


@Controller
public class StorageController {
	
//    private String bucketName = "my-project0228-269601";

   
	//   System.out.println(storage.toString());
    	

	   /*
    	Page<Blob> blobs = storage.list(projectId
				,BlobListOption.currentDirectory()
				,BlobListOption.prefix("foods/"));
	   for(Blob blob : blobs.iterateAll()) {
			String folderName = blob.getName().split("/")[1];
			System.out.println(blob.getName());
			System.out.println(folderName);
			//if(productId.equals(folderName)) {
			//	result = true;
			//}
	   }	*/
//		System.out.println("Google Storage 접속 완료");
    	//return "redirect:/";
    }
  
    	/*String projectId = "my-project0228-269601";
    	Storage storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(accountResource.getInputStream()))
                .build()
                .getService();
	   System.out.println(storage.toString());
	   
	   Page<Blob> blobs =storage.list(projectId, BlobListOption.prefix("foods/"));
	   for(Blob blob : blobs.iterateAll()) {
			String folderName = blob.getName().split("/")[1];
			
	   }	
	   System.out.println("Google Storage 접속 완료");*/
    /*
	   Storage storage = StorageOptions.newBuilder()
             .setCredentials(ServiceAccountCredentials.fromStream(accountResource.getInputStream()))
             .build()
             .getService();
	   Page<Blob> blobs = storage.list(projectId
				,BlobListOption.currentDirectory()
				,BlobListOption.prefix("foods/"));
	   for(Blob blob : blobs.iterateAll()) {
			String folderName = blob.getName().split("/")[1];
			System.out.println(blob.getName());
			System.out.println(folderName);
			//if(productId.equals(folderName)) {
			//	result = true;
			//}
	   }	*/
    
//	Page<Blob> blobs = storage.list(projectId
//	,BlobListOption.currentDirectory()
//	,BlobListOption.prefix("foods/"));
    
//	BlobId blobId = BlobId.of(projectId, storageFullPath);
//    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
//    Blob blob = storage.create(blobInfo, imageInByte);

