package com.memoko.integrated.recipe.vo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PrintRecipeVO 
{
	private int recipe_num;
	private String recipe_name;
	private String recipe_photo;
	private ArrayList<String> recipe_ingrds;
	private int recipe_hits;
	private int recipe_likes;
}
