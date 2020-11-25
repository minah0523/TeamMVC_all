package notice.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import notice.mdl.*;

public class NoticeViewAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			// 카테고리 목록을 조회해오기
				
			String noticeno = request.getParameter("noticeno"); // 제품번호
			
			InterNoticeDAO ndao = new NoticeDAO();
			
			// 제품번호를 가지고서 해당 번호의 게시글을 조회해오기
			NoticeVO nvo = ndao.selectOneListByNoticeno(noticeno);
			
			if(nvo == null) {
				// GET 방식이므로 사용자가 웹브라우저 주소창에서 장난쳐서 존재하지 않는 제품번호를 입력한 경우
				String message = "해당 게시글은 존재하지 않습니다.";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
			//	super.setRedirect(false);
				super.setViewPage("/WEB-INF/notice/notice.jsp");
				
				return;
			}
			else {
				// 제품이 있는 경우
				request.setAttribute("nvo", nvo); // 제품의 정보
				
			//	super.setRedirect(false);
				super.setViewPage("/WEB-INF/notice/view.jsp");
			}		
		
	}

}
