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
		
		// !!!! 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드) 작성하기 !!!! // 
		title = title.replaceAll("<", "&lt;");
		title = title.replaceAll(">", "&gt;");
		  
		// 입력한 내용에서 엔터는 <br>로 변환시키기
		title = title.replaceAll("\r\n", "<br>");
		
		contents = contents.replaceAll("<", "&lt;");
		contents = contents.replaceAll(">", "&gt;");
		  
		// 입력한 내용에서 엔터는 <br>로 변환시키기
		contents = contents.replaceAll("\r\n", "<br>");
		
		NoticeVO nvo = new NoticeVO();  
		
		
		nvo.setNoticeno(noticeno);
		nvo.setTitle(title);
		nvo.setContents(contents);
		
		InterNoticeDAO ndao = new NoticeDAO();
		int n = ndao.updateNotice(nvo);
		
		String message = "";
		
		String loc = request.getContextPath()+"/notice/view.neige?noticeno="+noticeno;
		
		if(n == 1) {
			message = "공지사항 수정 성공";
			loc = request.getContextPath()+"/notice/view.neige?noticeno="+noticeno;
		} 
		else {
			message = "공지사항 수정 실패";
			loc = "javascript:history.back()";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("loc", loc);
		
		super.setViewPage("/WEB-INF/msg.jsp");	
		
	
	}
		
}
