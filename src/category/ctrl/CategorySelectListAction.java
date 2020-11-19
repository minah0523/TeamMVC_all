package category.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import myshop.mdl.CategoryVO;
import myshop.mdl.ImageVO;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;
import myshop.mdl.ProductVO;

public class CategorySelectListAction extends AbstractController {
   
   // 카테고리클릭시 select해오기
   
   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      
      // gender 세션 받기
      HttpSession session = request.getSession();
      String gender = (String)session.getAttribute("gender");
      System.out.println("gender 받아오자 ~~~~~ " + gender);

      // 카테고리 코드 받아오기
      String pdcategory_fk = request.getParameter("pdcategory_fk");
      System.out.println("pdcategory_fk 받아오자 ~~~~~ " + pdcategory_fk);
      
      // 카테고리 코드 받은걸 세션에 저장하기
      session.setAttribute("pdcategory_fk", pdcategory_fk);
      
      System.out.println("세션의 pdcategory_fk 받아오자 ~~~~~ " + session.getAttribute("pdcategory_fk"));
      
      // 정렬 클릭 시 어떤 정렬인지 받아오기
      String sort = request.getParameter("sort");
      System.out.println("카테고리 클릭 후 우측 하단에 있는 정렬 영역 눌렀을때 발생하는 sort 타입은 ???? "+ sort);
      
      session.setAttribute("sort", sort);
      
      System.out.println("정렬 session : " + session.getAttribute("sort"));
      
      // 맴에 카테고리코드, 성별, 정렬타입을 담자 (카테고리와 카테고리눌른 상태에서 정렬 했을때 사용하기 위해 저장)
      Map<String, String> paraMap = new HashMap<String, String>();
      paraMap.put("gender", gender);
      paraMap.put("pdcategory_fk", pdcategory_fk);
      paraMap.put("sort", sort);
      
      // System.out.println("pdcategory_fk ==> " + pdcategory_fk);
      
      // 메인페이지 캐러셀 영역
      InterProductDAO pdao = new ProductDAO();
      List<ImageVO> imageCarouselList = pdao.ImageCarouselSelectAll();
      request.setAttribute("imageCarouselList", imageCarouselList);
      
      // 카테고리 목록 보여주는 리스트 보여주기
      List<CategoryVO> categoryList = pdao.CategoryListSelectAll();
      request.setAttribute("categoryList", categoryList);
      
      // 카테고리 목록 클릭시 카테고리 코드에 따라 조회하는 메소드
      List<ProductVO> categoryProducClickList = pdao.categoryProducClickSelectAll(paraMap);

      for(ProductVO pvo : categoryProducClickList) {
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
            
      
      request.setAttribute("categoryProducClickList", categoryProducClickList);

      super.setViewPage("/WEB-INF/category/categorySelectList.jsp");

      
   }

}