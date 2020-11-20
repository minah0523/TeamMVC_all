<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String ctxPath = request.getContextPath();
//			/MyMVC
%>

<!DOCTYPE html>
<html>
<head>

<jsp:include page="../header.jsp" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/style_dh.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/OwlCarousel2-2.3.4/dist/assets/owl.carousel.min.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/OwlCarousel2-2.3.4/dist/assets/owl.theme.green.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/OwlCarousel2-2.3.4/dist/assets/owl.theme.green.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" /> 
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<script src="<%= ctxPath%>/OwlCarousel2-2.3.4/dist/owl.carousel.min.js"></script>

<style>
	ul {
		padding: 0;
	}
	
	li{
		list-style-type: none;
	}
	
	#orderform > div.data > ul > li {
		margin: 0 auto;
	}
	
	li.notimg {
		padding-top: 30px;
	}
	
</style>

<script type="text/javascript">
	$(document).ready(function() {
		var datalength = $("div.data").length; 
		
		//--------------------------------- 페이지 로딩시 가격출력 ---------------------------------//
		var sumPrice = 0;
		var sumAmount = 0;
			
		for(var i=0; i<datalength; i++) {
			var iprice = Number( $("input.totalprice").eq(i).val() );
			var iamount = Number( $("input:text[name=amountInput]").eq(i).val() );
				
			sumPrice += iprice;
			sumAmount += iamount
		}
		
		var arrPdno = new Array();
		 <c:forEach var="pvo" items="${cartList}">
		   arrPdno.push('${pvo.pdno}'); 
		 </c:forEach>
		 
		 <c:forEach var="pinfovo" items="${productInfoList}" varStatus="status">
		 	if(arrPdno["${status.index}"] == "${pinfovo.pdno_fk}"){
				$("span.pcolor").eq("${status.index}").html("${pinfovo.pcolor}");
				$("span.psize").eq("${status.index}").html("${pinfovo.psize}");
				$("input:hidden[name=pinfono]").eq("${status.index}").val("${pinfovo.pinfono}");
			} 
		 </c:forEach>
		
		$("input.sumAmount").val( sumAmount );
		$("input.sumPrice").val( sumPrice );
		
		 // 페이지 출력시 선택된 상품이 없기 때문에 최종결제금액은 0원으로 출력
		$("input#checkedSumPrice").val("0");
		$("input#finalPrice").val("0");
		//--------------------------------- 페이지 로딩시 가격출력 끝 ---------------------------------//
		
		
		
		//--------------------------------- 상품수량버튼 ---------------------------------//
		
		$(".error").hide();
		
		$("i.up").click(function() {
			
			var index = $("i.up").index(this); //현재 클릭된 제품의 인덱스 번호를 얻어옴
			
			//--------------------------------- 수량에 따른 총가격변화 ---------------------------------//
			// 이벤트가 발생한 인덱스를 가진 html 값의 변화
			var $productpoint = $("input.productpoint").eq(index);
			const p_priceVal = $("input.p_price").eq(index).val();
			var amount = $("input:text[name=amountInput]").eq(index).val();
			
			amount = Number(amount) + 1;
			
			var finalPoint =  Number(p_priceVal)* 0.01 * amount;
			var finalPrice = Number( p_priceVal ) * amount; 
			
			$("input.productpoint").eq(index).val(finalPoint);
			$("input:text[name=amountInput]").eq(index).val(amount);
			$("input.totalprice").eq(index).val(finalPrice);
			//--------------------------------- 수량에 따른 총가격변화 끝 ---------------------------------//
			
			//--------------------------------- 수량에 따른 결제 가격변화  ---------------------------------//
			var chk = $("input:checkbox[name=buy]").eq(index).is(":checked");
			//해당 인덱스 번호의 체크박스를 확인한다.
			
			var checkedSumPrice = 0; // 선택된 제품들의 결재합
			var usePoint = Number( $("input#usePoint").val() ); // 사용된 포인트의 값
    		var finalPrice = 0;
			
        	for(var i=0; i<datalength; i++) {
        		
	    		if(chk) { // 수량을 올린 제품의 체크박스가 true인 경우 : 해당 물건을 구매할려는 경우
	    			// 다시 최종금액을 만들어서 출력
    				var iprice = Number( $("input.totalprice").eq(i).val() );
    				
    				checkedSumPrice += iprice;
    			}
        	}
        	// 결재금액은 최종적으로 선택되어진 상품들 - 사용된 유저 포인트를 차감하여 화면에 출력
    			finalPrice = checkedSumPrice - usePoint
    			
        		$("input#checkedSumPrice").val(checkedSumPrice);
        		$("input#finalPrice").val(finalPrice);
        		
			//--------------------------------- 수량에 따른 결제 가격변화 끝 ---------------------------------//
			
			//--------------------------------- 상품총액, 상품총수량 출력 ---------------------------------//
			
			var sumPrice = 0;
			var sumAmount = 0;
			
			for(var i=0; i<datalength; i++) {
				var iprice = Number( $("input.totalprice").eq(i).val() );
				var iamount = Number( $("input:text[name=amountInput]").eq(i).val() );
				
				sumPrice += iprice;
				sumAmount += iamount
			}
			
			$("input.sumAmount").val( sumAmount );
			$("input.sumPrice").val( sumPrice );
			
			//--------------------------------- 상품총액, 상품총수량 출력 끝---------------------------------//
		
		});
		
		$("i.down").click(function () {
			
			var index = $("i.down").index(this);
			
			var $productpoint = $("input.productpoint").eq(index);
			const p_priceVal = $("input.p_price").eq(index).val();
			var amount = $("input:text[name=amountInput]").eq(index).val();
			
			amount = Number(amount) - 1;
			
			if(amount < 0) {
				alert("수량은 0 이하로 설정하실수 없습니다.");
				return false;
			}
			
			var finalPoint =  Number(p_priceVal)* 0.01 * amount;
			var finalPrice = Number( p_priceVal ) * amount; 
			
			$("input.productpoint").eq(index).val(finalPoint);
			$("input:text[name=amountInput]").eq(index).val(amount);
			$("input.totalprice").eq(index).val(finalPrice);
			
			//--------------------------------- 수량에 따른 결제 가격변화  ---------------------------------//
			var chk = $("input:checkbox[name=buy]").eq(index).is(":checked");
			//해당 인덱스 번호의 체크박스를 확인한다.
			
			var checkedSumPrice = 0; // 선택된 제품들의 결재합
			var usePoint = Number( $("input#usePoint").val() ); // 사용된 포인트의 값
    		var finalPrice = 0;
			
        	for(var i=0; i<datalength; i++) {
        		
	    		if(chk) { // 수량을 올린 제품의 체크박스가 true인 경우 : 해당 물건을 구매할려는 경우
	    			// 다시 최종금액을 만들어서 출력
    				var iprice = Number( $("input.totalprice").eq(i).val() );
    				
    				checkedSumPrice += iprice;
    			}
        	}
        	// 결재금액은 최종적으로 선택되어진 상품들 - 사용된 유저 포인트를 차감하여 화면에 출력
    			finalPrice = checkedSumPrice - usePoint
    			
        		$("input#checkedSumPrice").val(checkedSumPrice);
        		$("input#finalPrice").val(finalPrice);
        		
			//--------------------------------- 수량에 따른 결제 가격변화 끝 ---------------------------------//
			
			//--------------------------------- 상품총액, 상품총수량 출력 ---------------------------------//
			
			var sumPrice = 0;
			var sumAmount = 0;
			
			for(var i=0; i<datalength; i++) {
				var iprice = Number( $("input.totalprice").eq(i).val() );
				var iamount = Number( $("input:text[name=amountInput]").eq(i).val() );
				
				sumPrice += iprice;
				sumAmount += iamount
			}
			
			$("input.sumAmount").val( sumAmount );
			$("input.sumPrice").val( sumPrice );
			
			//--------------------------------- 상품총액, 상품총수량 출력 끝---------------------------------//
			
		});
		//--------------------------------- 상품수량버튼  끝 ---------------------------------//
		
		//--------------------------------- 상품 전체 체크박스  ---------------------------------//
		$("input:checkbox[id=buyAllCheck]").click(function(){
        	var chk = $(this).is(":checked");//.attr('checked');
        	var checkedSumPrice = 0; // 선택된 제품들의 결재합
			var usePoint = Number( $("input#usePoint").val() ); // 사용된 포인트의 값
    		var finalPrice = 0;
    		
        	if(chk) { // 상품 전체 선택시 값
        		$("input:checkbox[name=buy]").prop('checked', true);
        		
        	// 상품을 체크박스에서 선택시 모든 체크박스들을 확인한 뒤 체크된 상품들만 가격을 합한다.
        		for(var i=0; i<datalength; i++) {
    				var iprice = Number( $("input.totalprice").eq(i).val() );
    				
    				checkedSumPrice += iprice;
    			}
    			
        	// 결재금액은 최종적으로 선택되어진 상품들 - 사용된 유저 포인트를 차감하여 화면에 출력
    			finalPrice = checkedSumPrice - usePoint
    			
        		$("input#checkedSumPrice").val(checkedSumPrice);
        		$("input#finalPrice").val(finalPrice);
        	}
        	else { // 상품 전체 해제시 값
        		$("input:checkbox[name=buy]").prop('checked', false);
        	
        	//상품 전체 해제시 결제금액을 0으로 만든다. 
        		$("input#checkedSumPrice").val(0);
        		$("input#finalPrice").val(0);
        	}
        	
    	});
		
		
		$("input:checkbox[name=buy]").click(function() {
			
			var flag = false;
			var checkedSumPrice = 0; // 선택된 제품들의 결재합
			var usePoint = Number( $("input#usePoint").val() ); // 사용된 포인트의 값
    		var finalPrice = 0; // 포인트와 결재합을 차감한 최종결재금액
    		var index = $("input:checkbox[name=buy]").index(this);
    		
			// 개별 체크박스에서 해제를 하거나 모두 선택할경우 전체 체크박스의 변화
			$("input:checkbox[name=buy]").each(function() {
				var checkboxBoolean = $(this).prop("checked");
				
				if(!checkboxBoolean){ // 상품 개별 해제
					flag = true;  // flag 상태를 바꾼다.
				}
				else { // 상품 개별 선택
					checkedSumPrice += Number( $("input.p_price").eq(index).val() ); 
				}
			});
			
			if(!flag) {
                // name 이 person인 모든 체크박스를 하나하나씩 체크유무를 검사를 마쳤을때
                // 모든 체크박스가 체크가 되어진 상태이라면 
                $("input:checkbox[id=buyAllCheck]").prop("checked", true);
                // "전체선택/전체해제 체크박스"에 체크를 해둔다.
             }
			else {
				$("input:checkbox[id=buyAllCheck]").prop("checked", false);
			}
			
			finalPrice = checkedSumPrice - usePoint
			
			$("input#checkedSumPrice").val(checkedSumPrice);
    		$("input#finalPrice").val(finalPrice);
			
		});
		
		
		//--------------------------------- 상품 전체 체크박스  끝 ---------------------------------//
		
		//--------------------------------- 추천상품(owl-carousel) ---------------------------------//
		var owl = $('.owl-carousel');
			
	    owl.owlCarousel({
	        items:3,                 // 한번에 보여줄 아이템 수
	        loop:true,               // 반복여부
	        margin:30,               // 오른쪽 간격
	        nav   : true,
	        navText:["<div class='nav-btn prev-slide'></div>","<div class='nav-btn next-slide'></div>"],
	        autoplay:true,           // 자동재생 여부
	        autoplayTimeout:1800,    // 재생간격
	        autoplayHoverPause:true  // 마우스오버시 멈출지 여부
	    });
		 $(".prev-slide").html('<i class="fa fa-chevron-left"></i>');
		 $(".next-slide").html('<i class="fa fa-chevron-right"></i>');
	    
		//--------------------------------- 추천상품(owl-carousel) 끝 ---------------------------------//
		
		//--------------------------------- 상품 개별삭제 ---------------------------------//
		$("button.deleteOne").click(function(evnet) {
			$(this).parent().parent().parent().remove();
		//--------------------------------- 개별 삭제된 뒤 상품총액, 상품총수량 출력 ---------------------------------//
			
		datalength = $("div.data").length; // 삭제된 뒤 제품들의 개수를 다시 받아옴
		
			var sumPrice = 0; 
			var sumAmount = 0;
			
			for(var i=0; i<datalength; i++) {
				var iprice = Number( $("input.totalprice").eq(i).val() );
				var iamount = Number( $("input:text[name=amountInput]").eq(i).val() );
				
				sumPrice += iprice;
				sumAmount += iamount
			}
			
			$("input.sumAmount").val( sumAmount );
			$("input.sumPrice").val( sumPrice );
			
		//--------------------------------- 개별 삭제된 뒤 상품총액, 상품총수량 출력 끝---------------------------------//
			
		//--------------------------------- 개별 삭제된 뒤 상품 전체 체크박스  ---------------------------------//
				var dflag = false;
				// 개별 체크박스에서 해제를 하거나 모두 선택할경우 전체 체크박스의 변화
				$("input:checkbox[name=buy]").each(function() {
					var checkboxBoolean = $(this).prop("checked");
					
					if(!checkboxBoolean){
						dflag = true;  // flag 상태를 바꾼다.
		                return false; // each를  break 한다.
					}
				});
				
				if(!dflag) {
	                // name 이 person인 모든 체크박스를 하나하나씩 체크유무를 검사를 마쳤을때
	                // 모든 체크박스가 체크가 되어진 상태이라면 
	                $("input:checkbox[id=buyAllCheck]").prop("checked", true);
	                // "전체선택/전체해제 체크박스"에 체크를 해둔다.
	             }
				else {
					$("input:checkbox[id=buyAllCheck]").prop("checked", false);
				}
			//--------------------------------- 개별 삭제된 뒤 상품 전체 체크박스  끝 ---------------------------------//
			
			//--------------------------------- 개별 삭제된 뒤 최종결제금액 출력 ---------------------------------//
			var checkedSumPrice = 0; // 선택된 제품들의 결재합 초기화
			var usePoint = Number( $("input#usePoint").val() ); // 사용된 포인트의 값
    		var finalPrice = 0;
			
        	for(var i=0; i<datalength; i++) {
        		var chk = $("input:checkbox[name=buy]").eq(i).is(":checked");
    			
	    		if(chk) { // 수량을 올린 제품의 체크박스가 true인 경우 : 해당 물건을 구매할려는 경우
	    			// 다시 최종금액을 만들어서 출력
    				var iprice = Number( $("input.totalprice").eq(i).val() );
    				
    				checkedSumPrice += iprice;
    			}
        	}
        	// 결재금액은 최종적으로 선택되어진 상품들 - 사용된 유저 포인트를 차감하여 화면에 출력
    			finalPrice = checkedSumPrice - usePoint
    			
        		$("input#checkedSumPrice").val(checkedSumPrice);
        		$("input#finalPrice").val(finalPrice);
        	//--------------------------------- 개별 삭제된 뒤 최종결제금액 출력  끝 ---------------------------------//
			
        	
        		if(datalength == 0){
    				$("input#checkedSumPrice").val(0);
    	    		$("input#finalPrice").val(0);
    			}
		});
		
		//--------------------------------- 개별 상품 개별삭제 끝---------------------------------//
		
		
		//--------------------------------- 다중 선택상품 삭제 ---------------------------------//
		$("button#selectDelete").click(function() {
			$("input:checkbox[name=buy]").each(function() {
				
				var checkboxBoolean = $(this).prop("checked");
				
				if(checkboxBoolean){
					$(this).parent().parent().parent().remove();
				}
				
				//--------------------------------- 삭제된 뒤 상품총액, 상품총수량 출력 ---------------------------------//
				datalength = $("div.data").length; 
				var sumPrice = 0;
				var sumAmount = 0;
				
				for(var i=0; i<datalength; i++) {
					var iprice = Number( $("input.totalprice").eq(i).val() );
					var iamount = Number( $("input:text[name=amountInput]").eq(i).val() );
					
					sumPrice += iprice;
					sumAmount += iamount
				}
				
				$("input.sumAmount").val( sumAmount );
				$("input.sumPrice").val( sumPrice );
				
				//--------------------------------- 삭제된 뒤 상품총액, 상품총수량 출력 끝---------------------------------//
				
				//--------------------------------- 삭제된 뒤 상품 전체 체크박스  ---------------------------------//
				var dflag = false;
				// 개별 체크박스에서 해제를 하거나 모두 선택할경우 전체 체크박스의 변화
				$("input:checkbox[name=buy]").each(function() {
					var checkboxBoolean = $(this).prop("checked");
					
					if(!checkboxBoolean){
						dflag = true;  // flag 상태를 바꾼다.
		                return false; // each를  break 한다.
					}
				});
				
				if(!dflag) {
	                // name 이 person인 모든 체크박스를 하나하나씩 체크유무를 검사를 마쳤을때
	                // 모든 체크박스가 체크가 되어진 상태이라면 
	                $("input:checkbox[id=buyAllCheck]").prop("checked", true);
	                // "전체선택/전체해제 체크박스"에 체크를 해둔다.
	             }
				else {
					$("input:checkbox[id=buyAllCheck]").prop("checked", false);
				}
				
				//--------------------------------- 삭제된 뒤 상품 전체 체크박스  끝 ---------------------------------//
			});
			
			//--------------------------------- 개별 삭제된 뒤 최종결제금액 출력 ---------------------------------//
			var checkedSumPrice = 0; // 선택된 제품들의 결재합 초기화
			var usePoint = Number( $("input#usePoint").val() ); // 사용된 포인트의 값
    		var finalPrice = 0;
			
        	for(var i=0; i<datalength; i++) {
        		var chk = $("input:checkbox[name=buy]").eq(i).is(":checked");
    			
	    		if(chk) { // 수량을 올린 제품의 체크박스가 true인 경우 : 해당 물건을 구매할려는 경우
	    			// 다시 최종금액을 만들어서 출력
    				var iprice = Number( $("input.totalprice").eq(i).val() );
    				
    				checkedSumPrice += iprice;
    			}
        	}
        	// 결재금액은 최종적으로 선택되어진 상품들 - 사용된 유저 포인트를 차감하여 화면에 출력
    			finalPrice = checkedSumPrice - usePoint
    			
        		$("input#checkedSumPrice").val(checkedSumPrice);
        		$("input#finalPrice").val(finalPrice);
        	//--------------------------------- 개별 삭제된 뒤 최종결제금액 출력  끝 ---------------------------------//
			
		});
		//--------------------------------- 다중 선택상품 삭제 끝 ---------------------------------//
		
		//--------------------------------- 장바구니 비우기 버튼---------------------------------//
		$("button#allDelete").click(function(){
			$("#orderform > div.data").remove();
			
			$("input.sumAmount").val(0);
			$("input.sumPrice").val(0);
			$("input#checkedSumPrice").val(0);
    		$("input#finalPrice").val(0);
		});	
		//--------------------------------- 장바구니 비우기 버튼 끝---------------------------------//
		
		//--------------------------------- 주소 불러오기 ---------------------------------//
		$("input:radio[id=load_address]").click(function() {
			$("input:text[id=name]").val("${sessionScope.loginuser.name}");
			$("input:text[id=postcode]").val("${sessionScope.loginuser.postcode}");
			$("input:text[id=address]").val("${sessionScope.loginuser.address}");
			$("input:text[id=mobile]").val("${sessionScope.loginuser.mobile}");
			$("input:text[id=detailAddress]").val("${sessionScope.loginuser.detailaddress}");
		//--------------------------------- 주소 불러오기 끝 ---------------------------------//
		
		});
		
		//--------------------------------- 새로운 배송지 작성 ---------------------------------//
		$("input:radio[id=Write_address]").click(function() {
			$("input:text[id=name]").val("");
			$("input:text[id=postcode]").val("");
			$("input:text[id=address]").val("");
			$("input:text[id=mobile]").val("");
			$("input:text[id=deliveryMessage]").val("");
			$("input:text[id=detailAddress]").val("");
		//--------------------------------- 새로운 배송지 작성 끝 ---------------------------------//
		
		});
		
		$("input#usePoint").blur(function() {
			var checkedSumPrice = Number( $("input#checkedSumPrice").val() );
    		var usePoint = Number( $("input#usePoint").val() );
    		var finalPrice = 0;
    		
    		// 포인트 사용량이 가격을 넘어설경우
    		if(checkedSumPrice <= usePoint) {
    			
    			// 결제금액을 0원으로 설정
    			finalPrice = 0;
    			
    			// 포인트 최대치를 가격과 동일하게 설정한다.
    			usePoint = checkedSumPrice;
    			
    			$("input#usePoint").val(usePoint);
    			$("input#finalPrice").val(0);
    		}
    		else if(checkedSumPrice > usePoint) {
    			finalPrice = checkedSumPrice - usePoint;
	    		$("input#finalPrice").val(finalPrice);
    		}
    		
		});
		
	});
	
	function goHome() {
		location.href="<%=ctxPath%>/TeamHomePage.neige";
	}
	
	function goback() {
		location.href="javascript:history.back()";
	}
	
	// 상품 개별삭제 버튼을 눌렀을경우
	function productAllDelete(){
		
		// ajax를 이용하여 장바구니 테이블에서 해당 유저의 선택된 상품 행을 삭제한다.
        var arrPdno = new Array();
		
		 <c:forEach var="pvo" items="${cartList}">
		   arrPdno.push('${pvo.pdno}'); 
		 </c:forEach>
		 
		 var para_pdnoes = arrPdno.join(","); // "1,1,1,1,2,3,4"
	 //  alert(para_pdnoes);
	
		
		var para_data = {"pdnoes":para_pdnoes,"userid_fk":'${sessionScope.loginuser.userid}'};
		
		$.ajax({
	         url:"/TeamMVC/product/productAllDelete.neige", 
	      	 type:"POST",
	         data:para_data, 
	         dataType:"JSON",
	         success:function(){
	            alert("success");
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"/n"+"message: "+request.responseText+"/n"+"error: "+error);
	         }
	      });
		
	}
	
	function productOneDelete(pdno) {
		
		$.ajax({
			url : "/TeamMVC/product/productOneDelete.neige",
			type : "POST",
			data : {"pdno":pdno,"userid":'${sessionScope.loginuser.userid}'},
			dataType : "JSON",
			success : function() {
				alert("success");
			},
			error : function(request, status, error) {
				alert("code: " + request.status + "/n" + "message: "
						+ request.responseText + "/n" + "error: " + error);
			}

		});
	}
	
	function productChoiceDelete() {
		
		var arrPdno = new Array();
		
		var datalength = $("div.data").length;
		
		for(var i=0; i<datalength; i++) {
    		var chk = $("input:checkbox[name=buy]").eq(i).is(":checked");
			
    		var pdno = $("input:hidden[name=pdno]").eq(i).val();
    		
    		if(chk) { 
    			arrPdno.push(pdno);	
			}
    	}
		
		var para_pdnoes = arrPdno.join(","); // "1,1,1,1,2,3,4"
		
		var para_data = {"pdnoes":para_pdnoes, "userid_fk":'${sessionScope.loginuser.userid}'};
		
		$.ajax({
			 url:"/TeamMVC/product/productChoiceDelete.neige", 
	      	 type:"POST",
	         data:para_data, 
	         dataType:"JSON",
	         success:function(){
	            alert("success");
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"/n"+"message: "+request.responseText+"/n"+"error: "+error);
	         }

		});
		
	}
	
	// 선택상품 주문하기 버튼 이벤트
	function productOrder(userid) {
		
		// 최종결제금액
		var finalPrice = $("input:text[id=finalPrice]").val();
		
		if(finalPrice == "0") {
			swal("상품을 선택해주시기 바랍니다.");
			return false;
		}
		
		// 결제URL
		var url = "/TeamMVC/product/paymentGateway.neige?userid="+userid+"&finalPrice="+finalPrice;
		
		
		window.open(url, "paymentGateway",
	    "left=350px, top=100px, width=850px, height=620px");
		} 
	
	// 전체상품 주문이벤트
	function productAllOrder(userid) {
		
		// 전체상품금액
		var finalPrice = $("input:text[name=sumPrice]").val();
		
		var datalength = $("div.data").length;
		
		for(var i=0; i<datalength; i++) {
			$("input:checkbox[name=buy]").eq(i).prop('checked', true);
    	}
		
		// 결제 URL
		var url = "/TeamMVC/product/paymentGateway.neige?userid="+userid+"&finalPrice="+finalPrice;
		
		window.open(url, "paymentGateway",
		"left=350px, top=100px, width=850px, height=620px");
		}
	
	function productOrderRecord(userid, finalPrice) {
		
		var arrPinfono = new Array();
		
		var datalength = $("div.data").length;
		
		var usePoint = $("input:text[id=usePoint]").val();
		
		if(usePoint == null) {
			usePoint = "0";
		}
		
		var amount = 0;
		
		for(var i=0; i<datalength; i++) {
    		var chk = $("input:checkbox[name=buy]").eq(i).is(":checked");
			var productAmount = Number($("input:text[name=amountInput]").eq(i).val());
			amount += productAmount;
			
    		var pinfono = $("input:hidden[name=pinfono]").eq(i).val();
    		
    		if(chk) { 
    			arrPinfono.push(pinfono);	
			}
    	}
		
		var para_pinfono = arrPinfono.join();
		
		var para_data = {"pdnoes":para_pinfono, "userid_fk":userid, "finalPrice":finalPrice, "usePoint":usePoint, "amount":amount};
		
		$.ajax({
			 url:"/TeamMVC/product/paymentSuccess.neige", 
	      	 type:"POST",
	         data:para_data,
	         dataType:"JSON",
	         success:function(json){
	        	 location.href="http://localhost:9090/TeamMVC/product/successPaymentPage.neige";
	         },
	         error: function(request, status, error){
	            alert("code: "+request.status+"/n"+"message: "+request.responseText+"/n"+"error: "+error);
	         }
		});
	} 
	
	
		
</script>

</head>
<body>


<div id="PaymentContainer">
<c:if test="${not empty cartList}">
		<%-- 로그인된 유저가 아닌 일반사용자가 장바구니를 클릭할 경우 --%>
		<%-- <c:if test="${not empty sessionScope.loginuser}"> --%>
			<div id="userInfo" class="widthController">
				<%-- <div>
					<a id="userName">${sessionScope.loginuser.userid}</a>님의 충전금 잔액 : [${sessionScope.loginuser.coin}]원 남아 있으십니다.
				</div>
				 --%>
				 <div>
					<a id="userName">${sessionScope.loginuser.userid}</a>님의 포인트 : [${sessionScope.loginuser.point}] 포인트 남아 있으십니다.
				</div>
			</div>
		<%-- </c:if> --%>
	
	<form name="orderform" id="orderform" class="orderform">
	<!-- 해당폼에서 나온 제품수량, 주문가격, 제품코드를 주문상세 DB에 insert시킨다. -->
		<!-- "장바구니 헤더" -->
		<div class="head">
			<!-- 상품 전체선택 체크박스 -->
			<div class="check col-md-1">
				<input type="checkbox" id="buyAllCheck" name="buyAllCheck">
			</div>
			<div class="col-md-1">이미지</div>
			<div class="col-md-3">상품정보</div>
			<div class="col-md-1">색상</div>
			<div class="col-md-1">사이즈</div>
			<div class="col-md-1">판매가</div>
			<div class="col-md-1">수량</div>
			<div class="col-md-1">적립금</div>
			<div class="col-md-1">합계</div>
			<div class="col-md-1" >삭제</div>
		</div>
		<!-- "장바구니 상품 목록" -->
		<!-- 장바구니 DB 테이블에서 내가 선택한 상품 LIST DTO를 받아온다. 없을경우 하단의 메세지를 출력 -->
		
		<c:forEach var="pvo" items="${cartList}" varStatus="status">
		<!-- 장바구니 DB의 내가 저장한 상품의 수 만큼 상품 row를 추가한다. -->
			<div class="data">
				<ul>
					<!-- 상품 개별 선택 체크박스 -->
					<li class="col-md-1 notimg">
						<input type="checkbox" name="buy">
					</li>
					
					<!-- 장바구니 테이블에서 상품번호를 받기 -> 상품 테이블에 접근하여 상품의 이미지이름, 가격, 상세정보를 받아온다. -->
					<li class="col-md-1">
						<img src="<%=ctxPath%>/images/${pvo.pdimage1}" style="width: 100px; height: 100px; margin: 0 auto; ">
					</li>
					
					<li class="col-md-3 notimg">
						<span>${pvo.pdcontent}</span>
					</li>
		
					<li class="col-md-1 notimg">
						<span class="pcolor"></span>
					</li>
					<li class="col-md-1 notimg">
						<span class="psize"></span>
					</li>
					<!-- 상품가격 -->
					<li class="col-md-1 notimg">
						<input type="text" style="width: 100px;" name="p_price" id="p_price1" class="p_price" value="${pvo.price}" readonly="readonly">
					</li>
					<li class="col-md-1 notimg">
					
						<!-- "장바구니 수량 변경" -->
						<div class="updown" style="display: inline">
							<input type="text" class="amountCtrl" name="amountInput" size="2" maxlength="4" value="1"> 
							<span><i class='fa fa-angle-up up' style='font-size: 24px; cursor: pointer;'></i></span> 
							<span><i class='fa fa-angle-down down' style='font-size: 24px; cursor: pointer;'></i></span>
						</div>
					</li>
					
					<!-- 적립금 -->
					<li class="col-md-1 notimg">
						<input style="width: 100px" type="number" class="productpoint" value="${pvo.point}" readonly="readonly" />&nbsp;P
					</li>
					
					<!-- 상품 총 가격 = 상품 수량*가격 -->
					<li class="col-md-1 notimg">
						<input style="width: 100px" name="totalprice" type="number" class="totalprice" value="${pvo.price}" readonly="readonly" />&nbsp;원
					</li>
					
					<!-- 상품 개별 삭제버튼 -->
					<li class="col-md-1 notimg">
						<button type="button" class="btn btn-primary deleteOne" onclick="productOneDelete('${pvo.pdno}')" >삭제</button>
					</li>
					
					<!-- 상품번호를 받아와 .java로 전송시켜줄 변수를 만든다.
						  상품번호로 상품DB에서 상품VO를 가져와 해당 form에 적용 
					 -->
					 <li>
						<input type="hidden" name="pdno" value="${pvo.pdno}">
					</li>
					<li>
						<input type="hidden" name="pinfono">
					</li>
		
				</ul>
			</div>
		</c:forEach>
		
			<!-- "선택된 상품들 삭제 버튼" -->
			<div class="widthController headBtn" align="right">
				<button type="button" class="btn btn-primary" id="selectDelete" onclick="productChoiceDelete()">선택상품삭제</button>
				&nbsp;|&nbsp;
			<!-- "장바구니 전체 상품 삭제 버튼" -->
				<button type="button" class="btn btn-primary" id="allDelete" onclick="productAllDelete()">장바구니비우기</button>
			</div>
		</form>
		
			<!-- "장바구니 전체 합계 정보" -->
				<div class="widthController sum_p_num" align="right">상품갯수: <input type="number" readonly="readonly" name="sumAmount" class="sumAmount" /> 개</div>
				<div class="widthController sum_p_price" align="right">합계금액: <input type="text" readonly="readonly" name="sumPrice" class="sumPrice" /> 원</div>
			<!-- 배송 정보 -->
			<table class="widthController" id="addressInfoTbl">
				<tbody>
					<tr>
						<th colspan="2" id="th">::: 배송정보 :::</th>
					</tr>
				<tbody>
					<tr>
						<td style="width: 20%; font-weight: bold;" class="td_title">
							<h2>배송지 선택</h2>
						</td>
						<td style="width: 80%; text-align: left;">
							<input type="radio" name="loadOrWrite" id="load_address" class="requiredInfo" value="1"/>주문자 정보와 동일&nbsp; 
							<input type="radio" name="loadOrWrite" id="Write_address" class="requiredInfo" value="2"/>새로운 배송지 작성
						</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;" class="td_title">받는분 성명</td>
						<td style="width: 80%; text-align: left;">
							<input type="text" name="name" id="name" /> <span class="error">성명은 필수입력 사항입니다.</span>
						</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;" class="td_title">우편번호</td>
						<td style="width: 80%; text-align: left;">
							<input type="text" id="postcode" name="postcode" size="6" maxlength="5" />&nbsp;&nbsp;
							<%-- 우편번호 찾기 --%>
							<img id="zipcodeSearch" src="<%=ctxPath%>/images/b_zipcode.gif" style="vertical-align: middle;" /> 
							<span class="error">우편번호 형식이 아닙니다.</span>
						</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;" class="td_title">주소</td>
						<td style="width: 80%; text-align: left;">
							<input type="text" id="address" name="address" size="60" placeholder="주소" style="margin-bottom: 10px;" /><br /> 
							<input type="text" id="detailAddress" name="detailAddress" size="60" placeholder="상세주소" /><br /> 
							<span class="error">주소를 입력하세요</span>
						</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;" class="td_title">연락처</td>
						<td style="width: 80%; text-align: left;">
							<input type="text" id="mobile" name="mobile" size="20" maxlength="11" />
							<span class="error">휴대폰 형식이 아닙니다.</span>
						</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;" class="td_title">배송메시지</td>
						<td style="width: 80%; text-align: left;">
							<textarea rows="5" cols="50" id="deliveryMessage" style="margin: 0px; width: 1089px; height: 182px;"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			
			<form name="totalForm">
			<!-- 해당 폼에서 총 주문금액, 포인트사용금액, 총 결제금액을 VO객체를 만들어 주문테이블에 insert한다 -->
			<!-- 총 주문금액 / 총 할인 금액/ 총 결제금액 -->
			<table class="widthController" id="totalTbl">
				<thead>
					<tr>
						<td>총 주문 금액</td>
						<td>포인트 사용 금액</td>
						<td>총 결제 금액</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<!-- 주문목록중 선택된 제품들만 가격을 더한 최종금액 -->
						<td><input type="text" id="checkedSumPrice" style="text-align: center;" readonly="readonly"/>원</td>
						<!-- 사용할 포인트를 입력받는 input -->
						<td><input type="text" id="usePoint" style="text-align: center;" placeholder="사용하실 포인트 입력" />원 </td>
						<!-- 주문목록중 선택된 제품들만 가격을 더한 최종금액에서 사용된 포인트 번호를 차감한 최종결재금액 -->
						<td><input type="text" id="finalPrice" style="text-align: center;" readonly="readonly"/>원</td>
					</tr>
				</tbody>
			</table>
	
	
			<!-- 상품 주문 -->
			<div class="widthController" align="right" style="display: inline-block;">
				<button type="button" class="btn btn-primary orderBtn" id="orderBtn" name="orderBtn" onclick="productOrder('${sessionScope.loginuser.userid}')">선택한 상품 주문</button>
				<button type="button" class="btn btn-primary orderBtn" id="allOrderBtn" name="allOrderBtn" onclick="productAllOrder('${sessionScope.loginuser.userid}')">전체 상품 주문</button>
			</div>
			
			<!-- 추천상품 -->
			<div id="RecommendItem">
				<h3>Recommend Item</h3>
			</div>
			<div id="myCarousel" class="carousel slide widthController" data-ride="carousel">
				<div class="owl-carousel owl-theme owl-loaded ">
					<div class="owl-stage-outer">
						<div class="owl-stage">
							<c:forEach var="imgvo" items="${imgList}" varStatus="status">
								<div class="owl-item">
									<img src="<%=ctxPath%>/images/${imgvo.imgfilename}" style="width: 100%; cursor: pointer;">
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- Greatest Offer News End -->
			</div>
	</form>
	</c:if>
	
	<c:if test="${empty cartList}">
	<!-- 장바구니 테이블이 비어있을 경우 -->
		<div style="border: 1px solid #E2E2E2; height:300px; margin-top: 0px; margin-bottom: 100px; display: table; vertical-align: middle; " class="widthController" id="nothingChoice">
			<h3 style="opacity: 0.5; display: table-cell; vertical-align: middle; margin-top:300px;" >선택하신 상품이 존재하지 않습니다.</h3>
		</div>
		
		<div style="display: inline;">
			<span>
				<button type="button" id="homeBtn" class="btn btn-warning" style="font-size: 15pt; margin-right: 15px;" onclick="goHome()">쇼핑몰 홈</button>
			</span>
			<span>
				<button type="button" id="homeBtn" class="btn btn-warning" style="font-size: 15pt;" onclick="goback()">이전 페이지로 돌아가기</button>
			</span>
		</div>
		
		<div id="RecommendItem">
				<h3>Recommend Item</h3>
			</div>
			<div id="myCarousel" class="carousel slide widthController" data-ride="carousel">
				<div class="owl-carousel owl-theme owl-loaded ">
					<div class="owl-stage-outer">
						<div class="owl-stage">
							<c:forEach var="imgvo" items="${imgList}" varStatus="status">
								<div class="owl-item">
									<img src="<%=ctxPath%>/images/${imgvo.imgfilename}" style="width: 100%; cursor:pointer;">
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- Greatest Offer News End -->
			</div>
	</c:if>
 </div>

<jsp:include page="../footer.jsp" />