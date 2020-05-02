$(function(){
		var empty = "far fa-heart fa-2x text-danger emptyHeart";
		var full = "fas fa-heart fa-2x text-danger fullHeart";
		$("body").on("click",".reviewLike",function(){
			var $this = $(this)
			var check = $(this).children("i").attr("class")
			check = check.split(" ")
			check = check[check.length-1]
			
			var review_num = $(this).children(":hidden").val()
			
			var formData = new FormData()
			formData.append("review_num",review_num)
			
			if(check == "emptyHeart"){
				formData.append("check",true)
			}else if(check == "fullHeart"){
				formData.append("check",false)
			}


			if(!($("#userId").val().length > 1)){
				alert("좋아요 기능은 로그인해야 사용가능")
				return false;
			}else{
				$.ajax({
					url : "/member/reviewLike",
					type : "post",
					contentType: false,
					processData: false,
					dataType    : "json",
					data : formData
					,success : function(result){
							if(result == 1){
								$this.children("i").attr("class",full)
								var reviewLikeText = $this.next("h4").children(".printReviewLike").text()
								
								var likes = Number(reviewLikeText)
								likes = likes + 1
								
								$this.next("h4").children(".printReviewLike").text(likes)
							}else if(result == 2){
								$this.children("i").attr("class",empty)
								var reviewLikeText = $this.next("h4").children(".printReviewLike").text()
								
								var likes = Number(reviewLikeText)
								likes = likes - 1
								
								$this.next("h4").children(".printReviewLike").text(likes)
							}
					},
					error : function(){
							alert("error")
					}
				})
			}

		})
	})