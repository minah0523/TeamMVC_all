<<<<<<< HEAD
package admin.ctrl;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import myshop.mdl.*;

public class ProductUpdateAction extends AbstractController {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 관리자로 로그인 했을때만 상품등록 할 수 있게 하자!
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		InterProductDAO pdao = new ProductDAO();
		
		// System.out.println("로그인 정보 -==> " + loginuser.getUserid());
		
		if( loginuser != null && "admin".equals(loginuser.getUserid())) {
			
			String method = request.getMethod();
			
			// 메소드가 get 방식이면 바로 등록 페이지로 이동한다. 
			if("GET".equalsIgnoreCase(method)) {
				
				System.out.println("아아아아아 난 get방식인데~~~");
				
				// 관리자페이지의 상품관리의 리스트 중에서 하나를 클릭했을때 여기로 넘어온다.
				String pdno = request.getParameter("pdno");
				System.out.println("상품 리스트 중 클릭한 번호는? ==> "+ pdno);
				
				// 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 조회해서 받아오자
				ProductVO pvo = pdao.adminProductDetail(pdno);
				
				String pdname = pvo.getPdname();
				String pdcategory_fk = pvo.getPdcategory_fk();
				String pdimage1 = pvo.getPdimage1();
				String pdimage2 = pvo.getPdimage2();
				int pdqty = pvo.getPdqty();
				int price = pvo.getPrice();
				int saleprice = pvo.getSaleprice();
				String pdcontent = pvo.getPdcontent();
				String texture = pvo.getTexture();
				String pdgender = pvo.getPdgender();
				
				request.setAttribute("pvo", pvo);
				
				// 색상, 사이즈 가져오기 
				List<ProductInfoVO> pdinfoLists = pdao.productInfoDetail(pdno);
				/*
				 * String[] strPcolor = pdinfoLists.toArray(new String[pdinfoLists.size()]);
				 * String[] strPsize = pdinfoLists.toArray(new String[pdinfoLists.size()]);
				 */
 				
				String pcolor = "";
				String psize = "";
				
				List<String> pdinfoColor = new ArrayList<String>();
				List<String> pdinfoSize = new ArrayList<String>();
				
				for(int i=0; i<pdinfoLists.size(); i++) {
					
					pcolor = pdinfoLists.get(i).getPcolor();
					psize = pdinfoLists.get(i).getPsize();
					
					pdinfoColor.add(pcolor);
					pdinfoSize.add(psize);
					
				}
				
				
				// 색상과 사이즈 중복된 요소는 한번씩 출력되도록(예: free, free, free ==> free)
				LinkedHashSet<String> linkedHashSetColor = new LinkedHashSet<>();
				
				for( String removeDoubleColor : pdinfoColor ) {
					
					linkedHashSetColor.add(removeDoubleColor);
					
				}
				
				LinkedHashSet<String> linkedHashSetSize = new LinkedHashSet<>();
				
				for( String removeDoubleSize : pdinfoSize ) {
					
					linkedHashSetSize.add(removeDoubleSize);
					
				}				
					
				System.out.println("중복 제거 사이즈 ==> "+ linkedHashSetSize);	
				
				// 객체를 문자열로 변환
				String sPcolor = String.join(",", linkedHashSetColor);
				String sPsize = String.join(",", linkedHashSetSize);
				
				System.out.println("문자열로 변환한 색상 ===> " + sPcolor);
				System.out.println("문자열로 변환한 사이즈 ===> " + sPsize);
				
				
				
				for( String color : pdinfoColor ) {
					System.out.println("색상은 ???" + color);
				}
				for( String size : pdinfoSize ) {
					System.out.println("사이즈 ???" + size);
				}
				
				request.setAttribute("sPcolor", sPcolor);
				request.setAttribute("sPsize", sPsize);
				
				super.setViewPage("/WEB-INF/admin/productUpdate.jsp");			
				
			}
			else {
				// post 방식으로 데이터가 넘어왔다면
				
				
				
			} // post 방식 끝!!!			
			
			
		}
		else {
	         // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "관리자만 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	      // super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	      }		
		
	}

}
=======
package admin.ctrl;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import myshop.mdl.*;

public class ProductUpdateAction extends AbstractController {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 관리자로 로그인 했을때만 상품등록 할 수 있게 하자!
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		InterProductDAO pdao = new ProductDAO();
		
		// System.out.println("로그인 정보 -==> " + loginuser.getUserid());
		
		if( loginuser != null && "admin".equals(loginuser.getUserid())) {
			
			String method = request.getMethod();
			
			// 메소드가 get 방식이면 바로 등록 페이지로 이동한다. 
			if("GET".equalsIgnoreCase(method)) {
				
				System.out.println("아아아아아 난 get방식인데~~~");
				
				// 관리자페이지의 상품관리의 리스트 중에서 하나를 클릭했을때 여기로 넘어온다.
				String pdno = request.getParameter("pdno");
				System.out.println("상품 리스트 중 클릭한 번호는? ==> "+ pdno);
				
				// 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 조회해서 받아오자
				ProductVO pvo = pdao.adminProductDetail(pdno);
				
				String pdname = pvo.getPdname();
				String pdcategory_fk = pvo.getPdcategory_fk();
				String pdimage1 = pvo.getPdimage1();
				String pdimage2 = pvo.getPdimage2();
				int pdqty = pvo.getPdqty();
				int price = pvo.getPrice();
				int saleprice = pvo.getSaleprice();
				String pdcontent = pvo.getPdcontent();
				String texture = pvo.getTexture();
				String pdgender = pvo.getPdgender();
				
				request.setAttribute("pvo", pvo);
				
				// 색상, 사이즈 가져오기 
				List<ProductInfoVO> pdinfoLists = pdao.productInfoDetail(pdno);
				/*
				 * String[] strPcolor = pdinfoLists.toArray(new String[pdinfoLists.size()]);
				 * String[] strPsize = pdinfoLists.toArray(new String[pdinfoLists.size()]);
				 */
 				
				String pcolor = "";
				String psize = "";
				
				List<String> pdinfoColor = new ArrayList<String>();
				List<String> pdinfoSize = new ArrayList<String>();
				
				for(int i=0; i<pdinfoLists.size(); i++) {
					
					pcolor = pdinfoLists.get(i).getPcolor();
					psize = pdinfoLists.get(i).getPsize();
					
					pdinfoColor.add(pcolor);
					pdinfoSize.add(psize);
					
				}
				
				
				// 색상과 사이즈 중복된 요소는 한번씩 출력되도록(예: free, free, free ==> free)
				LinkedHashSet<String> linkedHashSetColor = new LinkedHashSet<>();
				
				for( String removeDoubleColor : pdinfoColor ) {
					
					linkedHashSetColor.add(removeDoubleColor);
					
				}
				
				LinkedHashSet<String> linkedHashSetSize = new LinkedHashSet<>();
				
				for( String removeDoubleSize : pdinfoSize ) {
					
					linkedHashSetSize.add(removeDoubleSize);
					
				}				
					
				System.out.println("중복 제거 사이즈 ==> "+ linkedHashSetSize);	
				
				// 객체를 문자열로 변환
				String sPcolor = String.join(",", linkedHashSetColor);
				String sPsize = String.join(",", linkedHashSetSize);
				
				System.out.println("문자열로 변환한 색상 ===> " + sPcolor);
				System.out.println("문자열로 변환한 사이즈 ===> " + sPsize);
				
				
				
				for( String color : pdinfoColor ) {
					System.out.println("색상은 ???" + color);
				}
				for( String size : pdinfoSize ) {
					System.out.println("사이즈 ???" + size);
				}
				
				request.setAttribute("sPcolor", sPcolor);
				request.setAttribute("sPsize", sPsize);
				
				super.setViewPage("/WEB-INF/admin/productUpdate.jsp");			
				
			}
			else {
				// post 방식으로 데이터가 넘어왔다면
				
				
				
			} // post 방식 끝!!!			
			
			
		}
		else {
	         // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "관리자만 접근이 가능합니다.";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	      // super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	      }		
		
	}

}
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
