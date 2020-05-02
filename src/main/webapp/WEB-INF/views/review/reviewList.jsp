<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 게시판</title>

   <script src="/resources/js/jquery-3.4.1.min.js"></script>
   <script src="/resources/js/jquery.maphilight.js"></script>
   <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
   <script src="/resources/js/bootstrap.4.4.1.min.js"></script>
   <script src="/resources/js/bootstrap.min.js"></script>
   <script src="/resources/js/kakao.min.js"></script>
   <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed" rel="stylesheet">
   <link rel="stylesheet" href="/resources/css/nav.css">
   <link rel="stylesheet" href="/resources/css/album.css">

   <link rel="stylesheet" href="/resources/js/all.min.js" /> <!-- font awesome 아이콘 -->
   <link rel="stylesheet" href="/resources/css/all.min.css" /> <!-- font awesome 아이콘 -->
   <script src="/resources/js/sockjs.js"></script>
   <script src="/resources/js/stomp.js"></script>
<script type="text/javascript">
      $(function(){


    	  getPrevMessage()

         var inputRecipeNum = $("#inputReviewNum").val()
            
         var reviewNums = $(".forScroll")
            
            
         $(reviewNums).each(function(index,item){
            if(Number($(item).val()) == inputRecipeNum){
               var foundScrollVal = $(this).prev(".review_content").offset().top

               $('html,body').animate({
                  scrollTop : foundScrollVal-200
               },'slow')            
            }
         })
         

         if($("#userId").val().length > 1){
            connect();
            
            //새로고침시 웹소켓 disconnect
            document.onkeydown = function ( event ) {
                if ( event.keyCode == 116  // F5
                    || event.ctrlKey == true && (event.keyCode == 82) // ctrl + r
                ) {
                    //접속 강제 종료
                    disconnect();
                    // keyevent
                    event.cancelBubble = true; 
                    event.returnValue = false; 
                    setTimeout(function() {
                        window.location.reload();
                    }, 100);
                    return false;
                }
            }
         }

         $("#insert_title").click(function(){
            if(!($("#userId").val().length > 0)){
               alert("로그인해야 이용가능")
               $("#insert_title").blur();
               return false;
            }
         })
         
         $("#insert_content").click(function(){
            if(!($("#userId").val().length > 0)){
               alert("로그인해야 이용가능")
               $("#insert_content").blur();
               return false;
            }
         })

          $("#chat").focus(function(event){

            if($("#userId").val().length == 0){
               alert("로그인해야 이용가능")
               $("#chat").blur();
               return false;
            }else{
            	chatRoomDown()
            }
            document.onkeydown = function(event){
               //엔터 : 13
               //ctrl : 17
               if(event.keyCode == 13 && !(event.ctrlKey)){
                  sendMessage()
                  $("#chat").val("")
                  $("#chat").focus()
                  setTimeout(function(){
                	  chatRoomDown()
                  },500);
                  return false 
               }                
            }
          })

          var open_class = "fas fa-plus chatOpen"
      var close_class = "fas fa-minus chatClose"
      
      $("body").on("click",".fas",function(){

         var $this = $(this)
          var classes = $(this).attr("class")
          var arr = classes.split(" ")
          var plusORminus = arr[arr.length-1]

         if(plusORminus == "chatOpen"){
            $this.parent().parent().parent().next("div").css("display","block")
            $this.parent().parent().parent().next("div").next("textarea").css("display","block")
            $("#floatMenu").css("height","335px")
            $this.attr("class",close_class)
            chatRoomDown()
         }else if(plusORminus == "chatClose"){
            $this.parent().parent().parent().next("div").css("display","none")
            $this.parent().parent().parent().next("div").next("textarea").css("display","none")
            $("#floatMenu").css("height","auto")
            $this.attr("class",open_class)
         }
      })
          
          $("#review_content").focus(function(){

            if(!($("#userId").val().length > 0)){
               alert("로그인해야 이용가능")
               $("#review_content").blur();
               return false;
            }
          })
          
          $("#review_image").on("click",function(){
            $(this).next(":file").click()
          })

          $("body").on("focus",".reply_content",function(){

             if(!($("#userId").val().length > 0)){
               alert("로그인해야 이용가능")
               $(this).blur();
               return false;
            }
          })

          $("body").on("click",".insertReplyBtn",function(){
            //insertReply ajax
            
            var $this = $(this)
            var hidden = $this.prev(":hidden")
            var textarea = hidden.prev(".reply_content")
            
            
            $.ajax({
               url : "/review/insertReply",
               type : "post",
               dataType    : "json",
                 contentType : "application/x-www-form-urlencoded; charset=UTF-8",
               data : {
                     "review_num" : hidden.val(),
                     "reply_content" : textarea.val()
                  },
               success : function(item){

                  if(item != null){
                      var output = "<div class='row'>"
                     output += "<div class='col-md-1'>"+item.reply_num+"</div>"
                        output += "<div class='col-md-2'>"+item.member_id+"</div>"
                        output += "<div class='col-md-5'>"+item.reply_content+"</div>"
                        output += "<div class='col-md-2'>"

                        if(item.member_id == $("#userId").val()){
                          output += "<a href='#' class='replyUpdateBtn mr-2' onclick='return false;'><i class='fas fa-eraser'></i></a>"
                          output += "<a href='#' class='replyDeleteBtn mr-2' onclick='return false;'><i class='fas fa-trash-alt'></i></a>"
                      }
                     output += "</div>"
                     output += "</div>"
   
                     textarea.prev(".table").append(output)

                     textarea.val("")
                     textarea.focus()
                  }      
               },
               error : function(){
                  alert("error")
               }
            })   
          })
          
          $("body").on("click",".reviewUpdateBtn",function(){
            var $this = $(this)
            var temp = $this.parent().prev(".row").attr("class")
            var findParent = temp.split(" ").length;

            var img;
            var title;
            var content;
            var num;
            
            if(findParent > 1){
               img = $this.parent().prev(".row").prev(".row").children(".col-md-3").children("img")
                title = $this.parent().prev(".row").prev(".row").children(".col-md-6").children(".review_title").text()
                title = title.substring(8)
                content = $this.parent().prev(".row").prev(".row").children(".col-md-6").children(".review_content")
                num = $this.parent().prev(".row").prev(".row").children(".col-md-6").children(".review_num")
            }else{
               img = $this.parent().prev(".row").children(".col-md-3").children("img")
                title = $this.parent().prev(".row").children(".col-md-6").children(".review_title").text()
                title = title.substring(8)
                content = $this.parent().prev(".row").children(".col-md-6").children(".review_content")
                num = $this.parent().prev(".row").children(".col-md-6").children(".review_num")
            }
             
            
            if(img.attr("src") === undefined){
               
            }else{
               $(".updateImage").attr("src",img.attr("src"))
            }
            $("#update_title").val(title)
            $("#update_content").val(content.text())
            $("#update_num").val(num.val())


         })
         
                  
          $("body").on("click",".reviewDeleteBtn",function(){

            if(confirm("리뷰를 삭제하시겠습니까?")){
               var $this = $(this)
               var temp = $this.parent().prev(".row").attr("class")
               var findParent = temp.split(" ").length;
				var num;

				if(findParent > 1){
					num = $this.parent().prev(".row").prev(".row").children(".col-md-6").children(".review_num").val()
				}else{
					num = $this.parent().prev(".row").children(".col-md-6").children(".review_num").val()
				}
               
               $.ajax({
                  url : "/review/deleteReview",
                  type : "post",
                  data : {
                     "review_num" : num
                  },
                  success : function(result){

                     if(result > 0){


                         if(findParent > 1){

                             $this.parent().prev().prev().remove()
                             $this.parent().prev().remove()
                             $this.parent().next().next("hr").remove()
                             $this.parent().next().remove()
                             $this.parent().remove()

                         }else{
                             $this.parent().prev().remove()
                             $this.parent().next().next("hr").remove()
                             $this.parent().next().remove()
                             $this.parent().remove()

                         }                     
                     }
                  }
               })   
            }
      
          })

          var replyUpdateCheck = false;
          $("body").on("click",".replyUpdateBtn",function(){

            var $this = $(this)
            
            if(!replyUpdateCheck){
               var colmd5 = $this.parent().prev(".col-md-5")
               var content = $this.parent().prev(".col-md-5").text()
                        
               colmd5.html("<textarea class='form-control' style='width:100%'>"+content+"</textarea>")

               if($this.parent().next(".col-md-2").children(".replyUpdate").css("display")=='none'){
                  $this.parent().next(".col-md-2").children(".replyUpdate").css("display","inline")
                  $this.parent().next(".col-md-2").children(".replyUpdateCancle").css("display","inline")
               }

               replyUpdateCheck = true;
            }else{
               var textarea = $this.parent().prev(".col-md-5").children("textarea")
               var content = textarea.val()
               var reply_num = $this.parent().parent().children(".col-md-1").text()
            
               $.ajax({
                  url : "/review/replyUpdate",
                  type : "post",
                  data : {
                     "reply_num" : reply_num,
                     "reply_content" : content
                  },
                  dataType    : "json",
                  success : function(result){
                     if(result != null){
                        $this.parent().prev(".col-md-5").html(result.reply_content)
                     }
                  },
                  error : function(){
                     alert("error")
                  }
               })
                 
               
               replyUpdateCheck = false;

//               location.reload()
            }
            
          })
          
          $("body").on("click",".replyDeleteBtn",function(){
            var $this = $(this)
            if(confirm("리플을 삭제하시겠습니까?")){
               var reply_num = $this.parent().parent().children(".col-md-1").text()

               $.ajax({
                  url : "/review/replyDelete",
                  type : "post",
                  data : {
                     "reply_num" : reply_num
                  },
                  success : function(result){

                     if(result > 0){
                        $this.parent().parent().remove()
                     }else{
                        alert("실패 다시 시도하세요")
                     }
                  },
                  error : function(){
                     alert("error")
                  }
               })
            }
          })

         // 기존 css에서 플로팅 배너 위치(top)값을 가져와 저장한다.
         var floatPosition = parseInt($("#floatMenu").css('top'));
         // 250px 이런식으로 가져오므로 여기서 숫자만 가져온다. parseInt( 값 );

         $(window).scroll(function() {
            // 현재 스크롤 위치를 가져온다.
            var scrollTop = $(window).scrollTop();
            var newPosition = scrollTop + floatPosition + "px";
               /* 애니메이션 없이 바로 따라감
             $("#floatMenu").css('top', newPosition);
             */
               $("#floatMenu").stop().animate({
               "top" : newPosition
            }, 500);

         }).scroll();

         


         var check = true
         $(window).scroll(function(event) { 
            if(($(window).scrollTop()+10) >  $(document).height() - $(window).height()){ 
               
               if(check){
                  check = false;
                  event.preventDefault()
                  
                  var replyNums = [];

                  $(".review_num").each(function(index,item){
                     replyNums.push(Number(item.value))
                  })

                  var replyInputs = []
                  $(".replyInputs").each(function(index,item){
                     
                     replyInputs.push($(item).attr("id"))
                  })
                  
                  var lastId = replyInputs[replyInputs.length-1]
                  var lastNum = Number(lastId[lastId.length-1])
                  
                  var formData = new FormData()

                  formData.append("option",$("option:checked").val())
                  formData.append("searchText",$("#h_searchText").val())
                                 
                  $.ajax({
                     url : "/review/findReviews",
                     type : "post",
                     dataType    : "json",
                     data : formData,
                     contentType: false,
                     processData: false,
                     success : function(map){
                        console.log(map)
                        var reviews = map['reviews']
                        var likeReviews = map['likeReviews']
                        
                        $(reviews).each(function(index,item){
                        if(replyNums.indexOf(Number(item.review_num)) == -1){
                           var output = '<div class="row">'
                              output += '<div class="col-md-4">'
                              if(item.review_image != null){
                                 output += '<img src="/reviewUpload/'+item.review_image+'" width="250" height="160"> '
                              }else{
                                 output += '<img src="/resources/img/default-image.png" width="250" height="160"> '
                              }
                              output += '</div>'
                              output += '<div class="col-md-6" >'
                              output += "<p class='review_title'>"+item.review_title+"</p>"   
                              output += "<p class='review_content'>"+item.review_content+"</p>"
                              output += "<input type='hidden' class='review_num forScroll' value='"+item.review_num+"'>"
                               output += '</div>'
                              output += '<div class="col-md-2">'
                              output += "</div>"
                              output += '</div>'
                              output += '<div class="row justify-content-end" style="margin-top:2%;">'
                              if(item.member_id == $("#userId").val()){
                                 output += "<a class='js-scroll-trigger reviewUpdateBtn' href='/review/updateReviewForm' data-toggle='modal' data-target='#updateModal'>"
                                 output += "<i class='fas fa-eraser fa-2x'></i>"
                                 output += "</a>"
                                  
                                 output += "<a href='#' class='reviewDeleteBtn' onclick='return false;'>"
                                 output += "<i class='fas fa-trash-alt fa-2x'></i>"
                                 output += "</a>"
                              }
                              output += "<span class='printRevieLike'>좋아요수"+item.review_likes+"</span>"
                              if(likeReviews != null){
                                 if(likeReviews[Number(item.review_num)]){
                                    output += "<a href='#' class='reviewLike' onclick='return false;'>"
                                    output += "<img src='/resources/img/full.png' width='25px' height='25px' />"   
                                    output += "<input type='hidden' value='"+item.reivew_num+"'>"   
                                    output += "</a>"   
                                 }else {
                                    output += "<a href='#' class='reviewLike' onclick='return false;'>"
                                    output += "<img src='/resources/img/empty.png' width='25px' height='25px' />"   
                                    output += "<input type='hidden' value='"+item.reivew_num+"'>"   
                                    output += "</a>"
                                 }
                              }else{
                                 output += "<a href='#' class='reviewLike' onclick='return false;'>"
                                 output += "<img src='/resources/img/empty.png' width='25px' height='25px' />"   
                                 output += "<input type='hidden' value='"+item.reivew_num+"'>"   
                                 output += "</a>"
                              }
                              
                              output += "</div>"
                              output += "<div class='row' style='margin-top:2%;'>"
                              output += "<div class='col-md-12'>"
                              output += "<button class='btn btn-outline-success' style='width:100%;height:40px;' type='button' data-toggle='collapse' data-target='#replyInput"+(lastNum+1)+"' aria-expanded='false' aria-controls='replyInput"+(lastNum+1)+"'>"         
                              output += "<span class='fas fa-angle-double-down'>"
                              output += "</span>"
                              output += "</button>"
                              output += "</div>"
                              output += "<div class='collapse replyInputs col-md-12' id='replyInput"+(lastNum+1)+"'>"
                              output += "<div class='table'>"
                              output += "<div class='row'>"
                              output += "<div class='col-md-1'>번호</div>"
                              output += "<div class='col-md-2'>작성자</div>"
                              output += "<div class='col-md-5'>내용</div>"
                              output += "<div class='col-md-5'></div>"
                              output += "</div>"

                              $(item.reply).each(function(index,item){
                                 output += "<div class='row'>"
                                 output += "<div class='col-md-1'>"+item.reply_num+"</div>"
                                 output += "<div class='col-md-2'>"+item.member_id+"</div>"
                                 output += "<div class='col-md-5'>"+item.reply_content+"</div>"
                                 output += "<div class='col-md-2'>"
                                 if(item.member_id == $("#userId").val()){
                                    output += "<a href='#' class='replyUpdateBtn' onclick='return false;'>"
                                    output += "<i class='fas fa-eraser fa-2x'></i>"
                                    output += "</a>"

                                    output += "<a href='#' class='replyDeleteBtn' onclick='return false;'>"
                                    output += "<i class='fas fa-trash-alt fa-2x'></i>"
                                    output += "</a>"
                                 }
                                 output += "<div class='col-md-2'>"
                                 output += "</div>"
                                 output += "</div>"
                              })
                              output += "</div>"
                              output += "<textarea class='form-control reply_content' rows='6' name='reply_content' placeholder='리플내용'></textarea>"
                              output += "<input type='hidden' name='review_num' value='"+item.review_num+"'>"
                              output += "<input style='margin-left:93%;margin-top:15px;' class='btn btn-outline-success insertReplyBtn' type='button' value='등록'>"
                              output += "</div>"
                              output += "</div>"
                              output += "<hr>"
                                 
                              $("#reviewList").append(output)
                           }else{
                              console.log("이미있는 리뷰")
                           }
                        })



                        window.setTimeout(function(){
                           check = true;
                        },2000)
                        
                     },
                     error : function(){
                        alert("error")
                        window.setTimeout(function(){
                           check = true;
                        },2000)
                     }
                  })
               }
            }
         })

         var sel_file;
		$("body").on("change","#input_img",function(e){
		   
		       var file = e.target.files
		      var fileArr = Array.prototype.slice.call(file)
		      
		      var $this = $(this)
		      var imgTag = $(".contentImage")
		      
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
		
		$("body").on("change","#update_img",function(e){
		   
		       var file = e.target.files
		      var fileArr = Array.prototype.slice.call(file)
		      
		      var $this = $(this)
		      var imgTag = $(".updateImage")
		      
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
		
		$("#insertReviewForm").submit(function(){
		      if(!($("#insert_title").val().length > 0)){
		         alert("리뷰 제목을 입력하세요")
		         return false;
		      }else if(!($("#insert_content").val().length > 0)){
		         alert("리뷰 내용을 입력하세요")
		         return false;
		      }
		      
		})
   })
      
      var stompClient = null;
      
      //채팅방 연결
      function connect() {

         // WebsocketMessageBrokerConfigurer의 registerStompEndpoints() 메소드에서 설정한 endpoint("/endpoint")를 파라미터로 전달
         var socket = new SockJS('/endpoint');
         stompClient = Stomp.over(socket);
         stompClient.connect({}, function(frame) {
            
         // 메세지 구독
         // WebsocketMessageBrokerConfigurer의 configureMessageBroker() 메소드에서 설정한 subscribe prefix("/subscribe")를 사용해야 함
         stompClient.subscribe('/subscribe/chatRoom', function(message){
               var data = JSON.parse(message.body);
               var output;//${sessionScope.userId}
               if(data.id == $("#userId").val()){
                  output = "<div class='from-me'>"
                  output += "<small>"+data.username+"</small><br>"
                  output += data.message
                  output += "</div>"
                  output += "<div class='clear'></div>"
               }else{
                  output = "<div class='from-them'>"
                  output += "<small>"+data.username+"</small><br>"                     
                  output += data.message
                  output += "</div>"
                  output += "<div class='clear'></div>"
               }
               $("#chatting").append(output)            
            });
         });

         
      }
      
      //채팅 메세지 전달
      function sendMessage() {
         var str = $("#chat").val();
         str = str.replace(/ /gi, '&nbsp;')
         str = str.replace(/(?:\r\n|\r|\n)/g, '<br />');
         if(str.length > 0){
            // WebsocketMessageBrokerConfigurer의 configureMessageBroker() 메소드에서 설정한 send prefix("/")를 사용해야 함
            stompClient.send("/chatRoom", {}, JSON.stringify({
               message : str
            }));   
         }
      }
      
      // 채팅방 연결 끊기
      function disconnect() {
         stompClient.disconnect();
      }

      function getPrevMessage(){

         $.ajax({
            url : "/chat/getPrevMessage",
            type : "post",
            dataType    : "json",
            contentType: false,
            processData: false,
            success : function(msgs){
               console.log(msgs)
               var output;
               $(msgs).each(function(index,item){
                  if(item.id == $("#userId").val()){
                     output = "<div class='from-me'>"
                     output += "<small>"+item.username+"</small><br>"
                     output += item.message
                     output += "</div>"
                     output += "<div class='clear'></div>"
                  }else{
                     output = "<div class='from-them'>"
                     output += "<small>"+item.username+"</small><br>"                     
                     output += item.message
                     output += "</div>"
                     output += "<div class='clear'></div>"
                  }
                  $("#chatting").append(output)
               })
            }
            
         })
      } 

      function chatRoomDown(){
		$("#chatting").scrollTop($("#chatting")[0].scrollHeight)
      }

      function checkReivew(){
          if(!($("#review_title").val().length > 0)){
             alert("리뷰제목입력요망")
             return false;
          }else if(!($("#review_content").val().length > 0)){
             alert("리뷰내용입력요망")
             return false;
          }
       }
</script>
<style type="text/css">
   /* 전체 body bg-color(연한 회색) */
   body{
      background-color: #F9F9F9;
/*       padding-top: 80px; */
   }

/*메인 컨텐츠부분의 bg-color가 white인 부분*/
   /* col-xs/sm인(탭) 이하 */
   @media (max-width: 991px) {
      .mainContainer{
         background-color: #FFFFFF;
         width: 100%;
      }
   }
   /* col-md(모니터) 이상 */
   @media (min-width: 992px) {
      .mainContainer{
         background-color: #FFFFFF;
         width: 80%;
      }
   }
 
   /* 위 영역(.mainContainer)의 내부의 실제 컨텐츠 영역 */
      .content_ct{
      padding-top: 120px;
   }
 
   /* navbar의 bg-color */
   nav{
      background-color: #FFDC3C;
   }
   
/*    /* 로고 사이즈 */
/*    .navbar-brand img{ */
/*       width: 60%; */
/*    } */
   
   /* 커서 올리면 포인터로 바뀌게 하기 */
   .contentImage, .c_pointer {
      cursor: pointer;
   }
      </style>
   <style>
         /*제목*/
         .review_content{
            word-wrap:break-word;
            text-overflow: clip;
            padding : 20px 0 0 0;
         }
   
         /*참조레시피*/
         a{
            text-overflow:ellipsis;
            color: black;
         }

       
         .title{
            width: 434px;
            background-color: #ffc107cc;
          margin-right: 0px;
          margin-left: 700px;
         }
         
         .form_title{
         	margin-left:30px;
         }
         
         .submitMar{
            margin-right: 15px;
         }
         
         .submitBtn{
            margin-top: 20px;
         }
         
         /*리뷰 이미지*/
         .reviewImg{
            width: 250px;
            height: 200px;
         }
         
  
        #floatMenu {
          position: absolute;
         width: 300px;
/*         height: 335px; */
         left: 78%;
         top: 250px;
         background-color: #f8f9fa;
         color: black;
      }
      #floatMenu > div{
         width: 300px;
         height: 300px;
      }
      .clear {
        clear: both;
         height : 5px;
       
      }
      
      .from-me {
        position: relative;
        padding: 10px 20px;
        color: white;
        background: #0B93F6;
        border-radius: 25px;
        float: right;
      }
      .from-me:before {
        content: "";
        position: absolute;
        z-index: -1;
        bottom: -2px;
        right: -7px;
        height: 20px;
        border-right: 20px solid #0B93F6;
        border-bottom-left-radius: 16px 14px;
        -webkit-transform: translate(0, -2px);
      }
      .from-me:after {
        content: "";
        position: absolute;
        z-index: 1;
        bottom: -2px;
        right: -56px;
        width: 26px;
        height: 20px;
        background: white;
        border-bottom-left-radius: 10px;
        -webkit-transform: translate(-30px, -2px);
      }
      
      .from-them {
        position: relative;
        padding: 10px 20px;
        background: #E5E5EA;
        border-radius: 25px;
        color: black;
        float: left;
      }
      .from-them:before {
        content: "";
        position: absolute;
        z-index: 2;
        bottom: -2px;
        left: -7px;
        height: 20px;
        border-left: 20px solid #E5E5EA;
        border-bottom-right-radius: 16px 14px;
        -webkit-transform: translate(0, -2px);
      }
      .from-them:after {
        content: "";
        position: absolute;
        z-index: 3;
        bottom: -2px;
        left: 4px;
        width: 26px;
        height: 20px;
        background: white;
        border-bottom-right-radius: 10px;
        -webkit-transform: translate(-30px, -2px);
      }
      /*recipetitle ...*/
      #recipeName{
         width:240px;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
      }
      
      
      #update_title{
      width:100%
      }
      #update_content{
         width:100%
      }
      h1{
         text-align:center;
      }
      #insert_title{
         padding-top: 25px; 
         padding-bottom: 25px; 
         weight:790px; 
         height:30px;
      }
      #insert_img{
         display : none;
      }
      #insert_content{
         height: 200px;
      }
      #chatting{
         width:300px;
         heigth: 80%;
         background-color: skyblue;
      }
      
      .review_form{
   		border-radius: 10px;
      }
      
      .btn-wrap{
      	position: relative;
      }
      
      .sub_btn{
      	position: absolute;
      	right: 30px;
    	top: 10px;
      }
      .sub_btn2{
      	position: absolute;
      	right: 30px;
		margin-bottom : 35px;
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

   
<div id="updateModal" class="modal fade">
   <div class="modal-dialog modal-lg">
       <div class="modal-content">
          <div class="modal-body">
            <div class="container-fluid">
               <form action="/review/updateReview" method="post" encType="multipart/form-data">
               <div class="row">      
                       <div class="col-lg-6 col-sm-6 p-0">
                       <img class="rounded updateImage" width="100%" height="190"> 
                  </div>
                  <div class="col-lg-6 col-sm-6 p-0 content">
                     <input type="text" class="form-control" name="review_title" id="update_title" placeholder=" 리뷰 제목">
                      <textarea class="form-control" rows="6" name="review_content" id="update_content" class="update_content" 
                         placeholder="리뷰 내용"></textarea>   
                      <input type="hidden" name="review_num" id="update_num">            
                  </div>
                  <div class="col-lg-12 col-sm-12 p-0 image">
                     <input type="file" class="custom-file-input" name="upload" id="update_img" accept="image/gif,image/jpeg,image/png">
                         <label class="custom-file-label" for="validatedCustomFile">Choose file...</label>
                  </div>
               </div>
               <hr>
               <div class="row justify-content-end">
                      <input class="btn btn-outline-success" id="updateReview" type="submit" value="수정">
               </div>
               </form>
            </div>
          </div>
       </div>
   </div>
</div>
<!-- navbar 아래 컨텐츠 부분 -->
<div class="container-fluid mainContainer">
   <div class="container-fluid content_ct">
      <!-- 검색창 시작 -->
      <div class="search_ct pb-3">
         <!-- 검색창 부분 -->
         <form action="/review/reviewList" method="get">
         <div id="searchForm" class="my-3">
            <div class="form-row">
               <div class="col-md-4 mb-2">
                  <select class="custom-select custom-select-lg" id="selected" name="option">                  
                  	<c:choose>
                  		<c:when test="${map.option eq 'review_title'}">
                  		<option value="review_title" selected="selected">리뷰 제목으로 검색</option>
	                     <option value="review_content">리뷰 내용으로 검색</option>                  
	                     <option value="member_id">리뷰 작성자로 검색</option>
	                     <option value="recipe_title">따라한 레시피 제목으로 검색</option>
	                     <option value="recipe_ingrd">따라한 레시피 재료로 검색</option>
                  		</c:when>
                  		<c:when test="${map.option eq 'review_content'}">
                  		<option value="review_title">리뷰 제목으로 검색</option>
	                     <option value="review_content" selected="selected">리뷰 내용으로 검색</option>                  
	                     <option value="member_id">리뷰 작성자로 검색</option>
	                     <option value="recipe_title">따라한 레시피 제목으로 검색</option>
	                     <option value="recipe_ingrd">따라한 레시피 재료로 검색</option>
                  		</c:when>
                  		<c:when test="${map.option eq 'member_id' }">
                  		<option value="review_title">리뷰 제목으로 검색</option>
	                     <option value="review_content">리뷰 내용으로 검색</option>                  
	                     <option value="member_id" selected="selected">리뷰 작성자로 검색</option>
	                     <option value="recipe_title">따라한 레시피 제목으로 검색</option>
	                     <option value="recipe_ingrd">따라한 레시피 재료로 검색</option>
                  		</c:when>
                  		<c:when test="${map.option eq 'recipe_title'}">
                  		<option value="review_title">리뷰 제목으로 검색</option>
	                     <option value="review_content">리뷰 내용으로 검색</option>                  
	                     <option value="member_id">리뷰 작성자로 검색</option>
	                     <option value="recipe_title"  selected="selected">따라한 레시피 제목으로 검색</option>
	                     <option value="recipe_ingrd">따라한 레시피 재료로 검색</option>
                  		</c:when>
                  		<c:when test="${map.option eq 'recipe_ingrd'}">
                  		<option value="review_title">리뷰 제목으로 검색</option>
	                     <option value="review_content">리뷰 내용으로 검색</option>                  
	                     <option value="member_id">리뷰 작성자로 검색</option>
	                     <option value="recipe_title">따라한 레시피 제목으로 검색</option>
	                     <option value="recipe_ingrd" selected="selected">따라한 레시피 재료로 검색</option>
                  		</c:when>
                  		<c:otherwise>
                  		<option value="review_title" selected="selected">리뷰 제목으로 검색</option>
	                     <option value="review_content">리뷰 내용으로 검색</option>                  
	                     <option value="member_id">리뷰 작성자로 검색</option>
	                     <option value="recipe_title">따라한 레시피 제목으로 검색</option>
	                     <option value="recipe_ingrd">따라한 레시피 재료로 검색</option>
                  		
                  		</c:otherwise>
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
      </div>   <!-- .search_ct 검색창 끝 -->



   
   <h1><a href="/review/reviewList">리뷰게시판</a></h1>
   <div class="container-lg border border-warning review_form my-4">
   		<h3 class="pl-4 pt-4">리뷰 작성</h3>
      <form action="/review/insertReview" method="post" id="insertReviewForm" enctype="multipart/form-data">
         <div class="form-group p-2 ml-2 mb-1">
            <input id="insert_title" type="text" class="form-control form-control-lg none" name="review_title" placeholder="리뷰 제목을 입력해주세요">
         </div>
         <div id="reviewContent"class="p-2" >
            <div class="d-flex mb-3">
               <div class="p-2 mr-auto">
                  <img class="rounded contentImage" id="review_image" src="https://recipe1.ezmember.co.kr/img/pic_none2.gif" width="200" height="200">
                  <input id="input_img" type="file" style="display:none;"  name="upload" accept="image/gif,image/jpeg,image/png">
               </div>
               <div class="p-2 flex-grow-1">
                  <textarea id="insert_content" class="form-control" rows="7" class="review_content" name="review_content" placeholder="예) 제육볶음 맛있었어요~"></textarea>
               </div>
            </div>
         </div>
        <c:choose>
         <c:when test="${map.recipe != null }">
         <div class="row nameBorder box btn-wrap" >              
			<div class="row p-3 justify-content-between title form_title" >
				<a href="/recipe/recipeReadForm?listnum=${map.recipe.recipe_num}">
					<div id="recipeName"> [ ${map.recipe.recipe_name} ]</div>
				</a>
				<i class="far fa-hand-point-left p-1 fa-lg"></i>   보고 만들었어요
				<input type="hidden" name="recipe_num" value="${map.recipe.recipe_num}">
            </div>
            <span class="sub_btn">
	            <input class="btn btn-lg btn-outline-success" type="submit" value="리뷰 등록">
            </span>
        </div>
        </c:when>
        <c:otherwise>
         <div class="row box btn-wrap" >              
			<div class="row p-3 justify-content-between form_title" >
				
            </div>
            <span class="sub_btn2">
	            <input class="btn btn-lg btn-outline-success" type="submit" value="리뷰 등록">
            </span>
        </div>
        </c:otherwise>
		</c:choose>
         <div class="row justify-content-end" style="margin-top:2%;">
         </div>
      </form> 
   </div>

   
   <div class="container px-0 mt-5" id="reviewList">
      <c:if test="${map.reviews.size() > 0 }">
      <c:forEach var="i" begin="0" end="${map.reviews.size() < 10 ? map.reviews.size()-1 : 9}">
      <div class="">
         <div class='row'>
            <div class='col-md-3'>
               <c:choose>
                  <c:when test="${map.reviews.get(i).getReview_image() != null}">
                     <img src="/reviewUpload/${map.reviews.get(i).getReview_image()}" width='250' height='160'>
                  </c:when>
                  <c:otherwise>
                     <img class="reviewImg" src="/resources/img/default-image.png">
                  </c:otherwise>
               </c:choose>
            </div>
            <div class='col-md-6'>
               <div class='review_title'>리뷰 제목 : ${map.reviews.get(i).getReview_title()}</div>
               <p class='review_content col-8'>${map.reviews.get(i).getReview_content()}</p>
               <input type="hidden" class='review_num forScroll' value="${map.reviews.get(i).getReview_num()}">
            </div>
         </div>
           <c:if test="${map.reviews.get(i).recipe_num > 0}">
              <div class="row nameBorder box" >              
                     <div class="row p-3 justify-content-end title" >
					<a href="/recipe/recipeReadForm?listnum=${map.reviews.get(i).recipe_num}"><div id="recipeName"> [ ${map.reviews.get(i).recipe_name} ]</div></a> <i class="far fa-hand-point-left p-1 fa-lg"></i>   보고 만들었어요
               </div>
                 </div>
         </c:if>
         <div class='row justify-content-end' style='margin-top:2%;'>
            <c:if test="${map.reviews.get(i).getMember_id() eq sessionScope.userId}">
               <a class='js-scroll-trigger reviewUpdateBtn mr-3' href='/review/updateReviewForm' data-toggle='modal' data-target='#updateModal'>
                  <i class='fas fa-eraser fa-2x'></i>
               </a>
               <a href='#' class='reviewDeleteBtn mr-3' onclick='return false;'>
                  <i class='fas fa-trash-alt fa-2x'></i>
               </a>
               <input type="hidden" value="${map.reviews.get(i).getReview_num()}">
            </c:if>
           <%-- <span class='printReviewLike'>좋아요수${map.reviews.get(i).getReview_likes()}</span> --%>
               <c:set var="review_num">${map.reviews.get(i).getReview_num()}</c:set>
               <c:set var="check">${likeReviews[review_num]}</c:set>
            <c:choose>
               <c:when test="${check}">
                  <a href='#' class='reviewLike' onclick='return false;'>
                     <!-- <img src='/resources/img/full.png' width='25px' height='25px' /> -->
                     <i class="fas fa-heart fa-2x text-danger fullHeart"></i>
                     <input type='hidden' value="${map.reviews.get(i).getReview_num()}">   
                  </a>
               </c:when>
               <c:otherwise>
                  <a href='#' class='reviewLike mr-1' onclick='return false;'>
                  <!-- <img src='/resources/img/empty.png' width='25px' height='25px' /> -->
                  <i class="far fa-heart fa-2x text-danger emptyHeart"></i>
                  <input type='hidden' value="${map.reviews.get(i).getReview_num()}">
                  </a>            
               </c:otherwise>
            </c:choose>
            <h4><span class='printReviewLike mr-4'>${map.reviews.get(i).getReview_likes()}</span></h4>
               <c:set var="review_num">${map.reviews.get(i).getReview_num()}</c:set>
               <c:set var="check">${likeReviews[review_num]}</c:set>
         </div>                  
         <div class='row' style='margin-top:2%;'>
            <div class='col-md-12'>
               <button class='btn btn-outline-success' style='width:100%;height:40px;' type='button' data-toggle='collapse' data-target='#replyInput${i}' aria-expanded='false' aria-controls='replyInput${i}'>
                  <span class='fas fa-angle-double-down'>
                  </span>
               </button>
            </div>
            <div class='collapse replyInputs col-md-12' id='replyInput${i}'>
            <div class='table'>
               <div class='row'>
                  <div class='col-md-1'>번호</div>
                  <div class='col-md-2'>작성자</div>
                  <div class='col-md-5'>내용</div>
                  <div class='col-md-2'></div>
               </div>
               <c:forEach var="reply" items="${map.reviews.get(i).getReply()}">
               <div class='row'>
                  <div class='col-md-1'>${reply.getReply_num()}</div>
                  <div class='col-md-2'>${reply.getMember_id()}</div>
                  <div class='col-md-5'>${reply.getReply_content()}</div>
                  <div class='col-md-2'>
                     <c:if test="${sessionScope.userId eq reply.getMember_id()}">
                        <a href='#' class='replyUpdateBtn mr-2' onclick='return false;'><i class='fas fa-eraser'></i></a>
                          <a href='#' class='replyDeleteBtn mr-2' onclick='return false;'><i class='fas fa-trash-alt'></i></a>
                     </c:if>
                  </div>
                  <div class="col-md-2">
                     
                  </div>
               </div>
               </c:forEach>
             </div>
             <textarea class='form-control reply_content' rows='6' name='reply_content' placeholder='리플내용'></textarea>
            <input type='hidden' name='review_num' value="${map.reviews.get(i).getReview_num()}">
            <input style='margin-left:93%;margin-top:15px;' class='btn btn-outline-success insertReplyBtn' type='button' value='등록'>
            </div>
         </div>
      </div>
         <hr class="my-5">
      </c:forEach>   
      </c:if>      
   </div>
</div>
</div>


<div id="floatMenu">
   <h3>
      <span class="d-flex justify-content-start">
         <a href="#" onclick="return false;">
            <i class="fas fa-plus chatOpen"></i>
         </a>
      </span>
      <span class="d-flex justify-content-center">채팅방</span>
   </h3>

   <div id="chatting" style="overflow:auto;display:none;">
   
    </div>
    <textarea id="chat" rows="4" style="width:100%;display:none;"></textarea>
 </div>





</body>
<script src="/resources/js/login.js"></script>
<script src="/resources/js/reviewLike.js"></script>
<c:if test="${sessionScope.userId != null}">
	<input type="hidden" id="userId" value="${sessionScope.userId}">
</c:if>
	<input type="hidden" id="userId" value="${sessionScope.userId }">
	<input type="hidden" id="currentPath" value="/review/reviewList">
	<input type="hidden" id="inputReviewNum" value="${map.review_num}">
</html>