<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	div.updateArea {
		width: 80%;
		margin: 100px 0 50px 0 ;
	}
	
</style>


<jsp:include page="../header.jsp" />
<script type="text/javascript">

	$(document).ready(function(){
		
		
	});
	
	function goUpdateNotice() {
		
		//id admin check-----------수정필요 //
	      
		 var bFlagRequiredInfo = false;
			
			$(".form-control").each(function(){
				var data = $(this).val();
				if(data == "") {
					bFlagRequiredInfo = true;
					alert("글 제목과 글 내용에 공란이 있습니다.");
					return false;  // break 라는 뜻이다.
				}
			});
			
			
		  if(!bFlagRequiredInfo) {
	         var frm = document.noticeEditFrm;
	         frm.method = "POST";
	         frm.action = "updateEnd.neige";
	         frm.submit();
	      }
	}
	

</script>


	<div class="updateArea">
		<div class="row">
			<form name="noticeEditFrm">
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글 수정 양식</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="글 제목" name="title" maxlength="50" value="${nvo.title}">
							<input type="hidden" name="noticeno" value="${nvo.noticeno}"></td>
						</tr>
						<tr>
							<td><textarea class="form-control" placeholder="글 내용" name="contents" maxlength="2048" style="height: 350px;" >${nvo.contents}</textarea></td>
						</tr>
					</tbody>
				</table>
					<button type="button" class="btn btn-primary pull-right" id="btnEdit" onClick="goUpdateNotice();">글수정</button> 
			</form>
		</div>
	</div>
	
<jsp:include page="../footer.jsp" />