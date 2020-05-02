package com.memoko.integrated.review.vo;

import lombok.Data;

@Data
public class ReplyVO {
	private int reply_num;
    private int review_num;
    private String member_id;
    private String reply_content;
    private String reply_indate;
}
