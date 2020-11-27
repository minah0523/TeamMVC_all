package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class ProductOneDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		String pinfono = request.getParameter("pinfono");
		String userid_fk = request.getParameter("userid");
		
		int n = pdao.productOneDelete(pinfono, userid_fk);
		
		String message = "";
		
		if(n == 1) {
			message = "삭제성공";
		}
		
		JSONObject jobj = new JSONObject();
		
		jobj.put("msg", message);
		
		String json = jobj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}
