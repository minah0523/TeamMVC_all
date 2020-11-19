<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="notice.mdl.NoticeVO"%>
<%@ page import="notice.mdl.NoticeDAO"%>
<%@ page import="java.io.PrintWriter"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<%
	String ctxPath = request.getContextPath();
	//			/TeamMVC
%>

<jsp:include page="../header.jsp" />
<script type="text/javascript">

	$(document).ready(function(){
		
		
	});

</script>

	<%
		String userID = null;
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");
		} 
		int noticeno = 0;
		if (request.getParameter("noticeno") != null) {
			noticeno = Integer.parseInt(request.getParameter("noticeno"));
		}
		if (noticeno == 0) 	{
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.')");
		script.println("location.href = 'notice.jsp'");
		script.println("</script>");
		}
		NoticeVO notice = new NoticeDAO().getNotice(noticeno);
		
		NoticeDAO noticeDAO = new NoticeDAO();
		int result = noticeDAO.delete(noticeno);
		if (result == -1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('글 삭제에 실패했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'notice.jsp'");
			script.println("</script>");
		}	
		
	%>
<jsp:include page="../footer.jsp" />