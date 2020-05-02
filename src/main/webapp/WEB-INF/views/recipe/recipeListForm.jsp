<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,  initial-scale=1">
<title>RecipeList</title>
<script src="/resources/js/jquery-3.4.1.min.js"></script>
	<script src="/resources/js/jquery.maphilight.js"></script>
	<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
	<script src="/resources/js/bootstrap.4.4.1.min.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/kakao.min.js"></script>
	
	<link rel="stylesheet" href="/resources/css/nav.css">
	<link rel="stylesheet" href="/resources/css/mode2.css">
   	<link rel="stylesheet" href="/resources/css/album.css">
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
   <script src="/resources/js/all.min.js"></script>
   <link rel="stylesheet" href="/resources/css/all.min.css" /> <!-- font awesome 아이콘 -->
   
  <style>
      .r_box{
         position: relative;
         border: 2px solid #FFDC3C;
         padding: 10px;
         border-radius: 10px;
      }
      
      .r_rable{
         width: 60px;
         height: 28px;
         background: #FFDC3C;
         border-radius: 20px;
         display: flex;
         align-items: center;
         justify-content: center;
         
         position: absolute;
         top: -15px;
         left: 15px;
      }
      
      /* box_likes의 부모 div의 class */
      .box_shadow{
         position: relative;
      }
      
       .box_likes{
         padding: 2px 8px;
          background: white;
          border-radius: 15px;
          align-items: center;
         
          position: absolute;
         top: 25px;
         right: 20px;
         
         /* 아래에 둘 경우 아래 주석 */
/*          top: 318px; */
/*          right: 20px; */
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
   
   /*spoon position*/
		.s_size{
			width: 40px;
			height: 65px;
			transform: rotate(-15.27deg);
		}
		.m1_spoon{
			position: absolute;
			top: -10px;
			left: -35px;
		}
		
		/*fork position*/
		.m1f_size{
			width: 40px;
			height: 65px;
			transform: rotate(22deg);
		}
 		.m1_fork{
			position: absolute;
			top: -8px;
    		left: 110px;
		}
		
		.po_r{
			position: relative;
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
 


<!-- MODE1 -->
	<c:if test="${type eq 'mode1'}">
<div class="container-fluid mode1_ct">
	<div class="container-fluid px-0">
		<div class="container-lg">
			<div class="row pt-4">
				<!-- 메모코 캐릭터 / sm이하 사이즈에서 " 안내문구 " 부분 -->
				<div class="col-md-3">
					<div class="d-flex">
						<img src="/resources/img/memoco/memoco14.png" class="mx-3 mx-md-auto d-block memoco1_size">
						<div class="d-block d-md-none align-self-center">
							<h4 class="text-xs-center text-sm-left">
							<i class="fas fa-quote-left"></i>
							요리에 사용할 재료를 선택해주세요!!
							<i class="fas fa-quote-right"></i>
							</h4>
						</div>
					</div>
				</div>  <!-- div.col-md-3 끝 -->
		
					<!-- 말풍선 라디오박스 영역 -->
				<div class="col-md-9">
					<!-- 말풍선  -->
					<div class="d-flex flex-row">
						<div class="d-none d-md-block">
							<img src="/resources/img/b3.png" class="float-left">    <!-- md이상에서 안내 말풍선 -->
						</div>
					</div>
					<div class="d-flex flex-row pt-5">
						<!-- 주재료 박스 -->
						<div class="col-md-6">
							<div class="r_box pl-3 pr-2 px-sm-4 pt-4 pb-3 white_bg">
								<span class="r_rable">주재료</span>
								<fieldset id="mainFS">
									<c:if test="${food.size() > 0}">
									<c:forEach var="i" begin="0" end="${food.size() > 0 ? food.size() -1 : 0}">
										<c:choose>
											<c:when test="${food.get(i) eq mainFood}">
												<span class="pr-2 pr-sm-4 d-inline-block">
													${food.get(i)} <input type="checkbox" class="mainIngrd" value="${food.get(i)}" checked>
												</span>
											</c:when>
											<c:otherwise>
												<span class="pr-2 pr-sm-4 d-inline-block">
													${food.get(i)} <input type="checkbox" class="mainIngrd" value="${food.get(i)}">
												</span>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									</c:if>
								</fieldset>
							</div>
						</div>
						<!-- 부재료 박스 -->
						<div class="col-md-6">
							<div class="r_box pl-3 pr-2 px-sm-4 pt-4 pb-3 white_bg">
								<span class="r_rable">부재료</span>
								<fieldset id="subFS">
									<c:forEach var="i" begin="0" end="${food.size() -1}">
										<c:if test="${food.get(i) != mainFood}">
											<span class="pr-2 pr-sm-4 d-inline-block">
												${food.get(i)} <input type="checkbox" class="subIngrd" value="${food.get(i)}">
											</span>
										</c:if>
									</c:forEach>
								</fieldset> 
							</div>
						</div>
					</div>
				</div> <!-- col-md-9 -->
			</div> <!-- row -->	
		</div>

		<div class="rounded-lg my-5 pt-3 white_bg">
		<div class="d-flex justify-content-center my-3">
			<div class="po_r d-inline">
				<span class="m1_spoon"><img src="/resources/img/spoon.png" class="s_size"></span>
				<span class="m1_fork"><img src="/resources/img/fork.png" class="m1f_size"></span>
				<h4 align="center" class="d-inline pt-4"><strong>추천 레시피</strong></h4>     
			</div>
		</div>    
		    <div class="album py-4 px-0 px-sm-4">
		        <div class="container">
		        	<div class="row" id="recipeList">
		        		<c:if test="${map.recipes.size() > 0}">
			            <c:forEach var="i" begin="0" end="${map.recipes.size() < 10 ? map.recipes.size()-1 : 9 }">
				    	<div class="col-md-4">
					    	<div class='card mb-4 box-shadow border border-0 p-2 op_card'>
				       		<c:choose>
				       			<c:when test="${sessionScope.userId != null }">
				       				<c:set var="recipe_num">${map.recipes.get(i).getRecipe_num()}</c:set>
				       				<c:set var="check">${map.likeRecipes[recipe_num]}</c:set>
				       				<c:choose>
				       					<c:when test="${check}">
						       				<span class="box_likes">
						       					<a href="#" class="text-reset text-decoration-none">
						       						<i class="fas fa-heart text-danger fullHeart"></i>
							       					<input type="hidden" value="${map.recipes.get(i).getRecipe_num()}">
						       					</a>
						       					<span>&nbsp;${map.recipes.get(i).getRecipe_likes()}</span>
											</span>
											</c:when>
										<c:otherwise>
											<span class="box_likes">
												<a href="#" onclick="return false;" class="text-reset text-decoration-none">
													<i class="far fa-heart text-danger emptyHeart"></i>
							       					<input type="hidden" value="${map.recipes.get(i).getRecipe_num()}">
												</a>
						       					<span>&nbsp;${map.recipes.get(i).getRecipe_likes()}</span>
					       					</span>
										</c:otherwise>		
			       					</c:choose>
			       				</c:when>
			       				<c:otherwise>
					       			<span class="box_likes">
					       				<a href="#" onclick="return false;" class="text-reset text-decoration-none">
					       					<i class="far fa-heart text-danger emptyHeart"></i>
						       				<input type="hidden" value="${map.recipes.get(i).getRecipe_num()}">
					       				</a>
					       				<span>&nbsp;${map.recipes.get(i).getRecipe_likes()}</span>									       					
					      				</span>				
			       				</c:otherwise>
       						</c:choose>
		       				<c:choose>
			       				<c:when test="${map.recipes.get(i).getRecipe_num() >= 10000 }">
									<a href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}'>
										<img class='card-img-top' src='/recipeUpload/${map.recipes.get(i).getRecipe_photo()}'>
									</a>
								</c:when>
								<c:otherwise>
		       						<a href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}'>
										<img class='card-img-top rounded img_in_card' src='${map.recipes.get(i).getRecipe_photo()}'>
									</a>
								</c:otherwise>
							</c:choose>
				       	
				       		<div class='row card-body auto_w'>
								<div class="text_in_card">
									<a class="text-reset text-decoration-none" href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}'>
										${map.recipes.get(i).getRecipe_name()}
									</a>
								</div>
								<input type='hidden' class='recipe_num' value='${map.recipes.get(i).getRecipe_num()}'>
				        	</div>
				        	</div>
			        	</div>
			       		</c:forEach>	
			       		</c:if> 	 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	</c:if>
	
	<c:if test="${type eq 'mode2'}">
<div class="container-fluid mainContainer px-0">
	<div class="container-fluid">	
		<!-- 검색창 시작 -->
		<div class="search_ct pb-3">
			<p class="pt-5">레시피 검색</p>
			<!-- 검색창 부분 -->
			<form action="/recipe/recipeList" method="get">
			<div id="searchForm" class="my-3">
				<div class="form-row">
					<div class="col-md-4 mb-2">
						<select class="custom-select custom-select-lg" id="selected" name="option">
							<c:choose>
								<c:when test="${map.option eq 'recipe_title' || map.option eq ''}">
									<option value="recipe_title" selected="selected">음식 이름으로 검색</option>
									<option value="ingrd_name">음식 재료로 검색</option>
								</c:when>
								<c:when test="${map.option eq 'ingrd_name'}">
									<option value="recipe_title">음식 이름으로 검색</option>
									<option value="ingrd_name" selected="selected">음식 재료로 검색</option>
								</c:when>
							</c:choose>
																	
									
						</select>
					</div>
					<div class="input-group input-group-lg col-md-8 mb-2">
						<input type="text" class="form-control" name="searchText" id="searchText" value="${map.searchText}">
						<input type="hidden" id="doneSearch" value="${map.doneSearch}">
						<input type="hidden" id="h_searchText" value="${map.searchText}">
						<input type="hidden" id="h_option" value="${map.option}">
						<div class="input-group-append">
							<input type="submit" class="btn btn-outline-warning" id="searchBtn" value="검색"> 
						</div>

					</div>
				</div>
			</div>
			</form>
		</div>	<!-- .search_ct 검색창 끝 -->
	
	
	<div class="container">
		<h4 align="center" class="pt-2"><strong><a href="/recipe/recipeList">레시피 게시판</a></strong></h4>
	   	<div class="album py-4 px-0 px-sm-4">
	    	<div class="container">
	    		<div class="text-right mb-4">
	    			<button type="button" class="btn btn-lg btn-outline-warning" id="insertRecipeBtn">레시피 등록</button>
	    		</div>
		       	<div class="row" id="recipeList">
		       		<c:if test="${map.recipes.size() > 0}">
				    <c:forEach var="i" begin="0" end="${map.recipes.size() < 10 ? map.recipes.size()-1 : 10 }">
				    	<div class="col-md-4">
					    	<div class='card mb-4 box-shadow border border-0 p-2 op_card'>
				       		<c:choose>
				       			<c:when test="${sessionScope.userId != null }">
				       				<c:set var="recipe_num">${map.recipes.get(i).getRecipe_num()}</c:set>
				       				<c:set var="check">${map.likeRecipes[recipe_num]}</c:set>
				       				<c:choose>
				       					<c:when test="${check}">
						       				<span class="box_likes">
						       					<a href="#" onclick="return false;" class="text-reset text-decoration-none">
						       						<i class="fas fa-heart text-danger fullHeart"></i>
							       					<input type="hidden" value="${map.recipes.get(i).getRecipe_num()}">
						       					</a>
						       					<span>&nbsp;${map.recipes.get(i).getRecipe_likes()}</span>
											</span>
											</c:when>
										<c:otherwise>
											<span class="box_likes">
												<a href="#" onclick="return false;" class="text-reset text-decoration-none">
													<i class="far fa-heart text-danger emptyHeart"></i>
							       					<input type="hidden" value="${map.recipes.get(i).getRecipe_num()}">
												</a>
						       					<span>&nbsp;${map.recipes.get(i).getRecipe_likes()}</span>
					       					</span>
										</c:otherwise>		
			       					</c:choose>
			       				</c:when>
			       				<c:otherwise>
					       			<span class="box_likes">
					       				<a href="#" onclick="return false;" class="text-reset text-decoration-none">
					       					<i class="far fa-heart text-danger emptyHeart "></i>
						       				<input type="hidden" value="${map.recipes.get(i).getRecipe_num()}">
					       				</a>
					       				<span>&nbsp;${map.recipes.get(i).getRecipe_likes()}</span>									       					
					      				</span>				
			       				</c:otherwise>
       						</c:choose>
		       				<c:choose>
			       				<c:when test="${map.recipes.get(i).getRecipe_num() >= 10000 }">
									<a href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}' >
										<img class='card-img-top  rounded img_in_card' src='/recipeUpload/${map.recipes.get(i).getRecipe_photo()}'>
									</a>
								</c:when>
								<c:otherwise>
		       						<a href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}'>
										<img class='card-img-top rounded img_in_card' src='${map.recipes.get(i).getRecipe_photo()}'>
									</a>
								</c:otherwise>
							</c:choose>
				       	
				       		<div class='row card-body auto_w'>
								<div class="text_in_card">
									<a class="text-reset text-decoration-none" href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}'>
										${map.recipes.get(i).getRecipe_name()}
									</a>
								</div>
								<input type='hidden' class='recipe_num' value='${map.recipes.get(i).getRecipe_num()}'>
				        	</div>
				        	</div>
			        	</div>
					</c:forEach>	
				    </c:if> 	
		       	</div>
	       	</div>
		</div>
	</div>
</div>
</div>
</c:if>
<script>
	
</script>

<c:choose>
	<c:when test="${not empty sessionScope.userId }">
		<input type="hidden" id="userId" value="${sessionScope.userId}">
	</c:when>
	<c:otherwise>
		<input type="hidden" id="userId" value="0">
	</c:otherwise>
</c:choose>
</body>
<script type='text/javascript' src="/resources/js/login.js"></script>
<script type='text/javascript' src="/resources/js/recipe-recipeListForm.js"></script>
<!-- 레시피 좋아요 기능 -->
<script src="/resources/js/recipeLike.js"></script>
<script>

</script>
<script>

</script>
 <input type="hidden" id="currentPath" value="/recipe/recipeListForm">
 <input type="hidden" id="type" value="${type}">
</html>