package notice.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import notice.mdl.*;

public class NoticeUpdateEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		int noticeno = Integer.parseInt(request.getParameter("noticeno"));
		String title = request.getParameter("title"); 
		String contents = request.getParameter("contents"); 
		
		NoticeVO nvo = new NoticeVO();  
		
		nvo.setNoticeno(noticeno);
		nvo.setTitle(title);
		nvo.setContents(contents);
		
		InterNoticeDAO ndao = new NoticeDAO();
		int n = ndao.updateNotice(nvo);
		
		String message = "";
		
		String loc = request.getContextPath()+"/notice/view.neige?noticeno="+noticeno;
		
		if(n == 1) {
			message = "공지사항 등록 성공";
			loc = request.getContextPath()+"/notice/view.neige?noticeno="+noticeno;
		} 
		else {
			message = "공지사항 등록 실패";
			loc = "javascript:history.back()";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("loc", loc);
		
		super.setViewPage("/WEB-INF/msg.jsp");	
		
	
	}
		
}
