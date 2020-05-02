package com.memoko.integrated.member.dao;

import java.util.ArrayList;

import com.memoko.integrated.member.vo.MemberVO;
import com.memoko.integrated.member.vo.RecipeLikesVO;
import com.memoko.integrated.member.vo.ReviewLikesVO;

public interface MemberMapper {
	  public int insertMember(MemberVO member);
	   public MemberVO memberSelectOne(String idCheck);
	   public int memberUpdate(MemberVO member);
		public int addRecipeLike(RecipeLikesVO like);
		public int removeRecipeLike(RecipeLikesVO like);

		public int addReviewLike(ReviewLikesVO like);
		public int removeReviewLike(ReviewLikesVO like);
	  	public ArrayList<Integer> getLikeRecipesById(String member_id);
	  	public ArrayList<Integer> getLikeReviewsById(String member_id);
		public ArrayList<String> getIdsByNickname(String searchText);
}
