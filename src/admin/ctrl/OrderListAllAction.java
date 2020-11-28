package admin.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Payment.mdl.OrderDetailVO;
import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class OrderListAllAction extends AbstractController {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		InterProductDAO pdao = new ProductDAO();
		
		// 관리자 페이지에서 주문 리스트 가져오는(select) 메소드
		List<OrderDetailVO> adminOrderList = pdao.adminOrderListAll();
		request.setAttribute("adminOrderList", adminOrderList);
		
		super.setViewPage("/WEB-INF/admin/orderListAll.jsp");
		
	}

}
