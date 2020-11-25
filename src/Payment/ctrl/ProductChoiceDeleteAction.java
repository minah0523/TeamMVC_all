<<<<<<< HEAD
package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class ProductChoiceDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		
		String pdnoes = request.getParameter("pdnoes");
		String userid_fk = request.getParameter("userid_fk");
		
		String[] arrPdno = pdnoes.split(",");
		
		
		
		for(int i=0; i<arrPdno.length; i++) { 
			int pdno = Integer.parseInt(arrPdno[i]); 
			pdao.productChoiceDelete(pdno, userid_fk);
		}
		 
		
	}

}
=======
package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;

public class ProductChoiceDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();
		
		String pdnoes = request.getParameter("pdnoes");
		String userid_fk = request.getParameter("userid_fk");
		
		String[] arrPdno = pdnoes.split(",");
		
		
		
		for(int i=0; i<arrPdno.length; i++) { 
			int pdno = Integer.parseInt(arrPdno[i]); 
			pdao.productChoiceDelete(pdno, userid_fk);
		}
		 
		
	}

}
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
