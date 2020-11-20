package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class ProductOneDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		String pdno = request.getParameter("pdno");
		String userid_fk = request.getParameter("userid_fk");
		
		int n = pdao.productOneDelete(pdno,userid_fk);
		
		
	}

}
