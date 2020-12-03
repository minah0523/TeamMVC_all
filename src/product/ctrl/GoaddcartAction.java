package product.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import myshop.mdl.*;
import Payment.mdl.*;

public class GoaddcartAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		//HttpSession session = request.getSession();
		//MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		//if( loginuser != null) {
			//String method = request.getMethod();
			
			// 메소드가 get 방식이면 바로 등록 페이지로 이동한다. 
			//if("GET".equalsIgnoreCase(method)) {
				

		InterProductDAO pdao = new ProductDAO();
		
		
		String color = request.getParameter("color");
		String size = request.getParameter("size");
		String pdno = request.getParameter("pdno");
				
		// 이건 별도로 카트에 담아줄것
		String pqty = request.getParameter("pqty");
		
		String pinfono = pdao.getPinfono(color, size, pdno);
		
		Map<String,String> cartMap = new HashMap<>();
		
		cartMap.put("color", color);
		cartMap.put("size", size);
		cartMap.put("pdno", pdno);
		cartMap.put("pqty", pqty);
		cartMap.put("pinfono", pinfono);
		
		int CartList = pdao.sendCartList(cartMap);
		
		request.setAttribute("CartList", CartList);
		
		//setRedirect(false);
		super.setViewPage("/WEB-INF/productdetail/ProductDetail.jsp");
	}

}
