package common.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myshop.mdl.*;

public class ManMainController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// headerMan에서 보내주는 gender		
		String gender = request.getParameter("gender"); 
		
		// ManMain.jsp에서 보내주는 sort 받아오기
		String sortType = request.getParameter("sort");	
		
		// 받아온것을 paraMap에 넣어서  ProductMainImageSelectAll에 보내주기
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("gender",gender);
		paraMap.put("sortType",sortType);
		
		// 위에서 받아온 gender를 세션에 저장한다. (ManMain.jsp에서 세션을 불러서 url에 전송하기 위해 세션에 담는다. 
		HttpSession session = request.getSession(); 
		session.setAttribute("gender",gender);
		
		System.out.println("session값 : " + session.getAttribute("gender"));
		
		InterProductDAO pdao = new ProductDAO();
		
		// 캐러셀 이미지를 위한 DAO 호출
		List<ImageVO> imageCarouselList = pdao.ImageCarouselSelectAll();
		request.setAttribute("imageCarouselList", imageCarouselList);
		
		// 메인페이지 컨텐츠 영역 tbl_product 테이블 이용한 리스트 불러오기 
		List<ProductVO> productMainImageList = pdao.ProductMainImageSelectAll(paraMap);
		
		for(ProductVO pvo : productMainImageList) {
			List<String> colorList = pdao.ProductMainColorSelectAll(String.valueOf(pvo.getPdno()));
			// 제품마다 pdno를 불러온다. 
			String colores = "";
			for(int i=0; i< colorList.size(); i++) {
				String comma = (i< colorList.size() - 1 ) ? "," : "";
				colores += colorList.get(i)+ comma;
			}
			
			// 상품정보 리스트를 보여주는 productMainImageList 에 문자열로 된 색상을 넣는다. 
			pvo.setColores(colores);
		}		
		
		
		request.setAttribute("productMainImageList", productMainImageList);
		
		// 카테고리 목록 보여주는 리스트 보여주기
		List<CategoryVO> categoryList = pdao.CategoryListSelectAll();
		request.setAttribute("categoryList", categoryList);		
		
		setRedirect(false);
		setViewPage("/WEB-INF/ManMain.jsp");		
		
	}
	
}
