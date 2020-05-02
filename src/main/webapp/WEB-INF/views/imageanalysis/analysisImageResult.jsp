	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,  initial-scale=1">
<title>Result</title>

	<script src="/resources/js/jquery-3.4.1.min.js"></script>
	<script src="/resources/js/jquery.maphilight.js"></script>
	
	<!-- 이거 추가해야됨 -->
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
	.img_hidden{
		visibility: hidden;
	}

	.img_visible{
		visibility: visible;
	}
		
	/* 커서 올리면 포인터로 바뀌게 하기 */
	.c_pointer{
		cursor: pointer;
	}
	.map{
		width:500px;
		height:500px;
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



<div class="container-lg mode1_ct">
	<div class="row pt-4">
		<!-- 메모코 캐릭터 / sm이하 사이즈에서 " 안내문구 " 부분 -->
		<div class="col-md-3">
			<div class="d-flex">
				<img src="/resources/img/memoco/memoco12.png" class="mx-3 mx-md-auto d-block memoco1_size">
				<div class="d-block d-md-none align-self-center">
					<h4 class="text-xs-center text-sm-left">
					<i class="fas fa-quote-left"></i>
					재료를 확인해주세요!!
					<i class="fas fa-quote-right"></i>
					</h4>
				</div>
			</div>
		</div>  <!-- div.col-md-3 끝 -->

			<!-- 말풍선 / 컨텐츠 영역 -->
		<div class="col-md-9">
			<div class="d-flex flex-row">
				<div class="d-none d-md-block">
					<img src="/resources/img/b2.png" class="float-left">    <!-- md이상에서 안내 말풍선 -->
				</div>
			</div>
		</div> <!-- col-md-9 -->
	</div> <!-- row -->	
	
		<!-- 캐릭터 하단 사진/재료 입력 row -->
	<div class="row pt-3 pb-5 mx-0 mx-sm-3">
		<div class="col-sm-12 col-md-4 col-lg-6 pt-3">
			<img class="map img-fluid" src="/imageUpload/${savedFileName}" usemap="#map">
			<map name="map">
				<c:forEach items="${list}" var="subimage" varStatus="status">
					<c:if test="${subimage.value != ''}">
					<area shape="rect" class="area${status.count}" title="${subimage.value}" alt="${subimage.value}"
						coords="${subimage.x1 * 500 / subimage.width},${subimage.y1 * 500 / subimage.height},${subimage.x2 * 500 / subimage.width},${subimage.y3 * 500 / subimage.height}">
					</c:if>
				</c:forEach>
			</map>
		</div>
		<!-- 수정할 col영역 (재료 입력받고 수정할 수 있는 부분) -->
		<div class="col-sm-12 col-md-8 col-lg-6 pt-3">
			<div class="border border-warning rounded">
				<!-- 재료 전체 폼 -->
				<form action="/recipe/findRecipesByInput" id="findRecipeForm" method="post">
					<!-- 재료 input영역 -->
					<div id="inputdiv">
							<div class="p-3 px-5">
							<c:if test="${not empty sessionScope.nickName }">
								<strong>${sessionScope.nickName }</strong>님의 재료
							</c:if>
							</div>
							<c:if test="${subimage.value != ''}">
								<div id="recipeInfo" class="mx-1 mx-sm-5 align-self-center">
								<c:forEach  items="${list}" var="subimage" varStatus="status">
									<div class="input-group mb-3 inputDiv recipeIngrd">
										<c:choose>
											<c:when test="${status.count eq 1 }">
												<span class="align-self-center img_visible"><img src="/resources/img/tooltip1.png"></span>
											</c:when>
											<c:otherwise>
												<span class="align-self-center img_hidden"><img src="/resources/img/tooltip1.png"></span>
											</c:otherwise>
										</c:choose>									
										<!-- 라디오 버튼 -->
										<div class="input-group-prepend">
											<div class="input-group-text">
											<c:if test="${status.count eq 1}">
											<input type="radio" name="mainFood" class="mainFood" value="${subimage.value}" checked>
											</c:if>
											<c:if test="${status.count != 1 }">
											<input type="radio" name="mainFood" class="mainFood" value="${subimage.value}">
											</c:if>
											</div>
										</div>
										<!-- 재료명 -->
										<input type="text" name="food" class="area${status.count}_text form-control food" value="${subimage.value}" aria-label="Text input with checkbox" >
										<input type="hidden" name="filePath" class="area${status.count}_text" value="${subimage.filePath}" >
										<!-- 삭제 버튼 -->
										<div class="input-group-append">
											<input type="hidden" class="area${status.count}">
											<button class="btn btn-warning foodRemoveBtn">삭제</button>
										</div>
									</div>
								</c:forEach>
								</div>
							</c:if>
							</div>
							<!-- 추가 버튼 -->
							<div class="row justify-content-center">
								<p id="foodAddBtn" class="c_pointer"><i class="fas fa-plus-circle"></i> 추가</p>
							</div>
							<div class="mx-1 mx-sm-5 pb-3 align-self-center">
								<button type="submit" id="findBtn" class="btn btn-warning btn-block">레시피추천</button>
							</div>
						</form>
					</div>
				</div><!-- /.col(재료부분) -->
			</div>  <!-- /.row (사진/재료부분) -->
		</div><!-- /.container-lg -->

		
	<c:if test="${not empty sessionScope.userId }">
		<input type="hidden" id="userId" value="${sessionScope.userId}">
	</c:if>
<script type='text/javascript' src="/resources/js/login.js"></script>
<script type='text/javascript' src="/resources/js/analysisImageResult.js"></script>

</body>
<input type="hidden" id="currentPath" value="/imageanalysis/analysisImageResult">
</html>