<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피</title>
   <meta name="viewport" content="width=device-width, initial-scale=1"> 
   <script src="/resources/js/jquery-3.4.1.min.js"></script>
   <script src="/resources/js/jquery.maphilight.js"></script>
   <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
   <script src="/resources/js/bootstrap.4.4.1.min.js"></script>
   <script src="/resources/js/bootstrap.min.js"></script>
   <script src="/resources/js/kakao.min.js"></script>
   <link rel="stylesheet" href="/resources/css/nav.css">
   <link rel="stylesheet" href="/resources/css/mode2.css">
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
   <link rel="stylesheet" href="/resources/css/all.min.css" /> <!-- font awesome 아이콘 -->
   
   <link href='http://fonts.googleapis.com/css?family=Playfair+Display:900,400|Lato:300,400,700' rel='stylesheet' type='text/css'>
   <style>
       
       h2 { text-align:center; }
       
       /*row부분 간격*/
       
       .mainbox {    /*이미지 박스*/
           margin:auto; 
           position:relative;   /*내부 버튼의 배치를 위해*/
       }
       
        .slideFrame{
       width: 100%;
       height: 450px;
       background-color: #FFDC3C;
       padding: 6%;
       border-radius: 50px;
       } 
        .slideFrame2{
      width: 800px;
       height: 450px;
      margin-left: 300px;
       margin-top: 150px;
       background-color: #FFDC3C;
       padding: 5.3%;
       border-radius: 50px;
        }
       .slide { height:auto; 
       display:inline-block; }  /*슬라이드 이미지*/
       
       .button {   /*좌우버튼 공통 스타일*/
        position:absolute;  /*.mainbox 내부에 맞춰 배치*/
        top:220px;  /*버튼위치-세로 중앙 배치*/
        transform:translateY(-50%);  /*버튼의 세로 위치가 이미지의 중앙에 맞도록 버튼의 세로 크기의 절반만큼 위로 이동*/
        
        border:none;  padding:15px;
        background-color:rgba(5,5,10,.3); color:#fff;
        font-size:2em;
        cursor:pointer; transition:.3s;
        border-radius: 22px;
       }
       .button:hover { color:#fff; background-color:#fd7e14; }
       
       .left { left:0 }           /*왼쪽버튼 위치-좌측*/ 
       .right { right:0 }    /*오른쪽버튼 위치-우측*/ 
       
       /*장면 전환용 애니메이션*/
       .animate1 { animation:opac 0.8s; }
       @keyframes opac {
           from { opacity:0; }  to { opacity:1; }
       }
       
       /*recipe title*/
       .listName{
          margin-top: 20px;
          margin-bottom: 20px;
          padding-left: 230px;
          padding-right: 200px;
          text-align: center;
       }
       
@font-face {
   font-family: 'Handon3gyeopsal600g';
      src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.2/Handon3gyeopsal600g.woff') format('woff');
      font-weight: normal;
      font-style: normal;
   }       
       .text-monospaces{
          font-family: 'Handon3gyeopsal600g';
          text-align: center;
          Emoji","Segoe UI Symbol","Noto Color Emoji"; */
          font-size: 1rem;
          font-weight: lighter;
          line-height: 1.5;
          color: #000;
           /* background-color: #c729b491;*/
          position: relative;
       }
       
       .text-monospace2{
          font-family: monospace;
          text-align: center;
          Emoji","Segoe UI Symbol","Noto Color Emoji"; */
          font-size: 1rem;
          font-weight: 500;
          line-height: 1.5;
          color: #000;
          top: 100px;
          width: 800px;
          position: relative;
       }
       
   @font-face {
      font-family: 'Handon3gyeopsal300g';
      src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.2/Handon3gyeopsal300g.woff') format('woff');
      font-weight: normal;
      font-style: normal;
   }
   
   	@font-face {
	   font-family: 'BBTreeGR';
	   src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/BBTreeGR.woff') format('woff');
	   font-weight: normal;
	   font-style: normal;
	} 
       /*슬라이드 comment*/
       .slideText{
          font-family: 'Handon3gyeopsal300g';
          padding-left: 50px;
          padding-right: 50px;
          font-size: 1.3em;
       }
       
       
      /*spoon position*/
      .s_size{
         width: 40px;
      height: 65px;
      transform: rotate(-15.27deg);
      }
    .spoon{
      position: absolute;   
      top: 10px;
      left: 25px;
    }
     /*fork position*/
     .f_size{
       width: 40px;
       height: 65px;
       transform: rotate(25deg);
     }
    .fork{
       position: absolute;
       top: 10px;
       right: 25px;
      
    }
    
    .table{
       margin-top:10px;
    }
    
     .table td {
    padding: .65rem;
    vertical-align: top;
    /* border-top: 1px solid #101a8f99; */
    }
     .table th {
    padding: .65rem;
    vertical-align: top;
    /* border-top: 1px solid #101a8f99; */
    }
    
    /*memocoCh position*/
    .memoco{
       position: absolute;
       width: 90px; 
       height:70px;
    }
    
    .table_div{
       position: relative;
    }
    
    .mainIngr{
       position: absolute;
	    top: 55px;
	    left: -20px;
    }
    /*heart review*/
    .spanLine{
       padding-left: 35px;
    }
    /*review Button*/
    .btn_yellow{
       padding: .175rem .25rem;
       background-color: #FFDC3C;
    }
    /*hearthover*/
    a:hover{
       text-decoration: none;
    }
    
     .table_div{
        position: relation;
     }
    
    

   thead{
      background-color: #a4e822;
   }
   
   /* xs/sm사이즈 */
   @media (max-width: 767px) {
		.memoco-span{
			position: absolute;
			top: -55px;
   		}
   		.t-1{
   			padding: 0px;
   		}
   }
   /* md이상 사이즈 */
   @media (min-width: 768px) {
		.memoco-span{
			position: absolute;
		    left: -20px;
			top: -50px;
   		}
   		.t-1{
   			padding: 0px 15px;
   		}
   }

  </style>
        
    
</head>
<body class="white_bg">
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

<div class="container-fluid readform-wrap">
   <!-- MONGODB RECIPE -->
   <c:if test="${recipe_num < 10000}">
   <div class="row py-3">
   	<div class="col-md-8 mt-2 mb-3 relative-box">
     	
     	<div class="text-center"><h4 class="d-inline">${recipe.recipe.getListname()}</h4></div>
   	</div>
   	<div class="col-md-4 mt-3 align-self-end text-right text-sm-center">
   		<div class="d-inline-block align-items-center">
            <c:choose>
               <c:when test="${likeRecipe}">
                  <span class="pr-2">
                     <a href="#" onclick="return false;">
                        <i class="fas fa-heart text-danger fa-lg c_pointer fullHeart"></i>
                          <input type="hidden" value="${recipe_num}">
                     </a>
                      <span>&nbsp;${recipe.recipe_likes}</span>                     
                  </span>               
               </c:when>
               <c:otherwise>
                  <span class="pr-2">
                     <a href="#" onclick="return false;">
                        <i class="far fa-heart text-danger fa-lg c_pointer emptyHeart"></i>
                          <input type="hidden" value="${recipe_num}">
                     </a>
                       <span>&nbsp;${recipe.recipe_likes}</span>
                  </span>                              
               </c:otherwise>
            </c:choose>
            <span class="pr-2">
               <i class="far fa-eye fa-lg">
               </i>
               <span>&nbsp;${recipe.recipe_hits}</span>
            </span>
            <span class="pr-2">
               <input type="button" value="리뷰쓰러가기 " class="btn btn_yellow" onclick="javascript:goReviewList('${recipe_num}')">
            </span>      
         </div>
   	</div>
   </div>

        <div class="row">
          <div class="col-md-8 t-1"><!-- style="top: 30px;" -->
           <div class="mainbox relative-box">  <!--슬라이드용 이미지박스-->
           	<span class="memoco-span"><img src="/resources/img/memoco/memoco4.png" class="memoco"></span>   
            <c:forEach var="i" begin="0" end="${recipe.recipe.getListcontent().size()-1}">
                <div class="slide animate1">
                	<div>
	                   <img class="slideFrame" src="${recipe.recipe.getListphoto().get(i)}" alt="${i} slide">
                	</div>
                   <div class="text-monospaces">
                         <span class="spoon"><img src="/resources/img/spoon.png" class="s_size"></span>
                         <span class="fork"><img src="/resources/img/fork.png" class="f_size"></span>
                         <div class="p-3"><div class="slideText">${recipe.recipe.getListcontent().get(i)}</div></div>
                   </div>
               </div>
             </c:forEach>
                <button class="button left" onclick="slideView(-1)">❮</button>
                <button class="button right" onclick="slideView(1)">❯</button>
           </div>
         </div>
         <div class="col-md-4 my-4 my-md-0 px-5 px-md-3">
         	<div class="relative-box pl-5 pr-5 pr-md-0">
		        <span class="mainIngr"><img src="/resources/img/tooltip2.png"></span>      
	            <table class="table">            
	               <thead>
	                   <tr>
	                      <th scope="col">재료명</th>
	                      <th scope="col">재료량</th>
	                   </tr>
	                 </thead>
	               <tbody>
	                 <c:forEach var="i" begin="0" end="${recipe.recipe.getListingrd().size()-1}">
	                  <c:choose>
	                  <c:when test="${i == 0}">
	                  <tr>
	                     <td>${recipe.recipe.getListingrd().get(i)}</td>
	                     <td>${recipe.recipe.getListamount().get(i)}</td>
	                  </tr>
	                  </c:when>
	                  <c:otherwise>
	                     <tr>
	                        <td>${recipe.recipe.getListingrd().get(i)}</td>
	                        <td>${recipe.recipe.getListamount().get(i)}</td>   
	                     </tr>
	                  </c:otherwise>
	               </c:choose>
	               </c:forEach>
	              </tbody>
	            </table>                  
	         </div>
         </div>
         </div>
      </c:if>                  
                                    
      <!-- 오라클DB RECIPE -->
      <c:if test="${recipe_num >= 10000}">
      	<div class="row py-3">
         <div class="col-md-8 mt-2 mb-3 relative-box">
         	<div class="text-center"><h4 class="d-inline">${recipe.recipe.getRecipe_title()}</h4></div>
         </div>
      	<div class="col-md-4 mt-3 align-self-end text-right text-sm-center">
      	  <div class="d-inline-block align-items-center">
      		<c:choose>
               <c:when test="${likeRecipe}">
                  <span class="pr-2">
                     <a href="#" onclick="return false;">
                        <i class="fas fa-heart text-danger fa-lg c_pointer fullHeart"></i>
                          <input type="hidden" value="${recipe_num}">
                     </a>
                       <span>&nbsp;${recipe.recipe.recipe_likes}</span>
                  </span>               
               </c:when>
               <c:otherwise>
                  <span class="pr-2">
                     <a href="#" onclick="return false;">
                        <i class="far fa-heart text-danger fa-lg c_pointer emptyHeart"></i>
                          <input type="hidden" value="${recipe_num}">
                     </a>
                       <span>&nbsp;${recipe.recipe.recipe_likes}</span>                     
                  </span>                              
               </c:otherwise>
            </c:choose>
            <span class="pr-2">
               <i class="far fa-eye fa-lg">
               </i>
               <span>&nbsp;${recipe.recipe.recipe_hits}</span>
            </span>
            <c:if test="${sessionScope.userId != null}">
            <span class="pr-2">
               <input type="button" value="리뷰쓰러가기 " class="btn btn_yellow" onclick="javascript:goReviewList('${recipe_num}')">
            </span>      
            </c:if>
            <c:if test="${recipe.recipe.member_id eq sessionScope.userId}">
               <input type="button" value="수정" class="btn btn_yellow" onclick="javascript:goRecipeUpdateForm('${recipe_num}')">
            </c:if>
      	  </div>
      	</div>
      	
      	</div>
         
         
         <div class="row">
            <div class="col-md-8 t-1">
            <div class="mainbox relative-box">  <!--슬라이드용 이미지박스-->
	          <span class="memoco-span"><img src="/resources/img/memoco/memoco4.png" class="memoco"></span>   
              <c:forEach var="i" begin="0" end="${recipe.recipeContents.size()-1}">
               <div class="slide animate1">
                  <c:choose>
                     <c:when test="${recipe.recipeContents.get(i).getRecipe_image() != null}">
                      <img class="slideFrame" src="/recipeUpload/${recipe.recipeContents.get(i).getRecipe_image()}" alt="${i} slide">
                     </c:when>
                     <c:otherwise>
                      <img class="slideFrame" src="/resources/img/default-image.png" alt="${i} slide">
                     </c:otherwise>
                  </c:choose>
                   <div class="text-monospaces">
                       <span class="spoon"><img src="/resources/img/spoon.png" class="s_size"></span>
                       <span class="fork"><img src="/resources/img/fork.png" class="f_size"></span>
                       <div class="p-3"><div class="slideText">${recipe.recipeContents.get(i).getRecipe_content()}</div></div>
                   </div>                             
               </div>
             </c:forEach>
               <button class="button left" onclick="slideView(-1)">❮</button>
               <button class="button right" onclick="slideView(1)">❯</button>
             </div>
             </div>
             
          <div class="col-md-4 my-4 my-md-0 px-5 px-md-3">
           <div class="relative-box pl-5 pr-5 pr-md-0">
            <span class="mainIngr"><img src="/resources/img/tooltip2.png"></span>
            <table class="table">            
              <thead>
                <tr>
                  <th scope="col">재료명</th>
                  <th scope="col">재료량</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach var="i" begin="0" end="${recipe.recipeIngrds.size()-1}">
               <c:choose>
                  <c:when test="${i == 0}">
                     <tr>
                        <td>${recipe.recipeIngrds.get(i).getIngrd_name()}</td>
                        <td>${recipe.recipeIngrds.get(i).getIngrd_amount()}</td>
                     </tr>
                  </c:when>
                  <c:otherwise>
                     <tr>
                        <td> ${recipe.recipeIngrds.get(i).getIngrd_name()}</td>
                        <td>${recipe.recipeIngrds.get(i).getIngrd_amount()}</td>
                     </tr>
                  </c:otherwise>
               </c:choose>
              </c:forEach>
              </tbody>
            </table>   
           </div>
          </div>
          </div>
      </c:if>
      <c:if test="${recipe_num < 0}">
         <h1>입력받은 RECIPE_NUM이 잘못됐습니다</h1>
      </c:if>

</div>
</body>
<script type='text/javascript' src="/resources/js/login.js"></script>
<script type='text/javascript' src="/resources/js/slider.js"></script>
<script type='text/javascript' src="/resources/js/recipe-recipeReadForm.js"></script>

 <input type="hidden" id="currentPath" value="/recipe/recipeReadForm">
 <c:choose>
   <c:when test="${not empty sessionScope.userId }">
      <input type="hidden" id="userId" value="${sessionScope.userId}">
   </c:when>
   <c:otherwise>
      <input type="hidden" id="userId" value="0">
   </c:otherwise>
</c:choose>
</html>