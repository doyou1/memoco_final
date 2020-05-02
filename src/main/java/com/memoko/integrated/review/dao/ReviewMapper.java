package com.memoko.integrated.review.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.review.vo.PrintReviewVO;
import com.memoko.integrated.review.vo.ReplyVO;
import com.memoko.integrated.review.vo.ReviewVO;

public interface ReviewMapper {
	
	public int insertReview(ReviewVO review);
	public ArrayList<PrintReviewVO> selectAllReviewList();
	public ArrayList<ReplyVO> selectReplysByReviewNum(int review_num);
	public int addReviewLikes(int review_num);
	public int minusReviewLikes(int review_num);
	public int insertReply(ReplyVO reply);
	public ReplyVO selectReplyByReplyNum(int reply_num);
	public ArrayList<PrintReviewVO> searchReviewsById(String userId);
	public ArrayList<PrintReviewVO> getReviewsByReviewTitle(String searchText);
	public ArrayList<PrintReviewVO> getReviewsByReviewContent(String searchText);
	public ArrayList<PrintReviewVO> getReviewsByRecipeNums(HashMap<String,Object> map);
	public int updateReply(ReplyVO reply);
	public int deleteReply(ReplyVO reply);
	public int deleteReview(ReviewVO review);
	public PrintReviewVO getReviewByReviewNum(int review_num);
	public int updateReview(ReviewVO review);
	public ArrayList<PrintReviewVO> getReviewsByCount(int count, RowBounds rb);
	public int getReviewsCount();
	public int getReviewsCountByReviewNum(int review_num);
	public ArrayList<PrintRecipeVO> getReferencedRecipes();
	public ArrayList<Integer> getReferencedRecipeNums();
	public ArrayList<PrintReviewVO> getReviewsByUserId(String searchText);
	
}
