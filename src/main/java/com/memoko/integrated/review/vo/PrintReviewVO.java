package com.memoko.integrated.review.vo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PrintReviewVO {

	//리뷰 VO
	private int review_num;
	private int recipe_num;
	private String member_id;
	private String review_title;
	private String review_content;
	private String review_image;
	private String review_indate;
	private int review_likes;
	private String recipe_name;
	private String recipe_photo;
	private ArrayList<ReplyVO> reply;
	
}
