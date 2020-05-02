package com.memoko.integrated.review.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.review.vo.PrintReviewVO;
import com.memoko.integrated.review.vo.ReplyVO;
import com.memoko.integrated.review.vo.ReviewVO;

@Repository
public class ReviewDAO {

	
	@Autowired
	private SqlSession session;
	
	
	public int insertReview(ReviewVO review) {
		int check = 0;
		
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			check = mapper.insertReview(review);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	public ArrayList<PrintReviewVO> searchReviewsById(String userId){
		ArrayList<PrintReviewVO> willPrintReviews = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			willPrintReviews = mapper.searchReviewsById(userId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return willPrintReviews;
	}
	
	public ArrayList<PrintReviewVO> selectAllReviewList(){
		ArrayList<PrintReviewVO> list = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			list = mapper.selectAllReviewList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public ArrayList<ReplyVO> selectReplysByReviewNum(int review_num){
		ArrayList<ReplyVO> replys = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			replys = mapper.selectReplysByReviewNum(review_num);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return replys;
	}
	
	public int addReviewLikes(int review_num) {
		int check = 0;
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			check = mapper.addReviewLikes(review_num);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	public int minusReviewLikes(int review_num) {
		int check = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			check = mapper.minusReviewLikes(review_num);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	public int insertReply(ReplyVO reply) {
		int cnt = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			cnt = mapper.insertReply(reply);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	
	public ReplyVO selectReplyByReplyNum(int reply_num) {
		ReplyVO returnVO = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			returnVO = mapper.selectReplyByReplyNum(reply_num);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return returnVO;
	}

	public ArrayList<PrintReviewVO> getReviewsByReviewTitle(String searchText) {
		ArrayList<PrintReviewVO> reviews = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			reviews = mapper.getReviewsByReviewTitle(searchText);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return reviews;
	}

	public ArrayList<PrintReviewVO> getReviewsByReviewContent(String searchText) {
		ArrayList<PrintReviewVO> reviews = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			reviews = mapper.getReviewsByReviewContent(searchText);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return reviews;
	}

	public ArrayList<PrintReviewVO> getReviewsByRecipeNums(HashMap<String,Object> map) {
		ArrayList<PrintReviewVO> reviews = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			reviews = mapper.getReviewsByRecipeNums(map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return reviews;
	}

	public int updateReply(ReplyVO reply) {
		int check = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			check = mapper.updateReply(reply);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}

	public int deleteReply(ReplyVO reply) {
		int check = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			check = mapper.deleteReply(reply);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}

	public int deleteReview(ReviewVO review) {
		int check = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			check = mapper.deleteReview(review);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
		
	public PrintReviewVO getReviewByReviewNum(int review_num) {
		PrintReviewVO review = null;
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			review = mapper.getReviewByReviewNum(review_num);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return review;
	}
	
	public int updateReview(ReviewVO review) {
		int check = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			check = mapper.updateReview(review);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	
	public ArrayList<PrintReviewVO> getReviewsByCount(int count){
		ArrayList<PrintReviewVO> reviews = null;
		
		RowBounds rb = new RowBounds(0,count);
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			reviews = mapper.getReviewsByCount(count, rb);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return reviews;
	}
	
	public int getReviewsCount() {
		int count = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			count = mapper.getReviewsCount();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
		
	public int getReviewsCountByReviewNum(int review_num) {
		int prevCount = 0;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			prevCount = mapper.getReviewsCountByReviewNum(review_num);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return prevCount;
	}
	public ArrayList<PrintRecipeVO> getReferencedRecipes(){
		ArrayList<PrintRecipeVO> recipes = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			recipes = mapper.getReferencedRecipes();
		}catch (Exception e) {
			return null;
		}
		
		
		return recipes;
	}

	public ArrayList<Integer> getReferencedRecipeNums() {
		ArrayList<Integer> recipeNums = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			recipeNums = mapper.getReferencedRecipeNums();
		}catch (Exception e) {
			return null;
		}
		
		return recipeNums;
	}
	
	public ArrayList<PrintReviewVO> getReviewsByUserId(String searchText){
		ArrayList<PrintReviewVO> reviews = null;
		
		try {
			ReviewMapper mapper = session.getMapper(ReviewMapper.class);
			reviews = mapper.getReviewsByUserId(searchText);
		}catch (Exception e) {
			return null;
		}
		
		return reviews;
	}
	
	
}
