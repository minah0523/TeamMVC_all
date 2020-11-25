<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>    

<%-- <jsp:include page="../header.jsp" /> --%>  
<jsp:include page="../admin/adminMain.jsp" />


<style>
   table#tblProductRegister {
          width: 93%;
          
          /* 선을 숨기는 것 */
          /* border: solid 1px gray; */
          
          margin: 10px;
          border: hidden;
   }  
   
   table#tblProductRegister #th {
         height: 45px;
         text-align: center;
         font-family:Times New Roman;
         font-size: 14pt;
         padding-top: 50px;
         padding-bottom: 30px;
         margin:5px;
   }
   
   table#tblProductRegister td {
         border: solid 1px;  
         border-color:#CCCCCC;
         line-height: 20px;
         padding-top: 12px;
         padding-bottom: 12px;
         padding-left: 15px;
         height: 45px;

   }
   
   .star { color:  #F4A460;
           font-weight: bold;
           font-size: 13pt;
   }
   
   .titles {
   		font-weight: lighter;
   }
   
</style>

<script type="text/javascript">
	
$(document).ready(function() {
	// 상품등록 시 필수 입력사항 유효성 검사
	
	$("span.error").hide(); // 에러메세지 숨기기
	$("select#pdcategory_fk").focus();
	
	// 카테고리 
	// $("select#pdcategory_fk")
	
	// 제품명
	$("input#pdname").blur(function(){
		
		var pdname = $(this).val().trim(); 
		
		if(pdname == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
			
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$(this).parent().find(".error").hide(); // 에러메세지 숨기기
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}			
	}); // 아이디가 pdname인 것의 포커스를 잃어버렸을 경우(blur) 이벤트 끝-------------
	
	// 제품이미지1(파일 선택 이벤트는 change 이벤트이다.)
	$("input#pdimage1").change(function(){
		
		var pdimage1 = $(this).val();
		if(pdimage1 == "") {
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화			
			
			$(this).parent().parent().find(".error").show();
		}
		else{
			$(this).parent().parent().find(".error").hide();
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
			
		}
	});

	// 제품이미지2
	$("input#pdimage2").change(function(){
		
		var pdimage2 = $(this).val();
		if(pdimage2 == "") {
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화			
			
			$(this).parent().parent().find(".error").show();
			return;
		}
		else{
			$(this).parent().parent().find(".error").hide();
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
			
		}
		
	});	
	
	// 제품수량
	$("input#pdqty").blur(function(){
		
		var pdqty = $(this).val().trim(); 
		
		if(pdqty == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
			
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$(this).parent().find(".error").hide(); // 에러메세지 숨기기
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}			
	});		
	
	// 제품정가
	$("input#price").blur(function(){
		
		var price = $(this).val().trim(); 
		
		if(price == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
			
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$(this).parent().find(".error").hide(); // 에러메세지 숨기기
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}			
	});		
	
	// 제품판매가
	$("input#saleprice").blur(function(){
		
		var saleprice = $(this).val().trim(); 
		
		if(saleprice == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
			
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$(this).parent().find(".error").hide(); // 에러메세지 숨기기
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}			
	});		
	
	// 제품설명
	$("textarea#pdcontent").blur(function(){
		
		var pdcontent = $(this).val().trim();
		
		if(pdcontent == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
		
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();	
		}
		else {
			$(this).parent().find(".error").hide();
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}
	});
	
	// 소재
	$("input#texture").blur(function(){
		
		var texture = $(this).val().trim(); 
		
		if(texture == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
			
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$(this).parent().find(".error").hide(); // 에러메세지 숨기기
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}			
	});		
	
	
	// 색상
	$("input#pcolor").blur(function(){
		
		var pcolor = $(this).val().trim(); 
		
		if(pcolor == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
			
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$(this).parent().find(".error").hide(); // 에러메세지 숨기기
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}	
		
		// pcolor = pcolor.split(",");
	});		
	
	// 사이즈
	$("input#psize").blur(function(){
		
		var psize = $(this).val().trim(); 
		
		if(psize == "") {
			// 값을 입력하지 않았거나 공백만 입력한 경우
			$(":input").prop("disabled",true); // 모든 input 태그 비활성화
			$(this).prop("disabled", false);   // 자기자신 활성화
			
			$(this).parent().find(".error").show(); // 자기자신에서 부모로 올라가서 error class를 찾아서 보여주자, this 위에 부모 : tr 태그
			$(this).focus();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$(this).parent().find(".error").hide(); // 에러메세지 숨기기
			$(":input").prop("disabled", false);    // 모든 input태그 활성화
		}	
		
		// psize = psize.split(",");
		
	});		
	
	
}); // end of $(document).ready()-------------------------

function goProductRegister() {
	
	// 성별을 선택하지 않았을 경우 오류 메세지 출력하도록한다. 
	var radioCheckedLength = $("input:radio[name=pdgender]:checked").length;
	
	if( radioCheckedLength == 0 ) {
		// 체크한 길이가 0 이라면 오류 메세지 출력
		alert("성별을 선택해주세요!");
		return;
	}
	
	var psizes = $("input#psize").val();
	psizes = psizes.split(",");
	
	var pcolors = $("input#pcolor").val();
	pcolors = pcolors.split(",");
	
	if(psizes.length != pcolors.length ) {
		// 색상의 배열 길이와 사이즈의 배열 길이가 같은 경우에만 form을 보내준다. 
		alert("색상과 사이즈의 길이는 같아야 합니다. ");
		return;
	}
	
	// 필수 입력사항 모두 입력이 되어있는지 검사한다. // 
	var bFlagRequiredInfo = false;
	$(".requiredInfo").each(function(){ // 선택자 모두 반복
		
		var data = $(this).val();
		if(data == "") { // data에 아무것도 안쓰여져있다면
			bFlagRequiredInfo = true; // 한개라도 안쓰여져 있다면 flag를 true로 변경
			alert("*표시된 필수입력사항은 모두 입력하셔야 합니다.");
			return false; // 반복문 break
		}
	});	
		
	if(!bFlagRequiredInfo) { // bFlagRequiredInfo가 false 일때만 전송한다.
		var frm = document.registerProductFrm;
		frm.submit();
		
	}
	
}

function goReset() {
	
	$(":input").val("");
	
}

</script>

<br>
    
<div class="row" id="divRegisterFrm">
   <div class="col-md-9" align="left" style = " margin-left: 20px; width:1200px;">
   <form name="registerProductFrm" style ="margin-left: 50px;"
   		 action="<%= request.getContextPath()%>/product/productRegister.neige" 
   		 method="POST"                       
      	 enctype="multipart/form-data">   
   <table id="tblProductRegister">
      <thead>
      <tr>
         <th colspan="2" id="th" style=" background-color: white; padding-top: 0px;" > 제품등록 </th>
      </tr>
      </thead>
      <tbody>
	      <tr>
	      	 <td class = "titles" style="width: 20%;">카테고리&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	            <select id="pdcategory_fk" name="pdcategory_fk" style= "width: 170px; height: 40spx; padding: 8px;">
	               <option>선택</option>
	               <option value ="1">코트</option>
	               <option value ="2">자켓</option>
	               <option value ="3">점퍼</option>
	               <option value ="4">무스탕</option>
	               <option value ="5">가디건</option>
	            </select> 
	             <span class="error">카테고리는 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품명&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="pdname" id="pdname" class="requiredInfo" /> 
	             <span class="error">제품명은 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품이미지1&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	         	 <span>
	             	<input type="file" name="pdimage1" id="pdimage1" class="requiredInfo" /><span class="error">제품이미지는 필수입력 사항입니다.</span>
	             </span>
	         </td> 
	      </tr>
	      <tr>
	      	<td class = "titles" style="width: 20%;">제품이미지2&nbsp;<span class="star">*</span></td>
	      	<td style="width: 80%; text-align: left;">
	      		
	             	<input type="file" name="pdimage2" id="pdimage2" class="requiredInfo" />
	             	<span class="error">제품이미지는 필수입력 사항입니다.</span>
	             
	        </td>	      	
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품수량&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="text" name="pdqty" id="pdqty" class="requiredInfo" />
	            <span class="error">제품수량은 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품정가&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="text" id="price" name= "price" class="requiredInfo" /> 
	            <span class="error">제품정가는 필수입력 사항입니다. </span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품판매가&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="text" name="saleprice" id="saleprice" class="requiredInfo" /> 
	             <span class="error">제품판매가  필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%; ">제품설명&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <textarea id= "pdcontent" name = "pdcontent" rows="5" cols="50" class="requiredInfo"></textarea>
	             <span class="error">제품설명 필수입력 사항입니다.</span>
	         </td>
	      </tr>      
	      <tr>
	         <td class = "titles" style="width: 20%; ">소재&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	            <input type="text" id="texture" name="texture" size="20" maxlength="10" placeholder="ex) wool, cotton, polyester, leather" class="requiredInfo" />&nbsp;&nbsp;
	            <span class="error">소재는 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%; border-bottom: solid 1px #CCCCCC;">성별&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left; border-bottom: solid 1px #CCCCCC;">
	            <input type="radio" id="male" name="pdgender" value="1" /><label for="male" style="margin-left: 2%;">남자</label>
	            <input type="radio" id="female" name="pdgender" value="2" style="margin-left: 10%;" /><label for="female" style="margin-left: 2%;">여자</label>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">추가제품이미지&nbsp;</td>
	         <td style="width: 80%; text-align: left;">
	         	 <span>
	             	<input type="file" name="plusPdimage" id="plusPdimage" class="requiredInfo" />
	             </span>
	         </td> 
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">색상&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="pcolor" id="pcolor" class="requiredInfo" placeholder="ex) brown, black..."/> 
	             <span class="error">색상은 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">사이즈&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="psize" id="psize" class="requiredInfo" placeholder="ex) free, S, M... "/> 
	             <span class="error">사이즈는 필수입력 사항입니다.</span>
	         </td>
	      </tr>		      	      	               
	      <tr>
	         <td colspan="2" style="line-height: 30px; border-bottom: hidden;">
	            <span>
	            	<button type="button" id="btnRegister" style="margin: 20px 0px 20px 450px; border: none; border-radius: 5px; width: 100px; align:center; background-color: #BCA897; color:white;" onClick="goProductRegister();">등록하기</button>
	            	<button type="button" id="reset" style="margin-left: 20px; border: none; border-radius: 5px; width: 100px; align:center; background-color: #BCA897; color:white;" onClick="goReset();">초기화</button> <!-- backgorund-color : #555 -->
	            </span> 
	         </td>
	      </tr>
      </tbody>
   </table>
   </form>
   </div>
</div>


    </div>
  </div>
</div>

<jsp:include page="../footer.jsp" /> 