   <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 페이지</title>
   <script src="/resources/js/jquery-3.4.1.min.js"></script>
   <script src="/resources/js/jquery.maphilight.js"></script>
   
   <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
   <script src="/resources/js/bootstrap.4.4.1.min.js"></script>
   <script src="/resources/js/bootstrap.min.js"></script>
   <script src="/resources/js/kakao.min.js"></script>
   <link rel="stylesheet" href="/resources/css/nav.css">
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
   <script>
   function printRecipe(recipe_num){
      location.href="/recipe/recipeReadForm?listnum="+recipe_num
   }         

   function printReview(review_num){
      location.href="/review/reviewList?review_num="+review_num
   }   
   function updateRecipe(recipe_num){
      location.href="/recipe/recipeUpdateForm?recipe_num="+recipe_num
   }         

   </script>
   <style type="text/css">
   
<style>
   .card-body{
      width:380px;padding-top:10px;padding-bottom:10px;
   }
   .card-text1{
      width:10%;padding-right:0;
   }
   .card-text2{
      width:90%;padding-left:0;
   }
   .card-img-top{
      width:100%;height:350px;
   }
   
   .memoco13{
      width: auto;
      height:300px;
   }
   .memoco13Pos{
      position:absolute;
      margin-top: 50px;
      margin-left: 200px;
   }
   .memberInfo{
      margin-top: 75px;
   }
   
      @font-face {
         font-family: 'BBTreeGR';
         src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/BBTreeGR.woff') format('woff');
         font-weight: normal;
         font-style: normal;
      }      
      .text_in_card{
         font-family: 'BBTreeGR';
         font-size: 1.04rem;                                                                                                                               
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
        <div class="col-lg-12 col-sm-12 col-12 link-part">
            
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
 
   
<!-- 메모코 사진 -->
<div class="memoco13Pos"><img src="/resources/img/memoco/memoco13.png" class="memoco13"></div>


 <!-- 회원정보 수정 모달 -->
 <!-- Modal -->
<div class="modal fade text-center" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
   <div class="modal-dialog" role="document">
       <div class="modal-content">
         <div class=" col-sm-12 col-12 user-img">
          <img width="50" height="50" src="/resources/img/memoco/memoco3.png">
        </div>
        <div class="col-sm-12 col-12 user-name">
          <h1>User Update</h1>
          <button type="button" class="close" data-dismiss="modal">×</button>
        </div>
        
         <div class="col-lg-12 col-sm-12 col-12 form-input">
          <form style="text-align: center;" action="/member/update" method="post" onsubmit="return joinformCheck();">
             <div class="form-group input-group mb-3">
              <input type="text" class="form-control" value="${member.member_id }" readonly="readonly" aria-label="Recipient's username" aria-describedby="idCheck">
            </div>
             <div class="form-group">
                <input type="password" class="form-control" id="member_pw" name="member_pw" placeholder="기존의 비밀번호를 입력하세요">
             </div>
             <div class="form-group">
                <input type="password" class="form-control" id="updatePw" name="update_pw" placeholder="변경할 비밀번호를 입력하세요">
             </div>  
            <div class="form-group">
                <input type="password" class="form-control" id="reupdatePw" placeholder="동일한 비밀번호를 다시 입력하세요">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="nickname" name="member_nickname" value="${member.member_nickname }" >
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="email" name="member_email" value="${member.member_email}">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="favorite" name="member_favorite" value="${member.member_favorite }">
            </div>
         <div class="modal-footer align-items-start">
            <input type="submit"style="border: none; outline: none;" id="updateBtn" class="btn btn-success" value="수정">
         <button style="border: none; outline: none;" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
         </div>
          </form> 
       </div>
     </div>
   </div>
</div> 
 
 
 
  <main role="main">
     <section class="jumbotron memberInfo text-center">
        <div class="container">
          <h1 class="jumbotron-heading mb-3">회원정보</h1>
          <p class="lead text-muted">
          <b>아이디</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${member.member_id }
          </p>
          <p class="lead text-muted">
          <b>닉네임</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${member.member_nickname }
          </p>
          <p class="lead text-muted">
          <b>이메일</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${member.member_email }
          </p>
          <p>
            <c:if test="${sessionScope.login_Type eq 'default'}">
            <a data-toggle="modal" data-target="#updateModal" class="btn btn-success my-2">회원정보 수정</a>
            </c:if>
          </p>
        </div>
      </section>
 
 <h4 align="center">내가 쓴 레시피</h4>     
 <br>
        <div class="container">
    <div class="album py-4 px-4 bg-light">
		<div class="container">
          <div class="row">
             <c:if test="${recipes.size() > 0}">
            <c:forEach var="i" begin="0" end="${recipes.size() < 3 ? recipes.size()-1 : 2 }">
            <div class="col-md-4">
              <div class="card mb-4 box-shadow box-shadow border border-0 p-2 op_card">
            <c:choose>
		         <c:when test="${recipes.get(i).getRecipe_photo() != null }">
		            <img class='card-img-top rounded img_in_card' src='/recipeUpload/${recipes.get(i).getRecipe_photo()}'>
		         </c:when>
		         <c:otherwise>
		            <img class="card-img-top" src="/resources/img/default-image.png">                               
		         </c:otherwise>
     	 	</c:choose>
                <div class="row card-body auto_w">
               <div class="text_in_card">
                  <a class="text-reset text-decoration-none" href='/recipe/recipeReadForm?listnum=${recipes.get(i).getRecipe_num()}'>
                     ${recipes.get(i).getRecipe_name()}
                  </a>
               </div>
               <input type='hidden' class='recipe_num' value='${recipes.get(i).getRecipe_num()}'>
                </div>
              </div>
            </div>
            </c:forEach>
            </c:if>
         </div>
        </div>
      </div>
   </div>
</main>
 <br><br>
<h4 align="center">내가 쓴 리뷰</h4>
<br>
<!-- 내가 작성한 리뷰 -->
      <div class="container">
   <div class="album py-4 px-4 bg-light">
	<div class="container">
      <div class="row">
      <c:if test="${reviews.size() > 0}">
      <c:forEach var="review" items="${reviews}">
      <div class="col-md-4">
      <div class='card mb-4 box-shadow box-shadow border border-0 p-2 op_card'>
      <c:choose>
         <c:when test="${review.review_image != null}">
            <img class="card-img-top rounded img_in_card" src="/reviewUpload/${review.review_image}">         
         </c:when>
         <c:otherwise>
            <img class="card-img-top" src="/resources/img/default-image.png">                               
         </c:otherwise>
      </c:choose>
       <div class="row card-body auto_w">
            <div class="text_in_card">
               <a class="text-reset text-decoration-none" href="/review/reviewList?review_num=${review.review_num}">
                   ${review.review_title}
                </a>
            </div>
            <input type='hidden' class='review_num' value='${review.review_num}'>
      </div>
      </div>
      </div>
      </c:forEach>
      </c:if>
      </div>
    </div>
   </div>
</div><!-- /container -->

<c:choose>
   <c:when test="${not empty sessionScope.userId }">
      <input type="hidden" id="userId" value="1">
   </c:when>
   <c:otherwise>
      <input type="hidden" id="userId" value="0">
   </c:otherwise>
</c:choose>
</body>
<script type="text/javascript">
   $("#updateModal").on("shown.bs.modal",function(){
      $("#login_id").focus();
      loginCanEnter = true;
   }) 

   $("#updateBtn").click(function(){
      var originalPw = $("#originalPw").val();

      if(originalPw == ''){
         alert("기존 비밀번호를 입력해야만 변경 가능합니다");
         return false;
      }   
      var updatePw = $("#updatePw").val();
      var reupdatePw = $("#reupdatePw").val();
      if(updatePw != ''){
         if(updatePw != reupdatePw){
            alert("동일한 비밀번호를 입력하세요");
            return false;
         }            
      }
   })
   


</script>
<script type='text/javascript' src="/resources/js/login.js"></script>
<script type='text/javascript' src="/resources/js/recipeLike.js"></script>
<input type="hidden" id="currentPath" value="/member/myPage">
</html>
