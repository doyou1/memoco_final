$(function(){
	
	//주재료 바꿨을시
	$("body").on("change",".mainIngrd",function(event){
		var checkboxs = $(".mainIngrd");
		var subFS = $("#subFS");
		subFS.html("")
		var output = '<span class="r_rable">부재료</span>'
		for(var i = 0; i<checkboxs.length; i++){
			if(checkboxs[i] != event.target){
	            checkboxs[i].checked = false;
				ingrdName = checkboxs[i].value;
				output += '<span class="pr-4">'
	            output += ingrdName + " <input type='checkbox' class='subIngrd' value='"+ingrdName+"'></span>"
	        }else{
	        	 checkboxs[i].checked = true;
	        }
		}
		subFS.html(output)
		$("#recipeList").html("")
		findRecipesByMode1()
	})

	$("body").on("change","#subFS",function(){
		$("#recipeList").html("")
		findRecipesByMode1()
	})

	$("body").on("click",".viewBtn",function(){
		var $this = $(this)
		var recipe_num = $this.parent().parent().prev(".recipe_num").val()
		location.href='/recipe/recipeReadForm?listnum='+recipe_num
	})

	$("#insertRecipeBtn").on("click",function(){
		location.href="/recipe/insertRecipeForm"
	})

	/* list의 card부분 img에 마우스를 올렸을 때 불투명도 속성을 추가하는 함수 */
    $("body").on("mouseover",".op_card", function() {
    	$(this).css("opacity", "0.7");
    })
         
    $("body").on("mouseout",".op_card", function() {
    	$(this).css("opacity", "1");
    })
    
    var empty_class = "far fa-heart text-danger emptyHeart"
    var full_class = "fas fa-heart text-danger fullHeart"
    
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
    
    $("#insertRecipeBtn").on("click",function(){
		location.href="/recipe/insertRecipeForm"
	})

	
	var check = true	
	//스크롤에 따라 레시피 추가메서드
	$(window).scroll(function(event) { 
		if(($(window).scrollTop()+10) >  $(document).height() - $(window).height()){ 
			if(check){
				event.preventDefault()

				if($("#type").val() == 'mode1'){
					findRecipesByMode1()
				}else if($("#type").val() == 'mode2'){
					scrollFindRecipesByMode2()
				}

	 		}
	
		} 
	})

})





function findRecipesByMode1(){
	check = false;
	var recipeNums = [];
	
	$(".recipe_num").each(function(index,item){
			recipeNums.push(Number(item.value))
	})
	var mainFood = $(".mainIngrd:checked").val()
	var subIngrds = [];
	
	$(".subIngrd:checked").each(function(index,item){
		subIngrds.push($(item).val())
	})

	var formData = new FormData();
	formData.append("mainFood",mainFood)
	if(subIngrds.length > 0){
		formData.append("subFoods",subIngrds)
	}
	$.ajax({
		url : "/recipe/findRecipesByMode1",
		type : "post",
		dataType    : "json",
		contentType: false,
		processData: false,
		data : formData,
		success : function(map){
			var likeRecipe = map.likeRecipes
			var recipes = map.recipes
			var countRecipe = map.countRecipes
			
			var cnt = 0;
			$(recipes).each(function(index,item){
				if(recipeNums.indexOf(Number(item.recipe_num)) == -1){		
					if(cnt == 10) return;
					var output = "<div class='col-md-4'>"
						output += "<div class='card mb-4 box-shadow border border-0 p-2 op_card'>"
						if(likeRecipe != null){
							//로그인해서 좋아하는 Recipe가 있을때
							if(likeRecipe[item.recipe_num]){
								output += "<span class='box_likes'>"
								output += "		<a href='#' onclick='return false;' class='text-reset text-decoration-none'>"
								output += "			<i class='fas fa-heart text-danger fullHeart'></i>"
								output += "			<input type='hidden' value='"+item.recipe_num+"'>"
								output += "		</a>"
								output += "		<span>&nbsp;"+item.recipe_likes+"</span>"
								output += "</span>"
							}else{
								output += "<span class='box_likes'>"
								output += "		<a href='#' onclick='return false;' class='text-reset text-decoration-none'>"
								output += "			<i class='far fa-heart text-danger emptyHeart'></i>"
								output += "			<input type='hidden' value='"+item.recipe_num+"'>"
								output += "		</a>"
								output += "		<span>&nbsp;"+item.recipe_likes+"</span>"
								output += "</span>"
							}
						}else{
							output += "<span class='box_likes'>"
							output += "		<a href='#' onclick='return false;' class='text-reset text-decoration-none'>"
							output += "			<i class='far fa-heart text-danger emptyHeart'></i>"
							output += "			<input type='hidden' value='"+item.recipe_num+"'>"
							output += "		</a>"
							output += "		<span>&nbsp;"+item.recipe_likes+"</span>"
							output += "</span>"
						}			
						output += "<a class='text-reset text-decoration-none' href='/recipe/recipeReadForm?listnum="+item.recipe_num+"'>"
						if(Number(item.recipe_num) >= 10000){
							output += "<img class='card-img-top rounded in_card' src='/recipeUpload/"+item.recipe_photo+"'>"
						}else if(Number(item.recipe_num) < 10000){
							output += "<img class='card-img-top rounded in_card' src='"+item.recipe_photo+"'>"
						}
						output += "</a>"
						output += "<div class='row card-body auto_w'>"
						output += "<div class='in_card'>"
						output += "<a class='text-reset text-decoration-none' href='/recipe/recipeReadForm?listnum="+item.recipe_num+"'>"
						output += item.recipe_name
						output += "</a>"
						output += "</div>"
						output += "<input type='hidden' class='recipe_num' value='"+item.recipe_num+"'>"
				        output += "</div>"    
				        output += "</div>"    
				        output += "</div>"
					        
					$("#recipeList").append(output);
					cnt++;
				}
			})


			window.setTimeout(function(){
				check = true;				
			}, 2000);

		},
		error : function(){
			alert("error")
			window.setTimeout(function(){
				check = true;				
			}, 2000);
		}
	})
}

function scrollFindRecipesByMode2(){

	check = false;
	var recipeNums = [];
	
	$(".recipe_num").each(function(index,item){
			recipeNums.push(Number(item.value))
	})

	var formData = new FormData()

	formData.append("option",$("#h_option").val())
	formData.append("searchText",$("#h_searchText").val())
	$.ajax({
		url : "/recipe/findRecipesByMode2",
		type : "post",
		data : formData		,
		contentType: false,
		processData: false,
		dataType    : "json",
		success : function(map){

				var likeRecipe = map.likeRecipes
				var recipes = map.recipes
				var countRecipe = map.countRecipes

				var cnt = 0;
				$(recipes).each(function(index,item){
					if(recipeNums.indexOf(Number(item.recipe_num)) == -1){
						if(cnt == 10) return;
						var output = "<div class='col-md-4'>"
							output += "<div class='card mb-4 box-shadow border border-0 p-2 op_card'>"
							if(likeRecipe != null){
								//로그인해서 좋아하는 Recipe가 있을때
								if(likeRecipe[item.recipe_num]){
									output += "<span class='box_likes'>"
									output += "		<a href='#' onclick='return false;'  class='text-reset text-decoration-none'>"
									output += "			<i class='fas fa-heart text-danger fullHeart'></i>"
									output += "			<input type='hidden' value='"+item.recipe_num+"'>"
									output += "		</a>"
									output += "		<span>&nbsp;"+item.recipe_likes+"</span>"
									output += "</span>"
								}else{
									output += "<span class='box_likes'>"
									output += "		<a href='#' onclick='return false;' class='text-reset text-decoration-none'>"
									output += "			<i class='far fa-heart text-danger emptyHeart'></i>"
									output += "			<input type='hidden' value='"+item.recipe_num+"'>"
									output += "		</a>"
									output += "		<span>&nbsp;"+item.recipe_likes+"</span>"
									output += "</span>"
								}
							}else{
								output += "<span class='box_likes'>"
								output += "		<a href='#' onclick='return false;'  class='text-reset text-decoration-none'>"
								output += "			<i class='far fa-heart text-danger emptyHeart'></i>"
								output += "			<input type='hidden' value='"+item.recipe_num+"'>"
								output += "		</a>"
								output += "		<span>&nbsp;"+item.recipe_likes+"</span>"
								output += "</span>"
							}
							output += "<a class='text-reset text-decoration-none' href='/recipe/recipeReadForm?listnum="+item.recipe_num+"'>"
							if(Number(item.recipe_num) >= 10000){
								output += "<img class='card-img-top rounded img_in_card' src='/recipeUpload/"+item.recipe_photo+"'>"
							}else if(Number(item.recipe_num) < 10000){
								output += "<img class='card-img-top rounded img_in_card' src='"+item.recipe_photo+"'>"
							}
							output += "</a>"
							output += "<div class='row card-body auto_w'>"
							output += "<div class='text_in_card'>"
							output += "<a class='text-reset text-decoration-none' href='/recipe/recipeReadForm?listnum="+item.recipe_num+"'>"
							output += item.recipe_name
							output += "</a>"
							output += "</div>"
							output += "<input type='hidden' class='recipe_num' value='"+item.recipe_num+"'>"
					        output += "</div>"
					        output += "</div>"
					        output += "</div>"
						        
						$("#recipeList").append(output);
						cnt++;
					}
				})
				
				window.setTimeout(function(){
					check = true;				
				}, 2000);
		},error : function(request, status, error){
			alert("error")
			window.setTimeout(function(){
				check = true;				
			}, 2000);
		}
	}) 

}