<<<<<<< HEAD
package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;

public class ProductOrderAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if( loginuser.getUserid().equals(userid) ) {
			// 로그인한 사용자가 자신의 코인을 충전하는 경우
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/Payment/ProductOder.jsp");
		}
		else {
			// 로그인한 사용자가 다른 사용자의 코인을 충전하려고 시도하는 경우 
			String message = "다른 사용자의 결제 시도는 불가합니다.!!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		
	}

}
=======
package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;

public class ProductOrderAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if( loginuser.getUserid().equals(userid) ) {
			// 로그인한 사용자가 자신의 코인을 충전하는 경우
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/Payment/ProductOder.jsp");
		}
		else {
			// 로그인한 사용자가 다른 사용자의 코인을 충전하려고 시도하는 경우 
			String message = "다른 사용자의 결제 시도는 불가합니다.!!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		
	}

}
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
