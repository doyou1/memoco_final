var slideNo = 0;   //보여질 슬리이드 번호 초기값
    
    //슬라이드 보이기(번호)
    function slideView(n) {
      slideNo += n;
      
      var x = document.getElementsByClassName("slide");   //이미지 오브젝트
      
      if (slideNo > x.length) slideNo = 1;
          //슬라이드 번호가 마지막을 넘으면(전체슬라이드 개수 x.length) 처음으로 위치를 맞춤
      
      if (slideNo < 1) slideNo = x.length;
          //슬라이드 번호가 1보다 적으면 마지막으로 위치를 맞춤
      
      //모든 슬라이드(이미지)를 숨김
      var i;   //반복처리용 변수
      for (i = 0; i < x.length; i++) {
         x[i].style.display = "none";  
      }
      
      //전달받은 번호의 슬라이드(이미지)를 보이게 함
      x[slideNo-1].style.display = "block";  
    }
    
    
    $(document).keydown(function(event){
        var key = event.keyCode;
       
        if(key==37){
        //왼쪽
           slideView(-1)
        }else if(key==38){
        //위      
           slideView(-1)
        }else if(key==39){
        //오른쪽    
           slideView(1)
        }else if(key==40){
        //아래    
           slideView(1)
        }
    });