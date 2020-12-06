package product.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import common.ctrl.AbstractController;
import myshop.mdl.*;

public class CommentListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InterProductDAO pdao = new ProductDAO();
		
		String pdno = "";
		pdno = request.getParameter("pdno");	// 제품번호
		
		JSONArray jsArr = new JSONArray(); // []
		List<PurchaseReviewsVO> commentList = pdao.commentList(pdno);
		
		//System.out.println("commentLisAct pdno : " + pdno);
		//System.out.println("commentLisAct commentList : " + commentList);
		//System.out.println("commentLisAct commentList size : " + commentList.size());
		
		if(commentList != null && commentList.size() > 0) {
		
			for(PurchaseReviewsVO reviewsvo : commentList) {
				JSONObject jsobj = new JSONObject();				// {} {}
				jsobj.put("contents", reviewsvo.getContents());		// {"contents":"제품후기내용물"}												{"contents":"제품후기내용물2"}
				jsobj.put("name", reviewsvo.getMvo().getName());	// {"contents":"제품후기내용물", "name":"작성자이름"}							{"contents":"제품후기내용물2", "name":"작성자이름2"}
				jsobj.put("writeDate", reviewsvo.getWriteDate());	// {"contents":"제품후기내용물", "name":"작성자이름", "writeDate":"작성일자"}	{"contents":"제품후기내용물2", "name":"작성자이름2", "writeDate":"작성일자2"}
				
				jsArr.put(jsobj);	// [ {"contents":"제품후기내용물", "name":"작성자이름", "writeDate":"작성일자"}	{"contents":"제품후기내용물2", "name":"작성자이름2", "writeDate":"작성일자2"} ]
			}
			
		}
		
		String json = jsArr.toString();	// 문자열 형태로 변환해줌.
		
		request.setAttribute("json", json);
		//System.out.println("commentLisAct json2 : " + json);
		//super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
		// [{},{},{}]
		

	}

}
