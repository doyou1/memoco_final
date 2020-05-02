package com.memoko.integrated.review.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memoko.integrated.member.service.MemberService;
import com.memoko.integrated.member.vo.MemberVO;
import com.memoko.integrated.recipe.service.RecipeService;
import com.memoko.integrated.recipe.vo.MongoRecipeVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.review.service.ReviewService;
import com.memoko.integrated.review.vo.PrintReviewVO;
import com.memoko.integrated.review.vo.ReplyVO;
import com.memoko.integrated.review.vo.ReviewVO;

@Controller
@RequestMapping(value="/review")
public class ReviewController {
	
	@Autowired
	private MemberService memberservice;
	
	@Autowired
	private RecipeService recipeservice;
	
	@Autowired
	private ReviewService reviewservice;
	

	@RequestMapping(value="/reviewList",method=RequestMethod.GET)	
	public String reviewList(
			@RequestParam(value="review_num",defaultValue = "0") int review_num
			,@RequestParam(value="recipe_num",defaultValue = "0") int recipe_num
			,@RequestParam(value="option",defaultValue = "") String option
			,@RequestParam(value="searchText",defaultValue = "") String searchText
			,Model model
			,HttpSession session) {
		
		System.out.println("option : " + option);
		System.out.println("searchText : " + searchText);
		System.out.println("searchText.length() : " + searchText.length());
		System.out.println("review_num : " + review_num);
		System.out.println("recipe_num : " + recipe_num);
		
		
		HashMap<String,Object> map = new HashMap<>();
		
		PrintRecipeVO recipe = null;
		if(recipe_num > 0) {
			if(recipe_num <10000) {
				HashMap<String,Object> mongo = recipeservice.getMongoRecipeByRecipeNum(recipe_num);
				recipe = recipeservice.getMongoRecipe((MongoRecipeVO) mongo.get("recipe"));
			}else if(recipe_num >= 10000) {
				 recipe = recipeservice.getOracleRecipe(recipe_num);
			}
		}
		map.put("recipe",recipe);

		ArrayList<PrintReviewVO> reviews = null;
		if(review_num != 0) {
			reviews = reviewservice.getReviewsByReviewNum(review_num);
			map.put("review_num",review_num);
			
		}else {
			if(searchText.length() != 0) {
				System.out.println("getReviewsBySearch 시작");
				reviews = reviewservice.getReviewsBySearch(option, searchText);
			}
			else {
				System.out.println("getReviewsDefault 시작");
				reviews = reviewservice.getReviewsDefault();
			}			
		}
		map.put("reviews",reviews);
		
		String member_id = (String) session.getAttribute("userId");
		HashMap<String,Object> likeReviews = null;
		if(member_id != null) {
			likeReviews = memberservice.getMemberLikeReviews(member_id);
		}
		model.addAttribute("likeReviews",likeReviews);
		
		
		
		map.put("option",option);
		map.put("searchText",searchText);
		
		model.addAttribute("map", map);

		return "/review/reviewList";
	}
	
	
	@RequestMapping(value="/printReviewList")
	@ResponseBody
	public HashMap<String, Object> printReviewList(HttpSession session){
		HashMap<String, Object> map = new HashMap<String,Object>();
		
		return map;
	}
	
	
	
	@RequestMapping(value="/insertReview",method=RequestMethod.POST)
	public String insertReview(
			ReviewVO review
			, MultipartFile upload
			, @RequestParam(value="recipe_num",defaultValue = "0") int recipe_num
			,HttpSession session) {
		
		String member_id = (String) session.getAttribute("userId");
		
		MemberVO member = memberservice.getMemberVOByUserId(member_id);
		
		if(member_id != null) {
			review.setMember_id(member_id);
			review.setMember_ninckname(member.getMember_nickname());
			
			if(recipe_num > 0) {
				review.setRecipe_num(recipe_num);
			}
			int check = 0;
			check = reviewservice.insertReview(
					review,upload);
			if(check > 0) {
				System.out.println("REVIEW INSERT 성공");
			}
 
		}else {
			return "redirect:/review/reviewList";
		}

		return "redirect:/review/reviewList";
	}
	
	@RequestMapping(value="/insertReply",method=RequestMethod.POST)
	@ResponseBody
	public ReplyVO insertReply(ReplyVO reply,HttpSession session) {
		System.out.println("REPLYVO : " + reply);
		
		String member_id = (String) session.getAttribute("userId");
		int reply_num = 0;
		
		ReplyVO returnVO = null;
		if(member_id != null) {
			reply.setMember_id(member_id);
			
			reply_num = reviewservice.insertReply(reply);
			
			if(reply_num > 0) {
				System.out.println("INSERT REPLY 성공!");
			}
			
			returnVO = reviewservice.getReplyByReplyNum(reply_num);
			if(reply_num > 0 && returnVO != null) {
				return returnVO;			
			}
		}
		
		
		return null;
	}
	
	@RequestMapping(value="/replyUpdate",method=RequestMethod.POST)
	@ResponseBody
	public ReplyVO replyUpdate(ReplyVO reply
			,HttpSession session) {
		
		String member_id = (String) session.getAttribute("userId");

		ReplyVO returnVO = null;		
		if(member_id != null && reply.getReply_content().length() > 0) {
			reply.setMember_id(member_id);
			returnVO = reviewservice.updateReply(reply);
			
		}
		
		return returnVO;
	}

	@RequestMapping(value="/deleteReview",method=RequestMethod.POST)
	@ResponseBody
	public int reviewDelete(int review_num
			,HttpSession session) {

		String member_id = (String) session.getAttribute("userId");
		
		ReviewVO review = new ReviewVO();
		review.setMember_id(member_id);
		review.setReview_num(review_num);
		int check = 0;
		check = reviewservice.deleteReview(review);
		
		
		return check;
	}
	@RequestMapping(value="/replyDelete",method=RequestMethod.POST)
	@ResponseBody
	public int replyDelete(int reply_num
			,HttpSession session) {
		
		String member_id = (String) session.getAttribute("userId");

		ReplyVO reply = new ReplyVO();
		reply.setMember_id(member_id);
		reply.setReply_num(reply_num);
		
		int check = 0;
		System.out.println(reply);
		check = reviewservice.deleteReply(reply);
			
		
		return check;
	}
	
	@RequestMapping(value="/updateReview",method=RequestMethod.POST)
	public String updateReview(
			MultipartFile upload
			,ReviewVO review) {
 
		
		if(!upload.isEmpty()) {
			String savedName = reviewservice.getReviewImage(upload,review.getReview_num());
			review.setReview_image(savedName);
		}
		
		System.out.println(review);
		
		int check = reviewservice.updateReview(review);
		
		if(check > 0) {
			return "redirect:/review/reviewList?review_num="+review.getReview_num();
			
		}
		
		return "redirect:/review/reviewList";
		
	}
	
	
	
	@RequestMapping(value="/findReviews",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> findReivews(
			@RequestParam(value="option",defaultValue = "") String option
			,@RequestParam(value="searchText",defaultValue = "") String searchText
			,HttpSession session) {
		System.out.println("option : " + option);
		System.out.println("searchText : " + searchText);
		
		ArrayList<PrintReviewVO> reviews = null;
		if(searchText.length() > 0) {
			reviews = reviewservice.getReviewsBySearch(option, searchText);
		}
		else {
			reviews = reviewservice.getReviewsDefault();
		}
		
		String member_id = (String) session.getAttribute("userId");
		HashMap<String,Object> likeReviews = null;
		if(member_id != null) {
			likeReviews = memberservice.getMemberLikeReviews(member_id);
		}

		System.out.println("reviews.size() : " + reviews.size());
		
		HashMap<String,Object> map = new HashMap<>();
		
		map.put("option",option);
		map.put("searchText",searchText);
		map.put("reviews",reviews);
		map.put("likeReviews",likeReviews);

		
		return map;
	}
}
