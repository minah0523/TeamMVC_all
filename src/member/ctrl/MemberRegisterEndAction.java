package member.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;

public class MemberRegisterEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		super.setViewPage("/WEB-INF/member/memberRegisterEnd.jsp");
	}

}
