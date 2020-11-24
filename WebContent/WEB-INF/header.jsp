<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
    //    /TeamMVC
%>
<!DOCTYPE html>
<html>
<head>

<title>:::HOMEPAGE:::</title>
 
<style type="text/css">
	
	html, #body {
		width: 100%;
		height: 100%;
		padding : 0;
		margin : 0;
		overflow-x: hidden; 
		/* 가로 바 안보이게 지정 */
	}

	div#headerImg{
		/* background-color: black; */
		background-color: #F8ECDE;
	}
	
	div#headerMenuBar{
		background-color: white;
		border: none;
	}	
	
	#Sist_Logo {
		margin-left: 150px;
		width: 100px;
		height: 40px;
		cursor: pointer;
	}
	
	#Sist_Logo > ul >li  > a{
		margin-left: 20px;
		/*color: white;*/
		color: #7B5232;
		font-size: 16pt;
		font-weight: bold;
	}	
	
	#Gender_Category > ul> li > a{
		margin-left: 50px;
		color: #7B5232;
		font-weight: bold;
		font-size: 16pt;
	}
	
	#neige > a:hover, #Gender_Category> ul > li > a:hover {
		color: #7B5232;
		font-weight: bold;
		border-bottom: 3px solid white;
		border-bottom-color: #8b4513;
		padding-bottom: 9px;
		cursor:pointer;
	}
	
	#Notice {
		margin-left : 80px;
	}

	#Main_Search_Input {
		border: solid 3px #BCA897;	
		width: 250px;
		height: 30px;
		background-image: url("/TeamMVC/images2/icon_search.png");
		background-size: 30px 30px;
		background-position: 200px;
		background-repeat: no-repeat;
		border-radius: 30px;
	}
	
	/* input 박스를 클릭 했을떄 나오는 테두리 색상 없애기 */
	#Main_Search_Button:focus, #Main_Search_Input:focus {
		outline: none;
	}

	
	#Login_MyInfo_Cart > a{
		color : black;
		font-size : 11pt;
	}
	
	#Login_MyInfo_Cart > a:hover{
		font-weight: bold;
		color : black;
		border-bottom: 3px solid black;
		border-bottom-color: black;
		padding-bottom: 15px;
	}
	
	#First_Menu {
		margin-left: 220px;
		width: 100px;
	}
	
	.MainMenu {
		height: 45px;
		margin-top: 30px;
	} 
	
	 #Menu_Items > a:visited{
		color: black;
	}
	
	#contents {
		width: 100%;
		height: 100%;
	}
	
	.delimiter {
		color:black;
	}
	
	#Login_MyInfo_Cart > a.delimiter:hover {
		border-bottom-color: none;
	}
	
	nav#nav {
		background-color: white;
	}

	#header_gender, #header_gender > li {
		display: inline-block;
		list-style-type: none;		
	}	

</style>


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/style.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.11.4.custom/jquery-ui.css" />
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.11.4.custom/jquery-ui.js"></script>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("#Main_Search_Input").keydown(function(key) {
            //키의 코드가 13번일 경우 (13번은 엔터키)
            if (key.keyCode == 13) {
                goSearchProduct();
            }
        });		
		
	});
	
	function manClick() {
		$("li#man").click(function(){
		
			var manVal = $(this).attr('value');
			// alert("man"+ manVal);
			
			// 세션 스토리지 저장
			sessionStorage.setItem("gender", manVal);
						
			location.href="<%= ctxPath %>/ManMain.neige?gender=" + manVal;
			
		});
	}
	
	function womenClick() {
		$("li#women").click(function(){
			
			
			
			var womenVal = $(this).attr('value');
			// alert(womenVal);
			
			// sessionStrorage.setItem("gender", womanVal);
			
			location.href="<%= ctxPath %>/TeamHomePage.neige?gender=" + womenVal;
			
		});
	}
	
	function neigeClick() {
		
		$("li#neige").click(function(){
			
			var neigeVal = $(this).attr('value');
			// alert("하하 => " + neigeVal);
			
			location.href="<%= ctxPath %>/TeamHomePage.neige?gender=" + neigeVal;
			
		});
		
	}
	
	function goSearchProduct(){
		
		var gender = ${sessionScope.gender};
		var keyword = $('#Main_Search_Input').val();
		
		location.href="<%= ctxPath %>/search/SearchPage.neige?pdgender="+gender+"&pdcategory_fk=0&searchname="+keyword;
	}	

</script>

</head>
<body id = "body">

<div id = "container">
<nav id = "nav" class="navbar navbar-inverse navbar-fixed-top" style = "border: none;">
	<div id="headerImg">
		<div class="row MainRow">
			<div class="col-md-3" id = "Sist_Logo" >
				<ul>
					<li value = "2" id = "neige" style="list-style-type: none;">
						<a style="cursor:pointer;" href="javascript:void(0);" onclick="neigeClick();">NEIGE</a>
					</li>
				</ul>
			</div>
			
			<div class="col-md-3" id="Gender_Category">
				<ul id="header_gender">
					<li value = "1" id= "man">
						<a href ="javascript:void(0);" onclick="manClick();">MEN</a>
					</li>
					<li value = "2" id= "women">
						<a href="javascript:void(0);" onclick="womenClick();">WOMEN</a>
					</li>
				</ul>
			</div>
			
			<div class="col-md-3" id = "Gender_Category">
				<input type="text" placeholder="&nbsp;&nbsp;Search " name="Main_Search_Input" id="Main_Search_Input" />
				<button type="button" id="Main_Search_Button"
						style =  "background-color: #BCA897; border: none; color:#fff;
							      text-align: center; text-decoration: none; padding: 5px;
							      display: inline-block; font-size: 16px; cursor: pointer;
							      border-radius: 5px;"
						onclick = "goSearchProduct();">검색
				</button>
			</div>
			
			<div class="col-md-4" id="Login_MyInfo_Cart">
				<a id = "Notice" href="<%= ctxPath %>/notice/notice.neige" >공지사항 </a> <span class= "delimiter" >|</span>
				
				<a id = "Cart" href="<%= ctxPath %>/product/productCart.neige" >장바구니 </a> <span class= "delimiter" >|</span>
				
				<c:if test="${!empty sessionScope.loginuser}">
                     <a href="<%= ctxPath %>/member/myPage.neige">마이페이지 </a>
                     <span class="delimiter">|</span>
                </c:if>
                     
				<c:if test="${empty sessionScope.loginuser}">
                      <a href="<%= ctxPath %>/member/memberRegister.neige">회원가입 </a>
                      <span class="delimiter">|</span>
                </c:if>
				
				
				<%-- <a href="<%= ctxPath %>/login/loginPage.neige">로그인</a> <span class= "delimiter" >|</span> --%>		
                <c:choose>
                   <c:when test="${empty sessionScope.loginuser}">
                      <a href="<%= ctxPath %>/login/loginPage.neige">로그인</a>
                   </c:when>
                   <c:otherwise>
                     <%--  <span style="color:orange"> ${(sessionScope.loginuser).userid}님</span>  --%>
                     <a href="<%= ctxPath %>/login/logout.neige">로그아웃</a>&nbsp;<span class= "delimiter" >|</span>
                   </c:otherwise>

                </c:choose>
                <c:if test="${sessionScope.loginuser.userid == 'admin'}">
					<a href="<%= ctxPath %>/admin/adminMain.neige">관리자</a>	
				</c:if>
            </div>
            
		</div>
	</div>
	
	<%-- 	
	<div id="headerMenuBar">
		<div id = "Menu_Items" class="row MainMenu">		
			
			<div id = "First_Menu" class="col-md-1">
				<a style= 'color: black;' href="<%= ctxPath %>/product/productBestList.neige">상품랭킹</a>
			</div>
						
			<div class="col-md-1">
				<a style= 'color: black;' href="<%= ctxPath %>/product/newProductList.neige">신상품</a>
			</div>
			
			<div class="col-md-3">
				<input type="text" placeholder="&nbsp;&nbsp;Search " name="Main_Search_Input" id="Main_Search_Input" />
				<button type="button" id="Main_Search_Button"
						style =  "background-color: gray; border: none; color:#fff;
							      text-align: center; text-decoration: none; padding: 5px;
							      display: inline-block; font-size: 16px; cursor: pointer;
							      border-radius: 5px;"
						onclick = "goSearchProduct();">검색
				</button>
			</div>
			
		</div>
	</div>
	--%>
</nav>

<br><br>

<div id="contents" align="center">

<!-- <br><br><br><br><br><br> -->

