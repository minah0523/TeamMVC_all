package Payment.ctrl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class PaymentSuccessAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Random rnd = new Random();
		
		String pinfonos = request.getParameter("pinfonos");
		String userid_fk = request.getParameter("userid_fk");
		String finalPrice = request.getParameter("finalPrice");
		String addPoint = request.getParameter("addPoint");
		String usePoint = request.getParameter("usePoint");
		String amount = request.getParameter("amount");
		String prices = request.getParameter("prices");
		String amounts = request.getParameter("amounts");
		
		String[] pinfono = pinfonos.split(",");
		String[] price = prices.split(",");
		String[] productamount = amounts.split(",");
		
		// 인증키를 랜덤하게 생성하도록 한다.
		String ordCode = "";
		// 인증키는 영문소문자 5글자 + 숫자 7글자 로 만들겠습니다.
		// 예: certificationCode ==> dngrn4745003
		
		char randchar = ' ';
		for(int i=0; i<5; i++) {
		/*
		    min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
		    int rndnum = rnd.nextInt(max - min + 1) + min;
		       영문 소문자 'a' 부터 'z' 까지 랜덤하게 1개를 만든다.  	
		 */	
			
			randchar = (char) (rnd.nextInt('z' - 'a' + 1) + 'a');
			ordCode += randchar;
		}// end of for----------------------------
		
		int randnum = 0;
		for(int i=0; i<7; i++) {
			randnum = rnd.nextInt(9 - 0 + 1) + 0;
			ordCode += randnum;
		}// end of for----------------------------
		
		for(int i=0; i< pinfono.length; i++) {
			
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("ordCode", ordCode+"_"+i);
			paraMap.put("pinfono", pinfono[i]);
			paraMap.put("price", price[i]);
			paraMap.put("productamount", productamount[i]);
			paraMap.put("userid_fk", userid_fk);
			paraMap.put("finalPrice", finalPrice);
			paraMap.put("addPoint", addPoint);
			paraMap.put("amount", amount);
			paraMap.put("usePoint", usePoint);
			
			InterProductDAO pdao = new ProductDAO();
			
			pdao.RecordOrder(paraMap);
			
		}
		JSONObject jobj = new JSONObject();
		
		jobj.put("amount", amount);
		jobj.put("addPoint", addPoint);
		
		String json = jobj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}
