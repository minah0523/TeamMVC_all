package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class ProductAllDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		String pinfonos = request.getParameter("pinfonos");
		String userid_fk = request.getParameter("userid_fk");
		
		String[] arrPinfono = pinfonos.split(",");
		
		int n = 0;
		
		String msg = "";
		
		for(int i=0; i<arrPinfono.length; i++) {
			int pinfono = Integer.parseInt(arrPinfono[i]);
			n = pdao.productAllDelete(pinfono, userid_fk);
		}
		
		JSONObject jobj = new JSONObject();
		
		jobj.put("msg", msg);
		
		String json = jobj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
		
	}

}
