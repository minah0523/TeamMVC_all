<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
    //    /TeamMVC
%>

<jsp:include page="../header.jsp" />


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%= ctxPath%>/css/style.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>




<script type="text/javascript">
	
	$(document).ready(function(){
	    
		
		$("span.error").hide();
		$("input#name").focus();
		
		var method = "${method}";
	//	console.log("method : " + method);
				
		if(method == "GET") {
			$("#div_findResult").hide();
		}
		else if(method == "POST") {
			$("input#name").val("${name}");
			$("input#email").val("${email}");
		}
		
		$("input#email").blur(function(){

			var email = $(this).val();
			 //유효성 검사를 시작합니다. 
			 var regExp = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i); 
			 // 이메일 정규표현식 객체 생성
				
			 var bool = regExp.test(email);
				
			 if(!bool) {
					// 이메일이 정규표현식에 위배된 경우
					$(":input").prop("disabled",true);
					$(this).prop("disabled",false);
				
				//	$(this).next().show();
				//  또는
				    $(this).parent().find(".error").show();
				
				    $(this).focus();
				}
			 else {
					// 이메일이 정규표현식에 맞는 경우
				 
					$(this).parent().find(".error").hide();
					$(":input").prop("disabled",false);
			 }  
			
			
		}); // 아이디가 email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.		
		
		
		
		$("#btnFind").click(function(){
			
			
			var frm = document.idFindFrm;
			frm.action = "<%=ctxPath%>/login/idFind.neige";
			frm.method = "POST";
			frm.submit();
		});//end of $("#btnFind").click(function(){}
		
		
	});//end of $(document).ready(function(){}
	
</script>

<style>
div.titleArea {
	margin: 50px 0 50px;
	color: #526B8E;
	width: 180px;
	padding: 15px font-size: 17pt;
	margin: 30px;
	height: 30px;
	border-bottom: solid 3px #e3e3e3;
	letter-spacing: 4px;
}

div.titleArea2{
      margin: 50px 0 50px;
      
       padding: 15px font-size: 5pt;
       margin: 30px;
       height: 30px;
      
       letter-spacing: 4px;
   }

fieldset {


	width: 55%;
	margin: 100px;
	padding: 0 47px;
}

div.item {
	margin: 8px 0 0;
	color: #353535;
	line-height: 20px;
}


#div_findResult {
	width: 100%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
}

div#div_btnFind{

	margin:20px;

}


div#input{
	width: 40%;
  		 
  }
  
 
  
  label#name{
	float: left;
	width: 20px;
	  }
   
  label#Email{
	float: left;				
	width: 20px;	
	  
  }
  
 
</style>
<body>
<form name="idFindFrm">
	<fieldset>

		<div class="titleArea">ID Search</div>
			<div class="item">
				<div class="category">
				 <div class="form-group">
		<div style="font-size: 5px; color: gray;" class="titleArea2">
                 이름과 email을 입력하세요.</div>
                     
				<div id ="input">	
				
				<div id="div_name" style="margin-bottom:10px;"> 
					<label id="name" for="name">Name</label>
					<input placeholder="홍길동" size="15" type="text" id="loginUserid" name="name"  autocomplete="off" /> 
				</div>


				<div id="div_email"> 
				<label id="Email" for="Email">Email</label>
					 <input type="text" name="email" id="email" size="15" placeholder="hong@gmail.com" autocomplete="off" required />
					 <br/>
				 	 <span class="error">이메일 형식에 맞지 않습니다.</span>
				</div>

				<div id="div_findResult" align="center" style="margin:15px;">
					회원님의 ID는 <br/>[<span style="color: #D2B48C; font-size: 15pt; margin:15px; ">${userid}</span> ] 입니다.
				</div>

				<div id="div_btnFind" align="center">
					<button type="button" class="btn btn-success" id="btnFind"
					style="width: 30%; border-top: hidden; background-color: #6E6E6E; color: white; border-bottom: hidden; border-left: hidden; padding: 10px; vertical-align: middle; border: none;"
					>Search</button>
					
					 
						</div>
					</div>
				</div>
			</div>
		</div>
	</fieldset>
</form>
</body>
<jsp:include page="../footer.jsp" />