package notice.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import notice.mdl.*;

public class NoticeUpdateAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*HttpSession session = request.getSession();
	
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if (userID == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");
		}
		int bbsID = 0;
		if (request.getParameter("bbsID") != null) {
			bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if (bbsID == 0) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('유효하지 않은 글입니다.')");
				script.println("location.href = 'bbs.jsp'");
				script.println("</script>");
		}
		Bbs bbs = new BbsDAO().getBbs(bbsID);
		if (!userID.equals(bbs.getUserID())) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'bbs.jsp'");
			script.println("</script>");
		}
	
		// 아이디 admin 점검 -------------수정필요
		String userid = "";

		if( "admin".equals(userid) ) {*/
			// 로그인한 사용자가 자신의 정보를 수정하는 경우
		//	super.setRedirect(false);
		
			
			String noticeno = request.getParameter("noticeno");
			
			InterNoticeDAO ndao = new NoticeDAO();
			
			NoticeVO nvo = ndao.selectOneListByNoticeno(noticeno);
		
			request.setAttribute("nvo", nvo);
			super.setViewPage("/WEB-INF/notice/update.jsp");
		/*}
		else {
			// 로그인한 사용자가 다른 사용자의 정보를 수정하려고 시도하는 경우 
			String message = "다른 사용자의 정보 변경은 불가합니다.!!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}*/
		
	}
}