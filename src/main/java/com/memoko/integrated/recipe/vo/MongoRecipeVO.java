package com.memoko.integrated.recipe.vo;

import java.util.ArrayList;


import lombok.Data;

@Data
public class MongoRecipeVO 
{
	private String listname;
	private ArrayList<String> listingrd;
	private ArrayList<String> listamount;
	private ArrayList<String> listcontent;
	private ArrayList<String> listphoto;
	private String listurl;
	private String listnum;
}
