package product.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.*;
import Payment.mdl.CartVO;
import myshop.mdl.*;

public class ProductDetailAction extends AbstractController {
	
	@Override
	public String toString() {
		return "@@@ : 클래스 IndexController의 인스턴스 메소드 toString() 호출";
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// System.out.println("@@@ 확인용 IndexController의 메소드 execute호출됨");
				
				InterProductDAO pdao = new ProductDAO();
				String pdno = "";
				
				pdno = request.getParameter("pdno");
				
				List<ProductVO> productList = pdao.ProductList(pdno);
				request.setAttribute("productList", productList);
				//System.out.println("detail action check" + productList);
				
				List<ProductInfoVO> productinfoList = pdao.ProductInfoList(pdno);
				request.setAttribute("productinfoList", productinfoList);
				//System.out.println("detail action check2 : " +productinfoList);
				
				
				//super.setRedirect(false);
				super.setViewPage("/WEB-INF/productdetail/ProductDetail.jsp");
		
	}
	
}


