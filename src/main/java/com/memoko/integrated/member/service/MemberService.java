package com.memoko.integrated.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memoko.integrated.member.dao.MemberDAO;
import com.memoko.integrated.member.vo.MemberVO;
import com.memoko.integrated.member.vo.RecipeLikesVO;
import com.memoko.integrated.member.vo.ReviewLikesVO;
import com.memoko.integrated.recipe.dao.MongoDBCloneDAO;
import com.memoko.integrated.recipe.dao.OracleDBRecipeDAO;
import com.memoko.integrated.recipe.vo.MongoCloneVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.recipe.vo.RecipeContentVO;
import com.memoko.integrated.recipe.vo.RecipeIngrdVO;
import com.memoko.integrated.recipe.vo.RecipeVO;
import com.memoko.integrated.review.dao.ReviewDAO;
import com.memoko.integrated.review.vo.PrintReviewVO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberdao;
	
	  @Autowired
	  private OracleDBRecipeDAO recipedao;
	  
	  @Autowired
	  private MongoDBCloneDAO mongodao;
	  
	  @Autowired
	  private ReviewDAO reviewdao;
	  
	  
	  public ArrayList<PrintRecipeVO> getMemberRecipes(String userId) {
		  
		  ArrayList<PrintRecipeVO> willPrintRecipes = new ArrayList<>();
		  
		  ArrayList<Integer> recipeNums = recipedao.searchRecipeNumByUserId(userId);

	      if(recipeNums.size() > 0) {
		      TreeSet<Integer> distinct = new TreeSet<Integer>(recipeNums);
		      recipeNums.clear();
		      recipeNums.addAll(distinct);
		      
		      System.out.println("recipeNums.size() : "+recipeNums.size());
		      
		      ArrayList<RecipeVO> recipes = new ArrayList<RecipeVO>();
		      ArrayList<ArrayList<RecipeContentVO>> recipeContents = new ArrayList<ArrayList<RecipeContentVO>>();
		      ArrayList<ArrayList<RecipeIngrdVO>> recipeIngrds = new ArrayList<ArrayList<RecipeIngrdVO>>();
		      
		      for(int recipeNum : recipeNums) {
		    	  
		    	  RecipeVO recipe = recipedao.searchRecipeByNum(recipeNum);
		    	  ArrayList<RecipeContentVO> content = recipedao.searchRecipeContentByNum(recipeNum);
		    	  ArrayList<RecipeIngrdVO> ingrd = recipedao.searchRecipeIngrdByNum(recipeNum);
		    	  
		    	  recipes.add(recipe);
		    	  recipeContents.add(content);
		    	  recipeIngrds.add(ingrd);
		      }
		      
		      
		      
		      for (int i = 0; i < recipes.size(); i++) 
				{
					PrintRecipeVO willPrintRecipe = new PrintRecipeVO();
					willPrintRecipe.setRecipe_num(recipes.get(i).getRecipe_num());
					willPrintRecipe.setRecipe_name(recipes.get(i).getRecipe_title());
					willPrintRecipe.setRecipe_photo(recipeContents.get(i).get(0).getRecipe_image());
					willPrintRecipe.setRecipe_hits(recipes.get(i).getRecipe_hits());
					willPrintRecipe.setRecipe_likes(recipes.get(i).getRecipe_likes());
					
					ArrayList<String> ingrds = new ArrayList<String>();
					for(RecipeIngrdVO ingrd : recipeIngrds.get(i)) {
						ingrds.add(ingrd.getIngrd_name());
					}
					willPrintRecipe.setRecipe_ingrds(ingrds);
					willPrintRecipes.add(willPrintRecipe);
				}
	      } 
	      
	      
		  return willPrintRecipes;
	  }
	  
      
      public ArrayList<PrintReviewVO> getMemberReviews(String userId){
    	  ArrayList<PrintReviewVO> willPrintReviews = null;
    	  
    	  willPrintReviews = reviewdao.searchReviewsById(userId);
    	  
    	  for(PrintReviewVO review : willPrintReviews) {
    		  review.setReply(reviewdao.selectReplysByReviewNum(review.getReview_num()));
    	  }
    	  
    	  return willPrintReviews;
      }
	

    		  
      public HashMap<String,Object> getMemberLikeRecipes(String userId){
    	  
    	  HashMap<String,Object> likeRecipes = new HashMap<>();
    	  
    	  ArrayList<Integer> likeRecipeNums = memberdao.getLikeRecipesById(userId);
    	  
    	  if(likeRecipeNums.size() > 0) {
    		  
		      for(int likeRecipeNum : likeRecipeNums) {
		    	  likeRecipes.put(String.valueOf(likeRecipeNum), true);
		      }
    	  }
    	  
    	  return likeRecipes;
      }

      public HashMap<String,Object> getMemberLikeReviews(String userId){
    	  
    	  HashMap<String,Object> likeReviews = new HashMap<>();
    	  
    	  ArrayList<Integer> likeReviewNums = memberdao.getLikeReviewsById(userId);
    	  
    	  if(likeReviewNums.size() > 0) {
    		  
    		  for(int likeRecipeNum : likeReviewNums) {
    			  likeReviews.put(String.valueOf(likeRecipeNum), true);
    		  }
    	  }
    	  
    	  return likeReviews;
      }
      
      public int recipeLike(RecipeLikesVO like, boolean check) {
		  int cnt = 0; 
		  int recipe_num = like.getRecipe_num();
		  
    	  if(check) {
			   //좋아요 클릭시
			   cnt = memberdao.addRecipeLike(like);
			   if(cnt > 0) {
				  cnt = 0;
				  if(recipe_num >= 10000) {
					  cnt = recipedao.addRecipeLikes(recipe_num);
					  if(cnt > 0) {
						  System.out.println("RecipeLike 성공!");
					  }
				  }else {
					  
					  MongoCloneVO clone = null;
					  clone = mongodao.getMongoRecipeByNum(recipe_num);
					  
					  if(clone != null) {
						  cnt = mongodao.addRecipeLikes(recipe_num);
						  if(cnt > 0) {
							  System.out.println("Like성공");
						  }
					  }else {
						  int insertCheck = 0;
						  insertCheck = mongodao.insertMongoCloneRecipe(recipe_num);   
						  if(insertCheck > 0) {
							  System.out.println("새로운 MongoCloneRecipe INSERT 성공!");
							  
							  cnt = mongodao.addRecipeLikes(recipe_num);
							  if(cnt > 0) {
								  System.out.println("Like성공");
							  }							  
							  return cnt;
						  }						  
					  }
				  }
			   }
		   }else {
			   //좋아요 취소시
			   cnt = memberdao.removeRecipeLike(like);
			   if(cnt > 0) {
				   cnt = 0;
				   if(recipe_num >= 10000) {
					   cnt = recipedao.minusRecipeLikes(recipe_num);				   
				   }else {
					   cnt = mongodao.minusRecipeLikes(recipe_num);
				   }

				   if(cnt > 0) {
					   System.out.println("Like취소 성공");
				   }
					   return cnt;
			   }
		   }
    	  
    	  return cnt;
      }
      
      public int reviewLike(ReviewLikesVO like, boolean check) {
		  int cnt = 0;
		  int review_num = like.getReview_num();
		  
    	  if(check) {
			   cnt = memberdao.addReviewLike(like);
			   if(cnt > 0) {
				   cnt = 0;
				   System.out.println("REVIEW LIKE 성공!");
				   cnt = reviewdao.addReviewLikes(review_num);
				   if(cnt > 0) {
					   System.out.println("좋아요수 더하기 성공");
					   return cnt;
				   }
			   }
		  }else {
			   cnt = memberdao.removeReviewLike(like);
			   if(cnt > 0) {
				   cnt = 0;
				   cnt = reviewdao.minusReviewLikes(review_num);
				   if(cnt > 0) {
					   System.out.println("좋아요수 빼기 성공");
					   return cnt;
				   }
			   }
		  }
    	  
    	  return cnt;
      }
      
      public MemberVO getMemberVOByUserId(String userId) {
    	  MemberVO member = null;
    	  
    	  member = memberdao.memberSelectOne(userId);
    	  
    	  
    	  return member;
      }
}
