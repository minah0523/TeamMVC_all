package product.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import my.util.MyUtil;
import myshop.mdl.*;

public class CommentRegisterAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fk_userid = request.getParameter("fk_userid");
		String fk_pdno = request.getParameter("fk_pdno");
		String contents = request.getParameter("contents");
		
		// **** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드/secure code) 작성하기 **** //
		contents = MyUtil.secureCode(contents);
		
		contents = contents.replaceAll("\r\n", "<br>");
		
		PurchaseReviewsVO reviewsvo = new PurchaseReviewsVO();
		reviewsvo.setFk_userid(fk_userid);
		reviewsvo.setFk_pdno(Integer.parseInt(fk_pdno) );
		reviewsvo.setContents(contents);
		
		InterProductDAO pdao = new ProductDAO();
		pdao.addComment(reviewsvo);
		

	}

}
