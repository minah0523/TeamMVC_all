package product.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class AddCartAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pdno = request.getParameter("pdno");
		String userid = request.getParameter("userid");
		
		String color = request.getParameter("pcolor");
		String size = request.getParameter("psize");
		String qty = request.getParameter("qty"); 
		
		
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("pdno", pdno);
		paraMap.put("userid", userid);
		paraMap.put("color", color);
		paraMap.put("size", size);
		paraMap.put("qty", qty);
		
		
		System.out.println("AddcartAction paraMap Check : " + paraMap);
		
		
		InterProductDAO pdao = new ProductDAO();
		
/*		int n = pdao.like(paraMap);
		// n => 1 이라면 정상투표,  n => 0 이라면 중복투표
		System.out.println("AddcartAction check2 : " + paraMap);
		
		String msg = "";
		
		if(n==1) {
			msg = "해당제품에\n 좋아요를 클릭하셨습니다.";
		}
		else {
			msg = "이미 좋아요를 클릭하셨기에\n 두번 이상 좋아요는 불가합니다.";
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msg", msg); // {"msg":"해당제품에\n 좋아요를 클릭하셨습니다."}   {"msg":"이미 좋아요를 클릭하셨기에\n 두번 이상 좋아요는 불가합니다."} 
		
		String json = jsonObj.toString(); // "{"msg":"해당제품에\n 좋아요를 클릭하셨습니다."}"   "{{"msg":"이미 좋아요를 클릭하셨기에\n 두번 이상 좋아요는 불가합니다."}}" 
		
		request.setAttribute("json", json);
*/		
	//	super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");

	}

}
