package com.memoko.integrated.review.vo;

import lombok.Data;

@Data
public class ReviewVO {
	private int review_num;
	private int recipe_num;
    private String member_id;
    private String member_ninckname;
    private String review_title;
    private String review_content;
    private String review_image;
    
    private String review_indate;
    private int review_likes;
}
