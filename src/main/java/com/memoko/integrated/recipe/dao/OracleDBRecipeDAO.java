package com.memoko.integrated.recipe.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.recipe.vo.RecipeContentVO;
import com.memoko.integrated.recipe.vo.RecipeIngrdVO;
import com.memoko.integrated.recipe.vo.RecipeVO;

@Repository
public class OracleDBRecipeDAO {

	@Autowired
	private SqlSession session;
	
	public int insertRecipe(RecipeVO recipe) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.insertRecipe(recipe);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return check;
	}
	
	
	public int insertRecipeContent(RecipeContentVO contentVO) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.insertRecipeContent(contentVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	public int insertRecipeIngrd(RecipeIngrdVO ingrdVO) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.insertRecipeIngrd(ingrdVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	public ArrayList<Integer> searchRecipeNumByTitle(String searchText)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		try
		{
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			list = mapper.searchRecipeNumByTitle(searchText);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public RecipeVO searchRecipeByNum(int searchNum)
	{
		RecipeVO recipe = new RecipeVO();
		
		try
		{
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			recipe = mapper.searchRecipeByNum(searchNum);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return recipe;
	}
	
	public ArrayList<RecipeContentVO> searchRecipeContentByNum(int searchNum)
	{
		ArrayList<RecipeContentVO> content = new ArrayList<RecipeContentVO>();
		
		try
		{
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			content = mapper.searchRecipeContentByNum(searchNum);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return content;
		
	}
	

	public ArrayList<Integer> searchRecipeNumByIngrd(String searchText)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		try
		{
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			list = mapper.searchRecipeNumByIngrd(searchText);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	
	public ArrayList<RecipeIngrdVO> searchRecipeIngrdByNum(int searchNum){
		ArrayList<RecipeIngrdVO> recipeIngrds = null;
		
		try
		{
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			recipeIngrds = mapper.searchRecipeIngrdByNum(searchNum);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return recipeIngrds;
	}
	
    
    public ArrayList<Integer> searchRecipeNumByUserId(String userId){
    	ArrayList<Integer> recipeNums = null;
    	
    	try {
    		OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
    		recipeNums = mapper.searchRecipeNumByUserId(userId);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return recipeNums;
    }

	
	public int updateRecipeIngrd(RecipeIngrdVO recipeIngrd) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.updateRecipeIngrd(recipeIngrd);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	
	public int deleteRecipeIngrdByNum(int recipe_num) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.deleteRecipeIngrdByNum(recipe_num);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public int updateRecipe(RecipeVO recipe) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.updateRecipe(recipe);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public int deleteRecipeIngrd(RecipeIngrdVO ingrd) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.deleteRecipeIngrd(ingrd);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public int updateRecipeContent(RecipeContentVO content) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.updateRecipeContent(content);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
		
	public RecipeContentVO getRecipeContentByContentNum(RecipeContentVO content) {
		RecipeContentVO checkVO= null;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			checkVO = mapper.getRecipeContentByContentNum(content);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return checkVO;			
	}
	
	public int deleteRecipeContent(RecipeContentVO content) {
		int check = 0;
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.deleteRecipeContent(content);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
		   
	public int addRecipeLikes(int recipe_num) {
		int check = 0;
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.addRecipeLikes(recipe_num);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	public int minusRecipeLikes(int recipe_num) {
		int check = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			check = mapper.minusRecipeLikes(recipe_num);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	public ArrayList<Integer> getRecipeNums(){
		ArrayList<Integer> recipeNums = null;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			recipeNums = mapper.getRecipeNums();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return recipeNums;
	}
	

	public ArrayList<Integer> searchRecipeNumByNickname(String searchText){
		ArrayList<Integer> recipeNums = null;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			recipeNums = mapper.searchRecipeNumByNickname(searchText);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return recipeNums;
	}
	
	public ArrayList<PrintRecipeVO> getRecipesByRecipeNums(HashMap<String,Object> map){
		ArrayList<PrintRecipeVO> recipes = null;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			recipes = mapper.getRecipesByRecipeNums(map);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return recipes;
	}
	
	public int plusRecipeHits(int recipe_num) {
		int cnt = 0;
		
		try {
			OracleDBRecipeMapper mapper = session.getMapper(OracleDBRecipeMapper.class);
			cnt = mapper.plusRecipeHits(recipe_num);
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return cnt;
	}
	
}
