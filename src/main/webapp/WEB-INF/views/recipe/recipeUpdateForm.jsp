<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피 수정 폼</title>
	<script src="/resources/js/jquery-3.4.1.min.js"></script>
	<script src="/resources/js/jquery.maphilight.js"></script>
	<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
	<script src="/resources/js/bootstrap.4.4.1.min.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/kakao.min.js"></script>
	<link rel="stylesheet" href="/resources/css/nav.css">
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
   <link rel="stylesheet" href="/resources/css/nav.css">
   <link rel="stylesheet" href="/resources/css/all.min.css" /> <!-- font awesome 아이콘 -->
 

	<script>
		$(function(){
			
			/* x버튼이 나오고 사라지는 부분 */
			$(document).on("mouseover",".inputDiv", function() {
				$(this).find(".btn-del").css("display", "inline-block");
			})
			
			$(document).on("mouseout",".inputDiv", function() {
				$(this).find(".btn-del").css("display", "none");
		 	})
		 	
		 	
		})
	</script>
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
      <div class="modal-content">
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
           
            <input type="submit" value="Join" id="memberJoin" class="btn btn-success">
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
​
<!-- 메인컨텐츠의 bg-color:white로 지정된 영역 -->
<div class="container mainContainer">
	<!-- 메인컨텐츠의 실제 컨텐츠 영역 -->
	<div class="container-fluid content_ct">
		<div class="row">
			<h2 class="divTitle">레시피 수정</h2>
		</div>
		<!-- 게시글 form태그 -->
		<form action="/recipe/updateRecipe" method="post" enctype="multipart/form-data">
			<!-- 레시피(요리) 이름 / 작성자(hidden폼) div -->
			<div class="form-group">
				<input type="text" class="form-control form-control-lg none" id="recipe_title" name="recipe_title" value="${recipe.recipe.recipe_title}" placeholder="요리 이름을 입력해주세요">
				<input type="hidden" value="${recipe.recipe.getRecipe_num()}" name="recipe_num">
				<input type="hidden" value="${recipe.recipeIngrds.size()}" name="oldIngrdCount">
				<input type="hidden" value="${recipe.recipeContents.size()}" name="oldContentCount">
			</div>
			<hr>	<!-- 요리제목과 재료 사이의 구분선 -->
​
			<!-- 제목 아래 재료/조리과정 입력 부분 -->
			<div>
				<!-- 재료 입력 form-group (기존 재료 table) -->
				<div id="recipeIngrd">
					<c:forEach var="i" begin="0" end="${recipe.recipeIngrds.size()-1}">
						<c:if test="${i == 0}">
						<!-- 주재료 부분 -->
						<div class="form-row row justify-content-center inputDiv recipeIngrd">
							<input type="hidden" value="${i}" name="ingrd_num" class="ingrd_num">
							<label for="recipeIngrdLabel" class="col-md-1 col-form-label">주재료</label>
			    			<div class="col-6">
						        <input type="text" class="form-control ingrd_name" placeholder="예) 돼지고기" name="ingrd_name" value="${recipe.recipeIngrds.get(i).getIngrd_name()}">
						    </div>
						    <div class="col-3">
						    	<input type="text" class="form-control ingrd_amount" placeholder="예) 300g" name="ingrd_amount" value="${recipe.recipeIngrds.get(i).getIngrd_amount()}">
						    </div>
	 					    <div class="col-1 fa-i">
						    </div>		 
						</div>
						</c:if>
						<c:if test="${i != 0}">
						<!-- 부재료 부분 -->
						<div class="form-row row justify-content-center inputDiv recipeIngrd">
							<input type="hidden" value="${i}" name="ingrd_num" class="ingrd_num">
							<label for="recipeIngrdLabel" class="col-md-1 col-form-label">재료</label>
			    			<div class="col-6">
						        <input type="text" class="form-control ingrd_name" placeholder="예) 돼지고기" name="ingrd_name" value="${recipe.recipeIngrds.get(i).getIngrd_name()}">
						    </div>
						    <div class="col-3">
						    	<input type="text" class="form-control ingrd_amount" placeholder="예) 300g" name="ingrd_amount" value="${recipe.recipeIngrds.get(i).getIngrd_amount()}">
						    </div>
						    <div class="col-1 fa-i">
						    	<i class="fas fa-times ingrdRemoveBtn" ></i>
						    </div>		
						</div>	
						</c:if>
					</c:forEach>
				</div><!-- 재료 입력 form-group (기존 재료 table) 끝 -->
				
				<!-- 재료추가 버튼 -->
				<div class="row justify-content-center">
					
					<p class="addIngrd" id="ingrdAddBtn"><i class="fas fa-plus-circle"></i></p>
				</div>
				
				<hr>	<!-- 재료와 조리순서 사이의 구분선 -->
				
				<!-- 조리과정 입력 form-group (기존 레시피내용/사진 table) -->
				<fieldset disabled>
					<div class="form-group">
						<input type="text" class="form-control form-control-lg none" placeholder="요리 과정" readonly="readonly">
					</div>
				</fieldset>

				<div id="recipeContent">
					<c:forEach var="i" begin="0" end="${recipe.recipeContents.size()-1}">
					<div class="form-row row justify-content-center inputDiv contentText">
						<input type="hidden" value="${i}" class="content_num" name="content_num">
						<label for="recipeContentLabel" class="col-md-1 col-form-label labelForm">Step${(i+1)}</label>
						<input type="file" class="inputImage" name="recipe_image" style="display:none;">
						<div class="col-sm-3 col-md-2 inputImageDiv">
							<c:choose>
			      		<c:when test="${recipe.recipeContents.get(i).getRecipe_image() != null}">
					    	<img class="rounded contentImage" src="/recipeUpload/${recipe.recipeContents.get(i).getRecipe_image()}" width="160" height="160">	      		
			      		</c:when>
			      		<c:otherwise>
							<img class="rounded contentImage" src="/resources/img/default-image.png" width="160" height="160">
			      		</c:otherwise>
			      	</c:choose>	
						</div>
						<div class="col-sm-6 col-md-7">
							<textarea class="form-control" rows="6" class="recipe_content" name="recipe_content" placeholder="예) 닭고기를 한입 크기로 썰어주세요.">${recipe.recipeContents.get(i).getRecipe_content()}</textarea>
						</div>
					    <div class="col-1 fa-i">
					    	<i class="fas fa-times contentRemoveBtn" ></i>
					    </div>	
					</div>
					</c:forEach>
				</div>

				<div class="row justify-content-center">
					<p class="addContent" id="contentAddBtn"><i class="fas fa-plus-circle"></i> 추가</p>
					<!-- 조리과정추가 버튼 클래스나 아이디 아직 추가 안했습니다 -->
				</div>				
				
			</div>
		<input class="btn btn-primary" type="submit" value="수정">
		</form>
	</div>	<!-- 메인컨텐츠 영역 끝 div.container-fluid.content_ct -->
</div>	<!-- 내비 아래div.container.mainContainer (bg-color:white인 부분) -->


<c:choose>
	<c:when test="${not empty sessionScope.userId }">
		<input type="hidden" id="userId" value="1">
	</c:when>
	<c:otherwise>
		<input type="hidden" id="userId" value="0">
	</c:otherwise>
</c:choose>
<script>
//재료 추가 function
$("#ingrdAddBtn").on("click",function(){
	var ingrd_num = Number($(".ingrd_num").length)
	var output = 
			  "<div class='form-row row justify-content-center inputDiv recipeIngrd'>"
	output += "		<input type='hidden' value='"+(ingrd_num)+"' name='ingrd_num' class='ingrd_num'>"
	output += "		<label for='recipeIngrdLabel' class='col-md-1 col-form-label'>재료</label>"
	output += "			<div class='col-6'>"
	output += "				<input type='text' class='form-control' placeholder='예) 돼지고기' name='ingrd_name'>"						
    output += "			</div>"
	output += "			<div class='col-3'>"
	output += "				<input type='text' class='form-control' placeholder='예) 300g' name='ingrd_amount'>"				    
    output += "			</div>"	
    output += "			<div class='col-1 fa-i'>"
	output += "				<i class='fas fa-times ingrdRemoveBtn'></i>"
	output += "			</div>"
	output += "</div>"
	
	$("#recipeIngrd").append(output)
})

//재료 제거 function		
$("body").on("click",".ingrdRemoveBtn",function(event){
	$(event.target).parent().parent().remove()
	
	var ingrd_div = $("#recipeIngrd").children(".recipeIngrd")
			
	var ingrd_num = 0;
	ingrd_div.each(function(index,item){
		$(item).children(":hidden").val(ingrd_num)
		ingrd_num += 1
	})
})

//과정 추가 function
$("#contentAddBtn").on("click",function(){
	var content_num = Number($(".content_num").length)
	var output ="<div class='form-row row justify-content-center inputDiv contentText'>"
	output += "<input type='hidden' value='"+(content_num)+"' class='content_num' name='content_num'>"
	output += "<label for='recipeContentLabel' class='col-md-1 col-form-label labelForm'>Step"+(content_num+1)+"</label>"
	output += "<input type='file' name='recipe_image' class='inputImage' style='display:none;''>"
	output += "<div class='col-sm-3 col-md-2 inputImageDiv'>"
	output += "<img class='rounded contentImage' src='https://recipe1.ezmember.co.kr/img/pic_none2.gif'  width='160' height='160'>"
	output += "</div>"
	output += "<div class='col-sm-6 col-md-7'>"
	output += "<textarea class='form-control' rows='6' name='recipe_content' placeholder='예) 닭고기를 한입 크기로 썰어주세요.''></textarea>"
	output += "</div>"
	output += "<div class='col-1 fa-i'>"
    output += "<i class='fas fa-times contentRemoveBtn' ></i>"
   	output += "</div>"
    output += "</div>"

	$("#recipeContent").append(output)

})

//과정 제거 function
$("body").on("click",".contentRemoveBtn",function(event){
	$(event.target).parent().parent().remove()

	var content_div = $("#recipeContent").children()
	
	var content_num = 0
	content_div.each(function(index,item){
		$(item).children(".content_num").val(content_num)
		$(item).children("label").text("Step"+(content_num+1))
		content_num += 1
	})

})

//숨겨진 :fileTag 클릭 function
$("body").on("click",'.inputImageDiv', function(){
	$(this).prev(":file").click()
});

//Image 미리보기 function
var sel_file;
$("body").on("change",".inputImage",function(e){
	
 		var file = e.target.files
		var fileArr = Array.prototype.slice.call(file)
		
		var $this = $(this)
		var imgTag = $(this).next().children("img")
		
		fileArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("확장자는 이미지 확장자만 가능합니다")
				$this.val("")
				return;
			}

			sel_file = f

			var reader = new FileReader()
			reader.onload = function(e){
			imgTag.attr("src",e.target.result);
					
			}
			
			reader.readAsDataURL(f)
		})
})

$("body").on("submit",function(){
	var recipe_title = $("#recipe_title")
	var ingrd_num = $(".ingrd_num")
	var ingrd_name = $(".ingrd_name")
	var ingrd_amount = $(".ingrd_amount")
	
	var content_num = $(".content_num")
	var recipe_image = $(".inputImage")
	var recipe_content = $(".recipe_content")
	
	if(recipe_title.val().length <= 0){
		alert("요리제목 입력!")
		return false
	} else if(ingrd_num.length == 0){
		alert("요리재료 입력!")
		return false
	} else if(content_num.length == 0){
		alert("요리과정 입력!")
		return false
	} 
	

})


</script>
</body>
 <input type="hidden" id="currentPath" value="/recipe/recipeUpdateForm">
</html>