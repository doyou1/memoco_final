var loginAjax = function(){
		$.ajax({
			url : "/member/login",
			type : "post",
			data : {"login_id": $("#login_id").val(),
					"login_pw" : $("#login_pw").val()
					},
			success : function(result){
				//로그인 완료
				if(result == 1){
					window.location.reload()
				} else if(result == 2){//아이디 틀림
					alert("아이디가 틀림")
				} else if(result == 3){
					alert("비밀번호가 틀림")
				}
			},error : function(){
				alert("error")
			}
		})
	}
	
	var loginCanEnter = false;
	//회원가입 A태그
	$("#joinModal").on("shown.bs.modal",function(){
		$("#member_id").focus();

	})
	 
	$("#loginModal").on("shown.bs.modal",function(){
		$("#login_id").focus();
		loginCanEnter = true;
	}).on("hide.bs.modal",function(){
		loginCanEnter = false;
	})
	
	$(document).keydown(function(event){
		
		if(event.keyCode == 13 && loginCanEnter){
			if($("#login_id").val() == "" || $("#login_id").val() == null){
				return;
			}else if($("#login_pw").val() == "" || $("#login_pw").val() == null){
				return;
			}

			loginAjax()
		}
	});

	$("#memberLogin").on("click",function(){
		if($("#login_id").val() == "" || $("#login_id").val() == null){
			return;
		}else if($("#login_pw").val() == "" || $("#login_pw").val() == null){
			return;
		}

		loginAjax()
	})
	
	
			
	//Google Login
	function googleLogin(url){
		window.location.href= url
	}
	//Kakao Login
	function kakaoLogin(url){
		window.location.href = url;
	}
	//Naver Login
	function naverLogin(url){
		window.location.href = url;
	}

	//Google Logout	
	function googleLogout(){
		var logout = window.open("https://accounts.google.com/logout")
		logout.close();
		
		location.href="/member/logout?currentPath=" +$("#currentPath").val();
	}
	//Kakao Logout
	function kakaoLogout(){
		var logout = window.open("https://developers.kakao.com/logout");
		logout.close();
		location.href="/member/logout?currentPath=" +$("#currentPath").val();	
	} 

	//Naver Logout
	function naverLogout(){
		var logout = window.open("http://nid.naver.com/nidlogin.logout")
		logout.close();
		location.href="/member/logout?currentPath=" +$("#currentPath").val();
	}
	
	function defaultLogout(){
		location.href="/member/logout?currentPath=" +$("#currentPath").val();
	}

	//ID중복확인을 위한 val
	var check = false;
	//ID중복확인
	$("#idCheck").click(function(){	
		if($("#member_id").val() == "" || $("#member_id").val() == null){
			alert("ID값입력해야 중복확인가능")
			return false;
		} else if($("#member_id").val().length < 3){
			alert("ID는 3글자 이상")
		}

		$.ajax({
			url: "/member/idCheck",
		    type:"post",
		    data: {"member_id":$("#member_id").val()},
		    dataType:"json",
		    success: function(result){
		    	if(result == 1){
		        //회원가입 실패
		        	$("#idCheckMsg").html("중복된 아이디입니다 다시 입력하세요");
		        }else{
		        //회원가입 성공
		        	$("#idCheckMsg").html("입력한 아이디는 사용하실 수 있습니다")
		            check = true;
		        }
			},
		    error : function(){
		    	alert("error");
		    }
		})       
	})