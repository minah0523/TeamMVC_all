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

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>


    <script type="text/javascript">
        $(document).ready(function() {
        	
        	goLikeCount();	// 좋아요갯수를 보여주도록 하는 것이다.
        	
        	goCommentListView();  // 제품 구매후기를 보여주는 것.
      	  	
        	 var sqty = "";
            var color = "";

            $("select[name='productcolor']").click(function() {
                color = $("select[name='productcolor']").val();
                console.log(color);

            });

            var size = "";
            

            $("select[name='productsize']").click(function() {
                size = $("select[name='productsize']").val();
                console.log(size);

            });

            // $("input:hidden[name=qty]").val();

            /*  var qty = "";
             
             $("input[name='productqty']").click(function() {
             	qty = $("input[name='productqty']").val();
                 console.log(qty);
                 goPdnoPage(pdno);
             }); */
            
            $("input[name='inputBTN']").click(function() {
                // console.log("123568");
            	$("input[name='pdno']").val();
            	$("input[name='color']").val();
            	$("input[name='pdno']").val();
            	$("input[name='pdno']").val();

            });
            
            
           
            
            $("input[name='hit']").click(function() {
            
            	sqty = $("input[name='sqty']").val();
            	console.log($(this).val());
            	console.log("qty : " + sqty);
            	
            	$("input[name='pdno']").val(${pdno});
                $("input[name='pcolor']").val(${color});
                $("input[name='psize']").val(${size});
                $("input[name='pinfono']").val(${pinfono});
                //$("input[name='qty']").val(${qty});
            	
            });
            
         // **** 제품후기 쓰기 ****// 
            
     	   $("button.btnCommentOK").click(function(){
     		   
     		   if(${empty sessionScope.loginuser}) {
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
     		   var queryString = {"fk_userid":'${sessionScope.loginuser.userd}', 
     				              "fk_pnum" : ${pvo.pnum},
     				              "contents" : $("textarea#commentContents").val()};
     	  --%>
     		   // 보내야할 데이터를 선정하는 두번째 방법
     		   // jQuery에서 사용하는 것으로써,
     		   // form태그의 선택자.serialize(); 을 해주면 form 태그내의 모든 값들을 name값을 키값으로 만들어서 보내준다. 
     		   var queryString = $("form[name=commentFrm]").serialize();
     		   //  console.log(queryString); // commentContents=Good&pnum=3
     		   
     		   $.ajax({
     			   url:"/productdetail/commentRegister.up",
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

     	   $.ajax({
     		   url:"/productdetail/commentList.up",
     		   type:"GET",
     		   data:{"fk_pdno":${pdvo.pdno}},
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
     		   		
     				// == "#sideinfo" 의 height 값 설정해주기 == 
     				var contentHeight =	$("#content").height();
     				//	alert(contentHeight);
     				$("#sideinfo").height(contentHeight);
     		   },
     		   error: function(request, status, error){
     				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
     		   }
     	   });	   
     	   
        }
        
        

        function goPdnoPage(pdno) {
            var frm = document.PdnoFrm;

            frm.pdno.value = pdno;
            frm.pcolor.value = pcolor;
            frm.psize.value = psize;
            frm.qty.value = qty;

            frm.action = "<%=request.getContextPath()%>/member/ProductDetail.neige";
            frm.method = "POST";
            frm.submit();
        };

        function goCart() {

            var qty = $("input:text[name=productqty]").val();
            var queryString = $("form[name=PdnoForm]").serialize();
            
            $.ajax({
                url: "/productdetail/addCart.neige",
                type: "POST",
                data: queryString,
                success: function() {
                    alert("장바구니에 담았습니다.");
                    location.href='<%=ctxPath%>/Payment/productCart.neige';
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }

                
            });
        }

        // **** 특정제품에 대한 좋아요 등록하기 **** // 
        function golike(pdno) {

            $.ajax({
                url: "/productdetail/like.neige",
                type: "POST",
                data: {
                    "pdno": pdno,
                    "userid": "${sessionScope.loginuser.userid}"
                },
                dataType: "JSON",
                success: function(json) {
                    //alert(json.msg);
                    swal(json.msg);
                    goLikeCnt();
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });

        } // end of golike(pdno)---------------


        // **** 특정 제품에 대한 좋아요 갯수를 보여주기 **** //
        function goLikeCnt() {

            $.ajax({
                url: "/productdetail/likeCnt.neige",
                // type:"GET",
                data: {
                    "pdno": "${pdvo.pdno}"
                },
                dataType: "JSON",
                success: function(json) {
                    $("div#likeCnt").html(json.likecnt);
                },
                error: function(request, status, error) {
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
            });

        } // end of function goLikeCnt()-------------------

        // 전송한값 테스트
        function goAddCart(){
			
        	var pdno = document.addcartcheck.pdno;
        	
        	 var color = document.addcartcheck.productcolor.options.selectedIndex;
        	 
        	 var size = document.addcartcheck.productcolor.options.selectedIndex;
        	 //현재 선택되어 있는 값 가져오기
        	 //alert(document.form.productcolor.options.selectedIndex);
        	 var qty = document.addcartcheck.pqty;
        	 
        	 var select_text=document.addcartcheck.productcolor.options[i].text;

        	 addcartcheck.test.value = select_text;
        	 location.href='<%=ctxPath%>/productdetail/cartcheck.jsp'

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

                        <img src="<%= ctxPath%>/images/${pdvo.pdimage1}" style="width: 650px; height: 550px; float: left;"> <img src="<%= ctxPath%>/images/${pdvo.pdimage2 }" style="width: 100px; height: 100px; float: left; padding max-width: inherit;">

                    </div>
                </c:if>
                <br>
            </c:forEach>
        </div>


	<form name="PdnoForm">
        <input type="text" name="pdno" value="${pdvo.pdno}" />
        <input type="text" name="color" value="${pdinfo.pcolor}" />
        <input type="text" name="size" value="${pdinfo.psize}" />
        <%-- <input type="text" name="pdno_fk" value="${pdinfo.pdno_fk}" />--%>
        <input type="text" name="pinfono" value="${pdinfo.pinfono}" />
        <input type="text" name="qty" value="" />
        <input type="button" name="hit" value="" />
    </form>
   
   <%--  
    <c:if test="${productinfoList}">
  	<c:if test="${empty productinfoList.pinfono}" var="pinfono" />
  	<c:if test="${empty productinfoList.pcolor}" var="pcolor" />
  	<c:if test="${empty productinfoList.psize}" var="psize" />
  	<c:if test="${empty productinfoList.qty}" var="pqty" />
  	</c:if>

 --%>
 
        <div class="container">
            <div class="product-detail-right">
                <!-- product-detail option -->
                <c:forEach var="pdvo" items="${productList}" varStatus="status">
                    <h3>
                        <c:out value="${pdvo.pdname}" />
                        <!-- 제품명 pdname -->

                        <br> <small>Product No.
                            <c:out value="${pdvo.pdno}" />
                        </small>
                        <!-- 제품번호 pdno -->
                    </h3>
                    <h5>
                        <b>가격 : </b> &#8361;
                        <c:out value="${pdvo.price}" />
                        <br> <br> <b>재질: </b>
                        <c:out value="${pdvo.texture}" />
                    </h5>
                </c:forEach>
<%--                 
                <h5>
                    <b>색상 : </b> <select name="productcolor" class="productData" style="color: gray;">
                        <option name="colorbtn" value="colortag" style="color: gray;">선택</option>
                        <c:forEach var="pdinfo" items="${requestScope.productinfoList}">
                            <option value="${pdinfo.pcolor}" style="color: gray;">${pdinfo.pcolor}</option>
                        </c:forEach>
                    </select> <br> <br> <b>사이즈 : </b> 
                    <select name="productsize" class="productData" style="color: gray;">
                        <option name="sizebtn" value="sizetag" style="color: gray;">선택</option>
                        
                        <c:forEach var="pdinfo" items="${requestScope.productinfoList}">
                            <c:if test=""></c:if>
                            <option value="${pdinfo.psize}" style="color: gray;">${pdinfo.psize}</option>
                        </c:forEach>
                    </select> 
                    <br>
                </h5>
--%>               
<!--  // 전송한값 테스트 -->
 <!-- get 방식을 사용해서 데이터 전송 (method="get") -->
    <form name = "addcartcheck" action="/WEB-INF/productdetail/cartcheck.jsp" method="get">
    <h5>
    	<input type="hidden" name='pdno' value='${pdvo.pdno}'>
    	<b style="font-size: 18px; margin-bottom: 20px;">색상 : </b>
    	<select name="productcolor" class="productData" style="color: gray;">
        	<option name="colorbtn" value="colortag" style="color: gray;">필수</option>
				<c:forEach var="pdinfo" items="${requestScope.productinfoList}">
                  	<option value="${pdinfo.pcolor}" style="color: gray;">${pdinfo.pcolor}</option>
                </c:forEach>
        </select>
        </h5>
        <h5>
        <b style="font-size: 18px; margin-bottom: 20px;">사이즈 : </b>
        <select name="productsize" class="productData" style="color: gray;">
                 	<option name="sizebtn" value="sizetag" style="color: gray;">필수</option>
                    	<c:forEach var="pdinfo" items="${requestScope.productinfoList}">
                            <option value="${pdinfo.psize}" style="color: gray;">${pdinfo.psize}</option>
                        </c:forEach>
                 </select> 
                 <br>
          </h5> 
          <h5>         
          <ul style="list-style-type: none; margin-top: 15px;">
        <b style="font-size: 18px; margin-bottom: 20px;">수량 : </b>
        <input type="text" name="pqty" size="10" value='' style="width: 30px; height: 30px; color: gray;"><br>
        
          <h5>
               <b>배송비 : </b> FREE
          </h5>
        
            </ul>
            <!-- <input type="checkbox" name="hobby" value="reading">독서 -->
            <br>
            </h5>
        <input type="submit" name="addCart" onClick="goAddCart()" value="장바구니"  class="addtocart">
        
        <input type="submit" value="구매하기" onClick="location.href='<%=ctxPath%>/Payment/productCart.neige'" class = "buynow">
    </form>
 <!-- get 방식을 사용해서 데이터 전송 (method="get") end -->
                
<%-- 
                <button type="button" id="addcart" onclick="goCart()" class="addtocart">
                    <i id="cart" class="fas fa-heart">장바구니</i>
                </button>
                <button type="button" id="review" onclick="location.href='<%=ctxPath%>/productdetail/productReview.jsp'" class="writereview">
                    <i class="fas fa-pen">리뷰</i>
                </button>
                <br>
                <button type="button" id="buy" onclick="location.href='<%=ctxPath%>/Payment/productCart.neige'" class="buynow">
                    <i class="fas fa-shopping-cart">구매하기</i>
                </button>
--%>

            </div>
            <!-- product-detail option end -->


            <div class="product-detail-feature">
                <c:forEach var="pdvo" items="${productList}" varStatus="status">
                    <h3>상품상세정보</h3>
                    <c:forEach var="pdinfo" items="${requestScope.productinfoList}">
                        <p>
                            색상 :
                            <c:out value="${pdinfo.pcolor}" />
                        </p>
                        <p>
                            사이즈 :
                            <c:out value="${pdinfo.psize}" />
                        </p>
                    </c:forEach>
                    <p>
                        재질 :
                        <c:out value="${pdvo.texture}" />
                    </p>
                    
                    <p>
                        적립 포인트 :
                        <c:out value="${pdvo.point}" />
                    </p>
                    <br>
                    <br>
                    <h4>
                        <c:out value="${pdvo.pdcontent}" />
                    </h4>
                    <br>
                    <br>
                    <a href="<%=ctxPath%>/images/56_3.jpg"> <img src="<%=ctxPath%>/images/56_3.jpg" alt=""></a>
                    <img src="<%= ctxPath%>/images/${pdvo.pdimage1}" style="width: 100%;">
                    <img src="<%= ctxPath%>/images/${pdvo.pdimage2}" style="width: 100%;">
                </c:forEach>
            </div>
        </div>

        <div class="row" style="margin-bottom: 50px;">
            <div class="col-md-2 col-md-offset-2">
                <img src="<%=request.getContextPath()%>/images/like.png" style="cursor: pointer;" onClick="golike('${pvo.pdno}');" />
            </div>
            <div class="col-md-1" id="likeCnt" align="center" style="color: blue; font-size: 16pt;">
            </div>
        </div>
        
         	
	</div>

        <!-- container -->


<span style="font-size: 16pt; color: #A5907B;">제품사용 후기</span>
    <div id="viewComments">
    	<%-- 제품사용 후기 내용입력 --%>
    </div> 
    <form name="commentFrm">
    	<div>
    		<textarea cols="100" class="customHeight" name="contents" id="commentContents"></textarea>
    	</div>
    	<div>
    		<button type="button" class="customHeight btnCommentOK">후기등록</button>
    	</div>
    	<input type="hidden" name="fk_userid" value="${sessionScope.loginuser.userid}" />
    	<input type="hidden" name="fk_pdno" value="${pdvo.pdno}" />
    </form>
    
    
    </div>
    <!-- product detail -->

    
</body>

</html>

<jsp:include page="/WEB-INF/footer.jsp" />
