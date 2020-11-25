package product.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.ctrl.AbstractController;
import myshop.mdl.*;

public class LikeCntAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pnum = request.getParameter("pnum");
		
		InterProductDAO pdao = new ProductDAO();
		
		Map<String, Integer> map = pdao.getLikeCnt(pnum);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("likecnt", map.get("likecnt"));			// {"likecnt":1}
		
		String json = jsonObj.toString();	// {"likecnt":1,"dislikecnt":0}
		
		request.setAttribute("json", json);
		
		//super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
		
		
	}

}
