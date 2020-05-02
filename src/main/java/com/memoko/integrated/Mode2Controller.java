package com.memoko.integrated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memoko.integrated.member.service.MemberService;
import com.memoko.integrated.recipe.service.RecipeService;
import com.memoko.integrated.recipe.vo.MongoRecipeVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.review.service.ReviewService;
import com.memoko.integrated.review.vo.PrintReviewVO;

@Controller
@RequestMapping(value="/mode2")
public class Mode2Controller {
	
	
	@Autowired
	private MemberService memberservice;
	
	@Autowired
	private RecipeService recipeservice;
	
	@Autowired
	private ReviewService reviewservice;
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String mode2Home(HttpSession session,Model model) throws Exception {
		
		String member_id = null;
		member_id = (String) session.getAttribute("userId");
      	HashMap<String,Object> likeRecipes = null;
      	HashMap<String,Object> likeReviews = null;
      	
		if(member_id != null) {
			likeRecipes = memberservice.getMemberLikeRecipes(member_id);
			likeReviews = memberservice.getMemberLikeReviews(member_id);
		}
		
		
		ArrayList<PrintRecipeVO> willPrintRecipes = recipeservice.getRecipesByFood("돼지");

		ArrayList<PrintReviewVO> willPrintReviews = reviewservice.getReviewsDefault();
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("likeRecipes", likeRecipes);
		map.put("likeReviews", likeReviews);
		map.put("recipes",willPrintRecipes);	
		map.put("reviews",willPrintReviews);
		
		model.addAttribute("map",map);
		return "/mode2/home";
	}
	
	/* JSP에서의 CHECKBOX CHANGE시 AJAX로 새로운 레시피 검색 및 RPINT
	 * Oracle Recipe CRUD
     * 특정 SELECT 
     * + Mongo Recipe FIND
     * 음식재료 칼럼에에 접근*/
	@RequestMapping(value="/printRecipeList",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> printRecipeList(
			@RequestParam(value="searchText",defaultValue = "") String searchText
			,@RequestParam(value="option",defaultValue = "") String option
			,HttpSession session) throws Exception {
			
		String member_id = null;
		member_id = (String) session.getAttribute("userId");
      	HashMap<String,Object> likeRecipes = null;
      	
		if(member_id != null) {
			likeRecipes = memberservice.getMemberLikeRecipes(member_id);
			
		}
		
		ArrayList<PrintRecipeVO> willPrintRecipes = null;
		

		
		if(option.equals("recipe_title") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByTitle(searchText);
			
		}else if(option.equals("ingrd_name") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByFood(searchText);
		}else {

		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("likeRecipes", likeRecipes);
		map.put("recipe",willPrintRecipes);
		return map;
	}
}
