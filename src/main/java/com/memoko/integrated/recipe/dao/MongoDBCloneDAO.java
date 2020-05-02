package com.memoko.integrated.recipe.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memoko.integrated.recipe.vo.MongoCloneVO;

@Repository
public class MongoDBCloneDAO {

	@Autowired
	private SqlSession session;
	
	public int insertMongoCloneRecipe(int recipe_num) {
		int check = 0;
		
		try {
			MongoDBCloneMapper mapper = session.getMapper(MongoDBCloneMapper.class);
			check = mapper.insertMongoCloneRecipe(recipe_num);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	
	
	public int addRecipeLikes(int recipe_num) {
		int check = 0;
	
		try {	
			MongoDBCloneMapper mapper = session.getMapper(MongoDBCloneMapper.class);
			check = mapper.addRecipeLikes(recipe_num);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}

	public int minusRecipeLikes(int recipe_num) {
		int check = 0;
		
		try {
			MongoDBCloneMapper mapper = session.getMapper(MongoDBCloneMapper.class);
			check = mapper.minusRecipeLikes(recipe_num);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
		   
	public MongoCloneVO getMongoRecipeByNum(int recipe_num) {
		MongoCloneVO mongo = null;
		System.out.println("recipe_num : " + recipe_num);
		try {
			MongoDBCloneMapper mapper = session.getMapper(MongoDBCloneMapper.class);
			mongo = mapper.getMongoRecipeByNum(recipe_num);
		System.out.println("mongo : " + mongo);
		} catch(Exception e) {
			return null;
		}
		
		return mongo;
	}

	
	public ArrayList<MongoCloneVO> getMongoRecipeList(){
		ArrayList<MongoCloneVO> mongo = null;
		
		try {
			MongoDBCloneMapper mapper = session.getMapper(MongoDBCloneMapper.class);
			mongo = mapper.getMongoRecipeList();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return mongo;
	}

	public ArrayList<Integer> getRecipeNums(){
		ArrayList<Integer> recipeNums = null;
		
		try {
			MongoDBCloneMapper mapper = session.getMapper(MongoDBCloneMapper.class);
			recipeNums = mapper.getRecipeNums();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return recipeNums;
	}
	
	public int plusRecipeHits(int recipe_num) {
		int cnt = 0;
		
		try {
			MongoDBCloneMapper mapper = session.getMapper(MongoDBCloneMapper.class);
			cnt = mapper.plusRecipeHits(recipe_num);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
}
