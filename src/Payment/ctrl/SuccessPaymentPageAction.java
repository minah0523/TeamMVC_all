package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;

public class SuccessPaymentPageAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String amount = request.getParameter("amount");
		String addPoint = request.getParameter("addPoint");
		String date = request.getParameter("date");
		
		request.setAttribute("amount", amount);
		request.setAttribute("addPoint", addPoint);
		request.setAttribute("date", date);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/Payment/SuccessPaymentPage.jsp");
	}

}
