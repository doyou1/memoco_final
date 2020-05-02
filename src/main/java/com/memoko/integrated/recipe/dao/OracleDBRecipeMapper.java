package com.memoko.integrated.recipe.dao;


import java.util.ArrayList;
import java.util.HashMap;

import com.memoko.integrated.member.vo.RecipeLikesVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.recipe.vo.RecipeContentVO;
import com.memoko.integrated.recipe.vo.RecipeIngrdVO;
import com.memoko.integrated.recipe.vo.RecipeVO;

public interface OracleDBRecipeMapper {

	public int insertRecipe(RecipeVO recipe);
	public int insertRecipeContent(RecipeContentVO contentVO);
	public int insertRecipeIngrd(RecipeIngrdVO ingrdVO);

	//요리이름 -> 검색어 -> 해당문자열이 있는 요리의 번호 리스트
	public ArrayList<Integer> searchRecipeNumByTitle(String searchText);
	//위에서 받은 번호-> 해당 레시피의 정보
	public RecipeVO searchRecipeByNum(int searchNum);
	//searchrecipetitle에서 받은 번호 -> 해당레시피의 사진(0번,썸네일사진이필요해서)
	public ArrayList<RecipeContentVO> searchRecipeContentByNum(int searchNum);
	public ArrayList<Integer> searchRecipeNumByIngrd(String searchText);
	public ArrayList<RecipeIngrdVO> searchRecipeIngrdByNum(int searchNum);
    public ArrayList<Integer> searchRecipeNumByUserId(String userId);
	public int updateRecipeIngrd(RecipeIngrdVO recipeIngrd);
	public int deleteRecipeIngrdByNum(int recipe_num);
	public int addRecipeLike(RecipeLikesVO like);
	public int removeRecipeLike(RecipeLikesVO like);
  	public ArrayList<Integer> getLikeRecipesById(String member_id);
	public int updateRecipe(RecipeVO recipe);
	public int deleteRecipeIngrd(RecipeIngrdVO ingrd);
	public int updateRecipeContent(RecipeContentVO content);
	public RecipeContentVO getRecipeContentByContentNum(RecipeContentVO content);
	public int deleteRecipeContent(RecipeContentVO content);
	public int addRecipeLikes(int recipe_num);
	public int minusRecipeLikes(int recipe_num);
	public ArrayList<Integer> getRecipeNums();
	public ArrayList<Integer> searchRecipeNumByNickname(String searchText);
	public ArrayList<PrintRecipeVO> getRecipesByRecipeNums(HashMap<String,Object> map);
	public int plusRecipeHits(int recipe_num);
}
