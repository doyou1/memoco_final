$(function(){
		var empty = "/resources/img/empty.png";
		var full = "/resources/img/full.png";
		$("body").on("click",".recipeLike",function(){
			var check = $(this).children("img")
			var recipe_num = $(this).children(":hidden").val()
			var count = $(this).prev("span")
			var recipe_likes = Number(count.text().split("/")[0])
			var recipe_hits = Number(count.text().split("/")[1])
			
			if($("#userId").val() == "0"){
				alert("좋아요 기능은 로그인해야 사용가능")
				return false;
			}else{
				if(check.attr("src") == empty){
					$.ajax({
						url : "/member/recipeLike",
						type : "post",
						data : {
								"recipe_num" : recipe_num,
								"check" : true	
						},
						success : function(result){
							if(result > 0){
								check.attr("src",full)
								count.html((recipe_likes+1)+"/"+recipe_hits)
							}
						},
						error : function(){
							alert("error")
						}
					})
				}else if(check.attr("src") == full){
					$.ajax({
						url : "/member/recipeLike",
						type : "post",
						data : {
								"recipe_num" : recipe_num,
								"check" : false	
						},
						success : function(result){
							if(result > 0){
								check.attr("src",empty)
								count.html((recipe_likes-1)+"/"+recipe_hits)
							}
						},
						error : function(){
							alert("error")
						}
					})
				}
			}
		})
	})