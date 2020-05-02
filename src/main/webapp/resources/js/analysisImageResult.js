$(function(){
	
	$("#recipeInfo").on("click",".foodRemoveBtn",function(){
		var class_name = $(this).prev(":hidden").attr("class")
		
	 	$("."+class_name).filter("area").remove()
		
		$(this).parent().parent().remove()
	 })

	$("#foodAddBtn").on("click",function(){

		var output = "<div class='input-group mb-3 inputDiv recipeIngrd'>"
			output += "		<span class='align-self-center img_hidden'><img src='/resources/img/tooltip1.png'></span>"
			output += "			<div class='input-group-prepend'>"
			output += "				<div class='input-group-text'>"
			output += "					<input type='radio' name='mainFood' class='mainFood'>"	
			output += "				</div>"
			output += "			</div>"
			output += "			<input type='text' name='food' class='form-control food' aria-label='Text input with checkbox' >"
			output += "			<div class='input-group-append'>"
			output += "				<button class='btn btn-warning foodRemoveBtn'>삭제</button>"
			output += "			</div>"
			output += "</div>"
					
		$("#recipeInfo").append(output)
	})

	$("input[type=text]").mouseover(function(e) {
		var class_val = $(this).attr("class");
	    class_val = class_val.split("_")[0];
		class_val = '.'+class_val
	    $(class_val).mouseover();
	}).mouseout(function(e) {
	    var class_val = $(this).attr("class");
	    	class_val = class_val.split("_")[0];
		class_val = '.'+class_val
	    $(class_val).mouseout();
	})

	$("input[type=text]").focus(function(e) {
		var class_val = $(this).attr("class");
	    class_val = class_val.split("_")[0];
		class_val = '.'+class_val
	    $(class_val).mouseover();
	}).focusout(function(e) {
		var class_val = $(this).attr("class");
	    class_val = class_val.split("_")[0];
		class_val = '.'+class_val
	    $(class_val).mouseout();
	})

	$("body").on("click",".mainFood",function(){
		var visible = $(".img_visible")
		visible.attr("class","align-self-center img_hidden")

		var $this = $(this)

		$this.parent().parent().prev("span").attr("class","align-self-center img_visible")
	})

	$("area").on("click",function(){
		alert($(this).attr("title"))
	})
	       
	/*$('.map').maphilight({
		fill: true,
		fillColor: '000000',
		fillOpacity: 0.2,
		stroke: true,
		strokeColor: 'ff0000',
		strokeOpacity: 1,
		strokeWidth: 1,
		fade: true,
		alwaysOn: true,
		neverOn: true,
		groupBy: true,
		wrapClass: true,
		shadow: true,
		shadowX: 0,
		shadowY: 0,
		shadowRadius: 6,
		shadowColor: '000000',
		shadowOpacity: 0.8,
		shadowPosition: 'outside',
		shadowFrom: true
	});*/
	$('.map').maphilight();
	
	//submit전 유효성 검사
	$("#findRecipeForm").on("submit",function(){
		var radio = $(":checked").val()
		var mainFood = $(":checked").parent().parent().next(":text").val()
		
		//새로 추가한 버튼
		if(radio != mainFood){
			$(":checked").val($(":checked").parent().parent().next(":text").val())
		}else if($(":checked").length == 0){
			alert("주재료 선택해주세요")
			$("#findBtn").removeAttr("disabled")
			return false;
		}
		
		$(".food").each(function(index,item){
			if(!($(item).val().length > 0)){
				$(item).parent().remove()
			}
		})
		
		if(!($(".food").length > 0)){
			alert("재료를 입력해주세요")
			return false;
		}
	})
	
})