<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
   String ctxPath = request.getContextPath();
   //         /TeamMVC
%>



<span>${sessionScope.gender}</span>

<c:if test="${sessionScope.gender == 1}" >
   <jsp:include page="../CommonManMain.jsp" />
</c:if>

<c:if test="${sessionScope.gender == 2}" >
   <jsp:include page="../CommonWomenMain.jsp" />
</c:if>

<c:if test="${sessionScope.gender == null}" >
   <jsp:include page="../CommonWomenMain.jsp" />
</c:if>

<style type="text/css">
   
   .dot {
     height: 10px;
     width: 10px;
     border: 1px solid #DCDCDC;
     border-radius: 50%;
     display: inline-block;
   }      
          
   
</style>


<%-- 아이템 리스트 --%>

<div  id = "ItemLists" >
<div class = "container">
   <ul> 
     <c:forEach var="categoryProducClickListVo" items="${categoryProducClickList}" varStatus="status" >
      <li>
         <div class = "col-md-3" style="margin-bottom: 50px;" >
            <div class = "productImg">
               <img src = "<%= ctxPath %>/images2/${categoryProducClickListVo.pdimage2}" /> 
            </div>
            <div class = "discription">
               <ul>
                  <li><span class = "pName" style="font-weight: bold; font: " >${categoryProducClickListVo.pdname}</span></li>
                  <li> 
                  <c:if test="${categoryProducClickListVo.price != categoryProducClickListVo.saleprice }"> 
                        <span style = "text-decoration: line-through;">정상가 : ${categoryProducClickListVo.price}원</span>
                  </c:if> <%-- 정상가와 세일가가 같지 않으면 정상가만 출력하고 같으면 판매가만 출력 --%>
                        <span>&nbsp;&nbsp;판매가 : ${categoryProducClickListVo.saleprice}원</span>
                  </li>
                  <%-- 컬러 리스트 넣는 부분 (반복문) --%>
                  <c:forTokens var="item" items="${categoryProducClickListVo.colores}" delims=",">
                            <span>${item}</span>
                            <span class="dot" style="background-color:${item}"></span>
                  </c:forTokens>                 
               </ul>
            </div>
         </div>
      </li>
      </c:forEach>
   </ul>
</div>
</div>
 
 
<jsp:include page="../footer.jsp" />
