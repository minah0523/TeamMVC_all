<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
   String ctxPath = request.getContextPath();
//         /TeamMVC
%>

<jsp:include page="../header.jsp" />
<style>
table#tblMemberRegister {
	width: 93%;
	/* 선을 숨기는 것 */
	border: hidden;
	margin: 10px;
}

fieldset {
	padding: 15px;
}

div.titleArea2 {
	margin: 50px 0;
	height: 60px;
	border: solid 1px #e3e3e3;
	letter-spacing: 4px;
}

div #th {
	height: 60px;
	text-align: center;
	font-family: Times New Roman;
	font-size: 16pt;
	padding-top: 50px;
	padding-bottom: 30px;
	margin: 5px;
}

div.contentArea1 {
	margin: 50px 0;
	height: 60px;
	border: solid 1px #e3e3e3;
	letter-spacing: 13px;
}

div.contentArea1>div#pointArea {
	vertical-align: middle;
}

div.contentArea2>span#orderStatus {
	font-size: 10px;
	color: gray;
}

div.contentArea2 {
	width: 100%;
	margin: 50px 0 0 0;
	border: solid 1px #e3e3e3;
	letter-spacing: 13px;
	background-color: #F9F9F9;
	display: inline-block;
}

div#nanika {
	border: solid 1px #e3e3e3;
}

div#pointArea {
	width: 350px;
	float: left;
	padding: 15px;
}

div#orderNum {
	width: 350px;
	padding: 15px;
	float: right;
}

table#tblMemberEdit {
	width: 93%;
	/* 선을 숨기는 것 */
	border: hidden;
	margin: 10px;
}

table#tblMemberEdit td {
	/* border: solid 1px gray;  */
	border: solid 1px;
	border-color: #CCCCCC;
	line-height: 30px;
	padding-top: 10px;
	padding-bottom: 10px;
	padding-left: 15px;
	font-family: Times New Roman;
}

div .head {
	border: 1px solid gray;
	margin-top: 10px;
	margin-bottom: 30px;
	padding-top: 10px;
	padding-bottom: 50px;
}
</style>
<script>

	$(document).ready(function () {
		
	});


	function goMemberEdit(userid) {
		var frm = document.memberEditFrm;
		frm.userid.value = userid;
		
		frm.action = "memberEdit.neige";
		frm.method = "POST";
		frm.submit();
	}

</script>


<body>

	<form name="loginPageFrm">

		<fieldset>
			<div id="th" class="titleArea">My Page</div>

			<div style="font-size: 5px; color: gray;" class="titleArea2">
				<span style="text-align: center; font-size: 15pt; color: #F4A460;">
					${(sessionScope.loginuser).userid} </span> <span
					style="font-weight: lighter; font-size: 10pt; font-style: Franklin Gothic;">
					님은 [Neige]회원이십니다. </span>
			</div>




			<span> ::: 내 정보 ::: </span>

			<div id="nanika">

				<table id="tblMemberEdit" style="font-size: 10px; color: gray;">

					<tr>
						<td style="width: 20%; font-weight: bold;">성명</td>
						<td style="width: 80%; text-align: left;">
							${(sessionScope.loginuser).name}</td>
					</tr>

					<tr>
						<td style="width: 20%; font-weight: bold;">이메일</td>
						<td style="width: 80%; text-align: left;">
							${sessionScope.loginuser.email}</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;">연락처</td>
						<td style="width: 80%; text-align: left;">
							${(sessionScope.loginuser).mobile}</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;">우편번호</td>
						<td style="width: 80%; text-align: left;">
							${(sessionScope.loginuser).postcode}</td>
					</tr>
					<tr>
						<td style="width: 20%; font-weight: bold;">주소</td>
						<td style="width: 80%; text-align: left;">
							${(sessionScope.loginuser).address}
						   ,${(sessionScope.loginuser).detailaddress}
							${(sessionScope.loginuser).extraaddress}</td>
					</tr>


					<tr>
						<td colspan="2">

							<button type="button"
								style="color: white; height: 40px; width: 150px; border-radius: 4px; border: solid 1px #696969; margin-left: 500px; background: #696969; cursor: pointer;"
								id="btnMemberEdit"
								onclick="goMemberEdit('${sessionScope.loginuser.userid}')">내
								정보 수정</button>
						</td>
					</tr>
				</table>
			</div>



			<div style="font-size: 10px; color: gray;" class="contentArea1">
				<div id="pointArea">
					<span id="point">적립 포인트</span> <span style="color: orange;">${(sessionScope.loginuser).point}
						point </span>
					<button>조회</button>
				</div>
				<div>
					<span id="orderNum" style="float: right;">주문횟수</span>
				</div>
			</div>


			<div class="contentArea2">
				<span id="orderStatus">나의 주문처리 현황</span>
			</div>
			<div id="nanika">뭔가가 들어갑니다...</div>

		</fieldset>
	</form>


	<form name="memberEditFrm">
		<input type="hidden" name="userid" />
	</form>

</body>

<!-- <div class="row" id="divRegisterFrm">
	<div class="col-md-12" align="center">
	<form name="registerFrm">
	<fieldset>
	
	<table id="tblMemberRegister">
		
		<thead>
		<tr>
			<th colspan="2" id="th">My Page <span style="font-size: 10pt; "></span> </th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<th id="th2"><span  style="text-align:center;  font-size: 13pt; color:gray; ">이름</span>
			<span style=" sfont-weight: lighter; font-size: 10pt; font-style: Franklin Gothic;">님은 [Neige]회원이십니다. </span> </th>
		</tr>
	
		
	  
		</tbody>
	</table>
	</fieldset>
	</form>
	</div>
</div>
 -->

<jsp:include page="../footer.jsp" />