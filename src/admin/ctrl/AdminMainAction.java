package admin.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ctrl.AbstractController;


public class AdminMainAction extends AbstractController {
   
   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      // TODO Auto-generated method stub
      
      super.setViewPage("/WEB-INF/admin/adminMain.jsp");
      
   }

}