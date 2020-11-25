package admin.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import myshop.mdl.*;

public class ProductDeleteAction extends AbstractController {
   
   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      // == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
      HttpSession session = request.getSession();
      
      MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
      
      if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {   // 관리자(admin)로 로그인 했을 경우
         
         String pdno = request.getParameter("pdno");
         
         InterProductDAO pdao = new ProductDAO();
         
         // pdno에 해당하는 제품 삭제 메소드
         int n = pdao.productDelete(pdno);
         
         // 추가 이미지 파일은 있을 수도 있고 없을 수도 있기 때문에 select로 개수를 알아오자
         int pimg = pdao.pdImgSelected(pdno);
         
         int m = 0;
         
         if(pimg == 1) {
            // pdno에 해당하는 추가첨부파일 삭제 메소드
             m = pdao.pdImgDelete(pdno);
         } 
         else {
            ;
         }
         
         // pdno에 해당하는 색상과 사이즈 삭제 메소드
         int result = pdao.pdInfoDelete(pdno);
         
         super.setViewPage("/WEB-INF/admin/productDelete.jsp");
         
         String message = "";
         String loc = "";
         
         if(n == 1 || m == 1 || result == 1) {
            message = "상품 삭제 성공";
            loc = request.getContextPath()+"/admin/productListAll.neige";
         } 
         else {
            message = "상품 삭제 실패";
            loc = "javascript:history.back()";
         }
         
         request.setAttribute("message", message);
         request.setAttribute("loc", loc);
         
         super.setViewPage("/WEB-INF/msg.jsp");
         
      }
      else {
         // 일반 사용자가 정보를 수정하려고 시도하는 경우 
         String message = "관리자만 공지사항을 수정할 수 있습니다.";
         String loc = "javascript:history.back()";
         
         request.setAttribute("message", message);
         request.setAttribute("loc", loc);
         
      //   super.setRedirect(false);
         super.setViewPage("/WEB-INF/msg.jsp");
         return;
      }
      
   }

}