package com.memoko.integrated.recipe.dao;

import java.util.ArrayList;

import com.memoko.integrated.recipe.vo.MongoCloneVO;

public interface MongoDBCloneMapper {

	public int insertMongoCloneRecipe(int recipe_num);
	public int addRecipeLikes(int recipe_num);
	public int minusRecipeLikes(int recipe_num);
	public MongoCloneVO getMongoRecipeByNum(int recipe_num);
	public ArrayList<MongoCloneVO> getMongoRecipeList();
	public ArrayList<Integer> getRecipeNums();
	public int plusRecipeHits(int recipe_num);
}
