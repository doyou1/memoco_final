package com.memoko.integrated.member.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memoko.integrated.member.vo.MemberVO;
import com.memoko.integrated.member.vo.RecipeLikesVO;
import com.memoko.integrated.member.vo.ReviewLikesVO;

@Repository
public class MemberDAO {
	@Autowired
	   private SqlSession session;
	   
	   public int insertMember(MemberVO member) {
	      int cnt = 0;
	      try {
	         MemberMapper mapper = session.getMapper(MemberMapper.class);
	         cnt = mapper.insertMember(member);
	      }
	      catch(Exception e) {
	         e.printStackTrace();
	         
	      }
	      return cnt;
	   }

	   public MemberVO memberSelectOne(String idCheck) {
		   MemberVO member = null;
	      try {
	         MemberMapper mapper = session.getMapper(MemberMapper.class);
	         member = mapper.memberSelectOne(idCheck);
	      }
	      catch(Exception e) {
	         e.printStackTrace();
	      }
	      return member;
	   }
	   public int memberUpdate(MemberVO member) {
	         int cnt = 0;
	         try {
	            MemberMapper mapper = session.getMapper(MemberMapper.class);
	            cnt = mapper.memberUpdate(member);
	         }
	         catch(Exception e){
	            e.printStackTrace();
	         }
	         return cnt;
	      }
	   
		public int addRecipeLike(RecipeLikesVO like) {
			int check = 0;
			
			try {
				MemberMapper mapper = session.getMapper(MemberMapper.class);
				check = mapper.addRecipeLike(like);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return check;
			
		}

		public int removeRecipeLike(RecipeLikesVO like) {
			int check = 0;
			
			try {
				MemberMapper mapper = session.getMapper(MemberMapper.class);
				check = mapper.removeRecipeLike(like);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return check;
			
		}
		
		public int addReviewLike(ReviewLikesVO like) {
			int check = 0;
			
			try {
				MemberMapper mapper = session.getMapper(MemberMapper.class);
				check = mapper.addReviewLike(like);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return check;
			
		}
		
		public int removeReviewLike(ReviewLikesVO like) {
			int check = 0;
			
			try {
				MemberMapper mapper = session.getMapper(MemberMapper.class);
				check = mapper.removeReviewLike(like);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return check;
			
		}
		

	  	
	  	public ArrayList<Integer> getLikeRecipesById(String member_id){
	  		ArrayList<Integer> likeRecipeNums = null;

	  		try {
				MemberMapper mapper = session.getMapper(MemberMapper.class);
				likeRecipeNums = mapper.getLikeRecipesById(member_id);
			} catch(Exception e) {
				e.printStackTrace();
			}

	  		
	  		return likeRecipeNums;
	  	}

	  	public ArrayList<Integer> getLikeReviewsById(String member_id){
	  		ArrayList<Integer> likeReplyNums = null;
	  		
	  		try {
	  			MemberMapper mapper = session.getMapper(MemberMapper.class);
	  			likeReplyNums = mapper.getLikeReviewsById(member_id);
	  		} catch(Exception e) {
	  			e.printStackTrace();
	  		}
	  		
	  		
	  		return likeReplyNums;
	  	}
	  			
		public ArrayList<String> getIdsByNickname(String searchText){
			ArrayList<String> ids = null;
			
			try {
	  			MemberMapper mapper = session.getMapper(MemberMapper.class);
	  			ids = mapper.getIdsByNickname(searchText);
	  		} catch(Exception e) {
	  			e.printStackTrace();
	  		}
	  		
			return ids;
		}


}
