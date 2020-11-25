<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
      String ctxPath = request.getContextPath();
            //      /TeamMVC
   %>

<!DOCTYPE html>
<html>
<head>

<jsp:include page="../header.jsp" />

<title>주문 완료 페이지</title>

<style type="text/css">

.widthCtrl {
	width: 40%;
}

button.btns {
	margin-top: 80px;
	margin-bottom: 80px;
}

table, tr, td {
	border: 1px solid black;
}

.tbl_data {
background-color: #F3F8FD; 
padding-top:5px; 
padding-bottom:5px; 
border-top: 2px solid #0A6BC6; 
border-bottom: 2px solid #0A6BC6;
}

</style>

</head>
<body>
	<br><br><br>
	<div id="contentsHelpArea" class="widthCtrl">
		<div>
			<h1>【주문 승인】 주문이 완료되었습니다</h1>
			<div>
				<p>저희 쇼핑몰을 이용해주셔서 감사합니다.</p>
			</div>

			<h3 class="tbl_data" align="left">주문내역</h3>
			<h4 align="left">주문 감사합니다.</h4>
			<div class="mainimage">
				<table id="main_content">
					<tr align="left"> 
						<td>주문완료</td>
					</tr>
					<tr align="left"> 
						<td></td>
					</tr>
					<tr align="left"> 
						<td></td>
					</tr>
					<tr align="left"> 
						<td></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<button type="button" id="btnHome" class="btn btn-warning btns" onclick="location.href='http://localhost:9090/TeamMVC/TeamHomePage.neige'">HOME</button>
	<button type="button" id="btnLogin" class="btn btn-warning btns" onclick="location.href='http://localhost:9090/TeamMVC/login/loginPage.neige'">Login</button>
	<div></div>

</body>
</html>

<jsp:include page="../footer.jsp" />
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
      String ctxPath = request.getContextPath();
            //      /TeamMVC
   %>

<!DOCTYPE html>
<html>
<head>

<jsp:include page="../header.jsp" />

<title>주문 완료 페이지</title>

<style type="text/css">

.widthCtrl {
	width: 40%;
}

button.btns {
	margin-top: 80px;
	margin-bottom: 80px;
}

table, tr, td {
	border: 1px solid black;
}

.tbl_data {
background-color: #F3F8FD; 
padding-top:5px; 
padding-bottom:5px; 
border-top: 2px solid #0A6BC6; 
border-bottom: 2px solid #0A6BC6;
}

</style>

</head>
<body>
	<br><br><br>
	<div id="contentsHelpArea" class="widthCtrl">
		<div>
			<h1>【주문 승인】 주문이 완료되었습니다</h1>
			<div>
				<p>저희 쇼핑몰을 이용해주셔서 감사합니다.</p>
			</div>

			<h3 class="tbl_data" align="left">주문내역</h3>
			<h4 align="left">주문 감사합니다.</h4>
			<div class="mainimage">
				<table id="main_content">
					<tr align="left"> 
						<td>주문완료</td>
					</tr>
					<tr align="left"> 
						<td></td>
					</tr>
					<tr align="left"> 
						<td></td>
					</tr>
					<tr align="left"> 
						<td></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<button type="button" id="btnHome" class="btn btn-warning btns" onclick="location.href='http://localhost:9090/TeamMVC/TeamHomePage.neige'">HOME</button>
	<button type="button" id="btnLogin" class="btn btn-warning btns" onclick="location.href='http://localhost:9090/TeamMVC/login/loginPage.neige'">Login</button>
	<div></div>

</body>
</html>

<jsp:include page="../footer.jsp" />
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
