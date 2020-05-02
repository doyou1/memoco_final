/**
 * 
 */
$(function(){
	$(".mode1Btn").on("click",function(){
	   location.href = "/imageanalysis/uploadImageForm"
	})
	$(".mode2Btn").on("click",function(){
	   location.href = "/mode2/home"
	})

	$("#menu").hover(function(){
		$(".sub").css("display","block")
	})
})