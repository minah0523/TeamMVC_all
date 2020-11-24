<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
    //     /TeamMVC 
%>

<jsp:include page="../admin/adminMain.jsp" />

<style type="text/css">
   tr.memberInfo:hover {
   	  background-color: #e6ffe6;
   	  cursor: pointer;
   }
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		if( "${searchWord}" != "" ) {
			$("select#searchType").val("${searchType}");
			$("input#searchWord").val("${searchWord}");
		}
		
		$("input#searchWord").bind("keyup",function(event){
			if(event.keyCode == 13) { // 검색어에서 엔터를 하면 검색하러 가도록 한다.
				goSearch();
			}
		});
		
		
		// *** select 태그에 대한 이벤트는  click 이 아니라 change 이다. *** // 
		$("select#sizePerPage").bind("change", function(){
			goSearch();
		});
		
		if("${sizePerPage}" != "" ) {
			$("select#sizePerPage").val("${sizePerPage}");
		}
		
		
		$("tr.memberInfo").click(function(){
			var userid = $(this).children(".userid").text();
		//	alert(userid);
			location.href="<%= ctxPath%>/admin/memberOneDetail.neige?userid="+userid+"&goBackURL=${goBackURL}";            
		});
		
		
	}); // end of $(document).ready(function(){})-------------------

	
	function goSearch() {
		var frm = document.memberFrm;
		frm.action = "memberListAll.neige";
		frm.method = "GET";
		frm.submit();
	}
	
	
</script>

<h2 style="margin: 100px;">:::Member List :::</h2>
	
	<form name="memberFrm">
		<select id="searchType" name="searchType">
			<option value="name">회원명</option>
			<option value="userid">아이디</option>
			<option value="email">이메일</option>
		</select>
		<input type="text" id="searchWord" name="searchWord" />
		<input type="text" style="display: none;">
		<button type="button" onclick="goSearch();" style="margin-right: 30px;">검색</button>
		
		<span style="color: red; font-weight: bold; font-size: 12pt;">Page</span>
		<select id="sizePerPage" name="sizePerPage">
			<option value="10">10</option>
			<option value="5">5</option>
			<option value="3">3</option>
		</select>
    </form>

	<table id="memberTbl" class="table table-bordered" style="width: 90%; margin-top: 20px;">
        <thead>
        	<tr>
        		<th>아이디</th>
        		<th>회원명</th>
        		<th>이메일</th>
        		<th>성별</th>
        	</tr>
        </thead>
        
        <tbody>
			<%-- 일단은 페이징처리를 안한 관리자를 제외한 모든 회원정보를 조회하도록 한다. --%>
			<c:forEach var="mvo" items="${memberList}">
				<tr class="memberInfo">
					<td class="userid">${mvo.userid}</td>
					<td>${mvo.name}</td>
					<td>${mvo.email}</td>
					<td>
						<c:choose>
							<c:when test="${mvo.gender eq '1'}">
								남
							</c:when> 
							<c:otherwise>
								여
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>

    <div>
    	${pageBar}
    </div>    
        
<jsp:include page="../footer.jsp" />


        