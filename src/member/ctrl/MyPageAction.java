package member.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;

public class MyPageAction extends AbstractController {
	//마이페이지  
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
	
		//마이페이지에 들어오기 위한 전제조건은 먼저 로그인을 해야 하는 것이다. 
		if( loginuser != null ) {
			// 로그인을 했으면
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/myPage.jsp");
		}
		else {
			// 로그인을 안 했으면
			String message = "회원정보를 열람하기 위해서는 먼저 로그인을 하세요!!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}	
		
	}
}
