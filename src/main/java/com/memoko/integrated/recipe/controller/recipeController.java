package com.memoko.integrated.recipe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memoko.integrated.member.service.MemberService;
import com.memoko.integrated.recipe.service.RecipeService;
import com.memoko.integrated.recipe.vo.MongoRecipeVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
/**
 * Handles requests for the application home page.
 */

@Controller
@RequestMapping(value="/recipe")
public class recipeController {

	private static final Logger logger = LoggerFactory.getLogger(recipeController.class);

	//이미지가 저장되는 폴더의 전체경로(외부폴더)
	private static final String uploadPath = "\\C:\\recipeUpload";
	
	@Autowired
	private RecipeService recipeservice;
	
	@Autowired
	private MemberService memberservice;
	
	private ArrayList<String> oldFood = null;
	private ArrayList<String> oldFilePath = null;
	
	
	//Storage 객체 생성을 위한 변수선언
	String bucketName = "my-project0228-269601";
	
	//Stroage 객체 생성을 위한 bucket 사용 인증키
    @Value(value = "classpath:sjh200228.json")
    public Resource accountResource;

    //클래스 변수 선언 종료
    
    /*Oracle Recipe CRUD
     * Insert Recipe Form 이동*/
	@RequestMapping(value="/insertRecipeForm",method=RequestMethod.GET)
	public String insertRecipeForm() {
		
		return "/recipe/insertRecipeForm";
	}
	
	
	@RequestMapping(value="/insertRecipe",method=RequestMethod.POST)
	public String insertRecipe(
			@RequestParam(value="recipe_title",defaultValue = "") String recipe_title
			,@RequestParam(value="ingrd_num",defaultValue = "") int[] ingrd_num
			,@RequestParam(value="ingrd_name",defaultValue = "") String[] ingrd_name
			,@RequestParam(value="ingrd_amount",defaultValue = "") String[] ingrd_amount
			,@RequestParam(value="content_num",defaultValue = "") int[] content_num
			,ArrayList<MultipartFile> recipe_image
			,@RequestParam(value="recipe_content",defaultValue = "") String[] recipe_content
			,HttpSession session) {

		System.out.println("ORACLEDB RECIPE INSERT 시작");
		
		System.out.println("recipe_title : " + recipe_title);

		String member_id = (String) session.getAttribute("userId");

		int recipe_num = 0;
		recipe_num = recipeservice.insertRecipe(recipe_title
				, ingrd_num
				, ingrd_name
				, ingrd_amount
				, content_num
				, recipe_image
				, recipe_content
				, member_id);
		
		if(recipe_num > 0) {
			System.out.println("RECIPE INSERT 완료");
			
			return "redirect:/recipe/recipeReadForm?listnum="+recipe_num;
		}
		
		return "redirect:/recipe/recipeList";
	}
	
   
	/*Oracle Recipe CRUD
     * 다중 SELECT
     * + Mongo Recipe FIND */
    //사용자에게 입력받은 foods + filePath를 통해 Recipe List model 추가
    @RequestMapping(value="/findRecipesByInput", method=RequestMethod.POST)
	public String findRecipeList(
			@RequestParam(value="food",defaultValue = "") ArrayList<String> food
			,@RequestParam(value="filePath",defaultValue = "")ArrayList<String> filePath
			,@RequestParam(value="mainFood",defaultValue = "") String mainFood
			,Model model
			,HttpSession session) throws Exception
	{
    	logger.info("***FindRecipesByInput Start***");
    	logger.info("Food : {}", food);
    	logger.info("filePath : {}",filePath);
    	logger.info("mainFood : {}", mainFood);
    	
    	if(food.size() > 0) {
    		
       		ReferenceImageScheduler.food_list.addAll(food);
       		
    		oldFood = food;
    	}
  
    	ArrayList<PrintRecipeVO> willPrintRecipes = recipeservice.getRecipesByFood(mainFood);
//    	ArrayList<PrintRecipeVO> willPrintRecipes = recipeservice.getRecipesByFoods(mainFood,food);    	
    	
        String member_id = (String) session.getAttribute("userId");    	
        HashMap<String,Object> likeRecipes = null;
        if(member_id != null) {
        	likeRecipes = memberservice.getMemberLikeRecipes(member_id);
        }
        
       	if(filePath.size() > 0) {
    		ReferenceImageScheduler.filePath_list.addAll(filePath);
    		oldFilePath = filePath;
    	}
        
       	HashMap<String,Object> map = new HashMap<>();
        
       	map.put("recipes",willPrintRecipes);
       	map.put("likeRecipes",likeRecipes);
        
		model.addAttribute("type", "mode1");
    	model.addAttribute("food", food);
    	model.addAttribute("mainFood", mainFood);
    	model.addAttribute("map", map);

    	logger.info("***FindRecipesByInput End***");

		return "/recipe/recipeListForm";
	}
	
	/*Oracle Recipe CRUD
     * 특정 SELECT
     * + Mongo Recipe FIND */
	@RequestMapping(value="/recipeReadForm",method=RequestMethod.GET)
	public String recipeReadForm(
			@RequestParam(value="listnum",defaultValue = "") String listnum
			,Model model
			,HttpSession session) {

		String member_id = (String) session.getAttribute("userId");
		
		if(member_id != null) {			
			HashMap<String,Object> likeRecipes = memberservice.getMemberLikeRecipes(member_id);
			
			boolean check = false;
			try {
				check = (boolean) likeRecipes.get(listnum);				
			}catch (Exception e) {
				check = false;
			}
			
			if(check) {
				model.addAttribute("likeRecipe", true);
			}else {
				model.addAttribute("likeRecipe", false);
			}
		}else {
			model.addAttribute("likeRecipe", false);
		}
		
		if(listnum != "") {
			int recipe_num = Integer.parseInt(listnum);
			
			int cnt = 0;
			cnt = recipeservice.updateViewCount(recipe_num);
			
			HashMap<String,Object> recipe = null;
			if(recipe_num >= 10000) {
				recipe = recipeservice.getOracleRecipeByRecipeNum(recipe_num);
				model.addAttribute("recipe", recipe);
			}else {
				recipe = recipeservice.getMongoRecipeByRecipeNum(recipe_num);
				model.addAttribute("recipe", recipe);
			}
			
			model.addAttribute("recipe_num", recipe_num);
		}

		return "/recipe/recipeReadForm";
	}

	
	/*Oracle Recipe CRUD
     * 특정 레시피 UPDATE를 위한 UPDATE FORM
     */
	@RequestMapping(value="/recipeUpdateForm",method=RequestMethod.GET)
	public String recipeUpdateForm(
			@RequestParam(value="recipe_num",defaultValue = "0") int recipe_num,
			Model model) {

		if(recipe_num != 0) {
			
			if(recipe_num >= 10000) {
				//Oracle Recipe만 수정가능

				HashMap<String,Object> recipe = recipeservice.getOracleRecipeByRecipeNum(recipe_num);
				
				model.addAttribute("recipe",recipe);
				
				
				//JSP에서 Recipe Print와 수정삭제버튼 동적 활성화/비활성화를 위한
				model.addAttribute("recipe_num", recipe_num);
			}			


		}
		
		return "/recipe/recipeUpdateForm";
	}
	
	
	@RequestMapping(value="/updateRecipe",method=RequestMethod.POST)
	public String recipeUpdate(
			@RequestParam(value="recipe_title",defaultValue = "") String recipe_title
			,@RequestParam(value="ingrd_num",defaultValue = "") ArrayList<Integer> ingrd_num
			,@RequestParam(value="ingrd_name",defaultValue = "") ArrayList<String> ingrd_name
			,@RequestParam(value="ingrd_amount",defaultValue = "") ArrayList<String> ingrd_amount
			,@RequestParam(value="content_num",defaultValue = "") ArrayList<Integer> content_num
			,ArrayList<MultipartFile> recipe_image
			,@RequestParam(value="recipe_content",defaultValue = "") ArrayList<String> recipe_content
			,@RequestParam(value="recipe_num",defaultValue = "0") int recipe_num
			,@RequestParam(value="oldIngrdCount",defaultValue = "0") int oldIngrdCount
			,@RequestParam(value="oldContentCount",defaultValue = "0") int oldContentCount
			,HttpSession session) {
				
		String member_id = (String) session.getAttribute("userId");
		

		int cnt = 0;
		
		cnt = recipeservice.updateRecipe(recipe_title
				, ingrd_num
				, ingrd_name
				, ingrd_amount
				, content_num
				, recipe_image
				, recipe_content
				, recipe_num
				, oldIngrdCount
				, oldContentCount
				, member_id);
		
				
		if(cnt > 0) {
			System.out.println("RECIPE UPDATE 전체 성공");
			
			return "redirect:/recipe/recipeReadForm?listnum="+recipe_num;
		}
		
		return "redirect:/recipe/recipeList";
	}
		
	@RequestMapping(value="/searchRecipeByText",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> searchRecipeByText(
			@RequestParam(value="searchText",defaultValue = "")String searchText
			,@RequestParam(value="option",defaultValue = "")String option
			,HttpSession session) {
		
		ArrayList<PrintRecipeVO> willPrintRecipes = null;
		
		if(option.equals("recipe_title") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByTitle(searchText);
			
		}else if(option.equals("ingrd_name") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByFood(searchText);
			
		}else {
		
		}

		String member_id = null;
		member_id = (String) session.getAttribute("userId");

		HashMap<String,Object> likeRecipes = null;
        if(member_id != null) {
        	likeRecipes = memberservice.getMemberLikeRecipes(member_id);
        }

		HashMap<String,Object> map = new HashMap<>();
		map.put("recipes",willPrintRecipes);
       	map.put("likeRecipes",likeRecipes);
		map.put("searchText",searchText);
		map.put("option",option);
		logger.info("레시피 검색 끝");
		return map;
	}
	
	//Mode2/home에서의 이동
	@RequestMapping(value="/recipeList",method=RequestMethod.GET)
	public String recipeList(
			@RequestParam(value="option",defaultValue = "" ) String option
			,@RequestParam(value="searchText",defaultValue = "") String searchText
			,Model model
			, HttpSession session) {
		
		System.out.println("searchText : " + searchText);
		System.out.println("option : " + option);
		
		ArrayList<PrintRecipeVO> willPrintRecipes = null;
		
		if(option.equals("recipe_title") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByTitle(searchText);
			
		}else if(option.equals("ingrd_name") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByFood(searchText);
			
		}else {
			willPrintRecipes = recipeservice.getRecipesDefault();
		}

		String member_id = null;
		member_id = (String) session.getAttribute("userId");

		HashMap<String,Object> likeRecipes = null;
        if(member_id != null) {
        	likeRecipes = memberservice.getMemberLikeRecipes(member_id);
        }
        
        
		HashMap<String,Object> map = new HashMap<>();
		map.put("recipes",willPrintRecipes);
       	map.put("likeRecipes",likeRecipes);
		map.put("searchText",searchText);
		map.put("option",option);		

		if(searchText.length() > 0) {
			map.put("doneSearch","true");

		}else {
			map.put("doneSearch","false");
		}
		
		model.addAttribute("map", map);
		model.addAttribute("type", "mode2");
		
		return "/recipe/recipeListForm";
	}
	
	
	//Scroll 이벤트에 따른 getRecipeList
	@RequestMapping(value="/findRecipesByMode2",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> findRecipesByMode2(
			@RequestParam(value="option",defaultValue = "") String option
			,@RequestParam(value="searchText",defaultValue = "") String searchText
			,HttpSession session){
		
		System.out.println("findRecipesByMode2 시작");
		HashMap<String,Object> map = new HashMap<>();
		System.out.println("option : " + option);
		System.out.println("searchText : " + searchText);
		map.put("option",option);
		
		map.put("searchText",searchText);
		
		ArrayList<PrintRecipeVO> willPrintRecipes = null;
		
		if(option.equals("recipe_title") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByTitle(searchText);
		}else if(option.equals("ingrd_name") && searchText != "") {
			willPrintRecipes = recipeservice.getRecipesByFood(searchText);
			
		}else {
			willPrintRecipes = recipeservice.getRecipesDefault();
		}

		String member_id = null;
		member_id = (String) session.getAttribute("userId");

		HashMap<String,Object> likeRecipes = null;
        if(member_id != null) {
        	likeRecipes = memberservice.getMemberLikeRecipes(member_id);
        }


		
		map.put("recipes",willPrintRecipes);
       	map.put("likeRecipes",likeRecipes);
       	
		if(searchText.length() > 0) {
			map.put("doneSearch","true");
		}else {
			map.put("doneSearch","false");
		}

		System.out.println("findRecipesByMode2 종료");		
		return map;
	}
	
	@RequestMapping(value="/findRecipesByMode1",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> findRecipesByMode1(			
			@RequestParam(value="mainFood",defaultValue = "") String mainFood
			, @RequestParam(value="subFoods",defaultValue = "") ArrayList<String> subFoods
			,HttpSession session){
		System.out.println("findRecipesByMode1 시작");
		
		ArrayList<PrintRecipeVO> willPrintRecipes = null;
		System.out.println("mainFood : " + mainFood);

		if(subFoods.size() == 0) {
			willPrintRecipes = recipeservice.getRecipesByFood(mainFood);			
		}else {
			willPrintRecipes = recipeservice.getRecipesByFoods(mainFood, subFoods);
		}
		
		String member_id = null;
		member_id = (String) session.getAttribute("userId");

		HashMap<String,Object> likeRecipes = null;
        if(member_id != null) {
        	likeRecipes = memberservice.getMemberLikeRecipes(member_id);
        }


		HashMap<String,Object> map = new HashMap<>();
		map.put("recipes",willPrintRecipes);
       	map.put("likeRecipes",likeRecipes);

		System.out.println("findRecipesByMode1 종료");
		return map;
	}
}
