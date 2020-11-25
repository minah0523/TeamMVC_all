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
	margin-top: 50px;
	margin-bottom: 80px;
}

.order_row, .order_title{
border: 1px solid #0A6BC6; 
border-collapse: collapse;
}

.order_title, .row_title {
	padding: 10px;
	background-color: #F3F8FD;
}

div.order_title {
	float:left;
}

.row_title {
	text-align: left;
	border-right: 1px solid #0A6BC6;
}

.row_data {
	text-align: left;
	padding-left: 10px;
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

			<h3 class="order_title" align="left">주문내역</h3>
			<h4 align="left">&nbsp;주문 감사합니다.</h4>
			<div class="main_order">
				<div>
					<div class="order_row">
						<div class="row_title" style="display: inline-block; width: 30%;">주문번호</div> 
						<div class="row_data" style="display: inline-block; width: 69%" id="orderno">${ovo.odrcode}</div>
					</div>
					<div class="order_row">
						<div class="row_title" style="display: inline-block; width: 30%;">주문자</div> 
						<div class="row_data" style="display: inline-block; width: 69%" id="amount">${ovo.userid_fk}</div>
					</div>
					<div class="order_row">
						<div class="row_title" style="display: inline-block; width: 30%;">총 가격</div> 
						<div class="row_data" style="display: inline-block; width: 69%" id="orderday">${ovo.odrtotalprice}</div>
					</div>
					<div class="order_row">
						<div class="row_title" style="display: inline-block; width: 30%;">적립금액</div> 
						<div class="row_data" style="display: inline-block; width: 69%" id="addPoint">${ovo.odrtotalpoint}</div>
					</div>
					<div class="order_row">
						<div class="row_title" style="display: inline-block; width: 30%;">주문일시</div> 
						<div class="row_data" style="display: inline-block; width: 69%" id="addPoint">${ovo.odrdate}</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<button type="button" id="btnHome" class="btn btn-warning btns" onclick="location.href='http://localhost:9090/TeamMVC/TeamHomePage.neige'">HOME</button>
	<div></div>
</body>
</html>

<jsp:include page="../footer.jsp" />
