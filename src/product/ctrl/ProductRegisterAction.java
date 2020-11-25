package product.ctrl;

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

public class ProductRegisterAction extends AbstractController {
	
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
				
				super.setViewPage("/WEB-INF/product/productRegister.jsp");
				
				
			}
			else {
				// post 방식으로 데이터가 넘어왔다면
				
				MultipartRequest mtrequest = null; 
				
				// 1. 첨부되어진 파일을 디스크의 어느경로에 업로드 할 것인지 그 경로를 설정해야 한다.
				HttpSession sesssion = request.getSession();
			
				ServletContext svlCtx = sesssion.getServletContext();
				String imagesDir = svlCtx.getRealPath("/images2"); // 진짜 경로 어디인지 알아보고자 한다.
				
				System.out.println("=== 첨부되어지는 이미지 파일이 올라가는 절대경로 imagesDir ==> " + imagesDir);
				// === 첨부되어지는 이미지 파일이 올라가는 절대경로 imagesDir ==> C:\NCS\workspace(jsp)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MyMVC\images
				
				try {	
					  //  === 파일을 업로드 해준다.  ===
					  mtrequest = new MultipartRequest(request, imagesDir, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy() ); // 파일하나 올릴때 10MB만 올릴 수 있게 하겠다.
					
				} catch(IOException e) { // 파일이 저장 되어진 경로가 잘 못되어지면 입출력 에러인 IOExcetion 발생 시킨다.
					  request.setAttribute("message", "업로드 되어질 경로가 잘못되었거나 또는 최대용량 10MB를 초과했으므로 파일업로드 실패함!!");
					  request.setAttribute("loc", request.getContextPath()+"/product/productRegister.neige"); 
					  
					  super.setViewPage("/WEB-INF/msg.jsp");
					  return;
				}
				
				String pdcategory_fk = mtrequest.getParameter("pdcategory_fk");
				String pdname = mtrequest.getParameter("pdname");
				
				
				String pdimage1 = mtrequest.getFilesystemName("pdimage1");
				String pdimage2 = mtrequest.getFilesystemName("pdimage2");
				
				int pdqty = Integer.parseInt(mtrequest.getParameter("pdqty"));
				int price = Integer.parseInt(mtrequest.getParameter("price"));
				int saleprice = Integer.parseInt(mtrequest.getParameter("saleprice"));
				
				// !!!!! 크로스 사이트 스크립트 공격에 대응하는(Secure 보안) 코드  작성하기 !!!!! // 
				String pdcontent = mtrequest.getParameter("pdcontent");
				pdcontent = pdcontent.replaceAll("<", "&lt;");
				pdcontent = pdcontent.replaceAll(">", "&gt;");
				
				// 입력한 내용에서 엔터는 <br>로 변환시키기
				pdcontent = pdcontent.replaceAll("\r\n", "<br>");
				
				int point = (int)(price * 0.01);
				String texture = mtrequest.getParameter("texture");
				String pdgender = mtrequest.getParameter("pdgender");
				
				String pcolores = mtrequest.getParameter("pcolor").trim().replace(" ", "");
				String psizees = mtrequest.getParameter("psize").trim().replace(" ", "");
				
				
				int pdno = pdao.getPnumOfProduct(); // 제품번호 채번해오는 메소드
				
				ProductVO product = new ProductVO();
				
				product.setPdno(pdno);
				product.setPdcategory_fk(pdcategory_fk);
				product.setPdname(pdname);
				product.setPdimage1(pdimage1);
				product.setPdimage2(pdimage2);
				product.setPdqty(pdqty);
				product.setPrice(price);
				product.setSaleprice(saleprice);
				product.setPdcontent(pdcontent);
				product.setPoint(point);
				product.setTexture(texture);
				product.setPdgender(pdgender);
				
				// 이미지 VO에 이미지파일명 저장
				// ImageVO img = new ImageVO();
				
				// img.setImgfilename(plusPdimage);
				
				// product.setImg(img);
				
				// 상품 등록 메소드
				int registerProduct = pdao.ProdutcRegisterAll(product);
				
				int m = 1; // 추가 등록 파일이 없을 수도 있으니까 1로 지정
				String plusPdimage = mtrequest.getFilesystemName("plusPdimage");
				
				if(!"".equals(plusPdimage)) {
					// null이 아니고 값이 있다면
					
					// 추가 이미지 파일 insert하는 메서드
					m = pdao.product_imagefile_Insert(pdno, plusPdimage);
					
					if(m == 0) {
						// 파일 insert가 잘못 된 경우
						return;
					}
					
				} // end of if()---------------------------------------
				
				// 색상, 사이즈 저장
				// ProductInfoVO prodInfo = new ProductInfoVO();
				
				// prodInfo.setPcolor(pcolor);
				// prodInfo.setPsize(psize);
				
				// product.setPinfo(prodInfo);
				
				// 색상과 사이즈는 여러가지이니까 split으로 , 를 구분자로 하여 쪼개서 배열로 만든다.(색상과 사이즈를 상품상세정보 테이블에 insert를 할 것이다.)
				
				
				String[] pcolors = pcolores.split(","); // 색상을 배열로 
				String[] psizes = psizees.split(",");  //  사이즈를 배열로 
				
				// 배열을 리스트로 만들기
				// 1. 색상 배열 => 색상 리스트
				List<String> pcolorList = new ArrayList<>();
				
				Collections.addAll(pcolorList, pcolors);
				
				System.out.println("색상 리스트 => " + pcolorList);
				
				// 2. 사이즈 배열 => 사이즈 리스트
				List<String> psizeList = new ArrayList<>();
				Collections.addAll(psizeList, pcolors);
				
				System.out.println("사이즈 리스트 => " + psizeList);
				
				// pdno, 색상, 사이즈 Map에 담겨서 넘기자~~~~~
				Map<String, Object> paraMap = new HashMap<String, Object>();
				
				paraMap.put("pdno", String.valueOf(pdno));
				paraMap.put("pcolors", pcolors);
				paraMap.put("psizes", psizes);
				
				int productInfo = 0;
				
				for(int i=0; i<pcolors.length; i++) {
					
					// 색상과 사이즈를 insert하는 메소드 
					// productInfo = pdao.product_info_insert(paraMap);
					productInfo = pdao.product_info_insert(pdno, pcolorList.get(i), psizeList.get(i));
					
					
				}
				
				String message = ""; // alert 내용
				String loc = "";     // 경로			
			
				if( registerProduct * m * productInfo == 1) {
					// insert 성공 시
					message = "상품등록 성공";					
					
					// message 띄운후 어느 페이지로 갈래?
					loc = request.getContextPath() + "/TeamHomePage.neige"; // 성공하면 시작페이지로 이동할 것이다.				
				
				}
				else {
					// insert 실패 시
					message = "상품등록 실패";
					loc = request.getContextPath() + "/product/productRegister.neige"; // 실패하면 자바스크립트를 이용한 이전페이지로 이동하는 것.				
					
				}
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				// super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp"); // message, location 띄우기
				
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