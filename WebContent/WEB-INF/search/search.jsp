<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
   String ctxPath = request.getContextPath();
   //         /TeamMVC
%>

<jsp:include page="../header.jsp" />

<style>
   div#Menu_Items{
      visibility:hidden;
   }
   div#searchArea{
      color: gray;
   }
   div.titleArea{
      margin: 50px 0 50px;
      color: #526B8E;
       width: 180px;
       padding: 15px font-size: 17pt;
       margin: 30px;
       height: 30px;
       border-bottom: solid 3px #e3e3e3;
       letter-spacing: 4px;
   }
   div.searchBox{
      width: 50%;
      margin: 40px;
      padding: 40px;
      border: 1px solid #e5e5e5;
      color: #353535;
      font-size: 11pt;
   }
   fieldset{
       width: 60%;
       margin: 0 auto;
       padding: 0 47px;
   }

   div.genderRadio > div{
      display: inline;
      margin: 30px 0;
   }
   div.genderRadio > span {
      font-weight: bold; 
      text-align: left;
   }
   div.genderRadio  > div > label{
      text-align: left;      
      margin: 10px;
      font-weight: normal;
   }
   div.item > label{
      float: left;
        clear: left;
       padding: 5px 10px 0 0;
       text-align: left;
      margin: 0 0 10px 0;
   }
   label#category{
      letter-spacing: 3px;
   }
   label#keyword{
      letter-spacing: 2px;
   }
   select#category{
      width: 70%;
      margin: 0 0 10px 0;
      float: right;      
   }
   input#keyword{
      width: 70%;
      margin: 0 0 10px 0;
      float: right;
   }
   button#search{
      background-color: gray;
      margin: 10px 0;
      color: white; 
   }
   div.container{
      width: 1380px;   
      font-size: 11pt;
   }
   ul.orderBy{
      font-size: 10pt;
      text-align: right;
      padding: 10px;
      margin: 30px 60px;
   }
   ul.orderBy>li{
      padding: 0 ;
      display: inline;
   }
   ul{
      padding-inline-start: 0px;
       list-style-type: none;
   }
   ul.productList>li#box{
       display: block;
       font-size:13px;
       line-height: 150%;
   }
   div.col-md-3{
       margin: 0 0 50px 0;
   }
   div.item{
      margin: 8px 0 0;
       color: #353535;
       line-height: 20px;
   }
   img{
      width: 280px;
      height: 350px;
       position: relative;
       margin: 0 0 15px;
       text-align: center;
       overflow: hidden;
   }   
   div.pname{
      font-weight: bolder;
   }
   .dot {
     height: 12px;
     width: 12px;
     border: 1px solid #DCDCDC;
     border-radius: 50%;
     display: inline-block;
   }
   button#showMore{
      background-color: #F5F5F5;
      color: gray; 
      margin: 0 auto;
   }
   li.sort > span {
   		margin: 0 5px; 
   }


</style>

<script type="text/javascript">

   $(document).ready(function(){
       
      /* 검색 버튼을 누른경우 */
       $("button#search").click(function(){
          var gender = $("input[name='gender']:checked").val();
          var category = $("select#category").val();
          var searchname = $("input#keyword").val();

          location.href="<%= ctxPath%>/search/SearchPage.neige?pdgender="+gender+"&pdcategory_fk="+category+"&searchname="+searchname;
       });
       
      // 눌렀던 항목들 그대로 유지시키기
      if( ${requestScope.pdgender != null || requestScope.pdgender != "0"} ){
          $("input:radio[name='gender']:radio[value='${requestScope.pdgender}']").prop('checked', true);
       }
       
       if( ${requestScope.pdcategory_fk != null} ) {
          $("select#category").val("${requestScope.pdcategory_fk}");
       } 
      
      if( ${requestScope.searchname != null} ) {
         $("input#keyword").val("${requestScope.searchname}");
      } 
      
      if ( ${requestScope.searchCount != null} ) {
         $("span#searchno").html("${requestScope.searchCount}");
      }
      
      
      /* 정렬 버튼중 하나를 누른경우 */
      $("li.sort").click(function(){
          var gender = $("input[name='gender']:checked").val();
          var category = $("select#category").val();
          var searchname = $("input#keyword").val();
          // 클릭된 것이 무엇인지 알아오는 변수
          var sort = $(this).attr("id");
          location.href="<%= ctxPath%>/search/SearchPage.neige?pdgender="+gender+"&pdcategory_fk="+category+"&searchname="+searchname+"&sort="+sort;         
       });
      
      
       // 정렬 클릭한 것 bold처리하기 
	       if( ${requestScope.sort != null} ) {
	    	   var sort = "${requestScope.sort}"; 
	            
	    	    if(sort == "sortNewProduct") {
	 				$("li#sortNewProduct").css('font-weight','bold');
			 	}
			 	else if(sort == "sortLowPrice") {
			 		 $("li#sortLowPrice").css('font-weight','bold');
			 	}
			 	else if(sort == "sortHighPrice") {
			 		 $("li#sortHighPrice").css('font-weight','bold');
			 	}
			 	else if(sort == "sortBestProduct") {
			 		 $("li#sortBestProduct").css('font-weight','bold');
			 	}
       }
       
       
    	// *** select 태그에 대한 이벤트는  click 이 아니라 change 이다. *** // 
		$("select#sizePerPage").bind("change", function(){
			goSearch();
		});
		
		if("${sizePerPage}" != "" ) {
			$("select#sizePerPage").val("${sizePerPage}");
		}
    
   }); // end of $(document).ready()------------------------
   
   function goSearch() {
		var frm = document.memberFrm;
		frm.action = "search/SearchPage.neige";
		frm.method = "GET";
		frm.submit();
	}

	 

</script>


<%-- 검색 디스플레이 --%>
<div id="searchArea">
   <div class="titleArea">SEARCH ITEMS</div>
   <div class="searchBox">
   <fieldset>
      <div class="genderRadio">
         <span id="gender" style="margin: 0 30px 0 0 ">성  별</span>
	         <div id="all" ><label for="0">전체</label><input name="gender" type="radio" id="0" value="0" checked="checked"></div>
	         <div id="women"><label for="2">여성</label><input name="gender" type="radio" id="2" value="2"></div>
	         <div id="men"><label for="1">남성</label><input name="gender" type="radio" id="1" value="1"></div>
      </div>
      <div class="item">
         <div class="category">
            <div class="form-group">
               <label for="category">카테고리</label>
               <select id="category" name="category">
                  <option value="0">전체</option>
                  <option value="1">코트</option>
                  <option value="2">자켓</option>
                  <option value="3">점퍼</option>
                  <option value="4">무스탕</option>
                  <option value="5">가디건</option>
               </select>
            </div>
         </div>
      </div>
      <div class="item">
         <div class="keyword">
            <div class="form-group">
               <label for="keyword">검색 키워드</label>
               <input id="keyword" name="keyword" type="text">   
            </div>
         </div>
      </div>
      <div>
         <button type="button" class="btn btn-block" id="search">검색</button>
      </div>
   </fieldset>
   </div>
   
<%-- 검색된 정보 및 상품리스트 --%>
<div id="cateProductList">
   
   <%-- 검색 결과 개수 --%>
   <div class="searchResult" >
      <p class="record">총 <span id="searchno" style="font-weight: bold;"></span>개의 상품이 검색되었습니다.</p>
   </div>
   
   <%-- 정렬 순서 --%>
   <div class="orderBy">
      <ul class="orderBy">
         <li class="sort" value="sortNewProduct" id="sortNewProduct">
            <a href="javascript:void(0);" style="color: black;">신상품순</a><span class="delimiter">&#124;</span> 
         </li>
         <li class="sort" value="sortLowPrice" id="sortLowPrice">
            <a href="javascript:void(0);" style="color: black;">낮은가격순</a><span class="delimiter">&#124;</span> 
         </li>
         <li class="sort" value="sortHighPrice" id="sortHighPrice">
            <a href="javascript:void(0);" style="color: black;">높은가격순</a><span class="delimiter">&#124;</span> 
         </li>
         <li class="sort" value="sortBestProduct" id="sortBestProduct">
            <a href="javascript:void(0);" style="color: black;">인기상품순</a> 
         </li>
      </ul>
   </div>
   
   <%-- 상품리스트 보여주는 부분 --%>
   <div class="container">
   
      <ul class="productList"> 
      		  <%-- 일단은 페이징처리를 안한 관리자를 제외한 모든 검색리스트를 조회하도록 한다. --%>
              <c:forEach var="pvo" items="${searchProductList}" varStatus="status" >
               <li id="box">
                  <div class = "col-md-3">
                     <%-- 이미지 onmouseover onmouseout --%>
                     <div class="slideshow-container">
                        <div class="productImg" style="cursor:pointer">
                        <%-- <img src="기본 이미지 주소" onmouseover="this.src='마우스 오버 상태의 이미지 주소'" onmouseout="this.src='기본 이미지 주소'"> --%>
                          <img style="cursor:pointer" onclick="javascript:window.open('<%= ctxPath%>/ProductDetail.neige?pdno=${pvo.pdno}', '_self')" src="<%= ctxPath%>/images/${pvo.pdimage1}" onmouseover="this.src='<%= ctxPath%>/images/${pvo.pdimage2}'" onmouseout="this.src='<%= ctxPath%>/images/${pvo.pdimage1}'">
                        </div>
                 	 </div>
                     <div class = "discription">
                     <%-- 제품이름 --%>
                     <div style="cursor:pointer; font-weight: bold;" onclick="javascript:window.open('<%= ctxPath%>/ProductDetail.neige?pdno=${pvo.pdno}', '_self')" id="pname" style="color:#333; font-size:11.5pt; font-weight:bold ; line-height:170%">
                         ${pvo.pdname}        
                     </div>
                     <div>
                        <%-- 컬러 리스트 넣는 부분 (반복문) --%>
                        <span style="font-weight: bold;">COLOR : </span> 
                        <c:forTokens var="item" items="${pvo.colores}" delims=",">
                            <span><%-- ${item} --%></span>
                            <span class="dot" style="background-color:${item}"></span>
                        </c:forTokens>
                        <%-- 사이즈 리스트 넣는 부분 (반복문) --%>
                        <%-- 사이즈 구성 :  --%>
                        <span style="font-weight: bold;"> &nbsp;&nbsp;SIZE : </span> 
                        <c:forTokens var="item" items="${pvo.sizes}" delims=",">
                            ${item}
                        </c:forTokens>
                      </div>
                              <span style="font-weight: bold;">판매가 :  </span><c:if test="${pvo.price ne pvo.saleprice}"><span style="font-size:11px; text-decoration:line-through;">${pvo.price} </span></c:if><span>&nbsp;&nbsp;${pvo.saleprice}원</span>
                              <c:if test="${pvo.price ne pvo.saleprice}"><span class="badge badge-pill badge-warning" style="font-size: 8pt; background-color:lightcoral">SALE</span></c:if>
                              <c:if test="${pvo.price eq pvo.saleprice}"><span class="badge badge-pill badge-warning" style="font-size: 8pt; background-color:lightblue">NEW</span></c:if>
                       </div>
                  </div>
               </li>
              </c:forEach>
	      </ul>
	   </div>
	<div>
    	${pageBar}
    </div>    
</div>
</div>

<jsp:include page="../footer.jsp" />
