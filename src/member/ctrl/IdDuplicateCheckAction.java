package member.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import member.mdl.*;

public class IdDuplicateCheckAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");
		
		System.out.println(">>> 확인용 userid => " + userid);
		
		InterMemberDAO mdao = new MemberDAO();
		boolean isExists = mdao.idDuplicateCheck(userid);
		
		JSONObject jsonObj = new JSONObject(); // {}
		jsonObj.put("isExists", isExists);  // {"isExists":true} 또는 {"isExists":false} 으로 만들어준다.
		
		String json = jsonObj.toString(); // 문자열 형태인 {"isExists":true} 또는 {"isExists":false} 으로 만들어준다. 
	//	System.out.println(">>> 확인용 json => " + json);
	//  {"isExists":true}	{"isExists":false}
		
		request.setAttribute("json", json);
		
	//	super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}







