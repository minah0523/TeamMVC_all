<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
	//			/TeamMVC
%>

<style type="text/css">
	a, a:hover {
		color: #000000;
		text-decoration:none;
	}
	div.noticeBoard {
		width: 80%;
		margin: 100px 0 50px 0 ;
	}
	
</style>

<jsp:include page="../header.jsp" />
<script type="text/javascript">

	$(document).ready(function(){
		
		
	});

</script>
	
<div class="noticeBoard">
	<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
		<thead>
			<tr>
				<th style="background-color: #eeeeee; text-align: center;">번호</th>
				<th style="background-color: #eeeeee; text-align: center;">제목</th>
				<th style="background-color: #eeeeee; text-align: center;">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="nvo" items="${noticeList}">
				<tr class="memberInfo" style="cursor:pointer" onclick="javascript:window.open('<%= ctxPath%>/notice/view.neige?noticeno=${nvo.noticeno}', '_self')">
					<td>${nvo.noticeno}</td>
					<td>${nvo.title}</td>
					<td>${nvo.writeday}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div>
    	${pageBar}
    </div>   
	
	<br>
	
	<div>
		<a href="<%= ctxPath %>/notice/write.neige" class="btn btn-primary pull-right">글쓰기</a>
	</div>
</div>

<jsp:include page="../footer.jsp" />