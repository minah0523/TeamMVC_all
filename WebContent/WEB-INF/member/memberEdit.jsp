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

table#tblMemberUpdate td {
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
</style>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script> 
<script type="text/javascript">
   
   $(document).ready(function(){
      
      $(".error").hide();
      
         

      
      $("#email").blur(function(){
         
         var email = $(this).val();
         
         var regExp_EMAIL = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
         
         var bool = regExp_EMAIL.test(email);
         
         if(!bool) { // 이메일이 정규표현식에 위배된 경우
            $(this).parent().find(".error").show();
            $(":input").prop("disabled",true).addClass("bgcol");
            $(this).prop("disabled",false).removeClass("bgcol"); 
            $(this).focus();
         }
         else { // 이메일이 정규표현식에 맞는 경우
            $(this).parent().find(".error").hide();
            $(":input").prop("disabled",false).removeClass("bgcol");
         }
         
      });// end of $("#email").blur()--------------

      
      $("#hp2").blur(function(){
         var hp2 = $(this).val();
         
         var regExp_HP2 = /^[1-9][0-9]{2,3}$/g;
         // 숫자 3자리 또는 4자리만 들어오도록 검사해주는 정규표현식
         
         var bool = regExp_HP2.test(hp2);
         
         if(!bool) {
            $(this).parent().find(".error").show();
            $(":input").prop("disabled", true).addClass("bgcol");
            $(this).prop("disabled", false).removeClass("bgcol");
         }
         else {
            $(this).parent().find(".error").hide();
            $(":input").prop("disabled", false).removeClass("bgcol");
         }   
      });// end of $("#hp2").blur()-------------
      
      
      $("#hp3").blur(function(){
         var hp3 = $(this).val();
         
         var regExp_HP3 = /^\d{4}$/g;
         // 숫자 4자리만 들어오도록 검사해주는 정규표현식
         
         var bool = regExp_HP3.test(hp3);
         
         if(!bool) {
            $(this).parent().find(".error").show();
            $(":input").prop("disabled", true).addClass("bgcol");
            $(this).prop("disabled", false).removeClass("bgcol");
         }
         else {
            $(this).parent().find(".error").hide();
            $(":input").prop("disabled", false).removeClass("bgcol");
         }   
      });// end of $("#hp3").blur()-------------

      
      $("#zipcodeSearch").click(function(){
         new daum.Postcode({
               oncomplete: function(data) {
                   // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                   // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                   // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                   var addr = ''; // 주소 변수
                   var extraAddr = ''; // 참고항목 변수

                   //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                   if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                       addr = data.roadAddress;
                   } else { // 사용자가 지번 주소를 선택했을 경우(J)
                       addr = data.jibunAddress;
                   }

                   // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                   if(data.userSelectedType === 'R'){
                       // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                       // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                       if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                           extraAddr += data.bname;
                       }
                       // 건물명이 있고, 공동주택일 경우 추가한다.
                       if(data.buildingName !== '' && data.apartment === 'Y'){
                           extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                       }
                       // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                       if(extraAddr !== ''){
                           extraAddr = ' (' + extraAddr + ')';
                       }
                       // 조합된 참고항목을 해당 필드에 넣는다.
                       document.getElementById("extraAddress").value = extraAddr;
                   
                   } else {
                       document.getElementById("extraAddress").value = '';
                   }

                   // 우편번호와 주소 정보를 해당 필드에 넣는다.
                   document.getElementById('postcode').value = data.zonecode;
                   document.getElementById("address").value = addr;
                   // 커서를 상세주소 필드로 이동한다.
                   document.getElementById("detailAddress").focus();
               }
           }).open();            
      });
      
      // 아래의 작업은 휴대폰 번호가 하나로 되어져 있으므로 이것을 국번과 번호로 나누고자 하는 것이다.
      var mobile = "${sessionScope.loginuser.mobile}";
      
      $("input#hp2").val( mobile.substring(3,7) );
      $("input#hp3").val( mobile.substring(7) );
      
   });// end of $(document).ready()--------------------
   
   
   function goEdit() {
	   alert("클릭했습니다");
      
      var flagBool = false;
      
      $(".requiredInfo").each(function(){
         var data = $(this).val().trim();
         if(data == "") {
            flagBool = true;
            return false;
            /*
               for문에서의 continue; 와 동일한 기능을 하는것이 
               each(); 내에서는 return true; 이고,
               for문에서의 break; 와 동일한 기능을 하는것이 
               each(); 내에서는 return false; 이다.
            */
         }
      });
      
      if(flagBool) {
         alert("필수입력란은 모두 입력하셔야 합니다.");
         return;
      }
      else {
         var frm = document.editFrm;
         frm.method = "POST";
         frm.action = "/notice/updateEnd.neige";
         frm.submit();
      }
      
   }// end of goEdit(event)------------------

</script>


<body>

	<form name="editFrm">

		<fieldset>
			<div id="th" class="titleArea">My Page</div>

			<div style="font-size: 5px; color: gray;" class="titleArea2">
				<span style="text-align: center; font-size: 15pt; color: #F4A460;">
					${(sessionScope.loginuser).userid} </span> <span
					style="font-weight: lighter; font-size: 10pt; font-style: Franklin Gothic;">
					님은 [Neige]회원이십니다. </span>
					
			</div>




			<span class="titleArea2"> ::: 내 정보 수정 ::: </span >
 <div id="nanika">
      <table id="tblMemberUpdate"style="font-size: 10px; color: gray;">
         <tr>
            <td style="width: 20%; font-weight: bold;">성명&nbsp;<span class="star">*</span></td>
            <td style="width: 80%; text-align: left;">
                <input type="hidden" name="userid" value="${sessionScope.loginuser.userid}" readonly />
                <input type="text" name="name" id="name" value="${sessionScope.loginuser.name}" class="requiredInfo" required /> 
               <span class="error">성명은 필수입력 사항입니다.</span>
            </td>
         </tr>
      
         <tr>
            <td style="width: 20%; font-weight: bold;">이메일&nbsp;<span class="star">*</span></td>
            <td style="width: 80%; text-align: left;"><input type="text" name="email" id="email" value="${sessionScope.loginuser.email}" class="requiredInfo" placeholder="abc@def.com" /> 
                <span class="error">이메일 형식에 맞지 않습니다.</span>
            </td>
         </tr>
         <tr>
            <td style="width: 20%; font-weight: bold;">연락처</td>
            <td style="width: 80%; text-align: left;">
                <input type="text" id="hp1" name="hp1" size="6" maxlength="3" value="010" readonly />&nbsp;-&nbsp;
                <input type="text" id="hp2" name="hp2" size="6" maxlength="4" />&nbsp;-&nbsp;
                <input type="text" id="hp3" name="hp3" size="6" maxlength="4" />
                
              <%-- <input type="text" name="mobile" id="mobile" value="${sessionScope.loginuser.mobile}" size="20" maxlength="20" /> --%>  
                <span class="error">휴대폰 형식이 아닙니다.</span>
            </td>
         </tr>
         <tr>
            <td style="width: 20%; font-weight: bold;">우편번호</td>
            
            <td style="width: 80%; text-align: left;">
               <input type="text" id="postcode" name="postcode" value="${sessionScope.loginuser.postcode}" size="6" maxlength="5" />&nbsp;&nbsp;
               <!-- 우편번호 찾기 -->
               <button type="button" id="zipcodeSearch"style="vertical-align: middle; cursor: pointer;color:#696969; font-size: 10pt;">우편번호</button>
              <%--  <img id="zipcodeSearch" src="<%= ctxPath %>/images/b_zipcode.gif" style="vertical-align: middle;" /> --%>
               <span class="error">우편번호 형식이 아닙니다.</span>
            </td>
         </tr>
         <tr>
            <td style="width: 20%; font-weight: bold;">주소</td>
            <td style="width: 80%; text-align: left;">
               <input type="text" id="address" name="address" value="${sessionScope.loginuser.address}" size="40" /><br/>
                <input type="text" id="detailAddress" name="detailAddress" value="${sessionScope.loginuser.detailaddress}" size="40" />&nbsp;<input type="text" id="extraAddress" name="extraAddress" value="${sessionScope.loginuser.extraaddress}" size="40" /> 
                <span class="error">주소를 입력하세요</span>
            </td>
         </tr>
       
      </table>
               <button type="button" id="btnUpdate" style="margin-left: 40%; margin-top: 2%; width: 80px; height: 40px;" onClick="goEdit();"><span style="font-size: 15pt;">확인</span></button>
   </div>
   	</fieldset>
</form>

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

	
	


	 

</body>

 

<jsp:include page="../footer.jsp" />