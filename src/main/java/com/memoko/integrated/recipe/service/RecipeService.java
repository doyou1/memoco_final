package com.memoko.integrated.recipe.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.memoko.integrated.imageanalysis.service.FileService;
import com.memoko.integrated.member.dao.MemberDAO;
import com.memoko.integrated.recipe.controller.ReferenceImageScheduler;
import com.memoko.integrated.recipe.controller.recipeController;
import com.memoko.integrated.recipe.dao.MongoDBCloneDAO;
import com.memoko.integrated.recipe.dao.MongoDBDAO;
import com.memoko.integrated.recipe.dao.OracleDBRecipeDAO;
import com.memoko.integrated.recipe.vo.MongoCloneVO;
import com.memoko.integrated.recipe.vo.MongoRecipeVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.recipe.vo.RecipeContentVO;
import com.memoko.integrated.recipe.vo.RecipeIngrdVO;
import com.memoko.integrated.recipe.vo.RecipeVO;

@Service
public class RecipeService {
	
	private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

	
	//이미지가 저장되는 폴더의 전체경로(외부폴더)
	private static final String uploadPath = "\\C:\\recipeUpload";

	//MONGODB와 연동된 DAO
		@Autowired
		private MongoDBDAO mongodao;

		@Autowired
		private MongoDBCloneDAO mongoCdao;
		
		//ORACLEDB와 연동된 DAO
		@Autowired
		private OracleDBRecipeDAO recipedao;

		@Autowired
		private MemberDAO memberdao;

		
		public int insertRecipe(
				String recipe_title
				,int[] ingrd_num
				,String[] ingrd_name
				,String[] ingrd_amount
				,int[] content_num
				,ArrayList<MultipartFile> recipe_image
				,String[] recipe_content
				,String member_id) {
			
			logger.info("***InsertRecipe Start***");
			
			RecipeVO recipe = new RecipeVO();
			recipe.setMember_id(member_id);
			recipe.setRecipe_title(recipe_title);

			//레시피 테이블 INSERT/ 사용자입력칼럼은 member_id,recipe_title 
			int check = recipedao.insertRecipe(recipe);
			
			int recipe_num = 0;
			if(check > 0) {
				logger.info("RECIPE INSERT 성공!");
				logger.info("Selectkey를 이용해 방금 Insert된 recipe_num(seq.currval) 리턴받음 : {}"
						,recipe.getRecipe_num());
				
				recipe_num = recipe.getRecipe_num();

				logger.info("입력받은 이미지 갯수 recipe_image size : {}",recipe_image.size());
				logger.info("이미지에 상응하는 컨텐츠 갯수 recipe_content size : {}", recipe_content.length);
		
				//Contents, Images 다중 INSERT
				for(int i = 0; i < recipe_image.size(); i++) {
					RecipeContentVO contentVO = new RecipeContentVO();
					contentVO.setMember_id(member_id);
					contentVO.setRecipe_num(recipe_num);
					
					String content = recipe_content[i];
					MultipartFile image = recipe_image.get(i);
					int num = content_num[i];
					
					//content 입력했는지 확인
					if(content.length() != 0) {
						contentVO.setRecipe_content(content);
					}
					
					//image 입력했는지 확인
					if(!image.isEmpty()) {
						String savedFileName = FileService.saveFile(image, uploadPath);
						logger.info("Saved Image Name : {}", savedFileName);
						contentVO.setRecipe_image(savedFileName);
					}
					
					if(num >= 0) {
						contentVO.setContent_num(num);
					}
					
					check = recipedao.insertRecipeContent(contentVO);
					if(check > 0) {
						logger.info("Recipe_Content {}번째 Insert 성공!",i);
					}
				}

				logger.info("CONTENTS INSERT 완료");

				for(int i = 0; i < ingrd_name.length; i++) {
					
					
					RecipeIngrdVO ingrdVO = new RecipeIngrdVO();
					ingrdVO.setRecipe_num(recipe_num);
					ingrdVO.setMember_id(member_id);
					
					
					String name = ingrd_name[i];
					String amount = ingrd_amount[i];
					int num = ingrd_num[i];
					
					if(name != null && name != "") {
						ingrdVO.setIngrd_name(name);
					}else {
						continue;
					}
					if(amount != null && amount != "") {
						ingrdVO.setIngrd_amount(amount);					
					}else {
						continue;
					}
					
					if(num >= 0) {
						ingrdVO.setIngrd_num(num);
					}else {
						continue;
					}
					check = recipedao.insertRecipeIngrd(ingrdVO);
					if(check > 0) {
						logger.info("Recipe_Ingrd {}번째 Insert 성공!",i);
					}
				}
				
				logger.info("INGRDS INSERT 완료");
			}

			if(check > 0 && recipe_num > 0) {
				return recipe_num;
			}
			
			return 0;
		}
	
    	public ArrayList<PrintRecipeVO> getRecipesByFoods(String mainFood, ArrayList<String> foods){
    		ArrayList<PrintRecipeVO> willPrintRecipes = new ArrayList<>();
    		
    		if(foods.size() > 0) {
    			
    			//food 배열을 통해 몽고DB에서 모든 재료가 들어간 RecipeList를 리턴받음
        		ArrayList<MongoRecipeVO> mongos = mongodao.getMongoRecipesByFoods(mainFood,foods);
        		
        		ArrayList<PrintRecipeVO> mongoRecipes = getMongoRecipes(mongos);


        		ArrayList<Integer> recipeNums = null;
        		
        		recipeNums = recipedao.searchRecipeNumByIngrd(mainFood);
        		
        		ArrayList<Integer> temp = null;
        		for(String s : foods) {
        			if(s != null && s != "") {
        				temp = recipedao.searchRecipeNumByIngrd(s);
        				if(recipeNums != null && temp != null) {
       						recipeNums.addAll(temp);
       					}
       				}
       			}
        		
        		if(recipeNums != null) {
            		TreeSet<Integer> distinct = new TreeSet<Integer>(recipeNums);
      		      	recipeNums.clear();
      		      	recipeNums.addAll(distinct);
            		
      		      	ArrayList<PrintRecipeVO> oracleRecipes = getOracleRecipes(recipeNums);
      		      	
      		      	willPrintRecipes.addAll(mongoRecipes);
      		      	willPrintRecipes.addAll(oracleRecipes);
            		
            		Collections.shuffle(willPrintRecipes);        			
        		}
        		
        	}else {
        		//food 배열을 통해 몽고DB에서 모든 재료가 들어간 RecipeList를 리턴받음
        		ArrayList<MongoRecipeVO> mongos = mongodao.getMongoRecipesByMainFood(mainFood);
        		
        		ArrayList<PrintRecipeVO> mongoRecipes = getMongoRecipes(mongos);

    			
        		ArrayList<Integer> recipeNums = null;
        		recipeNums = recipedao.searchRecipeNumByIngrd(mainFood);
        		ArrayList<Integer> temp = null;
        		for(String s : foods) {
        			if(s != null && s != "") {
        				temp = recipedao.searchRecipeNumByIngrd(s);
        				if(recipeNums != null && temp != null) {
       						recipeNums.addAll(temp);
       					}
       				}
       			}
        		
        		if(recipeNums != null) {
            		TreeSet<Integer> distinct = new TreeSet<Integer>(recipeNums);
      		      	recipeNums.clear();
      		      	recipeNums.addAll(distinct);
      		      	ArrayList<PrintRecipeVO> oracleRecipes = getOracleRecipes(recipeNums);
      		      	
      		      	willPrintRecipes.addAll(mongoRecipes);
      		      	willPrintRecipes.addAll(oracleRecipes);
      		      	Collections.shuffle(willPrintRecipes);        			
        		}
        	}
    		
    		return willPrintRecipes;
    	}

    	public ArrayList<PrintRecipeVO> getRecipesByFood(String searchText){
    		
    		ArrayList<PrintRecipeVO> willPrintRecipes = new ArrayList<>();
    		
			//food 배열을 통해 몽고DB에서 모든 재료가 들어간 RecipeList를 리턴받음
    		//주재료로 검색해도될까요
    		ArrayList<MongoRecipeVO> mongos = null;
    		mongos = mongodao.getMongoRecipesByMainFood(searchText);
			
    		ArrayList<PrintRecipeVO> mongoRecipes = null;
    		if(mongos != null) {
        		mongoRecipes = getMongoRecipes(mongos);
        		if(mongoRecipes != null) {
            		willPrintRecipes.addAll(mongoRecipes);
            	}
    		}
			    			
        	
    		ArrayList<Integer> oracles = null;
        		
        	oracles = recipedao.searchRecipeNumByIngrd(searchText);
        		
        	if(oracles != null) {
            	ArrayList<PrintRecipeVO> oracleRecipes = getOracleRecipes(oracles);
        	    willPrintRecipes.addAll(oracleRecipes);    			
        	}
    			
    		    
        	Collections.shuffle(willPrintRecipes);


    		return willPrintRecipes;
    	}

    	public ArrayList<PrintRecipeVO> getRecipesByTitle(String searchText){

    		ArrayList<MongoRecipeVO> mongos = mongodao.getMongoRecipesByTitle(searchText);
    		ArrayList<PrintRecipeVO> mongoRecipes = getMongoRecipes(mongos);
			

    		ArrayList<Integer> oracles = recipedao.searchRecipeNumByTitle(searchText);
    		ArrayList<PrintRecipeVO> oracleRecipes = getOracleRecipes(oracles);
    		
    		ArrayList<PrintRecipeVO> willPrintRecipes = new ArrayList<>();
    		willPrintRecipes.addAll(mongoRecipes);
    		willPrintRecipes.addAll(oracleRecipes);
    		
    		return willPrintRecipes;
    	}
    	
    	public ArrayList<PrintRecipeVO> getRecipesDefault(){
    		ArrayList<MongoRecipeVO> mongos = null;
    		mongos = mongodao.getDefaultRecipeListForMode2();

    		ArrayList<PrintRecipeVO> mongoRecipes = null;

    		ArrayList<PrintRecipeVO> willPrintRecipes = null;

    		if(mongos != null) {

    			willPrintRecipes = new ArrayList<>();
        		mongoRecipes = new ArrayList<>();
    			System.out.println("getMongoRecipes : " + mongos.size());
    			
    										
    			for (MongoRecipeVO mongo : mongos) 
    	    	{
    	    		PrintRecipeVO willPrintRecipe = new PrintRecipeVO();

    	    		willPrintRecipe.setRecipe_num(Integer.valueOf(mongo.getListnum()));
    	    		willPrintRecipe.setRecipe_name(mongo.getListname());
    	    		willPrintRecipe.setRecipe_photo(mongo.getListphoto().get(0));
    	    		willPrintRecipe.setRecipe_ingrds(mongo.getListingrd());
    	    		mongoRecipes.add(willPrintRecipe);	 
    	    	}

    			if(mongoRecipes != null) {
            		willPrintRecipes.addAll(mongoRecipes);    				
    			}

    		}
    		
    		
    		return willPrintRecipes;
    	}
    	
    	
    	
		public ArrayList<PrintRecipeVO> getOracleRecipes(ArrayList<Integer> recipeNums){
			
			ArrayList<PrintRecipeVO> oracleRecipes = new ArrayList<>();
			
    		//Recipe 테이블을 담을 ArrayList
    		ArrayList<RecipeVO> recipeslist = new ArrayList<RecipeVO>();
    		//RecipeContent 테이블을 담을 ArrayList
    		ArrayList<ArrayList<RecipeContentVO>> recipeContentsList = new ArrayList<ArrayList<RecipeContentVO>>();
    		//RecipeIngrd 테이블을 담을 ArrayList
    		ArrayList<ArrayList<RecipeIngrdVO>> recipeIngrdsList = new ArrayList<ArrayList<RecipeIngrdVO>>();
    		
    		//리턴받은 recipe_num을 통해 RecipeVO, RecipeContent들, RecipeIngrd들을 모음
    		for(int recipe_num : recipeNums) {
    			RecipeVO recipe = recipedao.searchRecipeByNum(recipe_num);				
    			ArrayList<RecipeContentVO> recipeContents = recipedao.searchRecipeContentByNum(recipe_num);
    			ArrayList<RecipeIngrdVO> recipeIngrds = recipedao.searchRecipeIngrdByNum(recipe_num);
    			
    			System.out.println("recipe : " + recipe);
    			recipeslist.add(recipe);
    			recipeContentsList.add(recipeContents);
    			recipeIngrdsList.add(recipeIngrds);
    		}
    		
    		if(recipeNums != null) {
    			//Recipe 테이블을 담을 ArrayList
    			ArrayList<RecipeVO> recipes = new ArrayList<RecipeVO>();
    			//RecipeContent 테이블을 담을 ArrayList
    			ArrayList<ArrayList<RecipeContentVO>> recipeContents = new ArrayList<ArrayList<RecipeContentVO>>();
    			//RecipeIngrd 테이블을 담을 ArrayList
    			ArrayList<ArrayList<RecipeIngrdVO>> recipeIngrds = new ArrayList<ArrayList<RecipeIngrdVO>>();

    			//리턴받음 recipe_num을 통해 RecipeVO, RecipeContent들, RecipeIngrd들을 모음
    			for(int searchNum : recipeNums) {
    				RecipeVO recipe = recipedao.searchRecipeByNum(searchNum);				
    				ArrayList<RecipeContentVO> contents = recipedao.searchRecipeContentByNum(searchNum);
    				ArrayList<RecipeIngrdVO> ingrds = recipedao.searchRecipeIngrdByNum(searchNum);
    				
    				System.out.println("recipe : " + recipe);
    				recipes.add(recipe);
    				recipeContents.add(contents);
    				recipeIngrds.add(ingrds);
    			}
    			

    			for (int i = 0; i < recipes.size(); i++) 
    			{
    				PrintRecipeVO willPrintRecipe = new PrintRecipeVO();
    				willPrintRecipe.setRecipe_num(recipes.get(i).getRecipe_num());
    				willPrintRecipe.setRecipe_name(recipes.get(i).getRecipe_title());
    				willPrintRecipe.setRecipe_photo(recipeContents.get(i).get(0).getRecipe_image());
    				
    				ArrayList<String> ingrds = new ArrayList<String>();
    				for(RecipeIngrdVO ingrd : recipeIngrds.get(i)) {
    					ingrds.add(ingrd.getIngrd_name());
    				}
    				willPrintRecipe.setRecipe_likes(recipes.get(i).getRecipe_likes());
    				willPrintRecipe.setRecipe_hits(recipes.get(i).getRecipe_hits());
    				willPrintRecipe.setRecipe_ingrds(ingrds);
    				oracleRecipes.add(willPrintRecipe);
    			}

    		}
			
			return oracleRecipes;
		}
		
		public ArrayList<PrintRecipeVO> getMongoRecipes(ArrayList<MongoRecipeVO> mongos){
			ArrayList<PrintRecipeVO> mongoRecipes = new ArrayList<>();
			
			ArrayList<MongoCloneVO> mongoCRecipes = new ArrayList<>();
			mongoCRecipes = mongoCdao.getMongoRecipeList();
			ArrayList<Integer> mongoCNums = new ArrayList<>();
			mongoCNums = mongoCdao.getRecipeNums();
			
			if(mongoCRecipes != null) {
				for (int i = 0; i < mongos.size(); i++) 
	    		{
	    			PrintRecipeVO willPrintRecipe = new PrintRecipeVO();
	    			willPrintRecipe.setRecipe_num(Integer.parseInt(mongos.get(i).getListnum()));
	    			willPrintRecipe.setRecipe_name(mongos.get(i).getListname());
	    			willPrintRecipe.setRecipe_photo(mongos.get(i).getListphoto().get(0));
	    			willPrintRecipe.setRecipe_ingrds(mongos.get(i).getListingrd());
	    			
	    			if(mongoCNums != null) {
	    				if(mongoCNums.contains(willPrintRecipe.getRecipe_num())) {
	    					for(MongoCloneVO clone : mongoCRecipes) {
	    						
	    						if(clone.getRecipe_num() == willPrintRecipe.getRecipe_num()) {
	    							willPrintRecipe.setRecipe_hits(clone.getRecipe_hits());
	    							willPrintRecipe.setRecipe_likes(clone.getRecipe_likes());
	    						}
	    					}
	    				}
	    			}
	    			
	    			mongoRecipes.add(willPrintRecipe);
	    		}				
			}
			
			return mongoRecipes;
		}

		public PrintRecipeVO getOracleRecipe(int recipe_num){
			
    		RecipeVO recipe = recipedao.searchRecipeByNum(recipe_num);				
    		ArrayList<RecipeContentVO> recipeContents = recipedao.searchRecipeContentByNum(recipe_num);
    		ArrayList<RecipeIngrdVO> recipeIngrds = recipedao.searchRecipeIngrdByNum(recipe_num);
    		
    		PrintRecipeVO oracleRecipe = new PrintRecipeVO();
    		oracleRecipe.setRecipe_num(recipe.getRecipe_num());
    		oracleRecipe.setRecipe_name(recipe.getRecipe_title());
    		oracleRecipe.setRecipe_photo(recipeContents.get(0).getRecipe_image());
    			
    		ArrayList<String> ingrds = new ArrayList<String>();
   			for(RecipeIngrdVO ingrd : recipeIngrds) {
   				ingrds.add(ingrd.getIngrd_name());
    		}
   			oracleRecipe.setRecipe_likes(recipe.getRecipe_likes());
   			oracleRecipe.setRecipe_hits(recipe.getRecipe_hits());
   			oracleRecipe.setRecipe_ingrds(ingrds);

			return oracleRecipe;
		}
	

		public PrintRecipeVO getMongoRecipe(MongoRecipeVO mongo){
			PrintRecipeVO mongoRecipe = new PrintRecipeVO();
			
			mongoRecipe.setRecipe_num(Integer.parseInt(mongo.getListnum()));
			mongoRecipe.setRecipe_name(mongo.getListname());
			mongoRecipe.setRecipe_photo(mongo.getListphoto().get(0));
			mongoRecipe.setRecipe_ingrds(mongo.getListingrd());
			
			MongoCloneVO clone = null;
			clone = mongoCdao.getMongoRecipeByNum(Integer.parseInt(mongo.getListnum()));
			
			if(clone != null) {
				mongoRecipe.setRecipe_hits(clone.getRecipe_hits());
				mongoRecipe.setRecipe_likes(clone.getRecipe_likes());
			}
			return mongoRecipe;
		}
		
		
		
		public HashMap<String,Object> getOracleRecipeByRecipeNum(
				int recipe_num){
			
			HashMap<String,Object> oracleRecipe = new HashMap<String,Object>();
			RecipeVO recipe = recipedao.searchRecipeByNum(recipe_num);
			ArrayList<RecipeContentVO> recipeContents = recipedao.searchRecipeContentByNum(recipe_num);
			ArrayList<RecipeIngrdVO> recipeIngrds = recipedao.searchRecipeIngrdByNum(recipe_num);
			
			System.out.println(recipe);
			System.out.println(recipeContents);
			System.out.println(recipeIngrds);
			
			oracleRecipe.put("recipe",recipe);
			oracleRecipe.put("recipeContents",recipeContents);
			oracleRecipe.put("recipeIngrds",recipeIngrds);
			oracleRecipe.put("oldContentCount",recipeContents.size());
			oracleRecipe.put("oldIngrdCount",recipeIngrds.size());
			
			return oracleRecipe;
		}
		
		public HashMap<String,Object> getMongoRecipeByRecipeNum(int recipe_num) {
			HashMap<String,Object> recipe = new HashMap<>();
			
			MongoRecipeVO mongoRecipe = mongodao.getMongoRecipeByRecipeNum(recipe_num);
			
			recipe.put("recipe",mongoRecipe);
			
			MongoCloneVO clone = null;
			clone = mongoCdao.getMongoRecipeByNum(recipe_num);
			
			if(clone != null) {
				recipe.put("recipe_hits",clone.getRecipe_hits());
				recipe.put("recipe_likes",clone.getRecipe_likes());
			}
			
			return recipe;
		}

		
		public int updateRecipe(String recipe_title
				, ArrayList<Integer> ingrd_num
				, ArrayList<String> ingrd_name
				, ArrayList<String> ingrd_amount
				, ArrayList<Integer> content_num
				, ArrayList<MultipartFile> recipe_image
				, ArrayList<String> recipe_content
				, int recipe_num
				, int oldIngrdCount
				, int oldContentCount
				, String member_id) {
			
			System.out.println("recipe_title : " + recipe_title);
			System.out.println("ingrd_num : " + ingrd_num);
			System.out.println("ingrd_name : " + ingrd_name);
			System.out.println("ingrd_amount : " + ingrd_amount);
			System.out.println("content_num : " + content_num);
			System.out.println("recipe_image.size() : " + recipe_image.size());
			System.out.println("recipe_content : " + recipe_content);
			
			//recipe 테이블 수정
			int check = 0;
			if(recipe_num != 0 && recipe_num >= 10000 && recipe_title.length() != 0) {
				check = 0;
				RecipeVO willUpdateRecipe = new RecipeVO();
				willUpdateRecipe.setRecipe_num(recipe_num);
				willUpdateRecipe.setRecipe_title(recipe_title);
				
				check = recipedao.updateRecipe(willUpdateRecipe);
				
				if(check > 0) {
					System.out.println("RECIPE TABLE UPDATE 성공!");
				}
			}
			

			
			int cnt = 0;
			for(int i = 0; i < ingrd_num.size(); i++) {
				if(ingrd_name.get(i).length() == 0 || ingrd_amount.get(i).length() == 0 ) {
					ingrd_name.remove(i);
					ingrd_amount.remove(i);
					cnt++;
					continue;
				}
			}
			
			int ingrd_length = ingrd_num.size() - cnt;
			System.out.println("ingrd_length : " + ingrd_length);
			
			//1-1 기존의 ingrd의 갯수와 삽입된 ingrd의 갯수가 같을 때
			if(ingrd_length == oldIngrdCount) {
				
				for(int i=0; i<ingrd_length; i++) {
					check = 0;
					
					RecipeIngrdVO willUpdateIngrd = new RecipeIngrdVO();
					willUpdateIngrd.setRecipe_num(recipe_num);
					willUpdateIngrd.setIngrd_num(i);
					willUpdateIngrd.setIngrd_name(ingrd_name.get(i));
					willUpdateIngrd.setIngrd_amount(ingrd_amount.get(i));
					
					check = recipedao.updateRecipeIngrd(willUpdateIngrd);
					
					if(check > 0) {
						System.out.println("RECIPEINGRD TABLE UPDATE 성공!");
					}
				}
				
			} else if(ingrd_length > oldIngrdCount) {
				//기존의 ingrd의 갯수보다 삽입된 ingrd의 갯수가 더 많을때
				//기존번호는 update
				for(int i=0; i<oldIngrdCount; i++) {
					check = 0;
					RecipeIngrdVO willUpdateIngrd = new RecipeIngrdVO();
					willUpdateIngrd.setRecipe_num(recipe_num);
					willUpdateIngrd.setIngrd_num(i);
					willUpdateIngrd.setIngrd_name(ingrd_name.get(i));
					willUpdateIngrd.setIngrd_amount(ingrd_amount.get(i));
					
					check = recipedao.updateRecipeIngrd(willUpdateIngrd);
					
					if(check > 0) {
						System.out.println("RECIPEINGRD TABLE UPDATE 성공!");
					}
				}
				//그 이상은 insert
				for(int i=oldIngrdCount; i<ingrd_length; i++) {
					check = 0;
					RecipeIngrdVO willInsertIngrd = new RecipeIngrdVO();
					willInsertIngrd.setRecipe_num(recipe_num);
					willInsertIngrd.setMember_id(member_id);
					willInsertIngrd.setIngrd_num(i);
					willInsertIngrd.setIngrd_name(ingrd_name.get(i));
					willInsertIngrd.setIngrd_amount(ingrd_amount.get(i));
					
					check = recipedao.insertRecipeIngrd(willInsertIngrd);
					
					if(check > 0) {
						System.out.println("RECIPEINGRD TABLE INSERT 성공!");
					}
				}
			} else if(ingrd_length < oldIngrdCount) {
				//기존의 ingrd의 갯수보다 삽입된 ingrd의 갯수가 더 적을떄
				
				//삽입된 번호까진 update
				for(int i=0; i<ingrd_length; i++) {
					check = 0;
					RecipeIngrdVO willUpdateIngrd = new RecipeIngrdVO();
					willUpdateIngrd.setRecipe_num(recipe_num);
					willUpdateIngrd.setIngrd_num(i);
					willUpdateIngrd.setIngrd_name(ingrd_name.get(i));
					willUpdateIngrd.setIngrd_amount(ingrd_amount.get(i));
					
					check = recipedao.updateRecipeIngrd(willUpdateIngrd);
					
					if(check > 0) {
						System.out.println("RECIPEINGRD TABLE UPDATE 성공!");
					}
				}
				//나머지 delete
				for(int i=ingrd_length;i<oldIngrdCount;i++) {
					check = 0;
					RecipeIngrdVO ingrd = new RecipeIngrdVO();
					ingrd.setRecipe_num(recipe_num);
					ingrd.setIngrd_num(i);
					
					check = recipedao.deleteRecipeIngrd(ingrd);
					
					if(check > 0) {
						System.out.println("RECIPEINGRD TABLE DELETE 성공!");
					}
				}
			}

			//1-1 기존의 content의 갯수와 삽입된 content의 갯수가 같을 때
			if(content_num.size() == oldContentCount) {
				
				for(int i=0; i<content_num.size(); i++) {
					check = 0;
					RecipeContentVO willUpdateContent = new RecipeContentVO();
					willUpdateContent.setRecipe_num(recipe_num);
					willUpdateContent.setContent_num(content_num.get(i));
					willUpdateContent.setRecipe_content(recipe_content.get(i));
					
					if(!recipe_image.get(i).isEmpty()) {
						String savedFileName = null;
						savedFileName = FileService.saveFile(recipe_image.get(i), uploadPath);
						
						if(savedFileName != null) {
							willUpdateContent.setRecipe_image(savedFileName);
						}
					}
					
					check = recipedao.updateRecipeContent(willUpdateContent);
					
					if(check > 0) {
						System.out.println("RECIPECOTENT TABLE UPDATE 성공!");
					}
				}
			} else if(content_num.size() > oldContentCount) {
				//기존의 content의 갯수보다 삽입된 content의 갯수가 더 많을때
				
				//기존번호는 update
				for(int i=0; i<oldContentCount; i++) {
					check = 0;
					RecipeContentVO willUpdateContent = new RecipeContentVO();
					willUpdateContent.setRecipe_num(recipe_num);
					willUpdateContent.setContent_num(content_num.get(i));
					willUpdateContent.setRecipe_content(recipe_content.get(i));
					
					if(!recipe_image.get(i).isEmpty()) {
						String savedFileName = null;
						savedFileName = FileService.saveFile(recipe_image.get(i), uploadPath);
						
						if(savedFileName != null) {
							willUpdateContent.setRecipe_image(savedFileName);
						}
					}
					
					check = recipedao.updateRecipeContent(willUpdateContent);
					
					if(check > 0) {
						System.out.println("RECIPEINGRD TABLE UPDATE 성공!");
					}			
				}
				//그 이상은 insert
				for(int i=oldContentCount; i<content_num.size(); i++) {
					check = 0;
					RecipeContentVO willInsertContent = new RecipeContentVO();
					willInsertContent.setRecipe_num(recipe_num);
					willInsertContent.setMember_id(member_id);
					willInsertContent.setContent_num(i);
				//	if(recipe_content.get(i))
					willInsertContent.setRecipe_content(recipe_content.get(i));
					
					if(!recipe_image.get(i).isEmpty()) {
						String savedFileName = null;
						savedFileName = FileService.saveFile(recipe_image.get(i), uploadPath);
						
						if(savedFileName != null) {
							willInsertContent.setRecipe_image(savedFileName);
						}
					}
					
					check = recipedao.insertRecipeContent(willInsertContent);
					
					if(check > 0) {
						System.out.println("RECIPECONTENT TABLE INSERT 성공!");
					}
				}
			} else if(content_num.size() < oldContentCount) {
				//기존의 Content의 갯수보다 삽입된 Content의 갯수가 더 적을떄
				//삽입된 번호까진 update
				for(int i=0; i<content_num.size(); i++) {
					check = 0;
					RecipeContentVO willUpdateContent = new RecipeContentVO();
					willUpdateContent.setRecipe_num(recipe_num);
					System.out.println("recipe_num : " + recipe_num);
					willUpdateContent.setContent_num(content_num.get(i));
					System.out.println("content_num.get(i) : " + content_num.get(i));
					if(recipe_content.get(i).length() > 0) {
						willUpdateContent.setRecipe_content(recipe_content.get(i));
						System.out.println("recipe_content.get(i) : " + recipe_content.get(i));
					}
					
					if(!recipe_image.get(i).isEmpty()) {
						String savedFileName = null;
						savedFileName = FileService.saveFile(recipe_image.get(i), uploadPath);
						
						if(savedFileName != null) {
							willUpdateContent.setRecipe_image(savedFileName);
						}
					}
					
					check = recipedao.updateRecipeContent(willUpdateContent);
					
					if(check > 0) {
						System.out.println("RECIPECONTENT TABLE UPDATE 성공!");
					}			
				}
				//나머지 delete
				for(int i=content_num.size();i<oldContentCount;i++) {
					check = 0;
					RecipeContentVO content = new RecipeContentVO();
					content.setContent_num(i);
					content.setRecipe_num(recipe_num);
					
					//파일 삭제
					RecipeContentVO checkVO = recipedao.getRecipeContentByContentNum(content);
					
					String savedFileName = null;
					savedFileName = checkVO.getRecipe_image();
					if(savedFileName != null) {
						String fullPath = uploadPath + "\\" + savedFileName;
						boolean result = FileService.deleteFile(fullPath);
						if(result) {
							System.out.println("IMAGE FILE 삭제완료");
						}
					}
					
					check = recipedao.deleteRecipeContent(content);
					
					if(check > 0) {
						System.out.println("RECIPEINGRD TABLE DELETE 성공!");
					}
				}
			}
	
			return check;
		}
		
		public ArrayList<HashMap<String,Object>> getOracleRecipesByRecipeNums(
				ArrayList<Integer> recipeNums){
			
			ArrayList<HashMap<String,Object>> oracles = new ArrayList<>();
			
			for(int recipe_num : recipeNums) {
				HashMap<String,Object> oracleRecipe = new HashMap<String,Object>();
				RecipeVO recipe = recipedao.searchRecipeByNum(recipe_num);
				ArrayList<RecipeContentVO> recipeContents = recipedao.searchRecipeContentByNum(recipe_num);
				ArrayList<RecipeIngrdVO> recipeIngrds = recipedao.searchRecipeIngrdByNum(recipe_num);
				
				System.out.println(recipe);
				System.out.println(recipeContents);
				System.out.println(recipeIngrds);
				
				oracleRecipe.put("recipe",recipe);
				oracleRecipe.put("recipeContents",recipeContents);
				oracleRecipe.put("recipeIngrds",recipeIngrds);
				oracleRecipe.put("oldContentCount",recipeContents.size());
				oracleRecipe.put("oldIngrdCount",recipeIngrds.size());

				oracles.add(oracleRecipe);
			}
			
			return oracles;
		}
				
		public int updateViewCount(int recipe_num) {
			int cnt = 0;
			if(recipe_num >= 10000) {
				cnt = recipedao.plusRecipeHits(recipe_num);
				
				return cnt;
			}else {
				MongoCloneVO clone = null;
				clone = mongoCdao.getMongoRecipeByNum(recipe_num);
				
				if(clone != null) {
					cnt = mongoCdao.plusRecipeHits(recipe_num);
					
					return cnt;
				}else {
					int insertCnt = 0;
					insertCnt = mongoCdao.insertMongoCloneRecipe(recipe_num);
					
					if(insertCnt > 0) {
						cnt = mongoCdao.plusRecipeHits(recipe_num);
						return cnt;
					}
				}
				
			}
			
			return 0;
		}
		
}