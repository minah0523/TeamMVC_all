<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>

<%
	String ctxPath = request.getContextPath();
	//			/TeamMVC
%>


<style type="text/css">
	a, a:hover {
		color: #000000;
		text-decoration:none;
	}
	div.writeArea {
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
	
	

	function goWriteNotice() {
		
		//id admin check-----------수정필요 //
		
		
	    //// 필수입력사항에 모두 입력이 되었는지 검사한다  ////
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
			var frm = document.noticeInputFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath %>/notice/write.neige";
			frm.submit();
		}
		
		
	}
	

</script>

	
	<div class="writeArea">
		<div class="noticeTitleBox" id="noticeTitleBox">
			<div class="noticeTitleBox" id="noticeTitle">NOTICE</div>
			<div class="noticeTitleBox" id="noticeTitleDisc">공지사항 글쓰기는 관리자 권한입니다.</div>
		</div>
		<div class="row">
			<form name="noticeInputFrm"> 
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2" style="background-color: #f9f9f9; text-align: center; color: #353535">공지사항 글쓰기 양식</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="글 제목" name="title" maxlength="50"></td>
						</tr>
						<tr>
							<td><textarea class="form-control" placeholder="글 내용" name="contents" maxlength="2048" style="height: 350px;"></textarea></td>
						</tr>
					</tbody>
				</table>
					<button type="button" class="btn btn-primary pull-right" id="btnWrite"  onClick="goWriteNotice();" style="background-color: #BCA897; border-color: white;">글쓰기</button> 
			</form>
		</div>
	</div>
	
	
<jsp:include page="../footer.jsp" />