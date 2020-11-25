package notice.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import notice.mdl.*;

public class NoticeUpdateAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {	// 관리자(admin)로 로그인 했을 경우
					
			String noticeno = request.getParameter("noticeno");
			
			InterNoticeDAO ndao = new NoticeDAO();
			
			NoticeVO nvo = ndao.selectOneListByNoticeno(noticeno);
		
			// !!!! 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드) 다시 돌려놓기 !!!! // 
			String title = nvo.getTitle();
			String contents = nvo.getContents();
					
			title = title.replaceAll("&lt;", "<");
			title = title.replaceAll("&gt;", ">");
			// 입력한 내용에서 엔터는 <br>로 변환시키기
			title = title.replaceAll("<br>", "\r\n");
			
			contents = contents.replaceAll("&lt;", "<");
			contents = contents.replaceAll("&gt;", ">");
			// 입력한 내용에서 엔터는 <br>로 변환시키기
			contents = contents.replaceAll("<br>", "\r\n");
			
			
			nvo.setTitle(title);
			nvo.setContents(contents);
			
			
			request.setAttribute("nvo", nvo);
			
			super.setViewPage("/WEB-INF/notice/update.jsp");
			
			
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