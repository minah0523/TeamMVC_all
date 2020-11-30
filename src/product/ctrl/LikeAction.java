package product.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.*;

public class LikeAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		
		
		String pdno = "";
		pdno = request.getParameter("pdno");
		String userid = "";
		userid = request.getParameter("userid");
		
		System.out.println("likeact pdno1 : " + pdno);
		System.out.println("likeact userid1 : " + userid);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("pdno", pdno);
		paraMap.put("userid", userid);
		
		System.out.println("likeact pdno2 : " + pdno);
		System.out.println("likeact userid2 : " + userid);
		int n = pdao.like(paraMap);
		// n => 1 이라면 정상투표,  n => 0 이라면 중복투표
		
		System.out.println("likeact pdno3 : " + pdno);
		System.out.println("likeact userid3 : " + userid);
		System.out.println("likeact n = " + n);
		
		String msg = "";
		
		if(n==1) {
			msg = "해당제품에\n 좋아요를 클릭하셨습니다.";
		}
		else {
			msg = "이미 좋아요를 클릭하셨기에\n 두번 이상 좋아요는 불가합니다.";
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msg", msg); // {"msg":"해당제품에\n 좋아요를 클릭하셨습니다."}   {"msg":"이미 좋아요를 클릭하셨기에\n 두번 이상 좋아요는 불가합니다."} 
		System.out.println("likeact jsonObj : " + jsonObj);
		
		String json = jsonObj.toString(); // "{"msg":"해당제품에\n 좋아요를 클릭하셨습니다."}"   "{{"msg":"이미 좋아요를 클릭하셨기에\n 두번 이상 좋아요는 불가합니다."}}" 
		System.out.println("likeact json : " + json);
		
		request.setAttribute("json", json);
		System.out.println("likeact json2 : " + json);
		//	super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
