<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
   
   
   <%
      String ctxPath = request.getContextPath();
            //      /TeamMVC
   %>

<jsp:include page="../header.jsp" />  
   
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료 페이지</title>

<style type="text/css">

#registerEnd{
position:absolute;


}

body {
   text-align: center;
}

th#EndCheck {
   padding: 10px;
   background-color: #DEDEDE;
}

.title{
   margin-top:80px;
   margin-bottom:30px;
   display: -webkit-box;
   display: -ms-flexbox;
   display: flex;
   /* -webkit-box-align: center; */
   -ms-flex-align: center;
   align-items: center;
   /* -webkit-box-pack: center; */
   /* -ms-flex-pack: center; */
   justify-content: center;
   text-align: left;

}
.flex-container {
   width: 500px;
   height: 300px;
  
/*    display: -ms-flexbox;
   display: flex; */
   /* -webkit-box-align: center; */
  /*  -ms-flex-align: center;
   align-items: center; */
   /* -webkit-box-pack: center; */
   /* -ms-flex-pack: center; */
   justify-content: center;
   text-align: left;
}

/* .contentsbox {
   border-collapse: collapse; /* 테이블 태그에 해야함.  */
/*    border: text-align: center;
   width: 400px;
   height: 200px;
   border: solid 1px #B2B2B2;
} */
 
/*  .smallCont {
   border-collapse: collapse; // 테이블 태그에 해야함.  
   border: text-align: center;
   position: absolute;
   top:40%;
   left:40%;
   width: 400px;
   height: 200px;
   border: solid 1px #B2B2B2;
}  */

button{

background-color: gray;
color:white;
margin-bottom:40px;


}
</style>
</head>
<body>





<div class="title">
 			<span id=registerEnd style="font-size: 20px;font-family: ">회원가입이 완료되었습니다.</span>
            
</div>


   <div class="flex-container">
  
      <table>
         <thead>
            
         </thead>
         <tbody >
         <tr>
               <td>저희 쇼핑몰을 이용해주셔서 감사합니다.</td>
         </tr>  
            <tr>
               <td>아이디:</td>
              
               <td>이름:</td>
            </tr>

         </tbody>
      </table>
      
   </div>
    <button type="button" id="btnHome"
                     style="width: 80px; height: 40px; vertical-align: middle; cursor: pointer; color: #696969; font-size: 10pt;"
                     onclick = "location.href='http://localhost:9090/TeamMVC/TeamHomePage.neige'">HOME</button>
                  <button type="button" id="btnLogin"
                     style="width: 80px; height: 40px; vertical-align: middle; cursor: pointer; color: #696969; font-size: 10pt;"
                     onclick = "location.href='http://localhost:9090/TeamMVC/login/loginPage.neige'">Login</button>
   <div>
   
   
   </div>


</body>
</html>

<jsp:include page="../footer.jsp" />  