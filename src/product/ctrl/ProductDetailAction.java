package product.ctrl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.*;
import myshop.mdl.CartVO;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;
import myshop.mdl.ProductInfoVO;
import myshop.mdl.ProductVO;

public class ProductDetailAction extends AbstractController {
	
	@Override
	public String toString() {
		return "@@@ : 클래스 IndexController의 인스턴스 메소드 toString() 호출";
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" or "POST"
	/*      
	      if("GET".equalsIgnoreCase(method)) {
	    	  super.setRedirect(false);
	    	  super.setViewPage("/WEB-INF/login/loginPage.jsp");
	    	  return;
	      }
	      
	      // POST 방식으로 넘어온 것이라면 
	      String userid = request.getParameter("userid");
	      String pwd = request.getParameter("pwd");
		
		  HttpSession session = request.getSession(); 
		  // 메모리에 생성되어져 있는 session을 불러오는 것이다.
	      
	      // ===> 클라이언트의 IP 주소를 알아오는 것 <=== //
	      String clientip = request.getRemoteAddr();
	      // C:\NCS\workspace(jsp)\TeamMVC\WebContent\JSP 파일을 실행시켰을 때 IP 주소가 제대로 출력되기위한 방법.txt 참조할 것 
		  
		  Map<String, String> paraMap = new HashMap<>();
	      paraMap.put("userid", userid);
	      paraMap.put("pwd", pwd);
	      paraMap.put("clientip", clientip);
		  InterMemberDAO mdao = new MemberDAO();
	      MemberVO loginuser = mdao.selectOneMember(paraMap);
	      
	      if(loginuser != null) {
		         
		         if(loginuser.getIdle() == 1) {
		            String message = "로그인을 한지 1년이 지나서 휴면상태로 되었습니다. 관리자에게 문의 바랍니다.";
		            String loc = request.getContextPath()+"/TeamHomePage.neige";
		            // 원래는 위와같이 index.up 이 아니라 휴면인 계정을 풀어주는 페이지로 잡아주어야 한다.
		            
		            request.setAttribute("message", message);
		            request.setAttribute("loc", loc);
		            
		            super.setRedirect(false);
		            super.setViewPage("/WEB-INF/msg.jsp");
		            
		            return;  // 메소드 종료
		         }
		         else {
			         
			            // 막바로 페이지 이동을 시킨다. 
			            super.setRedirect(true);
			            super.setViewPage(request.getContextPath()+"/ProductDetail.neige");
			            
			         }
			         
			      }
			      else {
			         // System.out.println(">>> 확인용 로그인 실패!!! <<<");
			         String message = "로그인 실패";
			         String loc = "javascript:history.back()";
			         
			         request.setAttribute("message", message);
			         request.setAttribute("loc", loc);
			         
			         super.setRedirect(false);
			         super.setViewPage("/WEB-INF/msg.jsp");
			      }
	      
	      
		  session.setAttribute("loginuser", loginuser); 
		  // session(세션)에 로그인 되어진 사용자 정보인 loginuser 을 키이름을 "loginuser" 으로 저장시켜두는 것이다.
*/		  
		
		// System.out.println("@@@ 확인용 IndexController의 메소드 execute호출됨");
				
				InterProductDAO pdao = new ProductDAO();
				String pdno = "";
				
				pdno = request.getParameter("pdno");
				System.out.println("pdno : " + pdno);
				
				// 제품번호를 가지고서 해당 제품의 정보를 조회해오기
				//ProductVO pvo = pdao.selectOneProductByPnum(pdno);
				
				String addcartcheck = request.getParameter("addcartcheck");
				
				System.out.println("addcartcheck : " + addcartcheck);
				
				String userid = request.getParameter("userid");
				String color = request.getParameter("pcolor");
				String size = request.getParameter("psize");
				String qty = request.getParameter("qty"); 
				
				System.out.println("pd action userid : " + userid);
				System.out.println("pdaction color : " + color);
				System.out.println("pdaction size : " + size);
				System.out.println("pdaction qty : " + qty);
				
				
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("addcartcheck", addcartcheck);
				paraMap.put("pdno", pdno);
				paraMap.put("userid", userid);
				paraMap.put("color", color);
				paraMap.put("size", size);
				paraMap.put("qty", qty);
				System.out.println("DetailAction addcartcheck Check1 : " + addcartcheck);
				
				System.out.println("DetailAction paraMap Check1 : " + paraMap);
				
/*
 * 1124
 * 				String addcart = request.getParameter("addcart");
				
				System.out.println("addcartcheck : " + addcart);
				
				String cartno = request.getParameter("cartno");
				
				String userid_fk = request.getParameter("userid_fk");
				String pcolor = request.getParameter("pcolor");
				String psize = request.getParameter("psize");
				String pqty = request.getParameter("pqty"); 
				
				System.out.println("pd action cartno : " + cartno);
				System.out.println("pd action userid : " + userid_fk);
				System.out.println("pdaction color : " + pcolor);
				System.out.println("pdaction size : " + psize);
				System.out.println("pdaction qty : " + pqty);
				
				
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("addcart", addcart);
				paraMap.put("cartno", cartno);
				paraMap.put("userid_fk", userid_fk);
				paraMap.put("pcolor", pcolor);
				paraMap.put("psize", psize);
				paraMap.put("pqty", pqty);
				
				System.out.println("DetailAction addcartcheck Check1 : " + addcart);
				
				System.out.println("DetailAction paraMap Check1 : " + paraMap);
*/				
				
				
				
				
				List<ProductVO> productList = pdao.ProductList(pdno);
				request.setAttribute("productList", productList);
				System.out.println("detail action check" + productList);
				
				List<ProductInfoVO> productinfoList = pdao.ProductInfoList(pdno);
				request.setAttribute("productinfoList", productinfoList);
				System.out.println("detail action check2 : " +productinfoList);
				
				//super.setRedirect(false);
				super.setViewPage("/WEB-INF/productdetail/ProductDetail.jsp");
		
	}
	
}


