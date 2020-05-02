<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Home</title>
	
	<script src="/resources/js/jquery-3.4.1.min.js"></script>
	<script src="/resources/js/jquery.maphilight.js"></script>
	
	<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
	<script src="/resources/js/bootstrap.4.4.1.min.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/kakao.min.js"></script>
	<link rel="stylesheet" href="/resources/css/nav.css">
	<link rel="stylesheet" href="/resources/css/album.css">
	<link rel="stylesheet" href="/resources/css/mode2.css">
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
	<script src="/resources/js/all.min.js" ></script> <!-- font awesome 아이콘 -->
   <link rel="stylesheet" href="/resources/css/all.min.css" /> <!-- font awesome 아이콘 -->
	
	<!-- Link Swiper's CSS -->
  <link rel="stylesheet" href="/resources/css/swiper.min.css">
  <!-- Swiper JS -->
  <script src="/resources/js/swiper.min.js"></script>
<style>
		@import url('https://fonts.googleapis.com/css2?family=Kosugi+Maru&display=swap');

		.text-jp{
			font-family: 'Kosugi Maru', sans-serif;
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
       body {padding-bottom: 35px;}
       .footer {
			width:100%;
			max-height:150px;
			padding-left: 80px;
	    	padding-right: 80px;
			background:#fffcf0;
			text-align: center;
			position: absolute;
		}
		.mode2_memoco{
			position: absolute;
			right: 2px;
			bottom: 1px;
		}
		
		.memoco2-size{
			width: 180px;
			height: auto;
		}
		
		.memoco-size{
			width: 80px;
			height: auto;
		}
		
		.memoco3{
			position: absolute;
			right: 10px;
			bottom: 10px;
		}
		
		.memoco4{
			position: absolute;
			left: 10px;
			bottom: 10px;
		}

</style>
<script>
function swiperOn(){

var swiper1 = new Swiper('#recipe', {
    slidesPerView: 1,
    spaceBetween: 10,
    slidesPerGroup: 1,
    loop: true,
    loopFillGroupWithBlank: true,
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
    navigation: {
      nextEl: '#recipe-next',
      prevEl: '#recipe-prev',
    },
    breakpoints: {
        640: {
          slidesPerView: 2,
          spaceBetween: 20,
        },
        768: {
          slidesPerView: 3,
          spaceBetween: 30,
        },
      }
  });


var swiper2 = new Swiper('#review', {
    slidesPerView: 1,
    spaceBetween: 10,
    slidesPerGroup: 1,
    loop: true,
    loopFillGroupWithBlank: true,
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
    navigation: {
      nextEl: '#review-next',
      prevEl: '#review-prev',
    },
    breakpoints: {
        640: {
          slidesPerView: 2,
          spaceBetween: 20,
        },
        768: {
          slidesPerView: 3,
          spaceBetween: 30,
        },
      }
  });

}


function printRecipe(recipe_num){

	location.href="/recipe/recipeReadForm?listnum="+recipe_num
}

function printReview(review_num){

	location.href="/review/reviewList?review_num="+review_num
}
</script>

</head>
<body onload="swiperOn()">
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

<!-- navbar 아래 컨텐츠 부분 -->
<div class="container-fluid mainContainer">
	<div class="container-fluid pt-5 px-0 px-sm-3">
		<!-- 검색창 시작 -->
		<div class="search_ct py-3">
			<!-- 검색창의 구분(레시피/리뷰 선택) 버튼-->
			<div class="search_type mb-3 mb-sm-4">
				<div class="btn-group btn-group-lg btn-group-toggle btn-block" data-toggle="buttons">
					<label class="btn btn-outline-warning active">
						<input type="radio" class="option" name="options" id="option1" checked> 레시피 검색
					</label>
					<label class="btn btn-outline-warning">
						<input type="radio" class="option" name="options" id="option2"> 리뷰 검색
					</label>
				</div>
			</div>
			<!-- 검색창 부분 -->
			<form action="" method="get" id="search">
			<div id="searchForm" class="my-3">
				<div class="form-row">
					<div class="col-md-4 mb-2">
						<select class="custom-select custom-select-lg" id="selected" name="option">
							<option value="recipe_title" selected="selected">음식 이름으로 검색</option>
							<option value="ingrd_name">음식 재료로 검색</option>
						</select>
					</div>
					<div class="input-group input-group-lg col-md-8 mb-2">
						<input type="text" class="form-control" name="searchText" id="searchText">
						<div class="input-group-append">
							<input type="submit" class="btn btn-outline-warning" id="searchBtn" value="검색">
						</div>
					</div>
				</div>
			</div>
			</form>
		</div>	<!-- .search_ct 검색창 끝 -->

	<div class="container">
		<div class="container mt-4">
			<div class="d-flex justify-content-between px-2">
				<h4 align="center">최신 레시피</h4>

	    		<button type="button" class="btn btn-outline-warning" id="recipeBtn">레시피 더보기  <i class="fas fa-chevron-right"></i> </button>	
			</div>
	
	  	    <div class="album py-3">   	
		  		<div class="swiper-container" id="recipe"><!-- Swiper -->
					<div class="swiper-wrapper" id="recipeList">
		        		<c:if test="${map.recipes.size() > 0}">
						<c:forEach var="i" begin="0" end="${map.recipes.size()-1 < 10 ? map.recipes.size()-1 : 9}">
							<div class='card mb-4 swiper-slide box-shadow border border-0 p-2 op_card'>
							<c:choose>
					       		<c:when test="${sessionScope.userId != null}">
					       			<c:set var="recipe_num">${map.recipes.get(i).getRecipe_num()}</c:set>
					       			<c:set var="check">${map.likeRecipes[recipe_num]}</c:set>
					       			<c:choose>
					       				<c:when test="${check}">
							    			<span class="box_likes">
							      				<a href="#" onclick="return false;"  class="text-reset text-decoration-none">
							      					<i class="fas fa-heart text-danger fullHeart"></i>
								    				<input type="hidden" value="${map.recipes.get(i).getRecipe_num()}">
							       				</a>
							       				<span>&nbsp;${map.recipes.get(i).getRecipe_likes()}</span>
											</span>
										</c:when>
										<c:otherwise>
											<span class="box_likes">
												<a href="#" onclick="return false;"  class="text-reset text-decoration-none">
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
											<img class='card-img-top rounded img_in_card' src='/recipeUpload/${map.recipes.get(i).getRecipe_photo()}'>
										</a>
									</c:when>
									<c:otherwise>
			       						<a href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}'>
											<img class='card-img-top rounded img_in_card' src='${map.recipes.get(i).getRecipe_photo()}'>
										</a>
									</c:otherwise>
							</c:choose>
							<div class='row card-body px-0'>
								<div class="text_in_card">
										<a class="text-reset text-decoration-none" href='/recipe/recipeReadForm?listnum=${map.recipes.get(i).getRecipe_num()}'>
											${map.recipes.get(i).getRecipe_name()}
										</a>
								</div>
									<input type='hidden' class='recipe_num' value='${map.recipes.get(i).getRecipe_num()}'>
								</div>
							</div>
						</c:forEach>
						</c:if>
		    		</div>
		    		<div class="swiper-button-next" id="recipe-next"></div>
		    		<div class="swiper-button-prev" id="recipe-prev"></div>
				</div>
			</div>
		</div>
	
	
		<div class="container mt-4 pt-3">
			<div class="continaer">
				<div class="container d-flex justify-content-between px-2">
					<h4 align="center" class="mb-0 align-self-end">최신 리뷰</h4>
		       		<button type="button" class="btn btn-outline-warning" id="reviewBtn">리뷰 더보기 <i class="fas fa-chevron-right"></i> </button>
		       	</div>
		       	<div class="album py-3">          
					<div class="swiper-container" id="review"><!-- Swiper -->
						<div class="swiper-wrapper" id="reviewList">
							<c:if test="${map.reviews.size() > 0}">
							<c:forEach var="i" begin="0" end="${map.reviews.size()<10 ? map.reviews.size()-1:9}">
								<div class='card mb-4 swiper-slide box-shadow border border-0 p-2 op_card'>
			                  	<c:choose>
						       		<c:when test="${sessionScope.userId != null}">
						       			<c:set var="review_num">${map.reviews.get(i).getReview_num()}</c:set>
										<c:set var="check">${map.likeReviews[review_num]}</c:set>
				                  		<c:choose>
				                  			<c:when test="${check}">
					                  			<span class="box_likes">
									      				<a href="#" onclick="return false;" class="text-reset text-decoration-none">
									      					<i class="fas fa-heart text-danger reviewfullHeart"></i>
										    				<input type="hidden" value="${map.reviews.get(i).getReview_num()}">
									       				</a>
									       				<span>&nbsp;${map.reviews.get(i).getReview_likes()}</span>
												</span>
				                  			</c:when>
				                  			<c:otherwise>
					                  			<span class="box_likes">
									      				<a href="#" onclick="return false;" class="text-reset text-decoration-none">
									      					<i class="far fa-heart text-danger reviewemptyHeart"></i>
										    				<input type="hidden" value="${map.reviews.get(i).getReview_num()}">
									       				</a>
									       				<span>&nbsp;${map.reviews.get(i).getReview_likes()}</span>
												</span>
				                  			</c:otherwise>
				                  		</c:choose>
			                  		</c:when>
			                  		<c:otherwise>
							       		<span class="box_likes">
							       			<a href="#" onclick="return false;">
							       				<i class="far fa-heart text-danger reviewemptyHeart"></i>
								    			<input type="hidden" value="${map.reviews.get(i).getReview_num()}">
							       			</a>
							       			<span>&nbsp;${map.reviews.get(i).getReview_likes()}</span>									       					
							      		</span>
			                  		</c:otherwise>
			                  	</c:choose>
			                  	<c:choose>
			                  		<c:when test="${map.reviews.get(i).getReview_image() != null}">
										<a href='/review/reviewList?review_num=${map.reviews.get(i).getReview_num()}'  class="text-reset text-decoration-none">
						                	<img class="card-img-top rounded img_in_card" src="/reviewUpload/${map.reviews.get(i).getReview_image()}">                   		
						                </a>
			                  		</c:when>
			                  		<c:otherwise>
				            	      	<img class="card-img-top rounded img_in_card" src="/resources/img/default-image.png">
			                  		</c:otherwise>
			                  	</c:choose>
			                    <div class='row card-body px-0'>
			                        <div class="text_in_card">
			                        	<a class="text-reset text-decoration-none" href="/review/reviewList?review_num=${map.reviews.get(i).getReview_num()}">
			                        		${map.reviews.get(i).getReview_title()}
			                        	</a>
			                        </div>
			                        <input type='hidden' class='review_num' value='${map.reviews.get(i).getReview_num()}'>
								</div>
								</div>
					            </c:forEach>
					            </c:if>
			         		</div>
							<div class="swiper-button-next" id="review-next"></div>
							<div class="swiper-button-prev" id="review-prev"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- footer -->
<div class="footer pt-3">
	<!-- md사이즈 이상에서만 block -->
	<span class="mode2_memoco d-none d-md-block"><img src="/resources/img/memoco/memoco2.png" class="memoco2-size"></span>
	<!-- xs/sm 사이즈에서만 block -->
	<span class="memoco3 d-block d-md-none"><img src="/resources/img/memoco/memoco3.png" class="memoco-size"></span>	
	<span class="memoco4 d-block d-md-none"><img src="/resources/img/memoco/memoco4.png" class="memoco-size"></span>	
	<div>감사합니다. 언제나 열심히 하는 메모코가 되겠습니다.</div>
	<div class="text-jp pb-3">ご利用いただきありがとございます</div>
</div>


<c:choose>
	<c:when test="${not empty sessionScope.userId}">
		<input type="hidden" id="userId" value="${sessionScope.userId}">
	</c:when>
	<c:otherwise>
		<input type="hidden" id="userId" value="0">
	</c:otherwise>
</c:choose>
</body>
<script type='text/javascript' src="/resources/js/login.js"></script>
<script type='text/javascript' src="/resources/js/mode2-home.js"></script>
<!-- <script type='text/javascript' src="/resources/js/recipeLike.js"></script>
<script type='text/javascript' src="/resources/js/reviewLike.js"></script> -->
<script>
	$(function(){
		$("#insertRecipeBtn").on("click",function(){
			location.href="/recipe/insertRecipeForm"
		})
		$("#recipeBtn").on("click",function(){
			location.href="/recipe/recipeList"
		})
		$("#reviewBtn").on("click",function(){
			location.href="/review/reviewList"
		})
		$("body").on("change",".option",function(){
			var option = $(this).attr("id")
			//option1 : 레시피 검색
			/*
			<option value="recipe_title" selected="selected">음식 이름으로 검색</option>
			<option value="ingrd_name">음식 재료로 검색</option>
			*/
			//option2 : 리뷰 검색
			/*
			<option value="review_title" selected="selected">리뷰 제목으로 검색</option>
			<option value="review_content">리뷰 내용으로 검색</option>
			<option value="recipe_title">참조 레시피 제목으로 검색</option>
			<option value="recipe_ingrd">참조 레시피 재료으로 검색</option>	
			*/
			var output = "";
			if(option == 'option1'){

				output += '<option value="recipe_title" selected="selected">음식 이름으로 검색</option>'
				output += '<option value="ingrd_name">음식 재료로 검색</option>'

			}else if(option == 'option2'){
						
				output += '<option value="review_title" selected="selected">리뷰 제목으로 검색</option>'
				output += '<option value="review_content">리뷰 내용으로 검색</option>'
				output += '<option value="member_id">리뷰 작성자로 검색</option>'
				output += '<option value="recipe_title">따라한 레시피 제목으로 검색</option>'
				output += '<option value="recipe_ingrd">따라한 레시피 재료로 검색</option>'

			}

			$("#selected").html(output)
		})

		$("#search").on("submit",function(){
			var option = $(".option:checked").attr("id")
			//option1 레시피 검색
			//option2 리뷰 검색
			if(option == "option1"){

				$(this).attr("action","/recipe/recipeList")

				return true;
			}else if(option == "option2"){

				$(this).attr("action","/review/reviewList")

				return true;
			}
			
			return false;
		})
	})
</script>
<input type="hidden" id="currentPath" value="/mode2/home">

</html>