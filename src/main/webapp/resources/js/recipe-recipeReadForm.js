$(function(){
	slideView(1)
	
    var empty_class = "far fa-heart text-danger fa-lg c_pointer emptyHeart"
    var full_class = "fas fa-heart text-danger fa-lg c_pointer fullHeart"

	$("body").on("click",".fa-heart",function(){
		var $this = $(this)
    	var classes = $(this).attr("class")
    	var arr = classes.split(" ")
    	var empORfull = arr[arr.length-1]
    	var recipe_num = $this.next(":hidden").val()
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
        	}    		
    	}

	})
})
function goRecipeUpdateForm(recipe_num){
   
	location.href="/recipe/recipeUpdateForm?recipe_num="+recipe_num;
}

function goReviewList(recipe_num){
	
    location.href="/review/reviewList?recipe_num="+recipe_num;
}