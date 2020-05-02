package com.memoko.integrated.recipe.vo;

import lombok.Data;

@Data
public class RecipeVO {

	private int recipe_num;
	private String member_id;
	private String recipe_title;
	private String recipe_indate;
	private int recipe_hits;
	private int recipe_likes;
}
