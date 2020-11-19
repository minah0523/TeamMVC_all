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
		background-color: #a0a0a0;
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
   	
</style>

<jsp:include page="headerMan.jsp" />


<script type="text/javascript">
	$(document).ready(function(){
		
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
	    
		// 카테고리 리스트 클릭시(신상품, 낮은가격, 높은가격 등) 이벤트 처리
		$("li.sort").click(function(){
			
			var sort = $(this).attr('value');
			alert("남자 test~~~~ " + sort);
			
			var gender = ${sessionScope.gender};
			var pdcategory_fk = ${sessionScope.pdcategory_fk};
			
			location.href="<%= ctxPath%>/category/categorySelectList.neige?pdcategory_fk="+pdcategory_fk+"&sort="+sort+"&gender="+gender;
		});
		
		// 카테고리 코드 받아서 클릭 했을때 클릭 상태 표시를 위해서 배경색 변경
		var cate = ${sessionScope.pdcategory_fk};
		alert("카테고리 => " + cate);
		 
		if(cate == "1") {
			 $("li.1").css('background-color','#666666');
		}
		else if(cate == "2") {
			 $("li.2").css('background-color','#666666');
		}
		else if(cate == "3") {
			 $("li.3").css('background-color','#666666');
		}
		else if(cate == "4") {
			 $("li.4").css('background-color','#666666');
		}
		else if(cate == "5") {
			 $("li.5").css('background-color','#666666');
		}	 
		 
		
	});
	
</script>


<br><br>

<!-- == Carousel 예제 == -->
<div class="container" align="center"> 
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <c:forEach items="${imageCarouselList}" varStatus="status">
        <c:if test="${status.index == 5 }">
	      <li data-target="#myCarousel" data-slide-to="${status.index}" class="active"></li>        	
        </c:if>
        <c:if test="${status.index > 5}">
	      <li data-target="#myCarousel" data-slide-to="${status.index}"></li>        
        </c:if>
      </c:forEach>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner">
      <c:forEach var = "imgvo" items="${imageCarouselList}" varStatus="status">
        <c:if test="${status.index == 5}">
        	<div class="item active">
<%--         		<span>${status.index}</span>
        		<span>${imgvo.imgno}</span> --%>
        		<img src="<%= ctxPath %>/images/carousel/${imgvo.imgfilename}" style="width:100%; height: 80%">
        	</div>
        </c:if> 
        <c:if test="${status.index > 5}">
        	<div class="item">
<%--         		<span>${status.index}</span>
        		<span>${imgvo.imgno}</span> --%>
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
<!-- == Carousel 예제 끝== -->

<div id = "My_Title">
	OUTER
</div>


<%-- 카테고리 --%>
<div id = "Catagorys">
	<ul id = "CategoryLists">
		<li>
			<a style = "color:white;" href = "<%= ctxPath %>/ManMainPage.neige">전체</a>
		</li>
		
		<c:forEach var = "category" items = "${requestScope.categoryList}" varStatus = "status">
			<li class="${status.count}">
				<a style = "color:white;" href = "<%= ctxPath %>/category/categorySelectList.neige?pdcategory_fk=${category.cgcode}">${category.cgname}</a>
			</li>
		</c:forEach>
		 
	</ul>
</div>

<br>

<%-- 정렬 : 판매순, 가격순, 신상품  --%>

 <div id = "Order">
	<div class="col-md-3" id="OrderList" style="margin-left: 1100px;">
		<ul>
			<li value= "sortNewProduct" class="sort" id = "sortNewProduct">
				<c:if test="${sessionScope.sort eq 'sortNewProduct'}">
					<a style="font-weight: bold;" href = "javascript:void(0);"> 신상품 </a> <span class="delimiter">&#124;</span>			
				</c:if>
				<c:if test="${sessionScope.sort ne 'sortNewProduct'}">
					<a href = "javascript:void(0);"> 신상품 </a> <span class="delimiter">&#124;</span>			
				</c:if>				
			</li>
			<li value= "sortLowPrice" class="sort" id = "sortLowPrice">
				<c:if test="${sessionScope.sort eq 'sortLowPrice'}">
					<a style="font-weight: bold;" href = "javascript:void(0);"> 낮은가격 </a> <span class="delimiter">&#124;</span>			
				</c:if>
				<c:if test="${sessionScope.sort ne 'sortLowPrice'}">
					<a href = "javascript:void(0);"> 낮은가격 </a> <span class="delimiter">&#124;</span>			
				</c:if>					
			</li>
			<li value = "sortHighPrice" class="sort" id= "sortHighPrice">
				<c:if test="${sessionScope.sort eq 'sortHighPrice'}">
					<a style="font-weight: bold;" href = "javascript:void(0);"> 높은가격 </a> <span class="delimiter">&#124;</span>			
				</c:if>
				<c:if test="${sessionScope.sort ne 'sortHighPrice'}">
					<a href = "javascript:void(0);"> 높은가격 </a> <span class="delimiter">&#124;</span>			
				</c:if>						
			</li>			
			<li value = "sortBestProduct" class="sort" id ="sortBestProduct">
				<a href = "<%= ctxPath %>/product/productBestList.neige?gender=${sessionScope.gender}"> 인기상품 </a>					
			</li>						
		</ul>
	</div>	
</div>

<br><br>
 
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
