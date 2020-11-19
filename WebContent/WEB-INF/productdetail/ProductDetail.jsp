<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="css/style_1.css">
    <link rel="stylesheet" type="text/css" href="css/smoothproducts.css">

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>


    <script type="text/javascript">
        $(document).ready(function() {

            //            var goPdnoPage = $("Pdno").val();
            //
            //            if (product != null) {
            //                PdnoPage.focus();
            //                return;
            //            }

            //                    $("button#addcart").click(function() {
            //                            goCart = $("pdno").val();
            //
            //
            //                            console.log($ {
            //                                "#pcolor_1"
            //                            }.val());
            //
            //                            if (product != n)
            //
            //                        }

            //            var scartno = "001,002,003,004,005,006,007,008,009,010";
            //            var arrcartno = [];
            //            arrcartno = scartno.split(',');
            //            console.log(scartno);
            //            console.log(arrcartno);

            //var total_amount = 0;
            // name 으로 값 가져오기
            //  var name = "";
            //$('input[name=inputName]').val();
            // $('input[name=inputBTN]').click(function() {

            //alert("1235678");

            //  name = $(this).text();
            //  console.log(name);
            //
            //total_amount += Number($(this).val());
            // });

            // var c1 = $("#productcolor option:selected").text();
            // console.log(c1);

            // var c2 = $("select[name='productcolor']").val();
            //console.log("c2 : " + c2)

            // console.log("A2 : " + $("select[name=productcolor]:selected").val());
            var color = "";

            $("select[name='productcolor']").click(function() {
                color = $("select[name='productcolor']").val();
                console.log(color);
            });



            //  console.log("total_amount : " + total_amount);

            //            $("button#cardSlash").click(function() {
            //                goCardSlashEnd('${sessionScope.loginuser.userno}', total_amount);
            //            });


        });

        function goPdnoPage(pdno) {
            var frm = document.PdnoFrm;
            frm.pdnopage.value = pdnopage;

            frm.action = "<%=request.getContextPath()%>/member/ProductDetail_1.neige";
            frm.method = "POST";
            frm.submit();
        };

        function goPayLogInsert(userno, total_amount) {

            var frm = document.payLogInsert;
            frm.userno.value = userno;
            frm.total_amount.value = total_amount;

            frm.action = "<%=request.getContextPath()%>/payment/cardSlashUpdate.up";
            frm.method = "POST";
            frm.submit();
        }

    </script>
    <meta charset="UTF-8">
    <title>:: 상품상세정보 ::</title>
</head>

<body>
    <jsp:include page="/WEB-INF/header.jsp" />

    <div class="product-detail">

        <div class="product-detail-left">
            <c:forEach var="pdvo" items="${productList}" varStatus="status">
                <c:if test="${ status.index == 0 }">

                    <!--  <li data-target="#ProductDetail_info"></li>-->

                    <div class="ProductDetail-left">

                        <img src="<%= ctxPath%>/images/${pdvo.pdimage1}" style="width: 650px; height: 550px; float: left;"> <img src="<%= ctxPath%>/images/${pdvo.pdimage2 }" style="width: 100px; height: 100px; float: left; padding max-width: inherit;">

                    </div>
                </c:if>
                <br>
            </c:forEach>
        </div>


        <div class="container">
            <div class="product-detail-right">
                <!-- product-detail option -->
                <c:forEach var="pdvo" items="${productList}" varStatus="status">
                    <h3>
                        <c:out value="${pdvo.pdname}" />
                        <!-- 제품명 pdname -->

                        <br> <small>Product No.
                            <c:out value="${pdvo.pdno}" />
                        </small>
                        <!-- 제품번호 pdno -->
                    </h3>
                    <h5>
                        <b>가격 : </b> &#8361;
                        <c:out value="${pdvo.price}" />
                        <br> <br> <b>재질: </b>
                        <c:out value="${pdvo.texture}" />
                    </h5>
                </c:forEach>
                <%--
	<select name="pdno_fk" class="colordata">
				<option value="">:::선택하세요:::</option>
				 
					//<option value="1">색상1</option>
					//<option value="2">색상2</option>
					//<option value="3">색상3</option>
				 
				<c:forEach var="color" items="${requestScope.productinfoList}">
					<option name="pcolor_1" value="${color.pdno}">${color.pcolor}</option>
				</c:forEach>
			</select> 
--%>

                <h5>
                    <b>색상 : </b>
                    <select name="productcolor" class="productData">
                        <option name="colorbtn" value="colortag">Color</option>

                        <c:forEach var="pdinfo" items="${requestScope.productinfoList}">
                            <option value="${pdinfo.pcolor}">${pdinfo.pcolor}</option>
                        </c:forEach>

                    </select>
                    <br> <br>

                    <b>사이즈 : </b>

                    <select name="productSize" class="productData">
                        <%--<c:forEach var="pdSize" items="${requestScope.productinfoList}" value="${pdSize.psize}"> --%>
                            <c:set var="pdSize" value="${pdSize.psize}" />

                            <c:if test="${pdSize eq 'FREE'}">
                                <option value="${pdSize.psize}">FREE</option>
                            </c:if>
                            <c:if test="${pdSize eq '${pdSize.psize}'}">
                                <option value="${pdSize.psize}">${pdSize.psize}</option>
                            </c:if>

                            <!--<c:if test="${pdSize.psize} == 'FREE'}">
                                <option value="${pdSize.psize}">FREE</option>
                            </c:if>

                            <c:if test="${pdSize.psize} != 'FREE'}">
                                <option value="${pdSize.psize}">${pdSize.psize}</option>
                            </c:if>-->




                        <%-- </c:forEach> --%>
                    </select>

                </h5>
                <h5>

                    <b>배송비 : </b> FREE
                    <b>
                        <ul style="list-style-type: none; margin-top: 50px;">
                            <li style="margin-bottom: 40px;">
                                <label for="spinner">수량:</label>
                                <input id="spinner" name="oqty" value="1" style="width: 30px; height: 20px;">
                            </li>
                        </ul>
                    </b>
                </h5>

                <button type="button" id="addcart" onclick="location.href='<%=ctxPath%>/Payment/productCart.neige'" class="addtocart">
                    <i id="cart" class="fas fa-heart"></i>장바구니
                </button>
                <button type="button" id="review" onclick="location.href='<%=ctxPath%>/productdetail/productReview.jsp'" class="writereview">
                    <i class="fas fa-pen"></i>리뷰
                </button>
                <br>
                <button type="button" id="buy" onclick="location.href='<%=ctxPath%>/Payment/productCart.neige'" class="buynow">
                    <i class="fas fa-shopping-cart"></i>구매하기
                </button>
                <!-- <a href="#" class="addtocart"><i id="cart" class="fas fa-heart"></i> 장바구니</a>
				<a href="#" class="writereview"><i class="fas fa-pen"></i> 리뷰작성</a>
				<br> <a href="#" class="buynow"><i class="fas fa-shopping-cart"></i> 구매하기</a> -->
            </div>
            <!-- product-detail option end -->


            <div class="product-detail-feature">
                <c:forEach var="pdvo" items="${productList}" varStatus="status">
                    <h3>상품상세정보</h3>
                    <c:forEach var="pdinfo" items="${requestScope.productinfoList}">
                        <p>
                            색상 :
                            <c:out value="${pdinfo.pcolor}" />
                        </p>
                        <p>
                            사이즈 :
                            <c:out value="${pdinfo.psize}" />
                        </p>
                    </c:forEach>
                    <p>
                        재질 :
                        <c:out value="${pdvo.texture}" />
                    </p>
                    <p>
                        적립 포인트 :
                        <c:out value="${pdvo.point}" />
                    </p>
                    <br>
                    <br>
                    <h4>
                        <c:out value="${pdvo.pdcontent}" />
                    </h4>
                    <br>
                    <br>
                    <a href="<%=ctxPath%>/images/56_3.jpg"> <img src="<%=ctxPath%>/images/56_3.jpg" alt=""></a>
                    <img src="<%= ctxPath%>/images/${pdvo.pdimage1}" style="width: 100%;">
                    <img src="<%= ctxPath%>/images/${pdvo.pdimage2}" style="width: 100%;">
                </c:forEach>
            </div>
        </div>
        <!-- container -->

    </div>
    <!-- product detail -->


    <form name="PdnoForm">

        <input type="hidden" name="size" value="" />
        <input type="hidden" name="color" value="" />
        <input type="hidden" name="qty" value="" />
        <input type="button" name="inputBTN" value="A!S@#D#F" />

    </form>

</body>

</html>

<jsp:include page="/WEB-INF/footer.jsp" />
