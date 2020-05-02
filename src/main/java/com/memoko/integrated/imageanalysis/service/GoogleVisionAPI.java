package com.memoko.integrated.imageanalysis.service;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;

import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import com.google.cloud.vision.v1.NormalizedVertex;
import com.google.protobuf.ByteString;
import com.memoko.integrated.imageanalysis.controller.ImageAnalysisController;

public class GoogleVisionAPI {

	private static final Logger logger = LoggerFactory.getLogger(GoogleVisionAPI.class);
	
	/**
	 * Detects localized objects in the specified local image.
	 *
	 * @param filePath The path to the file to perform localized object detection on.
	 * @param out A {@link PrintStream} to write detected objects to.
	 * @throws Exception on errors while closing the client.
	 * @throws IOException on Input/Output errors.
	 */
	public static ArrayList<HashMap<String,Object>> detectLocalizedObjects(String fullPath, int width, int height)
	    throws Exception, IOException {
		
		logger.info("***Google Vision API DetectLocalizedObjects Start***");
				  
		ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		  
		List<AnnotateImageRequest> requests = new ArrayList<>();
	
		ByteString imgBytes = ByteString.readFrom(new FileInputStream(fullPath));
	
		Image img = Image.newBuilder().setContent(imgBytes).build();
		  
		AnnotateImageRequest request =
				AnnotateImageRequest.newBuilder()
		        .addFeatures(Feature.newBuilder().setType(Type.OBJECT_LOCALIZATION))
		        .setImage(img)
		        .build();
		requests.add(request);
		  
		// Perform the request
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    
			List<AnnotateImageResponse> responses = response.getResponsesList();
		
		    // Display the results
		    for (AnnotateImageResponse res : responses) {
		    	//분석한 각각의 object의 객체(특정 값을 갖고있는)
		    	for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
			        int x = 1;
			        int y = 1;
			        HashMap<String,Object> map = new HashMap<>();
			        
			        map.put("name", entity.getName());
			        map.put("score", entity.getScore());
			        map.put("width",width);
			        map.put("height",height);
			        
			        List<NormalizedVertex> vertexList = (List<NormalizedVertex>) entity.getBoundingPoly().getNormalizedVerticesList();
			        
			        //x1~x4까지, y1~y4
			        for(NormalizedVertex vertex : vertexList) {
			        	map.put("x" + x, (int) Math.round(width * vertex.getX()));	
		            	map.put("y" + y, (int) Math.round(height * vertex.getY()));	
		            	x++;
		            	y++;
			        }
			        
			        list.add(map);
		    	}
		    }
		  }
		  
		logger.info("***Google Vision API DetectLocalizedObjects End***");
		
		return list;
	}


}
