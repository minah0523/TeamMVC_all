package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class ProductAllDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		String pdnoes = request.getParameter("pdnoes");
		String userid_fk = request.getParameter("userid_fk");
		
		String[] arrPdno = pdnoes.split(",");
		
		for(int i=0; i<arrPdno.length; i++) {
			int pdno = Integer.parseInt(arrPdno[i]);
			pdao.productAllDelete(pdno, userid_fk);
		}
		
		
	}

}
