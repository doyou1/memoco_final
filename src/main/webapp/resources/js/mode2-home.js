    /* list의 card부분 img에 마우스를 올렸을 때 불투명도 속성을 추가하는 함수 */
    $("body").on("mouseover",".op_card", function() {
       $(this).css("opacity", "0.7");
    })
         
    $("body").on("mouseout",".op_card", function() {
       $(this).css("opacity", "1");
    })

	var empty_class = "far fa-heart text-danger emptyHeart"
    var full_class = "fas fa-heart text-danger fullHeart"
    
   	var review_empty_class = "far fa-heart text-danger reviewemptyHeart"
	var review_full_class = "fas fa-heart text-danger reviewfullHeart"
    	
    $("body").on("click",".fa-heart",function(){
    	var $this = $(this)
    	var classes = $(this).attr("class")
    	var arr = classes.split(" ")
    	var empORfull = arr[arr.length-1]
    	var recipe_num = $this.next(":hidden").val()
    	var review_num = $this.next(":hidden").val()
    	
    	var likesCount = Number($this.parent().next("span").text())

    	if(!($("#userId").val().length > 1)){
    		alert("좋아요 기능은 로그인해야 사용가능")
			return false;
    	}else{
        	if(empORfull == "emptyHeart"){
        		$.ajax({
					url : "/member/recipeLike",
					type : "post",
					data : {
							"recipe_num" : recipe_num,
							"check" : true	
					},
					success : function(result){
						if(result > 0){
							$this.attr("class",full_class)
							$this.parent().next("span").html("&nbsp;"+(likesCount+1))
						}
					},
					error : function(){
						alert("error")
					}
				})
        	}else if(empORfull == "fullHeart"){
        		$.ajax({
					url : "/member/recipeLike",
					type : "post",
					data : {
							"recipe_num" : recipe_num,
							"check" : false	
					},
					success : function(result){
						if(result > 0){
							$this.attr("class",empty_class)
							$this.parent().next("span").html("&nbsp;"+(likesCount-1))
						}
					},
					error : function(){
						alert("error")
					}
				})
        	}else if (empORfull == "reviewemptyHeart"){
        		$.ajax({
					url : "/member/reviewLike",
					type : "post",
					data : {
							"review_num" : review_num,
							"check" : true	
					},
					success : function(result){
						if(result > 0){
							$this.attr("class",review_full_class)
							$this.parent().next("span").html("&nbsp;"+(likesCount+1))
						}
					},
					error : function(){
						alert("error")
					}
				})
        	}else if (empORfull == "reviewfullHeart"){
        		$.ajax({
					url : "/member/reviewLike",
					type : "post",
					data : {
							"review_num" : review_num,
							"check" : false	
					},
					success : function(result){
						if(result > 0){
							$this.attr("class",review_empty_class)
							$this.parent().next("span").html("&nbsp;"+(likesCount-1))
						}
					},
					error : function(){
						alert("error")
					}
				})
        	} 		
    	}
   
    })
