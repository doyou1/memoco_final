<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<html>
<head>
<title>uploadImageForm</title>
	<script src="/resources/js/jquery-3.4.1.min.js"></script>
	<script src="/resources/js/jquery.maphilight.js"></script>
	
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	
	<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
	<script src="/resources/js/bootstrap.4.4.1.min.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/kakao.min.js"></script>
	<link rel="stylesheet" href="/resources/css/nav.css">
	<link rel="stylesheet" href="/resources/css/mode2.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" /> <!-- font awesome 아이콘 -->
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
</head>
<style>
	#memoko_logo{
		height : 45.6px;
	}
	

</style>   
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
          <img width="50" height="50" src="https://img.insight.co.kr/static/2019/12/30/700/u262az08bqr3g3l85j08.jpg">
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
          <img src="https://img.insight.co.kr/static/2019/12/30/700/u262az08bqr3g3l85j08.jpg">
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

 <!-- container 시작 -->
<div class="container-lg mode1_ct">
   <!-- 캐릭터 row -->
   <div class="row pt-4">
      <!-- 메모코 캐릭터 / sm이하 사이즈에서 " 안내문구 " 부분 -->
		<div class="col-md-3">
			<div class="d-flex">
				<img src="/resources/img/memoco/memoco11.png" class="mr-3 mx-md-auto d-block memoco1_size">
				<div class="d-block d-md-none align-self-center">
					<h4 class="text-xs-center text-sm-left">
						<i class="fas fa-quote-left"></i>
						사진을 올려주세요
						<i class="fas fa-quote-right"></i>
					</h4>
				</div>
			</div>
		</div>	<!-- div.col-md-3 끝 -->
    
    <div class="col-md-8">
			<div class="d-flex flex-row">
				<div class="d-none d-md-block">
					<img src="/resources/img/b1.png" class="float-left">	<!-- md이상에서 안내 말풍선 -->
				</div>
			</div>
			<!-- form태그 -->
			<form action="upload" id="uploadForm" method="post" enctype="multipart/form-data" class="was-validated">   
				<div class="row d-flex justify-content-center mx-1">
					<div>
						<h4 class="pt-5 pb-md-4 pb-2">* 이미지파일만 올려주세요!!</h4>
					</div>
					<div class="d-none d-md-block pt-3 ml-auto align-self-center float-left">
						<button type="submit" id="uploadBtn" class="btn btn-warning">사진으로 재료 분석</button>
					</div>
				</div>
				<!-- xs, sm사이즈에서만 보일 '재료등록' 버튼(풀 사이즈) -->
				<div class="d-block d-md-none pb-2 align-self-center mx-1">
					<button type="submit" id="uploadBtn" class="btn btn-warning btn-lg btn-block">사진으로 재료 분석</button>
				</div>
				<!-- 업로드 한 이미지 영역 -->
				<div class="row mx-1">
					<img id="img" class="img-fluid center-block pb-4">
				</div>
				<!-- 파일 업로드 -->
				<div class="row mx-1">
					<div class="custom-file mb-3">
						<input type="file" class="custom-file-input" id="input_img" name="upload" required="required" accept="image/gif,image/jpeg,image/png">
						<label class="custom-file-label" for="validatedCustomFile">재료 사진을 올려주세요</label>
					</div>
				</div>
			</form> <!-- form태그 끝 -->
			<div class="row">
				<div id="img_div" class="col">
					<img id="img" class="img-responsive center-block">
				</div>
			</div>
		</div>
	</div>
</div>

  <!-- 내용 끝입니다!! -->
	<c:if test="${not empty sessionScope.userId }">
		<input type="hidden" id="userId" value="${sessionScope.userId}">
	</c:if>


<script>
    // Add the following code if you want the name of the file appear on select
    $(".custom-file-input").on("change", function() {
      var fileName = $(this).val().split("\\").pop();
      $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

    var sel_file;

    $(document).ready(function(){
       
       $("#input_img").on("change", handleImgFileSelect);

       function handleImgFileSelect(e){
          
          var file = e.target.files
          var fileArr = Array.prototype.slice.call(file)

          fileArr.forEach(function(f){
             if(!f.type.match("image.*")){
                alert("확장자는 이미지 확장자만 가능합니다")
                $("#input_img").val("")
                return
             }

             sel_file = f

             var reader = new FileReader()
             reader.onload = function(e){
                $("#img").attr("src",e.target.result);
             }
             reader.readAsDataURL(f)
          })
       }
    })
    
    $("#uploadForm").on("submit",function(){
    	$("#uploadBtn").attr("disabled","disabled");
	 
    })
   
   
</script>
<script src="/resources/js/login.js"></script>
</body>
<input type="hidden" id="currentPath" value="/imageanalysis/uploadImageForm">
</html>