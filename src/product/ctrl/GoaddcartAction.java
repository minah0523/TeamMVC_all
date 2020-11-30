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
		
		System.out.println("GoaddcartAct- color ==> " + color);
		System.out.println("GoaddcartAct- size ==> " + size);
		System.out.println("GoaddcartAct- pdno ==> " + pdno);
		
		
		// 이건 별도로 카트에 담아줄것
		String pqty = request.getParameter("pqty");
		System.out.println("GoaddcartAct- pqty ==>" + pqty);
		
		String pinfono = pdao.getPinfono(color, size, pdno);
		System.out.println("GoaddcartAct- pinfono ==>" + pinfono);
		
		Map<String,String> cartMap = new HashMap<>();
		
		cartMap.put("color", color);
		cartMap.put("size", size);
		cartMap.put("pdno", pdno);
		cartMap.put("pqty", pqty);
		cartMap.put("pinfono", pinfono);
		
		System.out.println("GoaddcartAct Map Check : " + cartMap);
		
		
		int CartList = pdao.sendCartList(cartMap);
		
		request.setAttribute("CartList", CartList);
		
		
		/*pinfono = request.getParameter("pinfono");
		 *request.setAttribute("pinfono", pinfono);
		 *System.out.println("pinfono G.A.C.A 2nd check : " + pinfono);
		 *
		 * String message = ""; // alert 내용 String loc = ""; // 경로
		 * 
		 * if(cartMap == 1) { message = "장바구니 등록 완료";
		 * 
		 * // message 띄운후 어느 페이지로 갈래? loc = request.getContextPath() +
		 * "/Payment/Payment."; // 성공하면 리스트 페이지로 가자 }else { message = "장바구니 등록 실패"; loc
		 * = request.getContextPath() + "/productdetail/ProductDetail.neige"; }
		 * 
		 * } }
		 */
		
		
		//setRedirect(false);
		super.setViewPage("/WEB-INF/productdetail/ProductDetail.jsp");
	}

}
