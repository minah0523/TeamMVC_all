<<<<<<< HEAD
package notice.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import notice.mdl.InterNoticeDAO;
import notice.mdl.NoticeDAO;
import notice.mdl.NoticeVO;

public class NoticeDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		// == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {	// 관리자(admin)로 로그인 했을 경우
			
			String noticeno = request.getParameter("noticeno");
			
			InterNoticeDAO ndao = new NoticeDAO();
			
			int n = ndao.deleteNotice(noticeno);
			
			super.setViewPage("/WEB-INF/notice/delete.jsp");
			
			String message = "";
			
			String loc = request.getContextPath()+"/notice/notice.neige";
			
			if(n == 1) {
				message = "공지사항 삭제 성공";
				loc = request.getContextPath()+"/notice/notice.neige";
			} 
			else {
				message = "공지사항 삭제 실패";
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
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		
		
	}	

}
=======
package notice.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import notice.mdl.InterNoticeDAO;
import notice.mdl.NoticeDAO;
import notice.mdl.NoticeVO;

public class NoticeDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		// == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {	// 관리자(admin)로 로그인 했을 경우
			
			String noticeno = request.getParameter("noticeno");
			
			InterNoticeDAO ndao = new NoticeDAO();
			
			int n = ndao.deleteNotice(noticeno);
			
			super.setViewPage("/WEB-INF/notice/delete.jsp");
			
			String message = "";
			
			String loc = request.getContextPath()+"/notice/notice.neige";
			
			if(n == 1) {
				message = "공지사항 삭제 성공";
				loc = request.getContextPath()+"/notice/notice.neige";
			} 
			else {
				message = "공지사항 삭제 실패";
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
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		
		
	}	

}
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
