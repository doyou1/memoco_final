package com.memoko.integrated.imageanalysis.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.BatchOperationMetadata;
import com.google.cloud.vision.v1.CreateProductSetRequest;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageContext;
import com.google.cloud.vision.v1.ImportProductSetsGcsSource;
import com.google.cloud.vision.v1.ImportProductSetsGcsSource.Builder;
import com.google.cloud.vision.v1.ImportProductSetsInputConfig;
import com.google.cloud.vision.v1.ImportProductSetsResponse;
import com.google.cloud.vision.v1.Product;
import com.google.cloud.vision.v1.Product.KeyValue;
import com.google.cloud.vision.v1.ProductName;
import com.google.cloud.vision.v1.ProductSearchClient;
import com.google.cloud.vision.v1.ProductSearchParams;
import com.google.cloud.vision.v1.ProductSearchResults.Result;
import com.google.cloud.vision.v1.ProductSet;
import com.google.cloud.vision.v1.ReferenceImage;
import com.google.protobuf.ByteString;
import com.google.protobuf.FieldMask;
import com.memoko.integrated.imageanalysis.controller.ImageAnalysisController;
import com.memoko.integrated.imageanalysis.vo.OutputFoodVO;

public class GoogleProductSetManagement {
	
	private static final Logger logger = LoggerFactory.getLogger(GoogleProductSetManagement.class);

	
	private static String projectId = "my-project0228-269601";
	private static String computeRegion = "asia-east1";
	private static String productSetId = "MEMOKO-FOODS-77";
//	private static String productSetDisplayName = "FOODS";
	private static String productCategory = "general-v1";
	private static String filter = "";
	
	/**
     * Create a product set
     *
     * @param projectId - Id of the project.
     * @param computeRegion - Region name.
     * @param productSetId - Id of the product set.
     * @param productSetDisplayName - Display name of the product set.
     * @throws IOException - on I/O errors.
     */
    public static void createProductSet(
        String projectId, String computeRegion, String productSetId, String productSetDisplayName)
        throws IOException {
      try (ProductSearchClient client = ProductSearchClient.create()) {

        // A resource that represents Google Cloud Platform location.
        String formattedParent = ProductSearchClient.formatLocationName(projectId, computeRegion);

        // Create a product set with the product set specification in the region.
        ProductSet myProductSet =
            ProductSet.newBuilder().setDisplayName(productSetDisplayName).build();
        CreateProductSetRequest request =
            CreateProductSetRequest.newBuilder()
                .setParent(formattedParent)
                .setProductSet(myProductSet)
                .setProductSetId(productSetId)
                .build();
        ProductSet productSet = client.createProductSet(request);
        // Display the product set information
        System.out.println(String.format("Product set name: %s", productSet.getName()));
      }
    }
	
	
	/**
     * Import images of different products in the product set.
     *
     * @param projectId - Id of the project.
     * @param computeRegion - Region name.
     * @param gcsUri - Google Cloud Storage URI.Target files must be in Product Search CSV format.
     * @throws Exception - on client errors.
     */
	//ProductSet ����
    public static void importProductSets(String projectId, String computeRegion, String gcsUri)
        throws Exception {
      try (ProductSearchClient client = ProductSearchClient.create()) {

        // A resource that represents Google Cloud Platform location.
        String formattedParent = ProductSearchClient.formatLocationName(projectId, computeRegion);
        Builder gcsSource = ImportProductSetsGcsSource.newBuilder().setCsvFileUri(gcsUri);

        // Set the input configuration along with Google Cloud Storage URI
        ImportProductSetsInputConfig inputConfig =
            ImportProductSetsInputConfig.newBuilder().setGcsSource(gcsSource).build();

        // Import the product sets from the input URI.
        OperationFuture<ImportProductSetsResponse, BatchOperationMetadata> response =
            client.importProductSetsAsync(formattedParent, inputConfig);

        System.out.println(String.format("Processing operation name: %s", response.getName()));
        ImportProductSetsResponse results = response.get();
        System.out.println("Processing done.");
        System.out.println("Results of the processing:");

        for (int i = 0; i < results.getStatusesCount(); i++) {
          System.out.println(
              String.format(
                  "Status of processing line %s of the csv: %s", i, results.getStatuses(i)));
          // Check the status of reference image.
          if (results.getStatuses(i).getCode() == 0) {
            ReferenceImage referenceImage = results.getReferenceImages(i);
            System.out.println(referenceImage);
          } else {
            System.out.println("No reference image.");
          }
        }
      }
    }
    
    
    /**
     * Create one product.
     *
     * @param projectId - Id of the project.
     * @param computeRegion - Region name.
     * @param productId - Id of the product.
     * @param productDisplayName - Display name of the product.
     * @param productCategory - Category of the product.
     * @throws IOException - on I/O errors.
     */
	//Product ����
    public static void createProduct(
        String projectId,
        String computeRegion,
        String productId,
        String productDisplayName,
        String productCategory)
        throws IOException {
      try (ProductSearchClient client = ProductSearchClient.create()) {

        // A resource that represents Google Cloud Platform location.
        String formattedParent = ProductSearchClient.formatLocationName(projectId, computeRegion);
        // Create a product with the product specification in the region.
        // Multiple labels are also supported.
        Product myProduct =
            Product.newBuilder()
                .setName(productId)
                .setDisplayName(productDisplayName)
                .setProductCategory(productCategory)
                .build();
        Product product = client.createProduct(formattedParent, myProduct, productId);
        // Display the product information
        System.out.println(String.format("Product name: %s", product.getName()));
      }
    }
	
    /**
     * Add a product to a product set.
     *
     * @param projectId - Id of the project.
     * @param computeRegion - Region name.
     * @param productId - Id of the product.
     * @param productSetId - Id of the product set.
     * @throws IOException - on I/O errors.
     */
	//Product�� ProductSet�� �߰�
    public static void addProductToProductSet(
        String projectId, String computeRegion, String productId, String productSetId)
        throws IOException {
      try (ProductSearchClient client = ProductSearchClient.create()) {

        // Get the full path of the product set.
        String formattedName =
            ProductSearchClient.formatProductSetName(projectId, computeRegion, productSetId);

        // Get the full path of the product.
        String productPath = ProductName.of(projectId, computeRegion, productId).toString();

        // Add the product to the product set.
        client.addProductToProductSet(formattedName, productPath);

        System.out.println(String.format("Product added to product set."));
      }
    }
    
    /**
     * Remove a product from a product set.
     *
     * @param projectId - Id of the project.
     * @param computeRegion - Region name.
     * @param productId - Id of the product.
     * @param productSetId - Id of the product set.
     * @throws IOException - on I/O errors.
     */
	//Product ����
    public static void removeProductFromProductSet(
        String projectId, String computeRegion, String productId, String productSetId)
        throws IOException {
      try (ProductSearchClient client = ProductSearchClient.create()) {

        // Get the full path of the product set.
        String formattedParent =
            ProductSearchClient.formatProductSetName(projectId, computeRegion, productSetId);

        // Get the full path of the product.
        String formattedName =
            ProductSearchClient.formatProductName(projectId, computeRegion, productId);

        // Remove the product from the product set.
        client.removeProductFromProductSet(formattedParent, formattedName);

        System.out.println(String.format("Product removed from product set."));
      }
    }
    
    /**
     * Create a reference image.
     *
     * @param projectId - Id of the project.
     * @param computeRegion - Region name.
     * @param productId - Id of the product.
     * @param referenceImageId - Id of the image.
     * @param gcsUri - Google Cloud Storage path of the input image.
     * @throws IOException - on I/O errors.
     */
	//Product�� Image ����
    public static void createReferenceImage(
        String projectId,
        String computeRegion,
        String productId,
        String referenceImageId,
        String gcsUri)
        throws IOException {
      try (ProductSearchClient client = ProductSearchClient.create()) {

        // Get the full path of the product.
        String formattedParent =
            ProductSearchClient.formatProductName(projectId, computeRegion, productId);
        // Create a reference image.
        ReferenceImage referenceImage = ReferenceImage.newBuilder().setUri(gcsUri).build();

        ReferenceImage image =
            client.createReferenceImage(formattedParent, referenceImage, referenceImageId);
        // Display the reference image information.
        System.out.println(String.format("Reference image name: %s", image.getName()));
        System.out.println(String.format("Reference image uri: %s", image.getUri()));
      }
    }
    
    
    /**
     * Update the product labels.
     *
     * @param projectId - Id of the project.
     * @param computeRegion - Region name.
     * @param productId -Id of the product.
     * @param productLabels - Labels of the product.
     * @throws IOException - on I/O errors.
     */
	//Product Label ������Ʈ, Label ������ ����
    public static void updateProductLabels(
        String projectId, String computeRegion, String productId, String productLabels)
        throws IOException {
      try (ProductSearchClient client = ProductSearchClient.create()) {

        // Get the full path of the product.
        String formattedName =
            ProductSearchClient.formatProductName(projectId, computeRegion, productId);

        // Set product name, product labels and product display name.
        // Multiple labels are also supported.
        Product product =
            Product.newBuilder()
                .setName(formattedName)
                .addProductLabels(
                    KeyValue.newBuilder()
                        .setKey(productLabels.split(",")[0].split("=")[0])
                        .setValue(productLabels.split(",")[0].split("=")[1])
                        .build())
                .build();

        // Set product update field name.
        FieldMask updateMask = FieldMask.newBuilder().addPaths("product_labels").build();

        // Update the product.
        Product updatedProduct = client.updateProduct(product, updateMask);
        // Display the product information
        System.out.println(String.format("Product name: %s", updatedProduct.getName()));
        System.out.println(String.format("Updated product labels: "));
        for (Product.KeyValue element : updatedProduct.getProductLabelsList()) {
          System.out.println(String.format("%s: %s", element.getKey(), element.getValue()));
        }
      }
    }
    
	/**
	* List all product sets
	*
	* @param projectId - Id of the project.
	* @param computeRegion - Region name.
	* @throws IOException - on I/O errors.
	*/
	public static void listProductSets(String projectId, String computeRegion) throws IOException {
	 	  	
	      try (ProductSearchClient client = ProductSearchClient.create()) {
	        // A resource that represents Google Cloud Platform location.
	        String formattedParent = ProductSearchClient.formatLocationName(projectId, computeRegion);
	        // List all the product sets available in the region.
	        for (ProductSet productSet : client.listProductSets(formattedParent).iterateAll()) {
	          // Display the product set information
	          System.out.println(String.format("Product set name: %s", productSet.getName()));
	          System.out.println(
	              String.format(
	                  "Product set id: %s",
	                  productSet.getName().substring(productSet.getName().lastIndexOf('/') + 1)));
	          System.out.println(
	              String.format("Product set display name: %s", productSet.getDisplayName()));
	          System.out.println("Product set index time:");
	          System.out.println(String.format("\tseconds: %s", productSet.getIndexTime().getSeconds()));
	          System.out.println(String.format("\tnanos: %s", productSet.getIndexTime().getNanos()));
	        }
	      }
	    }
	    
	    /**
	     * Get info about the product set.
	     *
	     * @param projectId - Id of the project.
	     * @param computeRegion - Region name.
	     * @param productSetId - Id of the product set.
	     * @throws IOException - on I/O errors.
	     */
	    public static void getProductSet(String projectId, String computeRegion, String productSetId)
	        throws IOException {
	      try (ProductSearchClient client = ProductSearchClient.create()) {

	        // Get the full path of the product set.
	        String formattedName =
	            ProductSearchClient.formatProductSetName(projectId, computeRegion, productSetId);
	        // Get complete detail of the product set.
	        ProductSet productSet = client.getProductSet(formattedName);
	        // Display the product set information
	        System.out.println(String.format("Product set name: %s", productSet.getName()));
	        System.out.println(
	            String.format(
	                "Product set id: %s",
	                productSet.getName().substring(productSet.getName().lastIndexOf('/') + 1)));
	        System.out.println(
	            String.format("Product set display name: %s", productSet.getDisplayName()));
	        System.out.println("Product set index time:");
	        System.out.println(String.format("\tseconds: %s", productSet.getIndexTime().getSeconds()));
	        System.out.println(String.format("\tnanos: %s", productSet.getIndexTime().getNanos()));
	      }
	    }
	    
	    
	    /**
	     * Search similar products to image in local file.
	     *
	     * @param projectId - Id of the project.
	     * @param computeRegion - Region name.
	     * @param productSetId - Id of the product set.
	     * @param productCategory - Category of the product.
	     * @param filePath - Local file path of the image to be searched
	     * @param filter - Condition to be applied on the labels. Example for filter: (color = red OR
	     *     color = blue) AND style = kids It will search on all products with the following labels:
	     *     color:red AND style:kids color:blue AND style:kids
	     * @throws IOException - on I/O errors.
	     */
	    //����� Procuct ã��
	    public static String getSimilarProductsFile(String filePath)
	        throws IOException {
	      try (ImageAnnotatorClient queryImageClient = ImageAnnotatorClient.create()) {

	        // Get the full path of the product set.
	        String productSetPath =
	            ProductSearchClient.formatProductSetName(projectId, computeRegion, productSetId);

	        // Read the image as a stream of bytes.
	        File imgPath = new File(filePath);
	        byte[] content = Files.readAllBytes(imgPath.toPath());

	        // Create annotate image request along with product search feature.
	        Feature featuresElement = Feature.newBuilder().setType(Type.PRODUCT_SEARCH).build();
	        // The input image can be a HTTPS link or Raw image bytes.
	        // Example:
	        // To use HTTP link replace with below code
	        //  ImageSource source = ImageSource.newBuilder().setImageUri(imageUri).build();
	        //  Image image = Image.newBuilder().setSource(source).build();
	        Image image = Image.newBuilder().setContent(ByteString.copyFrom(content)).build();
	        ImageContext imageContext =
	            ImageContext.newBuilder()
	                .setProductSearchParams(
	                    ProductSearchParams.newBuilder()
	                        .setProductSet(productSetPath)
	                        .addProductCategories(productCategory)
	                        .setFilter(filter))
	                .build();

	        AnnotateImageRequest annotateImageRequest =
	            AnnotateImageRequest.newBuilder()
	                .addFeatures(featuresElement)
	                .setImage(image)
	                .setImageContext(imageContext)
	                .build();
	        List<AnnotateImageRequest> requests = Arrays.asList(annotateImageRequest);

	        // Search products similar to the image.
	        BatchAnnotateImagesResponse response = queryImageClient.batchAnnotateImages(requests);
	        ArrayList<OutputFoodVO> foodlist = new ArrayList<OutputFoodVO>();
	        
	        
	        List<Result> similarProducts =
	            response.getResponses(0).getProductSearchResults().getResultsList();

	        for (Result product : similarProducts) {
	        	foodlist.add(new OutputFoodVO( product.getProduct().getDisplayName(),product.getScore()));
	        }
	        
	        if(foodlist.size() > 0) {
		        Collections.sort(foodlist, new Comparator<OutputFoodVO>() {
		            @Override
		            public int compare(OutputFoodVO s1, OutputFoodVO s2) {
		                if (s1.getScore() < s2.getScore()) {
		                    return 1;
		                } else if (s1.getScore() > s2.getScore()) {
		                    return -1;
		                }
		                return 0;
		            }
		        });
		        
		        logger.info("가장 높은 정확도 : " + foodlist.get(0).getScore() + ", 가장 높은 결과값 : " + foodlist.get(0).getName());

		        //정확도가 0.25보다 높을시 return
		        if(foodlist.get(0).getScore() > 0.25) {
			        return foodlist.get(0).getName();        	
		        }		        	
	        }
	        
	        return "";
	      }
	    }
	    
	    /**
	     * List all images in a product.
	     *
	     * @param projectId - Id of the project.
	     * @param computeRegion - Region name.
	     * @param productId - Id of the product.
	     * @throws IOException - on I/O errors.
	     */
	    public static void listReferenceImagesOfProduct(
	        String projectId, String computeRegion, String productId) throws IOException {
	      try (ProductSearchClient client = ProductSearchClient.create()) {

	        // Get the full path of the product.
	        String formattedParent =
	            ProductSearchClient.formatProductName(projectId, computeRegion, productId);
	        for (ReferenceImage image : client.listReferenceImages(formattedParent).iterateAll()) {
	          // Display the reference image information.
/*	          System.out.println(String.format("Reference image name: %s", image.getName()));
	          System.out.println(
	              String.format(
	                  "Reference image id: %s",
	                  image.getName().substring(image.getName().lastIndexOf('/') + 1)));
	          System.out.println(String.format("Reference image uri: %s", image.getUri()));
	          System.out.println(
	              String.format(
	                  "Reference image bounding polygons: %s \n",
	                  image.getBoundingPolysList().toString()));	*/
	        	System.out.println(image.getName().substring(image.getName().lastIndexOf('/') + 1));
	        	break;
	        }
	      }
	    }
	    
	    
	   
	    
	    
}
