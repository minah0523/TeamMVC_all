package product.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import my.util.MyUtil;
import myshop.mdl.*;

public class CommentRegisterAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = "";
		userid = request.getParameter("userid");
		String pdno = "";
		pdno = request.getParameter("pdno");
		String contents = "";
		contents = request.getParameter("contents");
		
		//System.out.println("commentRegister check userid1" + userid);
		//System.out.println("commentRegister check pdno2" + pdno);
		//System.out.println("commentRegister check content1" + contents);
		
		// **** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드/secure code) 작성하기 **** //
		contents = MyUtil.secureCode(contents);
		
		contents = contents.replaceAll("\r\n", "<br>");
		
		PurchaseReviewsVO reviewsvo = new PurchaseReviewsVO();
		reviewsvo.setFk_userid(userid);
		reviewsvo.setFk_pdno(Integer.parseInt(pdno) );
		reviewsvo.setContents(contents);
		
		//System.out.println("commentRegister check content2" + contents);
		
		
		InterProductDAO pdao = new ProductDAO();
		pdao.addComment(reviewsvo);
		

	}

}
