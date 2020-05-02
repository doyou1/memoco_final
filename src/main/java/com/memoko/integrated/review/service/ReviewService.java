package com.memoko.integrated.review.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memoko.integrated.imageanalysis.service.FileService;
import com.memoko.integrated.member.dao.MemberDAO;
import com.memoko.integrated.recipe.dao.MongoDBDAO;
import com.memoko.integrated.recipe.dao.OracleDBRecipeDAO;
import com.memoko.integrated.recipe.service.RecipeService;
import com.memoko.integrated.recipe.vo.MongoRecipeVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.review.dao.ReviewDAO;
import com.memoko.integrated.review.vo.PrintReviewVO;
import com.memoko.integrated.review.vo.ReplyVO;
import com.memoko.integrated.review.vo.ReviewVO;

@Service
public class ReviewService {

   //MONGODB와 연동된 DAO
      @Autowired
      private MongoDBDAO mongodao;

      //ORACLEDB와 연동된 DAO
      @Autowired
      private OracleDBRecipeDAO recipedao;
      
      @Autowired
      private ReviewDAO reviewdao;

      @Autowired
      private MemberDAO memberdao;
      
      @Autowired
      private RecipeService recipeservice;

      //review_image 저장 폴더
      private static final String uploadPath = "\\C:\\reviewUpload";

   
      
      public ArrayList<PrintReviewVO> getReviewsDefault(){
         
         //존재하는 리뷰
         ArrayList<PrintReviewVO> reviews = reviewdao.selectAllReviewList();
         
         
         ArrayList<Integer> recipeNums = null;
         recipeNums = reviewdao.getReferencedRecipeNums();

         
         ArrayList<PrintRecipeVO> recipes = new ArrayList<>();
         
         HashMap<String,Object> map = new HashMap<>();
         map.put("recipeNums", recipeNums);
         ArrayList<PrintRecipeVO> oracles = null;
         if(recipeNums != null) {
            oracles = recipedao.getRecipesByRecipeNums(map);            
         }
         ArrayList<MongoRecipeVO> temps = new ArrayList<>();
         if(recipeNums != null) {
            for(int recipe_num : recipeNums) {
               MongoRecipeVO temp = mongodao.getMongoRecipeByRecipeNum(recipe_num);            
               if(temp != null) {
                   temps.add(temp);            	   
               }
            }            
         }
         
         
         ArrayList<PrintRecipeVO> mongos = null;

         if(!(temps.isEmpty())) {
        	 System.out.println("여기까지");
             mongos = recipeservice.getMongoRecipes(temps);        	 
         }
         
         if(oracles != null) {
            recipes.addAll(oracles);            
         }
         if(mongos != null) {
            recipes.addAll(mongos);            
         }
         
         if(recipes != null) {
            for(PrintReviewVO review : reviews) {
               if(review.getRecipe_num() != 0) {
                  for(PrintRecipeVO recipe : recipes) {
                     if(review.getRecipe_num() == recipe.getRecipe_num()) {
                        review.setRecipe_name(recipe.getRecipe_name());
                        review.setRecipe_photo(recipe.getRecipe_photo());
                     }
                  }
               }
            }            
         }
            
         //존재하는 레시피의 리플
         if(reviews != null) {
            for(PrintReviewVO review : reviews) {
               review.setReply(reviewdao.selectReplysByReviewNum(review.getReview_num()));   
            }
         }
            
      
         return reviews;
      }

      
      public ArrayList<PrintReviewVO> getReviewsBySearch(String option, String searchText){

         System.out.println("option : "  + option + ", searchText : " + searchText);
         //존재하는 레시피
         ArrayList<PrintReviewVO> reviews = new ArrayList<>();
         HashMap<String,Object > map = null;
         if(option.equals("review_title") && searchText != null) {
            
            reviews.addAll(reviewdao.getReviewsByReviewTitle(searchText));
            
         }else if(option.equals("review_content") && searchText != null) {
            
            reviews.addAll(reviewdao.getReviewsByReviewContent(searchText));
            
         }else if(option.equals("member_id") && searchText !=  null) {
            reviews.addAll(reviewdao.getReviewsByUserId(searchText));
            
         }else if(option.equals("recipe_title") && searchText != null){
            ArrayList<Integer> oracles = recipedao.searchRecipeNumByTitle(searchText);
            ArrayList<MongoRecipeVO> temp2 = mongodao.getMongoRecipesByTitle(searchText);
            
            ArrayList<Integer> mongos = new ArrayList<>();
            for(MongoRecipeVO mongo : temp2) {
               mongos.add(Integer.valueOf(mongo.getListnum()));
            }
            
            ArrayList<Integer> recipeNums = new ArrayList<Integer>();
            if(oracles != null) {
               recipeNums.addAll(oracles);               
            }
            if(mongos != null) {
               recipeNums.addAll(mongos);               
            }
            
            map = new HashMap<>();
            map.put("recipeNums", recipeNums);
            if(!(recipeNums.isEmpty())) {
               reviews.addAll(reviewdao.getReviewsByRecipeNums(map));               
            }
            
         }else if(option.equals("recipe_ingrd") && searchText != null) {
            ArrayList<Integer> oracles = recipedao.searchRecipeNumByIngrd(searchText);
            ArrayList<MongoRecipeVO> temp2 = null;
            temp2 = mongodao.getMongoRecipesByMainFood(searchText);
            
            ArrayList<Integer> mongos = new ArrayList<>();
            for(MongoRecipeVO mongo : temp2) {
               mongos.add(Integer.valueOf(mongo.getListnum()));
            }
            
            ArrayList<Integer> recipeNums = new ArrayList<Integer>();
            
            if(oracles != null) {
               recipeNums.addAll(oracles);               
            }
            
            if(!(mongos.isEmpty())) {
               recipeNums.addAll(mongos);               
            }
            
            map = new HashMap<>();
            map.put("recipeNums", recipeNums);
            if(!(recipeNums.isEmpty())) {
               reviews.addAll(reviewdao.getReviewsByRecipeNums(map));               
            }

         }
         
         ArrayList<Integer> recipeNums = null;
         recipeNums = reviewdao.getReferencedRecipeNums();
         System.out.println(recipeNums);
         map = new HashMap<>();
         map.put("recipeNums", recipeNums);
         ArrayList<PrintRecipeVO> oracles = null;
         if(recipeNums != null) {
            oracles = recipedao.getRecipesByRecipeNums(map);            
         }
         
         ArrayList<MongoRecipeVO> temps = new ArrayList<>();
         
         
         for(int recipe_num : recipeNums) {
        	MongoRecipeVO temp = mongodao.getMongoRecipeByRecipeNum(recipe_num);
            if(temp != null) {
            	temps.add(temp);
            	
            }
         }
         
         ArrayList<PrintRecipeVO> mongos = null;
         mongos = recipeservice.getMongoRecipes(temps);
         
         ArrayList<PrintRecipeVO> recipes = new ArrayList<>();
         if(oracles != null) {
            recipes.addAll(oracles);            
         }
         if(mongos != null) {
            recipes.addAll(mongos);            
         }
         
         if(recipeNums != null && !(recipes.isEmpty())) {
            for(PrintReviewVO review : reviews) {
               if(recipeNums.contains(review.getRecipe_num())){
                  for(PrintRecipeVO recipe : recipes) {
                     if(review.getRecipe_num() == recipe.getRecipe_num()) {
                        review.setRecipe_name(recipe.getRecipe_name());
                        review.setRecipe_photo(recipe.getRecipe_photo());
                     }
                  }
               }      
            }
         }
         
         
         //존재하는 레시피의 리플
         if(!(reviews.isEmpty())) {
            for(PrintReviewVO review : reviews) {
               review.setReply(reviewdao.selectReplysByReviewNum(review.getReview_num()));
            }
         }

         
         return reviews;
      }
      
      public int insertReview(
            ReviewVO review
            ,MultipartFile upload) {
         int check = 0;
         
         if(!upload.isEmpty()) {
            String review_image = FileService.saveFile(upload, uploadPath);
            
            if(review_image != null) {
               review.setReview_image(review_image);
            }
         }
         
         check = reviewdao.insertReview(review);
         
         return check;
      }
      
      public int insertReply(ReplyVO reply) {
         
         reviewdao.insertReply(reply);
         
         return reply.getReply_num();
      }
      
      public ReplyVO getReplyByReplyNum(int reply_num) {
         ReplyVO returnVO = null;
         returnVO = reviewdao.selectReplyByReplyNum(reply_num);
         
         return returnVO;
      }

      public ReplyVO updateReply(ReplyVO reply) {
         int check = 0;
         
         check = reviewdao.updateReply(reply);
         
         ReplyVO returnVO = null;
         
         if(check > 0) {
            returnVO = reviewdao.selectReplyByReplyNum(reply.getReply_num());
         }
         return returnVO;
      }

      public int deleteReply(ReplyVO reply) {
         int check = 0;
         
         check = reviewdao.deleteReply(reply);
         
         return check;
      }

      public int deleteReview(ReviewVO review) {
         int check = 0;
         
         check = reviewdao.deleteReview(review);
         
         return check;
      }

      
      public String getReviewImage(MultipartFile upload,int review_num) {
         String savedName = null;
         
         savedName = FileService.saveFile(upload, uploadPath);
         
         PrintReviewVO oldReview = reviewdao.getReviewByReviewNum(review_num);
         if(oldReview.getReview_image() != null) {
            String fullPath = uploadPath + "\\" + oldReview.getReview_image();
            boolean result = FileService.deleteFile(fullPath);
            if(result) {
               System.out.println("기존이미지삭제");
            }
         }
         return savedName;
      }
      
      
      public int updateReview(ReviewVO review) {
         int check = 0;
         
         
         check = reviewdao.updateReview(review);
         
         return check;
      }
      
      
      public ArrayList<PrintReviewVO> getReviewsByReviewNum(int review_num) {
                  
         ArrayList<PrintReviewVO> reviews = null;
         
         int max = reviewdao.getReviewsCount();
         int prevCount = reviewdao.getReviewsCountByReviewNum(review_num);
         System.out.println("max : " + max);
         System.out.println("prevCount : " + prevCount);
         if(max > 10) {
            if(prevCount > 10) {
               reviews = reviewdao.getReviewsByCount((prevCount+3) <= max ? (prevCount+3) : max);            
            }else {
               reviews = reviewdao.getReviewsByCount((prevCount+3) >= 10 ? (prevCount+3) : 10);
            }            
         }else {
            reviews = reviewdao.selectAllReviewList();
         }
         
         System.out.println("reivews : " + reviews);
         

         ArrayList<Integer> recipeNums = new ArrayList<>();
         if(reviews != null) {
            for(PrintReviewVO review : reviews) {
               
               if(review.getRecipe_num() != 0) {
                  recipeNums.add(review.getRecipe_num());
               }
            }            
         }

//         recipeNums = reviewdao.getReferencedRecipeNums();
         HashMap<String,Object> map = new HashMap<>();
         map.put("recipeNums", recipeNums);
         ArrayList<PrintRecipeVO> oracles = null;
         if(!(recipeNums.isEmpty())) {
            oracles = recipedao.getRecipesByRecipeNums(map);
            System.out.println("oracles : " + oracles);            
         }

         ArrayList<MongoRecipeVO> temps = new ArrayList<>();
         
         if(!(recipeNums.isEmpty())) {
	         for(int recipe_num : recipeNums) {
	        	MongoRecipeVO temp =  mongodao.getMongoRecipeByRecipeNum(recipe_num);
	            if(temp != null) {
		        	temps.add(temp);	            	
	            }
	         }
         }
         
         ArrayList<PrintRecipeVO> mongos = null;
         if(!(temps.isEmpty())) {
             mongos = recipeservice.getMongoRecipes(temps);        	 
         }
         
         ArrayList<PrintRecipeVO> recipes = new ArrayList<>();
         if(oracles != null) {
            recipes.addAll(oracles);
            
         }
         if(mongos != null) {
            recipes.addAll(mongos);            
         }
         
         if(!(recipeNums.isEmpty()) && !(recipes.isEmpty())) {
            for(PrintReviewVO review : reviews) {
               if(recipeNums.contains(review.getRecipe_num())){
                  for(PrintRecipeVO recipe : recipes) {
                     if(review.getRecipe_num() == recipe.getRecipe_num()) {
                        review.setRecipe_name(recipe.getRecipe_name());
                        review.setRecipe_photo(recipe.getRecipe_photo());
                     }
                  }
               }      
            }
         }


         //존재하는 레시피의 리플
         if(reviews != null) {
            for(PrintReviewVO review : reviews) {
               review.setReply(reviewdao.selectReplysByReviewNum(review.getReview_num()));
            }
         }
         return reviews;
      }
      
      
}
