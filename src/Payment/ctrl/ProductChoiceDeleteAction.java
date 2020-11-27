package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class ProductChoiceDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		
		String Pinfonos = request.getParameter("Pinfonos");
		String userid_fk = request.getParameter("userid_fk");
		
		String[] arrPinfonos = Pinfonos.split(",");
		
		int n = 0;
		String message = "";
		
		for(int i=0; i<arrPinfonos.length; i++) { 
			int Pinfono = Integer.parseInt(arrPinfonos[i]); 
			n = pdao.productChoiceDelete(Pinfono, userid_fk);
			
			if(n != 1) {
				message = "실패";
			}
		}
		
		JSONObject jobj = new JSONObject();
		
		jobj.put("msg", message);
		
		String json = jobj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		 
		
	}

}
