package product.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import myshop.mdl.*;
import Payment.mdl.*;
import Payment.mdl.CartVO;

public class GoaddcartAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		
		//setRedirect(false);
		setViewPage("/WEB-INF/productdetail/goaddcart.jsp");
	}

}
