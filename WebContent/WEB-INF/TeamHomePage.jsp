<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String ctxPath = request.getContextPath();
//			/TeamMVC
%>
<style>
	#My_Title{
		color:#526B8E; /*#002266*/
		width: 70px;
		padding: 15px
		font-size: 15pt;
		margin: 30px;
		height: 30px;
		border-bottom: solid 3px #e3e3e3;
		letter-spacing: 4px;
	}
	
	#Catagorys {
		/*color: #939393;*/
	}
	
	ul, li, #Catagorys > ul {
		list-style-type: none;		
	}
	
	#Catagorys > ul >li {
		display: inline-block;
		margin-left: 15px;
		width: 100px;
		height: 30px;
		background-color: #C1AA92;
		padding-top: 5px;	
	}
	
	#Catagorys > ul >li > a {
		text-align: center;
	}
	
	div.productImg > img {
	   width: 100%;
	   height: 50%;
       position: relative;
       margin: 0 0 15px;
       text-align: center;
       overflow: hidden;
	} 
	
   #OrderList > ul > li {
	   display: inline-block;
   }
	
   span.delimiter , #OrderList > ul > li > a{
	  color : gray;
	  font-size : 10pt;
   }
	
   #OrderList > a:hover{
	  font-weight: bold;
	  border-bottom: 3px solid white;
	  border-bottom-color: #E9462B;
	  padding-bottom: 15px;
   }
   
   div.discription > ul > li {
   	  margin-bottom: 5px;
   }
   div.discription > ul > li > span {
   	  font-size: 10pt;
   } 
   
   div#ItemLists > div.container {
   
     width : 1380px;
   }
   
   .dot {
     height: 10px;
     width: 10px;
     border: 1px solid #DCDCDC;
     border-radius: 50%;
     display: inline-block;
   }   
   
   .productImgItem:hover {
   		
   		cursor: pointer;
   		
   }
   
</style>

<jsp:include page="header.jsp" />


<script type="text/javascript">
	$(document).ready(function(){
		
		$("span.pdNo").hide();
		
		// Top, Down 버튼 올렸을 때 CSS 지정
		$("#goTopBtn").hover(function(){
			$("#goTopBtn").css('cursor','pointer');
		},
		function(){
			
		});
		
		$("#goDownBtn").hover(function(){
			$("#goDownBtn").css('cursor','pointer');
		},
		function(){
			
		});		
		
		// TOP 버튼 스크롤 함수
		$(window).scroll(function() { 
			 if ($(this).scrollTop() > 500) { // 현재 세로 스크롤 값을 읽어서 500보다 크면
            	$('#goTopBtn').fadeIn();  //버튼 보이게
	         } 
		});
		
		
	    $("#goTopBtn").click(function() { // 보인 버튼 클릭시 
	        $('html, body').animate({ // html태그나, body 태그 위치로 세로스크롤을 0으로 지정하며 0.4초  동안 이동한다.
	            scrollTop : 0
	        }, 400);
	        return false;
	    });
	    
	    $("#goDownBtn").click(function() { // 보인 버튼 클릭시 
	        $('html, body').animate({ // html태그나, body 태그 위치로 세로스크롤을 0으로 지정하며 0.4초  동안 이동한다.
	            scrollTop : document.body.scrollHeight // 스크롤 하지 않았을 때의 전체 높이를 구한다 
	        }, 400);
	        return false;
	    });    
		
		var sort ="";
		
		// 전체 정렬 (신상품, 낮은가격, 높은가격 등) 이벤트 처리
		$("li.sort").click(function(){
			
			sort = $(this).attr('value');
			alert("함수 안쓰고 li태그의 sort클래스 클릭 시 value 값은?? " + sort);
			
			location.href="<%= ctxPath%>/TeamHomePage.neige?sort="+sort;			
				
		});	
		
		// 각각의 이미지를 클릭 했을 경우 
		$("img.productImgList").click(function(){
			
			var pdno = $(this).parent().parent().find(".pdNo").val();
			alert("pdno ==========> ?" + pdno);
			
		});	
	});
	
	
</script>


<br><br>

<!-- == Carousel 예제 == -->
<div class="container" align="center"> 
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <c:forEach items="${imageCarouselList}" varStatus="status" begin="0" end="4" >
        <c:if test="${status.index == 0 && status.index < 5 }">
	      <li data-target="#myCarousel" data-slide-to="${status.index}" class="active"></li>        	
        </c:if>
        <c:if test="${status.index > 0}">
	      <li data-target="#myCarousel" data-slide-to="${status.index}"></li>        
        </c:if>
      </c:forEach>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner">
      <c:forEach var = "imgvo" items="${imageCarouselList}" varStatus="status" begin="0" end="4" >
        <c:if test="${status.index == 0}">
        	<div class="item active">
        		<img src="<%= ctxPath %>/images/carousel/${imgvo.imgfilename}" style="width:100%;">
        	</div>
        </c:if> 
        <c:if test="${status.index > 0 && status.index < 5}">
        	<div class="item">
        		<img src="<%= ctxPath %>/images/carousel/${imgvo.imgfilename}" style="width:100%; ">
        	</div>
        </c:if>              
      </c:forEach>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</div>

<div id = "My_Title">
	<%-- <span>${sessionScope.gender}</span> --%>
	OUTER
</div>


<%-- 카테고리 --%>
<div id = "Catagorys">
	<ul id = "CategoryLists">
		<li class = "catego" >
			<a style = "color:white;" href = "<%= ctxPath %>/TeamHomePage.neige">전체</a>
		</li>
		
		<c:forEach var = "category" items = "${requestScope.categoryList}">
			<li class = "catego" >
				<a style = "color:white;" href = "<%= ctxPath %>/category/categorySelectList.neige?pdcategory_fk=${category.cgcode}">${category.cgname}  </a>
			</li>
		</c:forEach>
		 
	</ul>
</div>

<br> 
 
<div id = "Order">
	<div class="col-md-3" id="OrderList" style="margin-left: 1100px;">
		<ul>

			<li value= "sortNewProduct" class = "sort" id = "sortNewProduct">
				 <a href = "javascript:void(0);"> 신상품 </a> <span class="delimiter">&#124;</span>			
			</li>
			<li value= "sortLowPrice" class = "sort" id = "sortLowPrice">
					<a href = "javascript:void(0);"> 낮은가격 </a> <span class="delimiter">&#124;</span>		
	
			</li>
			<li value = "sortHighPrice" class = "sort" id= "sortHighPrice">
				<a href = "javascript:void(0);"> 높은가격 </a> <span class="delimiter">&#124;</span>		
			</li>			
			<li value = "sortBestProduct" class = "sort" id ="sortBestProduct">
				<!-- <a href = "<%= ctxPath %>/product/productBestList.neige?gender=${sessionScope.gender}"> 인기상품 </a>  -->	
				<a href = "javascript:void(0);"> 인기상품 </a>				
			</li>						
		</ul>
	</div>	
</div> 
 
<br><br>

<%-- 아이템 리스트 --%>
<div  id = "ItemLists" >
<div class = "container">
	<ul> 
	  <c:forEach var="productMainImageVo" items="${productMainImageList}" varStatus="status" >
		<li>
			<div class = "col-md-3 productItem" style="margin-bottom: 40px;" >
				<div class = "productImg">
					<img class = "productImgItem" onclick="javascript:window.open('<%= ctxPath%>/ProductDetail.neige?pdno=${productMainImageVo.pdno}', '_self')" src = "<%= ctxPath %>/images2/${productMainImageVo.pdimage1}"/> <!--  onclick = "goProductDetail();"/> -->
				</div>
				<div class = "discription">
					<ul>
						<%-- 제품이름 --%>
						<li><span class = "pName" style="font-weight: bold; font-size: 11.5pt">${productMainImageVo.pdname}</span></li>
						<%-- 컬러 리스트 넣는 부분 (반복문) --%>
						<li>
					    <span style="font-weight: bold; font-size: 10pt;">COLOR : </span>
						<c:forTokens var="item" items="${productMainImageVo.colores}" delims=",">
                            <span><%--${item} --%></span>
                            <span class="dot" style="background-color:${item}"></span>
						</c:forTokens>
						</li>
						<%-- <li style = "display: inline-block;"><span>${productMainImageVo.colores}</span></li> --%>
						<li>
							<span style="font-weight: bold;">판매가 :  </span>
								<c:if test="${productMainImageVo.price ne productMainImageVo.saleprice}">
									<span style="font-size:11px; text-decoration:line-through;">${productMainImageVo.price} </span>
							    </c:if>
							    <span>&nbsp;&nbsp;${productMainImageVo.saleprice}원</span>
                                <c:if test="${productMainImageVo.price ne productMainImageVo.saleprice}">
                                	<span class="badge badge-pill badge-warning" style="font-size: 8pt; background-color:lightcoral">SALE</span>
                                </c:if>									
						</li>						
						<li>
							<span class="pdNo">${productMainImageVo.pdno}</span>
						</li> 
					</ul>
				</div>
			</div>
		</li>
	   </c:forEach>
	</ul>
</div>
</div>
 
<%-- TOP & DOWN 버튼 --%>

<div style="position: absolute; right: 20px; bottom: 0px;">
	<ul>
		<li id = "goTopBtn" style = "height: 35px;">
			<span>
				<img src = "<%= ctxPath %>/images2/top_icon.jpg" style = "width: 30px; height: 30px;"/>
			</span>
		</li>
		<li id = "goDownBtn" style = "height: 35px;">	
			<span>
				<img src = "<%= ctxPath %>/images2/down_icon.jpg" style = "width: 30px; height: 30px;"/> 
			</span>
		</li>
	</ul>
</div>

<%-- div contents --%>

<jsp:include page="footer.jsp" />