package com.memoko.integrated.member.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memoko.integrated.member.dao.MemberDAO;
import com.memoko.integrated.member.service.MemberService;
import com.memoko.integrated.member.vo.MemberVO;
import com.memoko.integrated.member.vo.RecipeLikesVO;
import com.memoko.integrated.member.vo.ReviewLikesVO;
import com.memoko.integrated.recipe.vo.MongoCloneVO;
import com.memoko.integrated.recipe.vo.PrintRecipeVO;
import com.memoko.integrated.review.vo.PrintReviewVO;
import com.memoko.integrated.socialapi.service.KakaoLogin;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	  @Autowired
	   private MemberDAO memberdao;
	  
	@Autowired
	  private MemberService memberservice;
	  
	   @RequestMapping(value="/join", method=RequestMethod.POST)
	   public String join(MemberVO member, HttpSession session) {
	      System.out.println(member);
		   
		   int cnt = memberdao.insertMember(member);
	      if(cnt > 0) {
	         System.out.println("가입 성공");
	      }
	      else {
	         System.out.println("가입 실패");
	      }
	      
	      //회원가입후 바로 로그인
	      session.setAttribute("userId", member.getMember_id());
	      session.setAttribute("nickName", member.getMember_nickname());
	      session.setAttribute("login_Type", "default");
	      return "redirect:/";
	   }
	   
	   @RequestMapping(value="/logout",method=RequestMethod.GET)
	   public String logout(HttpSession session
			   ,@RequestParam(value="currentPath", defaultValue = "/") String currentPath
			   ) {
		   
		   System.out.println("currentPath : " + currentPath);
	      String access_Token = (String) session.getAttribute("kakao_Access_Token");
	       if(access_Token != null) {
	          KakaoLogin.kakaoLogout(access_Token);          
	       }

	       	session.removeAttribute("userId");
	        session.removeAttribute("access_Token");
	        session.removeAttribute("login_type");
	        session.removeAttribute("nickName");
	        
	        if(currentPath.equals("myPage")) {
	 	       return "redirect:/";
	        }
	       return "redirect:"+currentPath;
	   }
	   
	   @RequestMapping(value="/login",method=RequestMethod.POST)
	   @ResponseBody
	   public int login(String login_id, String login_pw,HttpSession session) {
	      System.out.println(login_id);
	      System.out.println(login_pw);
		  MemberVO member = memberdao.memberSelectOne(login_id);
		  int result = 0;
		  
		  if(member != null) {
			  if(member.getMember_pw().equalsIgnoreCase(login_pw)) {
				  result = 1;
				  System.out.println("로그인 성공");
				  session.setAttribute("userId", member.getMember_id());
				  session.setAttribute("nickName", member.getMember_nickname());
			      session.setAttribute("login_Type", "default");
			  }else {//비밀번호 틀림
				  result = 3;
				  System.out.println("비밀번호 틀림");
			  }
		  } else {//아이디 틀림
			  System.out.println("아이디 틀림");
			  result = 2;
		  }

	      //return 1 : 로그인 성공, 2 : 아이디 틀림, 3 : 비밀번호 틀림
	      return result;
	   }

	   
	   @RequestMapping(value="/myPage", method=RequestMethod.GET)
	   public String updateMember(HttpSession session, Model model) {
	      String userId = (String)session.getAttribute("userId");
	      
	      System.out.println(userId);
	      MemberVO member = memberdao.memberSelectOne(userId);
	      
	      ArrayList<PrintRecipeVO> willPrintRecipes = memberservice.getMemberRecipes(userId);
	      ArrayList<PrintReviewVO> willPrintReviews = memberservice.getMemberReviews(userId);
	      
	      HashMap<String,Object> likeRecipes = memberservice.getMemberLikeRecipes(userId);
	      
	      HashMap<String,Object> likeReviews = memberservice.getMemberLikeReviews(userId);
	      
	      model.addAttribute("likeReviews", likeReviews);	
	      model.addAttribute("likeRecipes", likeRecipes);	
	      model.addAttribute("member", member);
	      model.addAttribute("recipes", willPrintRecipes);	
	      model.addAttribute("reviews", willPrintReviews);	
	      
	      return "/member/myPage";
	   }

	   
	   @RequestMapping(value="/idCheck", method=RequestMethod.POST)
	   @ResponseBody
	   public int idCheck(String member_id, Model model, HttpSession session) {
	      System.out.println("ID 중복확인 시작");
	      MemberVO member = memberdao.memberSelectOne(member_id);

	      //id중복확인
	      //select 후 아이디가 있으면 
	      int check = 0;
	      if(member != null) {
	         check = 1;
	      }

	      return check;
	      
	   }
	
	   @RequestMapping(value="update", method=RequestMethod.POST)
	      public String update(MemberVO member,String update_pw, HttpSession session) {
	         System.out.println("회원정보 수정");
	         String member_id = (String)session.getAttribute("userId");
	         System.out.println(member_id);
	         MemberVO oldVO = memberdao.memberSelectOne(member_id);
	         
	         if(oldVO.getMember_pw().equalsIgnoreCase(member.getMember_pw())) {
	            
	        	//현재 비밀번호에 잘 입력했으면
	            //update할 비밀번호가 아니기에 비워줌
	            member.setMember_pw("");
	            member.setMember_id(member_id);
	            if(update_pw != null && update_pw != "") {
	            //변경할 비밀번호가 입력됐으면   
	               member.setMember_pw(update_pw);               
	            }
	            
	            String update = member.getMember_pw();
	            System.out.println(update);
	           // System.out.println(update_pw);
	            System.out.println(member);
	            int cnt = memberdao.memberUpdate(member);
	            
	            if(cnt > 0) {
	               System.out.println("수정설공");
	            }
	            else {
	               System.out.println("수정 실패");
	            }
	            
	            if(update_pw != null) {
	            	return "redirect:/member/logout?currentPath=myPage";
	            }
	         }
	         return "redirect:/member/myPage";
	      }
	   
	   @RequestMapping(value="/recipeLike",method=RequestMethod.POST)
	   @ResponseBody
	   public int recipeLike(
			   @RequestParam(value="recipe_num",defaultValue = "0") int recipe_num
			   ,@RequestParam(value="check",defaultValue = "false") boolean check
			   ,HttpSession session){
		   
		   //Session에서 userId 받아오기
		   String member_id = (String) session.getAttribute("userId");
		   
		   //RecipeLikes VO 생성후 
		   RecipeLikesVO like = new RecipeLikesVO();
		   like.setMember_id(member_id);
		   like.setRecipe_num(recipe_num);
		   int cnt = 0;

		   cnt = memberservice.recipeLike(like,check);
		   
		   //cnt가 0이 아니면 성공
		   return cnt;
	   }

	   @RequestMapping(value="/reviewLike",method=RequestMethod.POST)
	   @ResponseBody
	   public int reviewLike(
			   @RequestParam(value="review_num",defaultValue = "0") int review_num
			   ,@RequestParam(value="check",defaultValue = "false") boolean check
			   ,HttpSession session) {
		   
		   System.out.println("review_num : " + review_num);
		   System.out.println("check : " + check);
		   
		   //Session에서 userId 받아오기
		   String member_id = (String) session.getAttribute("userId");
		   int cnt = 0;
		   if(member_id != null) {
			   ReviewLikesVO like = new ReviewLikesVO();
			   like.setReview_num(review_num);
			   like.setMember_id(member_id);
			   
			   cnt = memberservice.reviewLike(like,check);
		   }
		   
		   if(check && cnt > 0) {
			   return 1;
		   }else if(!check && cnt > 0) {
			   return 2;
		   }
		   
		   return 0;
	   }
}
