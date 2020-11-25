package Payment.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Payment.mdl.OrderVO;
import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class SuccessPaymentPageAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String userid = loginuser.getUserid();
		
		InterProductDAO dao = new ProductDAO();
		
		OrderVO ovo = dao.getOrderInfo(userid);
		
		request.setAttribute("ovo", ovo);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/Payment/SuccessPaymentPage.jsp");
	}

}
