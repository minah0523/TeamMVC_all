package notice.ctrl;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.ctrl.AbstractController;
import member.mdl.InterMemberDAO;
import member.mdl.MemberDAO;
import member.mdl.MemberVO;
import notice.mdl.*;

public class NoticeWriteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
				// == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
				HttpSession session = request.getSession();
				
				MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
				
				if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {	// 관리자(admin)로 로그인 했을 경우
	 					
					String method = request.getMethod();
					
					if("GET".equalsIgnoreCase(method)) {
					//  super.setRedirect(false);
						super.setViewPage("/WEB-INF/notice/write.jsp");
					}
					
					else {
						
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
						nvo.setTitle(title);
						nvo.setContents(contents);
						
						InterNoticeDAO ndao = new NoticeDAO();
						int n = ndao.NoticeInsert(nvo);
						
						String message = "";
						String loc = "";
						
						if(n == 1) {
							message = "공지사항 등록 성공";
							
							loc = request.getContextPath()+"/notice/notice.neige"; // 공지사항 목록 페이지로 이동한다. 
							///index.up
							/* super.setViewPage("/WEB-INF/member/memberRegisterEnd.jsp"); */
						}///WEB-INF/member/memberRegisterEnd.jsp
						else {
							message = "공지사항 등록 실패";
							loc = "javascript:history.back()" ; // 자바스크립트를 이용한 이전페이지로 이동하는것.
						}
						
						request.setAttribute("message", message);
						request.setAttribute("loc", loc);
						
					//	super.setRedirect(false);
						super.setViewPage("/WEB-INF/msg.jsp");
					}
					
				  } else { // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
					  
					  String message = "관리자만 접근이 가능합니다.";
					  String loc = "javascript:history.back()";
					  
					  request.setAttribute("message", message); 
					  request.setAttribute("loc", loc);
					  
					  super.setRedirect(false); 
					  super.setViewPage("/WEB-INF/msg.jsp"); 
				  }
				  
					
			}

		
}
