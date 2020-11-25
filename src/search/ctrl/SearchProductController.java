package search.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import my.util.MyUtil;
import myshop.mdl.*;


// search 페이지에 보여지는 상품이미지파일명을 모두 조회(select)하는 컨트롤러 (MINA)
public class SearchProductController extends AbstractController {

   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      String pdgender = request.getParameter("pdgender");
      String pdcategory_fk = request.getParameter("pdcategory_fk");
      String searchname = request.getParameter("searchname");
      String sort = request.getParameter("sort");
      
      //성별, 품목, 검색키워드를 넣는다
      Map<String,String> paraMap = new HashMap<>();
      paraMap.put("pdgender", pdgender); 
      paraMap.put("pdcategory_fk", pdcategory_fk); 
      paraMap.put("searchname", searchname);
      paraMap.put("sort", sort);
      
      InterProductDAO pdao = new ProductDAO();
      
      // *** 페이징 처리를 안한 모든 검색상품 또는 검색한 검색상품 목록 보여주기 *** //
      /* List<ProductVO> searchProductList = pdao.searchProduct(paraMap); */
      
      
      // *** 페이징 처리를 한 모든 검색상품 또는 검색한 검색상품 목록 보여주기 *** //
   			String currentShowPageNo = request.getParameter("currentShowPageNo");
   			// currentShowPageNo 은 사용자가 보고자하는 페이지바의 페이지번호 이다.
   			// 메뉴에서 검색상품목록 만을 클릭했을 경우에는 currentShowPageNo 은 null 이 된다.
   			// currentShowPageNo 이 null 이라면 currentShowPageNo 을 1 페이지로 바꾸어야 한다.
   			
			/* String sizePerPage = "16"; */
   			// 한 페이지당 화면상에 보여줄 검색상품의 개수
   			// *** neige의 search view page는 제품 16개씩 보여주기로 한다. *** //
   			
   			if( currentShowPageNo == null ) {
   				currentShowPageNo = "1";
   			}
   			
   			// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자 아닌 문자를 입력한 경우 또는 
   			//     int 범위를 초과한 숫자를 입력한 경우라면 currentShowPageNo 는 1 페이지로 만들도록 한다. ==== // 
   			try {
   				Integer.parseInt(currentShowPageNo);
   			} catch(NumberFormatException e) {
   				currentShowPageNo = "1";
   			}
   			
   			
   			paraMap.put("currentShowPageNo", currentShowPageNo);
			/* paraMap.put("sizePerPage", sizePerPage); */
   			
   			List<ProductVO> searchProductList = pdao.searchProduct(paraMap);
   			
   			request.setAttribute("searchProductList", searchProductList);
   			request.setAttribute("gender", pdgender);
   			request.setAttribute("category", pdcategory_fk);
   			request.setAttribute("searchname", searchname);
   			request.setAttribute("sort", sort);
			/* request.setAttribute("sizePerPage", sizePerPage); */
   			
   			// **** ========= 페이지바 만들기 ========= **** //
   			/*
   			    1개 블럭당 10개씩 잘라서 페이지 만든다.
   			    1개 페이지당 3개행 또는 5개행 또는  10개행을 보여주는데
   			        만약에 1개 페이지당 5개행을 보여준다라면 
   			        총 몇개 블럭이 나와야 할까? 
   			        총 검색상품수가 207명 이고, 1개 페이지당 보여줄 상품수가 5 이라면
   			    207/5 = 41.4 ==> 42(totalPage)        
   			        
   			    1블럭               1 2 3 4 5 6 7 8 9 10 [다음]
   			    2블럭   [이전] 11 12 13 14 15 16 17 18 19 20 [다음]
   			    3블럭   [이전] 21 22 23 24 25 26 27 28 29 30 [다음]
   			    4블럭   [이전] 31 32 33 34 35 36 37 38 39 40 [다음]
   			    5블럭   [이전] 41 42 
   			 */
   			
   			// 페이징처리를 위해서 검색상품에 대한 총페이지 개수 알아오기(select)  
   			Map<String, String> totalMap = new HashMap<>();
   			totalMap = pdao.getTotal(paraMap);
   			
   			int totalSearchProduct = Integer.parseInt(totalMap.get("totalSearchProduct"));
   			int totalPage = Integer.parseInt(totalMap.get("totalPage"));
   			
   		//	System.out.println("~~~ 확인용 totalPage => " + totalPage);
   			
   			// ==== !!! 공식 !!! ==== // 
   		/*
   		    1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지번호 시작값(pageNo)은  1 이다.
   		    11 12 13 14 15 16 17 18 19 20  -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다.	
   		    21 22 23 24 25 26 27 28 29 30  -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
   		    
   		    currentShowPageNo      pageNo
   		    --------------------------------------------------------------------------------------
   		         1                   1  = ( (currentShowPageNo - 1)/blockSize ) * blockSize + 1 
   		         2                   1  = ( (2 - 1)/10 ) * 10 + 1
   		         3                   1  = ( (3 - 1)/10 ) * 10 + 1
   		         4                   1  = ( (4 - 1)/10 ) * 10 + 1
   		         5                   1  = ( (5 - 1)/10 ) * 10 + 1
   		         6                   1  = ( (6 - 1)/10 ) * 10 + 1
   		         7                   1  = ( (7 - 1)/10 ) * 10 + 1
   		         8                   1  = ( (8 - 1)/10 ) * 10 + 1
   		         9                   1  = ( (9 - 1)/10 ) * 10 + 1
   		         10                  1  = ( (10 - 1)/10 ) * 10 + 1
   		         
   		         11                 11  = ( (11 - 1)/10 ) * 10 + 1
   		         12                 11  = ( (12 - 1)/10 ) * 10 + 1 
   		         13                 11  = ( (13 - 1)/10 ) * 10 + 1
   		         14                 11  = ( (14 - 1)/10 ) * 10 + 1
   		         15                 11  = ( (15 - 1)/10 ) * 10 + 1
   		         16                 11  = ( (16 - 1)/10 ) * 10 + 1
   		         17                 11  = ( (17 - 1)/10 ) * 10 + 1
   		         18                 11  = ( (18 - 1)/10 ) * 10 + 1
   		         19                 11  = ( (19 - 1)/10 ) * 10 + 1
   		         20                 11  = ( (20 - 1)/10 ) * 10 + 1  
   		         
   		         21                 21  = ( (21 - 1)/10 ) * 10 + 1 
   		         22                 21  = ( (22 - 1)/10 ) * 10 + 1 
   		         23                 21  = ( (23 - 1)/10 ) * 10 + 1 
   		         ..                 21  = ( (.. - 1)/10 ) * 10 + 1 
   		         29                 21  = ( (29 - 1)/10 ) * 10 + 1 
   		         30                 21  = ( (30 - 1)/10 ) * 10 + 1 
   		 */
   			
   			String pageBar = "";
   			
   			int blockSize = 10;
   			// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
   			
   			int loop = 1;
   			// loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다. 
   			
   			int pageNo = 0;
   			// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
   			
   			// !!!! 다음은 pageNo 를 구하는 공식이다. !!!! // 
   			pageNo = ( ( Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
   					
   			/*request.setAttribute("pdgender", pdgender);
   			request.setAttribute("pdcategory_fk", pdcategory_fk);
   			request.setAttribute("searchname", searchname);
   			request.setAttribute("sort", sort);
   			request.setAttribute("sizePerPage", sizePerPage);
   			*/
   			
   			if( pdgender == null ) {
   				pdgender = "0";
   			}
   			
   			if( pdcategory_fk == null ) {
   				pdcategory_fk = "0";
   			}
   			
   			if( searchname == null ) {
   				searchname = "";
   			}
   			
   			if( sort == null ) {
   				sort = "sortNewProduct";
   			}
   			
			/*
			 * if( pdcategory_fk == null ) { pdcategory_fk = "0"; }
			 */
   			
   			
   			/*<ul class="productList"> 
              <c:forEach var="pvo" items="${searchProductList}" varStatus="status" >
               <li id="box">
                  <div class = "col-md-3">*/
   			
   			// **** [맨처음][이전] 만들기 **** //
   			// pageNo-1 == 11 - 1 == 10 ==> currentShowPageNo
   			if( pageNo != 1 ) {
   				/*location.href="<%= ctxPath%>/search/SearchPage.neige?pdgender="+gender+"&pdcategory_fk="+category+"&searchname="+searchname+"&sort="+sort;*/
   				pageBar += "&nbsp;<a href='SearchPage.neige?currentShowPageNo=1&pdgender="+pdgender+"&pdcategory_fk="+pdcategory_fk+"&searchname="+searchname+"&sort="+sort+"'>[맨처음]</a>&nbsp;";
   				pageBar += "&nbsp;<a href='SearchPage.neige?currentShowPageNo="+(pageNo-1)+"&pdgender="+pdgender+"&pdcategory_fk="+pdcategory_fk+"&searchname="+searchname+"&sort="+sort+"'>[이전]</a>&nbsp;";
   			}
   			
   			while( !(loop > blockSize || pageNo > totalPage ) ) {
   				
   				if( pageNo == Integer.parseInt(currentShowPageNo) ) {
   					pageBar += "&nbsp;<span style='border:solid 1px #7B5232; font-weight: bold; color:#7B5232; padding: 2px 4px;'>"+pageNo+"</span>&nbsp;";
   				}
   				else {
   					pageBar += "&nbsp;<a style='color:black;' href='SearchPage.neige?currentShowPageNo="+pageNo+"&pdgender="+pdgender+"&pdcategory_fk="+pdcategory_fk+"&searchname="+searchname+"&sort="+sort+"'>"+pageNo+"</a>&nbsp;"; 
   				}
   				
   				loop++;   // 1 2 3 4 5 6 7 8 9 10 
   				          
   				pageNo++; //  1  2  3  4  5  6  7  8  9 10 
   				          // 11 12 13 14 15 16 17 18 19 20 
   				          // 21 
   			}// end of while---------------------------------
   			
   			
   			// **** [다음][마지막] 만들기 **** //
   			// pageNo ==> 11
   			if( !( pageNo > totalPage ) ) {
   				pageBar += "&nbsp;<a href='SearchPage.neige?currentShowPageNo="+pageNo+"&pdgender="+pdgender+"&pdcategory_fk="+pdcategory_fk+"&searchname="+searchname+"&sort="+sort+"'>[다음]</a>&nbsp;";
   				pageBar += "&nbsp;<a href='SearchPage.neige?currentShowPageNo="+totalPage+"&pdgender="+pdgender+"&pdcategory_fk="+pdcategory_fk+"&searchname="+searchname+"&sort="+sort+"'>[마지막]</a>&nbsp;";
   			}
   			
   			
   			request.setAttribute("pageBar", pageBar);
   			
   			

   			// *** 현재 페이지를 돌아갈 페이지(goBackURL)로 주소 지정하기 *** // 
   			String currentURL = MyUtil.getCurrentURL(request);
   			// 검색상품조회를 했을시 현재 그 페이지로 그대로 되돌아가길 위한 용도로 쓰임.
   			
   		//	System.out.println("currentURL => " + currentURL);
   		//  currentURL => member/searchProductList.up?currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=%ED%99%8D%EC%8A%B9%EC%9D%98
   			
   			currentURL = currentURL.replaceAll("&", " ");
   		//	System.out.println("currentURL => " + currentURL); 
   		//  currentURL => member/searchProductList.up?currentShowPageNo=15 sizePerPage=5 searchType=name searchWord=%ED%99%8D%EC%8A%B9%EC%9D%98	
   			
   			request.setAttribute("goBackURL", currentURL);
   			
   		//	super.setRedirect(false);
   			super.setViewPage("/WEB-INF/search/search.jsp");
   
   
      
      //제품별 컬러리스트를 String으로 변환하는 것
      for(ProductVO pvo : searchProductList) {
         
         List<String> colorList = pdao.selectProductColor(String.valueOf(pvo.getPdno())); 
         
         String colores = "";
         for(int i=0; i<colorList.size(); i++) {
            String comma = (i < colorList.size()-1)?",":"";
            colores += colorList.get(i)+comma;
         }
         
         pvo.setColores(colores);
      }
      
      //제품별 사이즈리스트를 String으로 변환하는 것
      for(ProductVO pvo : searchProductList) {
         
         List<String> sizeList = pdao.selectProductSize(String.valueOf(pvo.getPdno())); 
         
         String sizes = "";
         for(int i=0; i<sizeList.size(); i++) {
            String comma = (i < sizeList.size()-1)?",":"";
            sizes += sizeList.get(i)+comma;
         }
         
         pvo.setSizes(sizes);
      }
      
      request.setAttribute("pdgender", pdgender);
      
      request.setAttribute("searchProductList", searchProductList); 
      request.setAttribute("searchCount", totalSearchProduct);
      
      request.setAttribute("pdcategory_fk", pdcategory_fk);
      request.setAttribute("searchname", searchname);
      
      request.setAttribute("sort", sort);
      
   //   super.setRedirect(false);
      super.setViewPage("/WEB-INF/search/search.jsp");
   }
}