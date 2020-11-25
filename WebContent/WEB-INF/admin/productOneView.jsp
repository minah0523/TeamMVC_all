<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();
%>    

<%-- <jsp:include page="../header.jsp" /> --%>  
<jsp:include page="../admin/adminMain.jsp" />


<style>
   table#tblProductUpdate {
          width: 93%;
          
          /* 선을 숨기는 것 */
          /* border: solid 1px gray; */
          
          margin: 10px;
          border: hidden;
   }  
   
   table#tblProductUpdate #th {
         height: 45px;
         text-align: center;
         font-family:Times New Roman;
         font-size: 14pt;
         padding-top: 50px;
         padding-bottom: 30px;
         margin:5px;
   }
   
   table#tblProductUpdate td {
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

	
	// 관리자 페이지의 상품 리스트중에서 하나 클릭해서 들어왔을때 값을 받아온 것들을 화면에 보여주자
	// 카테고리명
	$("select#pdcategory_fk option").each(function(){
		
		if($(this).val() == "${pvo.pdcategory_fk}") {
			$(this).attr("selected", "selected");
		}
		
	});
	
	// 상품목록 버튼 클릭 시 이벤트 
	$("button#btnGoProductList").click(function() {
		
		location.href = "<%= request.getContextPath()%>/admin/productListAll.neige"
		
	}); 

	
	
}); // end of $(document).ready()-------------------------

function goProductUpdate() {
	
	alert("버튼 클릭");
		
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
		var frm = document.updateProductFrm;
		frm.submit();
		
	}
		
	
}

function goReset() {
	
	$(":input").val("");
	
}

// 버튼 삭제 클릭시 이벤트 함수
function goProductDelete() {
	
	location.href = "<%= ctxPath %>/admin/productDelete.neige?pdno=${pdno}";
	
}

</script>

<br>
    
<div class="row" id="divRegisterFrm">
   <div class="col-md-9" align="left" style = " margin-left: 20px; width:1200px;">
   <form name="updateProductFrm" style ="margin-left: 50px;"
   		 action="<%= request.getContextPath()%>/admin/productUpdate.neige" 
   		 method="POST"                      
      	 enctype="multipart/form-data">   
   <table id="tblProductUpdate">
      <thead>
      <tr>
         <th colspan="2" id="th" style=" background-color: white; padding-top: 0px;" > 상품등록 </th>
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
	             <input type="hidden" name="pdno" id="pdno" value="${pdno}" />
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품명&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="pdname" id="pdname" class="requiredInfo" value="${pvo.pdname}" /> 
	             <span class="error">제품명은 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품이미지1&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	         	 <span>
	         	 	<img width="200px" height="200px;" src = "<%= ctxPath %>/images2/${pvo.pdimage1}">
	         	 	<input type="hidden" name = "pdimage1" value="${pvo.pdimage1}" />
	             	   <%-- <input type="file" name="pdimage1" id="pdimage1" value="${pvo.pdimage1}"  /> --%>   
	             	<span class="error">제품이미지는 필수입력 사항입니다.</span>
	             </span>
	         </td> 
	      </tr>
	      <tr>
	      	<td class = "titles" style="width: 20%;">제품이미지2&nbsp;<span class="star">*</span></td>
	      	<td style="width: 80%; text-align: left;">
	      		<span>
	      			<img width="200px" height="200px;" src = "<%= ctxPath %>/images2/${pvo.pdimage2}">
	      			<input type="hidden" name = "pdimage2" value="${pvo.pdimage2}" />
	             	 <%-- <input type="file" name="pdimage2" id="pdimage2" value="${pvo.pdimage2}"  /> --%>  
	             	<span class="error">제품이미지는 필수입력 사항입니다.</span>
	             </span>
	        </td>	      	
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품수량&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="text" name="pdqty" id="pdqty" class="requiredInfo" value="${pvo.pdqty}"/>
	            <span class="error">제품수량은 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품정가&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="text" id="price" name= "price" class="requiredInfo" value="${pvo.price}" /> 
	            <span class="error">제품정가는 필수입력 사항입니다. </span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">제품판매가&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;"><input type="text" name="saleprice" id="saleprice" class="requiredInfo" value="${pvo.saleprice}" /> 
	             <span class="error">제품판매가  필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%; ">제품설명&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <textarea id= "pdcontent" name = "pdcontent" rows="5" cols="50" class="requiredInfo" >${pvo.pdcontent}</textarea>
	             <span class="error">제품설명 필수입력 사항입니다.</span>
	         </td>
	      </tr>      
	      <tr>
	         <td class = "titles" style="width: 20%; ">소재&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	            <input type="text" id="texture" name="texture" size="20" maxlength="10" placeholder="ex) wool, cotton, polyester, leather" class="requiredInfo" value="${pvo.texture}"/>&nbsp;&nbsp;
	            <span class="error">소재는 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%; border-bottom: solid 1px #CCCCCC;">성별&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left; border-bottom: solid 1px #CCCCCC;">
	         	<c:if test="${pvo.pdgender == '1' }">
	         		<input type="radio" id="male" name="pdgender" value="1" checked="checked" /><label for="male" style="margin-left: 2%;">남자</label>
	         		<input type="radio" id="female" name="pdgender" value="2" style="margin-left: 10%;" /><label for="female" style="margin-left: 2%;">여자</label>
	         	</c:if>
	         	<c:if test="${pvo.pdgender == '2' }">
	         		<input type="radio" id="male" name="pdgender" value="1" /><label for="male" style="margin-left: 2%;">남자</label>
	         		<input type="radio" id="female" name="pdgender" value="2" style="margin-left: 10%;" checked="checked" /><label for="female" style="margin-left: 2%;">여자</label>
	         	</c:if>
	         	
	         	<%--
	            <input type="radio" id="male" name="pdgender" value="1" /><label for="male" style="margin-left: 2%;">남자</label>
	            <input type="radio" id="female" name="pdgender" value="2" style="margin-left: 10%;" /><label for="female" style="margin-left: 2%;">여자</label>
	             --%>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">추가제품이미지&nbsp;</td>
	         <td style="width: 80%; text-align: left;">
	         	 <span>
	         	 	<c:if test="${empty pimgvo}">
	             		<input type="file" name="imgfilename" id="imgfilename" />
	             	</c:if>
	             	
	             	<c:if test="${!empty pimgvo}">
	             		<img width="200px" height="200px;" src = "<%= ctxPath %>/images2/${pimgvo.imgfilename}">
	             		<input type = "hidden" name = "imgfilename" value = "${pimgvo.imgfilename}" />
	             		<!-- <input type="file" name="imgfilename" id="imgfilename" /> -->
	             	</c:if>
	             	
	             </span>
	         </td> 
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">색상&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="pcolor" id="pcolor" class="requiredInfo" placeholder="ex) brown, black..." value="${sPcolor}"/> 
	             <span class="error">색상은 필수입력 사항입니다.</span>
	         </td>
	      </tr>
	      <tr>
	         <td class = "titles" style="width: 20%;">사이즈&nbsp;<span class="star">*</span></td>
	         <td style="width: 80%; text-align: left;">
	             <input type="text" name="psize" id="psize" class="requiredInfo" placeholder="ex) free, S, M... " value="${sPsize}"/> 
	             <span class="error">사이즈는 필수입력 사항입니다.</span>
	         </td>
	      </tr>		      	      	               
	      <tr>
	         <td colspan="2" style="line-height: 30px; border-bottom: hidden;">
	            <span>
	            	<button type="button" id="btnUpdate" style="margin: 20px 0px 20px 300px; border: none; border-radius: 5px; width: 100px; align:center; background-color: #BCA897; color:white;" onClick="goProductUpdate();">수정하기</button>
	            	<button type="button" id="btnDelete" style="margin: 20px 0px 20px 20px; border: none; border-radius: 5px; width: 100px; align:center; background-color: #BCA897; color:white;" onClick="goProductDelete();">삭제하기</button>
	            	<button type="button" id="btnGoProductList" style="margin: 20px 0px 20px 20px; border: none; border-radius: 5px; width: 100px; align:center; background-color: #BCA897; color:white;">상품목록</button>
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