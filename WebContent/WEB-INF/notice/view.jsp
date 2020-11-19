<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="notice.mdl.NoticeVO" %>
<%@ page import="notice.mdl.NoticeDAO" %>

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
		width: 80%;
		margin: 100px 0 50px 0 ;
	}
	
</style>


<jsp:include page="../header.jsp" />
<script type="text/javascript">

	$(document).ready(function(){
		
		
	});

</script>

	<div class="viewArea">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style="background-color: #eeeeee; text-align: center;">게시판 글 보기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%;">글 제목</td>
						<td colspan="2">${nvo.title}</td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2">${nvo.writeday}</td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2" style="min-height: 200px; text-align: left;">${nvo.contents}</td>
					</tr>
				</tbody>
			</table>
			<a href="<%= ctxPath %>/notice/notice.neige" class="btn btn-primary">목록</a>
			
			<a href="<%= ctxPath %>/notice/update.neige?noticeno=${nvo.noticeno}" class="btn btn-primary">수정</a>
			<a href="<%= ctxPath %>/notice/delete.neige?noticeno=${nvo.noticeno}" class="btn btn-primary">삭제</a>
			
		</div>
	</div>
	
	
<jsp:include page="../footer.jsp" />