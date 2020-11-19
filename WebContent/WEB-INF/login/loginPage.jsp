<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
      String ctxPath = request.getContextPath();
            //      /TeamMVC
   %>

<jsp:include page="../header.jsp" />
<script type="text/javascript">

   $(document).ready(function(){
      
      // === 로컬스토리지(localStorage)에 저장된 key 가 "saveid"인  userid 값을 불러와서 input 태그 userid에 넣어주기 === //
      var loginUserid = localStorage.getItem('saveid');
      
      if(loginUserid != null) {
         $("input#loginUserid").val(loginUserid);
         $("input:checkbox[id=saveid]").prop("checked", true);
      }
      
      
      $("button#btnSubmit").click(function(){
         goLogin(); // 로그인 시도한다.
      });
      
      $("input#loginPwd").keyup(function(event){
         if(event.keyCode == 13) { // 암호입력란에 엔터를 했을 경우 
            goLogin(); // 로그인 시도한다.
         }
      });
      
      
      $(".myclose").click(function(){
          
         javascript:history.go(0);
          // 현재 페이지를 새로고침을 함으로써 모달창에 입력한 성명과 이메일의 값이 텍스트박스에 남겨있지 않고 삭제하는 효과를 누린다. 
      
      /* === 새로고침(다시읽기) 방법 3가지 차이점 ===
         >>> 1. 일반적인 다시읽기 <<<
         window.location.reload();
         ==> 이렇게 하면 컴퓨터의 캐시에서 우선 파일을 찾아본다.
                  없으면 서버에서 받아온다. 
         
         >>> 2. 강력하고 강제적인 다시읽기 <<<
         window.location.reload(true);
         ==> true 라는 파라미터를 입력하면, 무조건 서버에서 직접 파일을 가져오게 된다.
                  캐시는 완전히 무시된다.
         
         >>> 3. 부드럽고 소극적인 다시읽기 <<<
         history.go(0);
         ==> 이렇게 하면 캐시에서 현재 페이지의 파일들을 항상 우선적으로 찾는다.
      */   
      });
      
   });// end of $(document).ready()--------------------------------------

   
   // === 로그인 처리 함수 === //
   function goLogin() {
   //   alert("로그인 시도함");
   
       var loginUserid = $("input#loginUserid").val().trim();
       var loginPwd = $("input#loginPwd").val().trim();
       
       if(loginUserid == "") {
          alert("아이디를 입력하세요!!");
          $("input#loginUserid").val("");
          $("input#loginUserid").focus();
          return;  // goLogin() 함수 종료 
       }
       
       if(loginPwd == "") {
          alert("암호를 입력하세요!!");
          $("input#loginPwd").val("");
          $("input#loginPwd").focus();
          return;  // goLogin() 함수 종료 
       }
       
      
       
      if( $("input:checkbox[id=saveid]").prop("checked") ) {
         // alert("아이디저장 체크를 하셨네요");
         
         localStorage.setItem('saveid', $("input#loginUserid").val());
      }
      else {
         // alert("아이디저장 체크를 안 하셨네요");
         
         localStorage.removeItem('saveid');
      }   
         
       
       var frm = document.loginPageFrm;
       frm.action = "<%= request.getContextPath()%>/login/loginPage.neige";
       frm.method = "post";
       frm.submit();
   
   }// end of function goLogin()-----------------------------------------
   
   
   function goLogOut() {
      
      // 로그아웃을 처리해주는 페이지로 이동
      location.href="<%= request.getContextPath()%>/login/logout.neige";
      
   }// end of function goLogOut()-----------------------------------------
   

   
</script>
<style>
 
   div#Menu_Items{
      visibility:hidden;
   }
   
   
   
   
   div.titleArea{
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
  
   fieldset{
       width: 60%;
       margin: 100px;
/*        margin: 0 auto; */
       padding: 0 47px;
   }
    
 
    div.item{
      margin: 8px 0 0;
       color: #353535;
       line-height: 20px;
   }
 
 
    button#showMore{
      background-color: light-gray;
      color: gray; 
      margin: 0 auto;
   }
   
   div#input{
         width: 40%;
          
   }
   
   div#idArea{
         margin: 10px 0;
   }
   
   label#id{
         float: left;
         width: 20px
   }
    
   label#Pwd{
         float: left;
         width: 20px
   
   }
   
   label#saveid{
         margin: 0 10px;
         font-size: 10pt;
         font-weight: lighter;
   }
    
   div#search{
   
   margin:20px;
   }
    
  
  
 
</style>




<body>
   <%-- *** 로그인을 하기 위한 폼을 생성 *** --%>
   <%-- 
<c:if test="${sessionScope.loginuser == null}">
또는
--%>

   <form name="loginPageFrm">
      
      <fieldset>
      <div class="titleArea">LOGIN</div>
      
         <div class="item">
            <div class="category">
               <div class="form-group">
                  <div style="font-size: 5px; color: gray;" class="titleArea2">
                  로그인 하시면 다양하고 특별한 혜택을 이용하실 수 있습니다.</div>
                     
                  <div id ="input">   
                  
                     <div id="idArea">
                        <label id="id" for="id">ID</label>                  
                        <input placeholder="Id" type="text" id="loginUserid" name="userid" class="box" autocomplete="off" /> 
                     </div>
                  
                     <div id="pwdArea">
                        <label id="Pwd" for="Pwd">PWD</label>
                        <input placeholder="Password" type="password" id="loginPwd" name="pwd" class="box" />
                     </div>      
                  </div>
               </div>
            </div>
         </div>
         
         
         <div id=idsave>
            <input type="checkbox" id="saveid" name="saveid" />
            <label id="saveid" for="saveid" style="margin-right: 20px; vertical-align: middle;">아이디저장</label>
         </div>
         <div>
         <br>
         <button type="button" id="btnSubmit"
            style="width: 30%; border-top: hidden; background-color: #6E6E6E; color: white; border-bottom: hidden; border-left: hidden; padding: 10px; vertical-align: middle; border: none;">로그인</button>
         </div>

         <%-- === 아이디 찾기, 비밀번호 찾기 === --%>
         <div id="search">
            <hr color="gray">
            <a onclick="location.href='<%=ctxPath %>/login/idFind.neige'" style="cursor: pointer; color: gray;" data-toggle="modal" data-target="#userIdfind" data-dismiss="modal">아이디찾기</a> 
            | <a onclick="location.href='<%=ctxPath %>/login/pwdFind.neige'"style="cursor: pointer; color: gray;" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal" data-backdrop="static">비밀번호찾기</a>
            | <a onclick="location.href='<%=ctxPath %>/member/memberRegister.neige'" style="cursor: pointer; color: gray;" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal" data-backdrop="static">회원가입</a>
         </div>
         
      </fieldset>
   </form>


</body>



<jsp:include page="../footer.jsp" />