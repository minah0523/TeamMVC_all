package common.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myshop.mdl.CategoryVO;
import myshop.mdl.ImageVO;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;
import myshop.mdl.ProductInfoVO;
import myshop.mdl.ProductVO;

public class HomePageController extends AbstractController {
	
	@Override
	public String toString() {
		return "@@@ : 클래스 IndexController의 인스턴스 메소드 toString() 호출";
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String gender = request.getParameter("gender");
		
		// 성별이 null이면 여자로 변경 
		if(gender == null) {
			gender = "2";
		}
		
		HttpSession session = request.getSession();
		
		// 성별을 세션 저장
		session.setAttribute("gender", gender);
		
		// (정렬영역 눌르면 받아진다.) 정렬타입도 받아와서 세션에 저장
		String sort = request.getParameter("sort");
		
		// 정렬도 session에 저장해서 어떤 값을 클릭했는지 bold로 보여주자
		session.setAttribute("sort", sort);
				
		// 성별, 정렬 타입 Map에 저장(상품 불러올때 사용됨)
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("gender",gender);
		paraMap.put("sortType",sort);		
		
		// 메인페이지 캐러셀 영역
		InterProductDAO pdao = new ProductDAO();
		List<ImageVO> imageCarouselList = pdao.ImageCarouselSelectAll();
		request.setAttribute("imageCarouselList", imageCarouselList);
		
		// 메인페이지 컨텐츠 영역 tbl_product 테이블 이용한 리스트 불러오기 
		List<ProductVO> productMainImageList = pdao.ProductMainImageSelectAll(paraMap);
		
		// 메인페이지 컨텐츠 영역 tbl_product 테이블을을 가져와서 productMainColorSelectAll함수를 호출한다.(호출시 prodcutVO의 no를 받아서 함수에 매개변수로 같이 전달한다. / 반복문이니까 1부터 시작)
		// 받아온 리스트(colorList)들을 반복문을 돌려서 하나의 문자열처럼 만든다.
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
		setViewPage("/WEB-INF/TeamHomePage.jsp");

	}
	
}




