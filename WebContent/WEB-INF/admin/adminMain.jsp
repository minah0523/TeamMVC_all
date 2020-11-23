<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
    //    /TeamMVC
%>    

<jsp:include page="../header.jsp" />


<style>
  /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
  .row.content {height: 800px}
  
  /* Set gray background color and 100% height */
  .sidenav {
  	border-right: solid 1px #E2E2E2;
  
    background-color: white;
    width: 230px;
    height: 100%;
  }
  
  /* Set black background color, white text and some padding */
  footer {
    background-color: #555;
    color: white;
    padding: 15px;
  }
  
  /* On small screens, set height to 'auto' for sidenav and grid */
  @media screen and (max-width: 767px) {
    .sidenav {
      height: auto;
      padding: 15px;
    }
    .row.content {height: auto;} 
  }
</style>

<br>

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav" style = "text-align: left; padding: 20px;">
      <h4>&nbsp;&nbsp;&nbsp;관리자 메뉴</h4>
      <ul class="nav nav-pills nav-stacked">
      	<%-- <li class="active"><a href="<%= ctxPath %>/product/productRegister.neige" style="background: #F8ECDE; color:#7B5232;">상품등록</a></li> --%>
        <li class="active"><a href="<%= ctxPath %>/admin/productListAll.neige" style="background: #F8ECDE; color:#7B5232;">상품관리</a></li>
        <li><a href="<%= ctxPath %>/admin/orderListAll.neige" style = "color:#7B5232;">주문관리</a></li>
        <li><a href="#section4" style = "color:#7B5232;">회원관리</a></li>
      </ul>
    </div>

    <div class="col-sm-9">
    
 
