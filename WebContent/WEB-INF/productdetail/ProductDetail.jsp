<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>

<head>
<jsp:include page="/WEB-INF/header.jsp" />

<link rel="stylesheet" type="text/css" href="css/style_1.css">
<link rel="stylesheet" type="text/css" href="css/smoothproducts.css">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

<style type="text/css">
	.line {border: 0px solid red;
	       border-collapse: collapse;
	       margin-top: 20px;
	       margin-bottom: 20px; }
	       
	li {margin-bottom: 10px;} 
	
	.customHeight {height: 50px;}
	
	textarea#commentContents {font-size: 12pt;}
	
	div#viewComments {width: 80%;
	           		  margin: 3% 0 0 0; 
	                  text-align: left;
	                  max-height: 300px;
	                  overflow: auto;
	                  /* border: solid 1px red; */
	}
	
	span.markColor {color: #ff0000; }
	 
	div.customDisplay {display: inline-block;
	                   margin: 1% 3% 0 0;
	}
	                
	div.commentDel {margin-bottom: 5%;
	                font-size: 8pt;
	                font-style: italic;
	                cursor: pointer;
	}	      
</style>


<script type="text/javascript">
        $(document).ready(function() {
        	
        	goLikeCnt();	// 좋아요갯수를 보여주도록 하는 것이다.
        	
        	goCommentListView();  // 제품 구매후기를 보여주는 것.
      	  	
         // **** 제품후기 쓰기 ****//
    
	   $("button[name='btnCommentOK']").click(function(){
		   
		   if(${empty sessionScope.loginuser.userid}) {
			   alert("제품사용 후기를 작성하시려면 먼저 로그인 하셔야 합니다.");
			   return;
		   }
		   
		   var commentContents = $("textarea#commentContents").val().trim();
		   
		   if(commentContents == "") {
			   alert("제품후기 내용을 입력하세요!!");
			   return; 
		   }
		  
		   // 보내야할 데이터를 선정하는 첫번째 방법
	  <%-- 
		   var queryString = {"fk_userid":'${sessionScope.loginuser.userid}', 
				              "fk_pdno" : ${pdvo.pdno},
				              "contents" : $("textarea#commentContents").val()};
	  --%>
		   // 보내야할 데이터를 선정하는 두번째 방법
		   // jQuery에서 사용하는 것으로써,
		   // form태그의 선택자.serialize(); 을 해주면 form 태그내의 모든 값들을 name값을 키값으로 만들어서 보내준다. 
		   var queryString = $("form[name=commentFrm]").serialize();
		   //  console.log(queryString); // commentContents=Good&pdno=3
   		   
   		   $.ajax({
   			   url:"/TeamMVC/productdetail/commentRegister.neige",
			   type:"POST",
			   data:queryString,
			   success:function(){
				// alert("확인용 : 제품후기 글쓰기 성공!!");
				   goCommentListView(); // 제품후기글을 보여주는 함수호출하기 
				   $("textarea#commentContents").val("").focus();
			   },
			   error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			   }
   		   });
     		 
     	   });

        }); // end of ready function---------------
        
     // 특정 제품의 제품후기글들을 보여주는 함수
        function goCommentListView() {
			
        	var pdno = $("input:hidden[name=pdno]").val();
        	
        	$.ajax({
     		   url:"/TeamMVC/productdetail/commentList.neige",
     		   type:"GET",
     		   data:{"pdno":pdno},
     		   dataType:"JSON",
     		   success:function(json) {
     			   // [{"contents":"제품후기내용물", "name":"작성자이름", "writeDate":"작성일자"},{"contents":"제품후기내용물2", "name":"작성자이름2", "writeDate":"작성일자2"}] 
     			    var html = "";
     				
     				if (json.length > 0) {    
     					$.each(json, function(index, item){
     						html +=  "<div> <span class='markColor'>▶</span> "+item.contents+"</div>"
					         + "<div class='customDisplay'>"+item.name+"</div>"      
					         + "<div class='customDisplay'>"+item.writeDate+"</div>"
					         + "<div class='customDisplay commentDel'>후기삭제</div>";
     					} ); 
     				}// end of if -----------------------
     				
     				else {
    					html += "<div>등록된 상품후기가 없습니다.</div>";
    				}// end of else ---------------------
     				
     				$("div#viewComments").html(html);
     		   },
     		   error: function(request, status, error){
     				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
     		   }
     	   });	   
  	   
     }

        // **** 특정제품에 대한 좋아요 등록하기 **** // 
        function golike() {
		
        	var pdno = $("input:hidden[name=pdno]").val();
        	
            $.ajax({
                url: "/TeamMVC/productdetail/like.neige",
                type: "POST",
                data: {
                    "pdno": pdno,
                    "userid": "${sessionScope.loginuser.userid}"
                },
                dataType: "JSON",
                success: function(json) {
                    alert(json.msg);
                    //swal(json.msg);
                    //location.href=json.loc;
                    goLikeCnt();
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });
        	
        } // end of golike(pdno)---------------

        // **** 특정 제품에 대한 좋아요 갯수를 보여주기 **** //
        function goLikeCnt() {

        	var pdno = $("input:hidden[name=pdno]").val();
        	
            $.ajax({
                url: "/TeamMVC/productdetail/likeCnt.neige",
                type:"POST",
                data: {
                    "pdno": pdno
                },
                dataType: "JSON",
                success: function(json) {
                    $("div#likeCnt").html(json.likecnt);
                    $("div#likeCnt").show();
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });

        } // end of function goLikeCnt()-------------------

        
        
        // 장바구니 전송값
        function goAddCart(){
        	
        	 var color = $("select[name=productcolor]").val();
        	 
        	 var size = $("select[name=productsize]").val();
        	 //현재 선택되어 있는 값 가져오기
        	 //alert(document.form.productcolor.options.selectedIndex);
        	 var pqty = $("input:text[name=pqty]").val();
        	 
        	 var pdno = $("input:hidden[name=pdno]").val();
        	 
        	 var para_data = {"color":color, "size":size, "pqty":pqty, "pdno":pdno};
        	 
        	 $.ajax({
        		 url:"/TeamMVC/productdetail/goaddcart.neige",
                 type:"GET",
                 data: para_data,
        	 <%--data: queryString,--%>
                 success: function(json) {
                     /* alert("장바구니에 성공적으로 담겼습니다."); */
                 },
                 error: function(request, status, error) {
                     alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                 }
        	 });

        	}

        
    </script>
<meta charset="UTF-8">
<title>:: 상품상세정보 ::</title>
</head>

<body>

	<div class="product-detail">

		<div class="product-detail-left">
			<c:forEach var="pdvo" items="${productList}" varStatus="status">
				<c:if test="${ status.index == 0 }">

					<!--  <li data-target="#ProductDetail_info"></li>-->

					<div class="ProductDetail-left">

						<img src="<%= ctxPath%>/images/${pdvo.pdimage1}"
							onmouseover="this.src='<%= ctxPath%>/images/${pdvo.pdimage2 }'"
							onmouseout="this.src='<%= ctxPath%>/images/${pdvo.pdimage1}'"
							style="width: 650px; height: 550px;">
						<!-- <img src="<%= ctxPath%>/images/${pdvo.pdimage2 }" style="width: 100px; height: 100px; float: left; padding max-width: inherit;"> -->

					</div>
				</c:if>
				<br>
			</c:forEach>
		</div>

		<div class="container">
			<div class="product-detail-right" align="left">
				<!-- product-detail option -->
				<c:forEach var="pdvo" items="${productList}" varStatus="status">
					<h3 style="font-weight: bold; font-size: 16pt; color: #353535;"><c:out value="${pdvo.pdname}" />
							<input type="hidden" value="${pdvo.pdname}" name="pdname">
						<!-- 제품명 pdname -->

						<br> <small style="font-weight: bold;">Product No. <c:out value="${pdvo.pdno}" />
							<input type="hidden" value="${pdvo.pdno}" name="pdno" />
						</small>
						<!-- 제품번호 pdno -->
					</h3>
					<h5 style="font-weight: bold; font-size: 11.5pt">
						<b style="font-size: 18px; margin-bottom: 20px;">가격 : </b> &#8361;
						<fmt:formatNumber value="${pdvo.price}" pattern="###,###" />
						<br> <br>
						<b style="font-size: 18px; margin-bottom: 20px;">재질 : </b>
						<c:out value="${pdvo.texture}" />
					</h5>
				</c:forEach>

				<!-- get 방식을 사용해서 데이터 전송 (method="get") -->
				<form name="addcart" action="/productdetail/cartcheck.jsp"
					method="GET">
					<h5 style="font-weight: bold; font-size: 11.5pt">
						<input type="hidden" name="pdno" value='${pdvo.pdno}'> 
						<b style="font-size: 18px; margin-bottom: 20px;">색상 : </b> 
							<select name="productcolor" class="productData" style="color: gray; font-weight: bold; font-size: 11.5pt">
							<option name="colorbtn" value="colortag" style="color: gray; font-weight: bold; font-size: 11.5pt">필수</option>
							<c:forEach var="pdinfo" items="${requestScope.productinfoList}">
								<option name="color" value="${pdinfo.pcolor}" style=" color: gray; font-weight: bold; font-size: 11.5pt"
									style="color: gray;">${pdinfo.pcolor}</option>
							</c:forEach>
						</select>
					</h5>
					<h5 style="font-weight: bold; font-size: 11.5pt">
						<b style="font-size: 18px; margin-bottom: 20px;">사이즈 : </b> 
						<select name="productsize" class="productData" style="color: gray; font-weight: bold; font-size: 11.5pt">
							<option name="sizebtn" value="sizetag" style="color: gray; font-weight: bold; font-size: 11.5pt">필수</option>
							<c:forEach var="pdinfo" items="${requestScope.productinfoList}">
								<option name="size" value="${pdinfo.psize}" style="color: gray; font-weight: bold; font-size: 11.5pt">${pdinfo.psize}</option>
							</c:forEach>
						</select> <br>
					</h5>
					<h5 style="font-weight: bold; font-size: 11.5pt" >
							<b style="font-size: 18px; margin-bottom: 20px;" >수량 : </b>
							<!-- <input type="number" name= "pqty" min="1" max="1000" value="10" step="1" /> --> 
							<input type="text" name="pqty" size="10" value='' style="width: 30px; height: 30px; color: gray;" />

							<h5 style="font-weight: bold; font-size: 11.5pt">
								<b style="font-weight: bold; font-size: 18px; ">배송비 : </b> FREE
							</h5>

						<!-- <input type="checkbox" name="hobby" value="reading">독서 -->
					</h5>
					<input type="button" name="addCart" onClick="goAddCart()" value="장바구니" class="addtocart"> 
					<input type="submit" name="buynow" value="구매하기" onClick="location.href='<%=ctxPath%>/product/productCart.neige'" class="buynow">
					<input type="hidden" value="${gender}" name="gender">
				</form>
				<!-- get 방식을 사용해서 데이터 전송 (method="get") end -->

			</div>
			<!-- product-detail option end -->

			<div class="product-detail-feature">
				<c:forEach var="pdvo" items="${productList}" varStatus="status">
					<h3>상품상세정보</h3>
					<c:forEach var="pdinfo" items="${requestScope.productinfoList}">
						<p>
							색상 : <c:out value="${pdinfo.pcolor}" />
						</p>
						<p>
							사이즈 : <c:out value="${pdinfo.psize}" />
						</p>
					</c:forEach>
					<p>
						재질 : <c:out value="${pdvo.texture}" />
					</p>
					<p>
						적립 포인트 : <c:out value="${pdvo.point}" />
					</p>
					
					<h4 style="font-weight: bold; font-size: 11.5pt; color: #353535;">
						<c:out value="${pdvo.pdcontent}" />
					</h4>
					<br>
					<a href="<%=ctxPath%>/images/56_3.jpg" > 
					<img src="<%=ctxPath%>/images/56_3.jpg" >
					</a>
					<img src="<%= ctxPath%>/images/${pdvo.pdimage1}" style="width: 100%;">
					<img src="<%= ctxPath%>/images/${pdvo.pdimage2}" style="width: 100%;">
				</c:forEach>
			</div>
		</div>

		<div class="row" style="margin-bottom: 50px;">
			<div class="col-md-2 col-md-offset-5"> 
				<div id="likeCnt" align="center" style="color: #A5907B; font-size: 20pt;"></div>
				<img src="<%=request.getContextPath()%>/images/like.png" style="width:30%; cursor: pointer; margin: 0 auto;" onClick="golike()" />
				<p style="font-weight: bold; font-size: 14pt; color: #353535;">좋아요</p>
			</div>
		</div>
	
	<%-- container  --%>
	<h3 style="font-weight: bold; font-size: 12pt; color: #353535;">제품사용 후기</h3>
	<div id="viewComments">
		<%-- 제품사용 후기 내용입력 --%>
	</div>
	<form name="commentFrm">
		<div>
			<textarea cols="100" class="customHeight" name="contents" id="commentContents" style="margin: 0px; width: 400px; height: 100px;"></textarea>
		</div>
		<div>
			<button type="button" class="customHeight" name="btnCommentOK" style="font-weight: bold; font-size: 11pt">후기등록</button>
		<input type="hidden" name="userid" value="${sessionScope.loginuser.userid}" />
    	<input type="hidden" name="pdno" value="${pdvo.pdno}" />
		</div>
	</form>

	</div>
	<!-- product detail -->

</body>

</html>

<jsp:include page="/WEB-INF/footer.jsp" />
