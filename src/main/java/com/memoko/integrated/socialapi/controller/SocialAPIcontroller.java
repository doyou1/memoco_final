package com.memoko.integrated.socialapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.memoko.integrated.member.dao.MemberDAO;
import com.memoko.integrated.member.service.CreateRandomPw;
import com.memoko.integrated.member.vo.MemberVO;
import com.memoko.integrated.socialapi.service.KakaoLogin;
import com.memoko.integrated.socialapi.service.NaverLogin;
import com.memoko.integrated.socialapi.vo.AuthInfo;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="/socialapi")
public class SocialAPIcontroller {
	
//	private static final Logger logger = LoggerFactory.getLogger(SocialAPIcontroller.class);
	
	@Autowired
	private MemberDAO dao;

    @Inject
    private AuthInfo authInfo;
    
//    @Autowired
//    private GoogleOAuth2Template googleOAuth2Template;
    
    @Autowired
    private OAuth2Parameters googleOAuth2Parameters;
  
    //Google Login API
    @RequestMapping(value = "/googleLogin",method=RequestMethod.GET)
    public String doSessionAssignActionPage(HttpServletRequest request, Model model,HttpSession session) throws Exception {

        String code = request.getParameter("code");
        System.out.println("code : " + code);
        
      //RestTemplate을 사용하여 Access Token 및 profile을 요청한다.
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        System.out.println(code);
        parameters.add("client_id", authInfo.getClientId());
        parameters.add("client_secret", authInfo.getClientSecret());
        parameters.add("redirect_uri", googleOAuth2Parameters.getRedirectUri());
        parameters.add("grant_type", "authorization_code");
        System.out.println("googleOAuth2Parameters.getScope() : " + googleOAuth2Parameters.getScope());
        System.out.println("PARAMETER : "  + parameters);
      
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntity, Map.class);
        Map<String, Object> responseMap = responseEntity.getBody();
        
        // id_token 라는 키에 사용자가 정보가 존재한다.
        // 받아온 결과는 JWT (Json Web Token) 형식으로 받아온다. 콤마 단위로 끊어서 첫 번째는 현 토큰에 대한 메타 정보, 두 번째는 우리가 필요한 내용이 존재한다.
        // 세번째 부분에는 위변조를 방지하기 위한 특정 알고리즘으로 암호화되어 사이닝에 사용한다.
        //Base 64로 인코딩 되어 있으므로 디코딩한다.
 
        String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
        Base64 base64 = new Base64(true);
        String body = new String(base64.decode(tokens[1]));
        
        System.out.println("tokens.length : " + tokens.length);
        System.out.println("tokens[0] : " + new String(Base64.decodeBase64(tokens[0]), "utf-8"));
        System.out.println("tokens[1] : " + new String(Base64.decodeBase64(tokens[1]), "utf-8"));
 
        //Jackson을 사용한 JSON을 자바 Map 형식으로 변환
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = mapper.readValue(body, Map.class);
        
        
        
        System.out.println("result.get('email')" + result.get("email"));
        System.out.println("result.get('name')" + result.get("name"));
        
        String email = result.get("email");
        String member_id = email + "gg#";
        String member_pw = CreateRandomPw.getRandomPw();
        String member_nickname = result.get("name");

        
        MemberVO google = new MemberVO();
        google.setMember_id(member_id);
        google.setMember_pw(member_pw);
        google.setMember_nickname(member_nickname);
        google.setMember_email(email);
        System.out.println("google : " + google);
        
        
        MemberVO check = dao.memberSelectOne(google.getMember_id());
        
        if(check != null) {
        	//기존 계정 있음
        	System.out.println("기존계정있음");
        	session.setAttribute("userId", google.getMember_id());
            session.setAttribute("nickName", google.getMember_nickname());        	
        } else {
        	System.out.println("기존계정없음");
        	//기존 계정 없음, 회원가입
        	int cnt = dao.insertMember(google);
        	if(cnt > 0) {
        		//회원 가입 성공
        		session.setAttribute("userId", google.getMember_id());
                session.setAttribute("nickName", google.getMember_nickname());
        	}
        }
        
        
        session.setAttribute("login_Type", "google");
        
        return "redirect:/";
    }
    
    //Kakao Login API
    @RequestMapping(value = "/kakaoLogin",method=RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value="code") String code,HttpSession session) throws Exception {
    	String access_Token = KakaoLogin.getAccessToken(code);
    	HashMap<String, Object> userinfo = KakaoLogin.getUserInfo(access_Token);
    	  String email = (String) userinfo.get("email");
          String member_id = email + "kk#";	//카카오 특징
          String member_pw = CreateRandomPw.getRandomPw();
          String member_nickname = (String) userinfo.get("nickname");

          
          MemberVO kakao = new MemberVO();
          kakao.setMember_id(member_id);
          kakao.setMember_pw(member_pw);
          kakao.setMember_nickname(member_nickname);
          kakao.setMember_email(email);
          
          MemberVO check = dao.memberSelectOne(kakao.getMember_id());
          
          if(check != null) {
          	//기존 계정 있음
          	System.out.println("기존 계정 있음");
              session.setAttribute("userId", kakao.getMember_id());
              session.setAttribute("nickName", kakao.getMember_nickname());    
          
          } else {
          	//기존 계정 없음, 회원가입
            	System.out.println("기존 계정 없음");
       	  int cnt = dao.insertMember(kakao);
          	if(cnt > 0) {
          		//회원 가입 성공
          		 session.setAttribute("userId", kakao.getMember_id());
                 session.setAttribute("nickName", kakao.getMember_nickname()); 
                 
          	}
          }
          session.setAttribute("access_Token", access_Token);
          session.setAttribute("login_Type", "kakao");
    	return "redirect:/";
    }
    
    @RequestMapping(value="/naverLogin",method=RequestMethod.GET)
    public String naverLogin(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
            throws IOException, ParseException {
        OAuth2AccessToken access_Token;
        access_Token = NaverLogin.getAccessToken(session, code, state);
 
        String userInfo = NaverLogin.getUserProfile(access_Token);
      //2. String형식인 userInfo를 json형태로 바꿈
        System.out.println(userInfo);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(userInfo);
        JSONObject jsonObj = (JSONObject) obj;

      //3. 데이터 파싱
      //Top레벨 단계 _response 파싱
      JSONObject response_obj = (JSONObject)jsonObj.get("response");
      //response의 nickname값 파싱

      String email = (String)response_obj.get("email");
      String member_id = email + "nv#";	//네이버
      String member_pw = CreateRandomPw.getRandomPw();
      String member_nickname = (String)response_obj.get("nickname");

      MemberVO naver = new MemberVO();
      naver.setMember_id(member_id);
      naver.setMember_pw(member_pw);
      naver.setMember_nickname(member_nickname);
      naver.setMember_email(email);
      
      MemberVO check = dao.memberSelectOne(naver.getMember_id());
      
      if(check != null) {
      	//기존 계정 있음
          session.setAttribute("userId", naver.getMember_id());
          session.setAttribute("nickName", naver.getMember_nickname());    
      } else {
      	//기존 계정 없음, 회원가입
      	int cnt = dao.insertMember(naver);
      	if(cnt > 0) {
      		//회원 가입 성공
      		 session.setAttribute("userId", naver.getMember_id());
             session.setAttribute("nickName", naver.getMember_nickname());  
      	}
      }
      
      
      session.setAttribute("access_Token", access_Token);      
      session.setAttribute("login_Type", "naver");
        return "redirect:/";
    }
	
}
