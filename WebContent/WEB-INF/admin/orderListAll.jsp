<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%
    String ctxPath = request.getContextPath();
    //    /TeamMVC
%>    

<style type="text/css">
	a, a:hover {
		color: #000000;
		text-decoration:none;
	}
	div.orderListBoard {
		width: 100%;
		margin: 30px -150px 50px 0;
	}
	
	h3#title {
		margin-bottom: 25px;
		text-align: center; 
		font-size: 14pt; 
		font-family:Times New Roman;
		font-weight: bold;
		
	}
	
	thead#tableHead > tr {
		background-color: #F8ECDE;
	}
	thead#tableHead > tr > th  {
		text-align: center;
		color: #7B5232;
	}
	tbody#tableBody > tr > td {
		/*border-top:hidden;*/
		border-bottom: solid 1px #ddd;
		border-right: solid 1px #ddd;
		
	}	
	
</style>

<jsp:include page="adminMain.jsp" />

<script type="text/javascript">

	$(document).ready(function(){
		
		
	});

</script>

<div class="orderListBoard col-ms-12">
  <h3 id = "title">주문 리스트</h3>
	<table class="table" style="text-align: center; border: solid 0px white;">
		<thead id = "tableHead">
			<tr>
				<th style="text-align: center;">주문상세번호</th>
				<th style="text-align: center;">주문번호</th>
				<th style="text-align: center;">주문일자</th>
				<th style="text-align: center;">주문자</th>
				<th style="text-align: center;">상품명</th>
				<th style="text-align: center;">결제금액</th>
				<th style="text-align: center;">배송상태</th>
				<th style="text-align: center;">배송완료일자</th>
			</tr>
		</thead>
		<tbody id = "tableBody">
			
			<c:forEach var="adminOrdervo" items="${adminOrderList}">
				<tr class="orderInfo" style="cursor:pointer" >
					<td>${adminOrdervo.odrseqnum}</td>
					<td>${adminOrdervo.fk_odrcode}</td>
					<td>${adminOrdervo.ordervo.odrdate}</td>
					<td>${adminOrdervo.membervo.name}</td>
					<td>${adminOrdervo.productvo.pdname}</td>
					<td><fmt:formatNumber value="${adminOrdervo.odrprice}" pattern="###,###" />원</td>
					<c:if test="${adminOrdervo.deliverstatus eq '1'}">
						<td>주문완료</td>
					</c:if>
					<c:if test="${adminOrdervo.deliverstatus eq '2'}">
						<td>배송중</td>
					</c:if>
					<c:if test="${adminOrdervo.deliverstatus eq '3'}">
						<td>배송완료</td>
					</c:if>		
					<c:if test="${adminOrdervo.deliverdate eq null}">
						<td style = "border-right: hidden;">-</td>	
					</c:if>		
					<c:if test="${adminOrdervo.deliverdate ne null}">
						<td style = "border-right: hidden;">${adminOrdervo.deliverdate}</td>	
					</c:if>																		
									
				</tr>
			</c:forEach> 
			 
		</tbody>
	</table>
	
	<div>
    	${pageBar}
    </div>   

</div>


<jsp:include page="../footer.jsp" />

