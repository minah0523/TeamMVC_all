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
		width: 70%;
		margin: 100px 0 50px 0 ;
	}
	
	div.noticeTitleBox{
		align-content: center;
		text-align: center;
		color: rgb(85, 85, 85);
	}
	div#noticeTitleBox{
		margin: 50px 0;
	}
	
	div#noticeTitle{
		font-weight: bold;
		font-size: 16pt;
	}
	
	div#noticeTitleDisc{
		margin: 5px 0;
		font-size: 10pt;
	}
	
	tbody#noticeTbody td{
		font-size: 11pt;
	}
	
</style>

<jsp:include page="../header.jsp" />
<script type="text/javascript">

	$(document).ready(function(){
		
		
		
	});
	
	function RemoveWriteBtnForNonAdmins(){
		if (  ) {
			remove_action( 'media_buttons', 'media_buttons' );
		}
	}
		
</script>
	
<div class="noticeBoard">
	<div class="noticeTitleBox" id="noticeTitleBox">
		<div class="noticeTitleBox" id="noticeTitle">NOTICE</div>
		<div class="noticeTitleBox" id="noticeTitleDisc">NEIGE 공지사항</div>
	</div>
	<table class="table table-hover" style="text-align: center; border: 1px solid #dddddd">
		<thead>
			<tr>
				<th style="background-color: #f9f9f9; text-align: center; color: #353535">번호</th>
				<th style="background-color: #f9f9f9; text-align: center; color: #353535">제목</th>
				<th style="background-color: #f9f9f9; text-align: center; color: #353535">작성일</th>
			</tr>
		</thead>
		<tbody id="noticeTbody">
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
	
	<c:if test="${loginuser.userid eq 'admin'}">
		<div>
			<a href="<%= ctxPath %>/notice/write.neige" class="btn btn-primary pull-right" style="background-color: #BCA897; border-color: white;">글쓰기</a>
		</div>
	</c:if>
</div>

<jsp:include page="../footer.jsp" />