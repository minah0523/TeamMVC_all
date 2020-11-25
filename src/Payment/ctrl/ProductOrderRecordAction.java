<<<<<<< HEAD
package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;

public class ProductOrderRecordAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.setRedirect(true);
		super.setViewPage("/WEB-INF/Payment/SuccessPaymentPage.jsp");
		
	}

}
=======
package Payment.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;

public class ProductOrderRecordAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.setRedirect(true);
		super.setViewPage("/WEB-INF/Payment/SuccessPaymentPage.jsp");
		
	}

}
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
