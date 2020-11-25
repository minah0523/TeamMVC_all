package admin.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;
import myshop.mdl.ProductVO;

public class ProductListAllAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 상품 리스트 메인화면에서는 상품번호, 카테고리명, 상품명, 재고, 가격, 성별만 보여준다. (상품번호 오름차순 정렬로 보여줄 예정)
		// 리스트 각각 하나씩 클릭하는 화면에서는 클릭한 상품 번호를 가지고 이미지와 상세 정보를 볼 수 있게 한다. 
		// 여기에 수정 버튼을 눌렀을때 상품 등록 페이지에 가서 데이터를 넣어준다. 
		
		InterProductDAO pdao = new ProductDAO();
		
		// 검색조건 없이 전체 조회한 DAO
		List<ProductVO> adminProdList = pdao.adminProductListAll();
		request.setAttribute("adminProdList", adminProdList);
		
		// 검색조건과 함께 조회한 DAO 
		
		// 검색 form 에서 넘어온 값 받기 
		String pdgender = request.getParameter("pdgender");
		String searchType = request.getParameter("searchType");	
		String searchWord = request.getParameter("searchWord");
		String prodRegType = request.getParameter("prodRegType");
		
		// 받은걸 Map에 담기
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("pdgender",pdgender);
		paraMap.put("searchType",searchType);
		paraMap.put("searchWord",searchWord);
		paraMap.put("prodRegType",prodRegType);
		
		
		// *** 페이징 처리를 한 모든 검색상품 또는 검색한 검색상품 목록 보여주기 *** //
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		// currentShowPageNo 은 사용자가 보고자하는 페이지바의 페이지번호 이다.
		// 메뉴에서 검색상품목록 만을 클릭했을 경우에는 currentShowPageNo 은 null 이 된다.
		// currentShowPageNo 이 null 이라면 currentShowPageNo 을 1 페이지로 바꾸어야 한다.
		
		String sizePerPage = request.getParameter("sizePerPage");
		// sizePerPage 는 한 페이지당 화면상에 보여줄 회원의 개수이다. 
		// "10" or "5" 
		// 메뉴에서 회원목록만 클릭 했을 경우에는 sizePerPage는 null 이라면 sizePerPage를 10으로 바꾸어야 한다. 	
		
		if( currentShowPageNo == null ) {
			currentShowPageNo = "1";
		}
		
		if(sizePerPage == null || 
		   !("5".equals(sizePerPage) || "10".equals(sizePerPage))) {
			sizePerPage = "10";
		}
		
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자 아닌 문자를 입력한 경우 또는 
		//     int 범위를 초과한 숫자를 입력한 경우라면 currentShowPageNo 는 1 페이지로 만들도록 한다. ==== // 
		try {
			Integer.parseInt(currentShowPageNo);
		} catch(NumberFormatException e) {
			currentShowPageNo = "1";
		}
		
		
		paraMap.put("currentShowPageNo", currentShowPageNo);
		paraMap.put("sizePerPage",sizePerPage);
		
		List<ProductVO> adminprodList = pdao.adminProductListAll(paraMap);
				
		// 넘어온 결과를 productListAll.jsp에 넘겨 보낸다. 
		request.setAttribute("adminprodList", adminprodList);
		
		// 위에서 getParameter로 가져온 값들을 다시 productListAll.jsp에 넘겨 보내서 jsp페이지에서 검색조건이 무엇이였는지 다시 뿌려준다. 
		request.setAttribute("pdgender", pdgender);
		request.setAttribute("searchType", searchType);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("prodRegType", prodRegType);
		
		request.setAttribute("sizePerPage", sizePerPage);
		
		// *** ==================== 페이지 바 만들기 ==================== *** //
		/*
		 	1개 블럭당 10개 씩 잘라서 페이지를 만든다. [이전] 1 2 3 4 5 6 7 8 9 10 [다음] => 블락
		 	1개 페이지당 3개행 또는 5개행 또는 10개 행을 보여주는데
		 	만약에 1개 페이지 당 5개 행을 보여준다라면
		 	총 몇개 블락이 나와야 할까?
		 	총 회원수가 207명이고, 1개 페이지당 보여줄 회원수가 5이라면 
		 	207 / 5 => 41.4, 41하고 나머지 2 => 42페이지(나머지 2명이 42페이지에 출력)
		 	
		 	1블럭  		1  2  3  4  5  6  7  8  9  10 [다음]
		 	2블럭	  [이전] 11 12 13 14 15 16 17 18 19 20 [다음]
		 	3블럭	  [이전] 21 22 23 24 25 26 27 28 29 30 [다음]			 	
		 	4블럭	  [이전] 31 32 33 34 35 36 37 38 39 40 [다음]	
		 	5블럭	  [이전] 41 42 			 			 	
		 	
		 */
					
		// 페이징 처리를 위해서 총 페이지 개수를  알아오기(select)
		int totalPage = pdao.getTotalPage(paraMap); // 검색어에 따른 (컬럼명) 페이징 처리를 하기 위해 searchType, searchWord를 담고 있는 paraMap을 보낸다. 
		
		// System.out.println("~~~ 확인용 totalPage => " + totalPage);
		
		/*
			  1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지 번호 시작값(pageNo)은 1이다. 
		 	  11 12 13 14 15 16 17 18 19 20    -- 두번째 블럭의 페이지 번호 시작값(pageNo)은 11이다.
		 	  21 22 23 24 25 26 27 28 29 30    -- 세번째 블럭의 페이지 번호 시작값(pageNo)은 21이다.
		 	  
		 	  currentShowPageNo 	  pageNo		 	  
		 	  --------------------------------------------------------------------------------------
			 	  1						1	= ((currentShowPageNo -1) / blockSize) * blockSize + 1
			 	  2						1	= ((2 -1) / 10) * 10 + 1 => 정수/정수 = ( 0 * 10 + 1) => 0 + 1 => 1
			 	  3						1	= ((3 -1) / 10) * 10 + 1 
			 	  4						1	= ((4 -1) / 10) * 10 + 1 
			 	  5						1	= ((5 -1) / 10) * 10 + 1 
			 	  6						1	= ((6 -1) / 10) * 10 + 1 
			 	  7						1	= ((7 -1) / 10) * 10 + 1 
			 	  8						1	= ((8 -1) / 10) * 10 + 1 
			 	  9						1	= ((9 -1) / 10) * 10 + 1 
			 	  10					1	= ((10 -1) / 10) * 10 + 1 
			 	  
			 	  11					11	= ((11 -1) / 10) * 10 + 1 
			 	  12					11	= ((12 -1) / 10) * 10 + 1 
			 	  13					11	= ((13 -1) / 10) * 10 + 1 
			 	  14					11	= ((14 -1) / 10) * 10 + 1 
			 	  15					11	= ((15 -1) / 10) * 10 + 1 
			 	  16					11	= ((16 -1) / 10) * 10 + 1 
			 	  17					11	= ((17 -1) / 10) * 10 + 1 
			 	  18					11	= ((18 -1) / 10) * 10 + 1 
			 	  19					11	= ((19 -1) / 10) * 10 + 1 
			 	  20					11	= ((20 -1) / 10) * 10 + 1 
			 	  
			 	  21					21	= ((21 -1) / 10) * 10 + 1 
			 	  22					21	= ((22 -1) / 10) * 10 + 1 
			 	  23					21	= ((23 -1) / 10) * 10 + 1 
			 	  24					21	= ((24 -1) / 10) * 10 + 1 
			 	  25					21	= ((25 -1) / 10) * 10 + 1 
			 	  26					21	= ((26 -1) / 10) * 10 + 1 
			 	  27					21	= ((27 -1) / 10) * 10 + 1 
			 	  28					21	= ((28 -1) / 10) * 10 + 1 
			 	  29					21	= ((29 -1) / 10) * 10 + 1 
			 	  30					21	= ((30 -1) / 10) * 10 + 1 
		*/
		
		
		String pageBar = "";
		
		int blockSize = 10; // blockSize는 블락(토막)당 보여지는 페이지 번호의 개수이다. 
		
		int loop = 1; // loop는 1부터 증가하여 1개 블락을 이루는 페이지 번호의 개수(지금은10개)까지만 증가하는 용도이다. (증가치)
		
		int pageNo = 0; // pageNo는 페이지바에서 보여지는 첫번째 번호이다. 
		
		// !!!! 다음은 pageNo를 구하는 공식이다. !!!! //
		pageNo = ( ( Integer.parseInt(currentShowPageNo) -1) / blockSize ) * blockSize + 1;
		
		// 처음페이지에는 성별, 검색 타입과 검색어가 없으므로 url에 null값이 들어간다. 그러니 값을 바꾸자.
		
		if( pdgender == null ) {
			pdgender = "0";
		}					
		
		if(searchType == null) {
			searchType = "0";
		}
		
		if(searchWord == null) {
			searchWord = "";
		}
		
		if(prodRegType == null) {
			prodRegType = "0";
		}
		
					
		// *** [맨처음][이전] 만들기  *** // 
		// pageNo-1 ==> 11 -1
		
		if( pageNo != 1) {
			// 첫번째 페이지가 아니라면 두번째 블락부터는 이전페이지나 맨 처음 페이지로 이동해야 한다. 
			// pageNo가  totalPage보다 큰게 아니라면 (즉, 22가 아니라면 다음을 보여줘라)
			pageBar += "&nbsp; <a href='productListAll.neige?currentShowPageNo=1&sizePerPage="+sizePerPage+"&pdgender="+pdgender+"&searchType="+searchType+"&searchWord="+searchWord+"&prodRegType="+prodRegType+"'>[맨처음]</a>&nbsp;" ;
			pageBar += "&nbsp; <a href='productListAll.neige?currentShowPageNo="+(pageNo-1)+"&sizePerPage="+sizePerPage+"&pdgender="+pdgender+"&searchType="+searchType+"&searchWord="+searchWord+"&prodRegType="+prodRegType+"'>[이전]</a>&nbsp;" ;
			// 10페이지나 20페이지로 이동하도록 이전 페이지를 만든다(한칸씩 전으로 가도록)
			
		}			
		
		while( !(loop > blockSize || pageNo > totalPage ) ) { // !()에서 ()는 반복문 탈출 조건, 즉, loop가 blockSize보다 크면 빠져나온다.(loop가 11이 되는 순간 반복문 탈출)
			
			if( pageNo ==  Integer.parseInt(currentShowPageNo) ) {
				// 클릭한 페이지
				pageBar += "&nbsp;<span style='border: solid 1px gray; color:red; padding: 2px 4px;'>"+pageNo+"</span>&nbsp;" ;
			}
			else {
				// 클릭한 페이지가 아니라면
				// 이동할 수 있는 페이지 보여주기(만약 현재 페이지가 3번  페이지라면 3번을 제외한 나머지를 링크 만들고 번호 보여주기
				
				pageBar += "&nbsp; <a href='productListAll.neige?currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&pdgender="+pdgender+"&searchType="+searchType+"&searchWord="+searchWord+"&prodRegType="+prodRegType+"'> "+pageNo+" </a>&nbsp;" ;
			}
			loop++;	  // 반복횟수 
					  // 1 2 3 4 5 6 7 8 9 10

			pageNo++; // 페이지 번호
					  // 1 2 3 4 5 6 7 8 9 10
			  	 	  // 11 12 13 14 15 16 17 18 19 20 
					  // 21
		} // end of while() -----------------------------------------------
		
		
		// pageNo가 11인 상태에서 빠져나온다.
					

		// *** [다음][마지막] 만들기  *** // 
		// pageNo ==> 11
		if( !(pageNo > totalPage ) ) {
			// pageNo가  totalPage보다 큰게 아니라면 (즉, 22가 아니라면 다음을 보여줘라)
			pageBar += "&nbsp; <a href='productListAll.neige?currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&pdgender="+pdgender+"&searchType="+searchType+"&searchWord="+searchWord+"&prodRegType="+prodRegType+"'>[다음]</a>&nbsp;" ;				
			pageBar += "&nbsp; <a href='productListAll.neige?currentShowPageNo="+totalPage+"&sizePerPage="+sizePerPage+"&pdgender="+pdgender+"&searchType="+searchType+"&searchWord="+searchWord+"&prodRegType="+prodRegType+"'>[마지막]</a>&nbsp;" ;
		}
		
		request.setAttribute("pageBar", pageBar);		
		
		super.setViewPage("/WEB-INF/admin/productListAll.jsp");
		
	}
	
}

