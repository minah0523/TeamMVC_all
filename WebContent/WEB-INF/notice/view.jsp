<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="notice.mdl.NoticeVO" %>
<%@ page import="notice.mdl.NoticeDAO" %>

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
	div.viewArea {
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
	
	function deleteCheck() {
		if ( confirm("정말로 삭제하시겠습니까?") == true) { //확인
			location.href="<%= ctxPath %>/notice/delete.neige?noticeno=${nvo.noticeno}";       
		}
		else{   //취소
		    return;
		}
		
	}

</script>

	<div class="viewArea">
	
	<div class="noticeTitleBox" id="noticeTitleBox">
		<div class="noticeTitleBox" id="noticeTitle">NOTICE</div>
		<div class="noticeTitleBox" id="noticeTitleDisc">NEIGE 공지사항</div>
	</div>
		<div class="row">
			<table class="table" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="width: 30%; background-color: #f9f9f9; text-align: center; color: #353535">글 제목</th>
						<th colspan="2" style="background-color: #f9f9f9; text-align: center; color: #353535">${nvo.title}</th>
					</tr>
				</thead>
				<tbody id="noticeTbody"	>
					<tr>
						<td>작성일자</td>
						<td colspan="2">${nvo.writeday}</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">내용</td>
						<td colspan="2" style="min-height: 200px; text-align: center;">${nvo.contents}</td>
					</tr>
				</tbody>
			</table>
			<a href="<%= ctxPath %>/notice/notice.neige" class="btn btn-primary" style="background-color: #BCA897; border-color: white;">목록</a>
			
			
			<c:if test="${loginuser.userid eq 'admin'}">
				<a href="<%= ctxPath %>/notice/update.neige?noticeno=${nvo.noticeno}" class="btn btn-primary" style="background-color: #BCA897; border-color: white;">수정</a>
				<span class="btn btn-primary" onclick="deleteCheck()" style="background-color: #BCA897; border-color: white;">삭제</span>
			</c:if>
			
		</div>
	</div>
	
	
<jsp:include page="../footer.jsp" />