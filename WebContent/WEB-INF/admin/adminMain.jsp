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
  #menuLists > li > a:hover {
  	background-color: #F8ECDE;
  	border-radius: 5px;
  }
  #menuLists > li > a:visited {
  	background-color: #F8ECDE;
  	font-weight: bold;
  }  
  
  .adminMenuList{
  	font-weight: bold;
  	background-color:#F8ECDE;
  }
</style>


<script type="text/javascript">

	$(document).ready(function(){
		
		$("ul#menuLists > li").click(function(){
			
			var menuList = $(this).attr('id');
			
			localStorage.setItem("menuList", menuList);
			
		});	
				
		var menuList = localStorage.getItem("menuList");
		
		if(menuList == 'product') {
			$("li#product").addClass("adminMenuList");
		}
		else if(menuList == 'order') {
			$("li#order").addClass("adminMenuList");
		}
		else if(menuList == 'user') {
			$("li#user").addClass("adminMenuList");
		}

	});

</script>

<br>

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav" style = "text-align: left; padding: 20px;">
      <h4>&nbsp;&nbsp;&nbsp;관리자 메뉴</h4>
      <!-- <ul class="nav nav-pills nav-stacked"> -->
      <ul id="menuLists" class="nav nav-stacked">
        <li id="product" class="adminMenu">
        	<a href="<%= ctxPath %>/admin/productListAll.neige" style="color:#7B5232;">상품관리</a>
        </li>
        <li id="order" class="adminMenu">
	        <a href="<%= ctxPath %>/admin/orderListAll.neige" style = "color:#7B5232;">주문관리</a>
        </li>
        <li id="user" class="adminMenu">
        	<a href="<%= ctxPath %>/admin/memberListAll.neige" style = "color:#7B5232;">회원관리</a>
        </li>
      </ul>
    </div>

    <div class="col-sm-9">
    
 
