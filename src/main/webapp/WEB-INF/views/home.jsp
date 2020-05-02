<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html>
<head>
   <title>Home</title>
   
   <script src="/resources/js/jquery-3.4.1.min.js"></script>
   <script src="/resources/js/jquery.maphilight.js"></script>
   
   <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
   <script src="/resources/js/bootstrap.4.4.1.min.js"></script>
   <script src="/resources/js/bootstrap.min.js"></script>
   <script src="/resources/js/kakao.min.js"></script>
   <link rel="stylesheet" href="/resources/css/nav.css">
   <link rel="stylesheet" href="/resources/css/home.css">
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
   <link rel="stylesheet" href="/resources/js/all.min.js" /> <!-- font awesome 아이콘 -->
   <link rel="stylesheet" href="/resources/css/all.min.css" /> <!-- font awesome 아이콘 -->
   
   <style type="text/css">
   
   #navi2 {width:100%; background:#093;margin-top: 70px;}
   
   #menu/*ul*/ {list-style:none; padding:0; margin:0 auto; display:flex; width:100%; max-width:1200px; 
   justify-content:space-between; color:#fff; cursor:pointer;}
   
   .main /*ul의 list*/ {position:relative; line-height:50px; width:100%; text-align:center;}
   
   .main:hover {color:#80FF80; transition:.4s ease-out}
   
   .sub /*list안에 ul*/ {position:absolute; top:100%; width:100%; list-style:none; padding:0; margin:0; display:none;
   background:#fff; color:#000;}
   
   .sub li {text-align:center; line-height:45px;}
   .sub li:hover {background:#008040; color:#fff;}
   
   .container{
       max-width: 1140px;
       max-height: 600px;
       }
 
   </style>
</head>
<body>

 <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container-fluid">
      <a class="navbar-brand js-scroll-trigger" href="/"> <!-- 메모코 로고 -->
		<img alt="MEMOCO" class="memoco_logo mt-1" src='<c:url value="/resources/img/memocoLogo.png" />'>
      </a>
     <!-- Toggler/collapsibe Button -->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
       <span class="navbar-toggler-icon"></span>
      </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="/imageanalysis/uploadImageForm">요리모드</a>
          </li>
        <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="/mode2/home">일반모드</a>
          </li>
        <c:choose>
         <c:when test="${sessionScope.userId != null }">
          <li class="nav-item">
         <c:if test="${sessionScope.login_Type eq 'google'}">
               <a class="nav-link js-scroll-trigger" href="javascript:googleLogout()">로그아웃</a>
            </c:if>
            <c:if test="${sessionScope.login_Type eq 'kakao'}">
               <a class="nav-link js-scroll-trigger" href="javascript:kakaoLogout()">로그아웃</a>
            </c:if>
            <c:if test="${sessionScope.login_Type eq 'naver'}">
               <a class="nav-link js-scroll-trigger" href="javascript:naverLogout()">로그아웃</a>
            </c:if>
         <c:if test="${sessionScope.login_Type eq 'default'}">
               <a class="nav-link js-scroll-trigger" href="/member/logout">로그아웃</a>
            </c:if>
          </li>            
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="/member/myPage">마이페이지</a>
          </li>
         </c:when>
         <c:otherwise>
         <li class="nav-item">
             <a class="nav-link js-scroll-trigger joinBtn" href="/member/joinForm" data-toggle="modal" data-target="#joinModal">회원가입</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger loginBtn" href="/member/loginForm" data-toggle="modal" data-target="#loginModal">로그인</a>
          </li>
            </c:otherwise>
      </c:choose>      
        </ul>
      </div>
    </div>
  </nav>


 <!-- 회원가입 -->
 <!-- Button trigger modal-->
<div id="joinModal" class="modal fade text-center">
  <div class="modal-dialog modal-lg">
    <div class="col-lg-8 col-sm-8 col-12 main-section">
      <div class="modal-content modal-last-center py-3">
        <div class="col-lg-12 col-sm-12 col-12 user-img">
          <img width="50" height="50" src="/resources/img/memoco/memoco3.png">
        </div>
        <div class="col-lg-12 col-sm-12 col-12 user-name">
          <h1>User Join</h1>
          <button type="button" class="close" data-dismiss="modal">×</button>
        </div>
        
        <div class="col-lg-12 col-sm-12 col-12 form-input">
          <form id="joinForm" action="/member/join" method="post" onsubmit="return joinformCheck();">
            <div class="form-group input-group mb-3">
              <input type="text" class="form-control" id="member_id" name="member_id" placeholder="Enter Id" aria-label="Recipient's username" aria-describedby="idCheck" required>
              <div id="idCheckMsg"></div>
            </div>
            <div class="form-group input-group-append idCheckdiv">
               <input type="button" class="btn btn-outline-secondary" value="아이디 중복확인" id="idCheck">
            </div>
            <div class="form-group">
              <input type="password" class="form-control" id="member_pw" name="member_pw" placeholder="Enter password" required>
            </div>
            <div class="form-group">
              <input type="password" class="form-control" id="pwCheck" placeholder="Enter password" required>
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="member_nickname" name="member_nickname" placeholder="Enter nickname" required>
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="member_email" name="member_email" placeholder="Enter email" required>
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="member_favorite" name="member_favorite" placeholder="Enter favorite">
            </div>
           
            <input type="submit" value="Join" id="memberJoin" class="btn btn-success btn-block">
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

  <!-- 로그인 -->
   <!-- Modal -->
<div id="loginModal" class="modal fade text-center">
  <div class="modal-dialog">
    <div class="col-lg-8 col-sm-8 col-12 main-section">
      <div class="modal-content">
        <div class="col-lg-12 col-sm-12 col-12 user-img">
          <img src="/resources/img/memoco/memoco3.png">
        </div>
        <div class="col-lg-12 col-sm-12 col-12 user-name">
          <h1>User Login</h1>
          <button type="button" class="close" data-dismiss="modal">×</button>
        </div>
        <div class="col-lg-12 col-sm-12 col-12 form-input">
            <div class="form-group">
              <input type="text" class="form-control" id="login_id" name="member_id" placeholder="Enter Id" required>
            </div>
            <div class="form-group">
              <input type="password" class="form-control" id="login_pw" name="member_pw" placeholder="Enter Password" required>
            </div>
            <button type="button" id="memberLogin" class="btn btn-success">Login</button>
        </div>
        <p>
            <a href="javascript:googleLogin('${google_url}')">
               <img src="https://developers.google.com/identity/images/btn_google_signin_dark_normal_web.png?hl=ko" width="225" height="52">
             </a> 
        </p>
          <div>
         <p id="kakaoLogin">
             <a href="javascript:kakaoLogin('${kakao_url}')">
               <img src="/resources/img/kakao_account_login_btn_medium_narrow.png">
            </a>
         </p>
         <p>
             <a href="javascript:naverLogin('${naver_url}')">
               <img src="/resources/img/naver_login_btn_completed.PNG" width="222" height="49">
            </a>
         </p>
         <hr>
      </div>
    </div>
  </div>
</div>
</div>


<div id="mode1" class="container col-6">
 <img src="/resources/img/refrigerator.png" alt="Avatar" class="image">  
  <div class="overlay mode1">
    <div class="text">
    <a class="mode1Btn">
      <div class="t1">냉장고에 남아있는 재료로 맛있는 요리를!</div>
      <div class="t2">사진 한장이면 많은 레시피를 볼 수 있어요</div>
      <div class="t3">사진을 올리러 가볼까요?</div>
    </a>
      <div class="btn_detail"><a class="mode1Btn">요리모드</a></div>
   </div>
  </div>
</div>



<div id="mode2" class="container col-6">
 <img src="/resources/img/mode22.jpg" alt="Avatar" class="image">
   <div class="overlay mode2">
          <div class="text">
          	<a class="mode2Btn">
              <div class="t1">다른 사람들과 레시피를 공유하고!</div>
	          <div class="t2">또한 채팅까지 할 수 있어요</div>
    	      <div class="t3">당신이 요리한 사진들을 올려볼까요?</div>
          	</a>
             <div class="btn_detail"><a class="mode2Btn">일반모드</a></div>
          </div>

     </div>
</div> 
</body>
<input type="hidden" id="currentPath" value="/">
<script type='text/javascript' src="/resources/js/login.js"></script>
<script type='text/javascript' src="/resources/js/home.js"></script>
</html>