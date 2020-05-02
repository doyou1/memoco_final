package com.memoko.integrated.recipe.vo;

import lombok.Data;

@Data
public class RecipeIngrdVO {
	private int recipe_num;
	private String member_id;
	private int	ingrd_num;
	private String ingrd_name;
	private String ingrd_amount;
}
