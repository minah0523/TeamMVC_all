package Payment.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class PaymentSuccessAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pdnoes = request.getParameter("pdnoes");
		String userid_fk = request.getParameter("userid_fk");
		String finalPrice = request.getParameter("finalPrice");
		String usePoint = request.getParameter("usePoint");
		
		String amount = request.getParameter("amount");
		
		int addPoint = 0;
		
		//addPoint = (int)(Integer.parseInt(finalPrice)/100);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		paraMap.put("pdnoes", pdnoes);
		paraMap.put("userid_fk", userid_fk);
		paraMap.put("finalPrice", finalPrice);
		paraMap.put("usePoint", usePoint);
		
		InterProductDAO pdao = new ProductDAO();
		
		String date = pdao.RecordOrder(paraMap);
		
		JSONObject jobj = new JSONObject();
		
		jobj.put("amount", amount);
		jobj.put("addPoint", addPoint);
		jobj.put("date", date);
		
		String json = jobj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}
