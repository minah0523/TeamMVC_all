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
            
            // 관리자페이지의 상품관리의 리스트 중에서 하나를 클릭했을때 여기로 넘어온다.
            String pdno = request.getParameter("pdno");
            
            // System.out.println("상품 리스트 중 클릭한 번호는? ==> "+ pdno);
            
            // pdno를 jsp파일에서 가지고 있도록 setAttribute 시켜준다.
            request.setAttribute("pdno", pdno);
            
            // 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 조회해서 받아오자
            ProductVO pvo = pdao.adminProductDetail(pdno);
            
            /*
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
            */
            
            request.setAttribute("pvo", pvo);
            
            // 색상, 사이즈 가져오기 
            List<ProductInfoVO> pdinfoLists = pdao.productInfoDetail(pdno);

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
            
            // 객체를 문자열로 변환
            String sPcolor = String.join(",", linkedHashSetColor);
            String sPsize = String.join(",", linkedHashSetSize);
            
            // System.out.println("문자열로 변환한 색상 ===> " + sPcolor);
            // System.out.println("문자열로 변환한 사이즈 ===> " + sPsize);
            
            /* 
            for( String color : pdinfoColor ) {
               System.out.println("색상은 ???" + color);
            }
            for( String size : pdinfoSize ) {
               System.out.println("사이즈 ???" + size);
            }
            */
            
            request.setAttribute("sPcolor", sPcolor);
            request.setAttribute("sPsize", sPsize);
            
            // 추가 이미지 파일 가져오기
            ProductImageFileVO pimgvo = pdao.addProductImageFileDetail(pdno);
            
            request.setAttribute("pimgvo", pimgvo);
            
            super.setViewPage("/WEB-INF/admin/productUpdate.jsp");         
            
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
                 request.setAttribute("loc", request.getContextPath()+"/admin/productUpdate.neige"); 
                 
                 super.setViewPage("/WEB-INF/msg.jsp");
                 return;
            }
            
            String pdno = mtrequest.getParameter("pdno");
            
            // 수정버튼 누르면 여기로 넘어온다. 
            String pdcategory_fk = mtrequest.getParameter("pdcategory_fk");
            String pdname = mtrequest.getParameter("pdname");
            // String pdimage1 = mtrequest.getFilesystemName("pdimage1");
            // String pdimage2 = mtrequest.getFilesystemName("pdimage2");
            int pdqty = Integer.parseInt(mtrequest.getParameter("pdqty"));
            int price =  Integer.parseInt(mtrequest.getParameter("price"));
            int saleprice =  Integer.parseInt(mtrequest.getParameter("saleprice"));
            String pdcontent = mtrequest.getParameter("pdcontent");
            String texture = mtrequest.getParameter("texture");
            String pdgender = mtrequest.getParameter("pdgender");
            String pcolores = mtrequest.getParameter("pcolor").trim().replace(" ", "");
            String psizees = mtrequest.getParameter("psize").trim().replace(" ", "");
            
            // 첨부파일은 변경 안하는걸로...
                        
            System.out.println("상품번호 ==> " + pdno);
            System.out.println("카테고리 ==> " + pdcategory_fk);
            System.out.println("상품명 ==> " + pdname);
            // System.out.println("pdimage1 ==> " + pdimage1);
            // System.out.println("pdimage2 ==> " + pdimage2);
            System.out.println("재고량 ==> " + pdqty);
            System.out.println("가격 ==> " + price);
            System.out.println("할인가 ==> " + saleprice);
            System.out.println("내용 ==> " + pdcontent);
            System.out.println("소재 ==> " + texture);
            System.out.println("성별 ==> " + pdgender);
            System.out.println("색상 ==> " + pcolores);
            System.out.println("사이즈 ==> " + psizees);
            
            ProductVO pvo = new ProductVO();
            
            
            pvo.setPdno(Integer.parseInt(pdno));
            pvo.setPdcategory_fk(pdcategory_fk);
            pvo.setPdname(pdname);
            // pvo.setPdimage1(pdimage1);
            // pvo.setPdimage2(pdimage2);
            pvo.setPdqty(pdqty);
            pvo.setPrice(price);
            pvo.setSaleprice(saleprice);
            pvo.setPdcontent(pdcontent);
            pvo.setTexture(texture);
            pvo.setPdgender(pdgender);
            
            // 상품 업데이트 메소드(update)
            int prodUpdate = pdao.productUpdate(pvo);
            
            // 색상과 사이즈의 개수가 같지 않으면 
            
            String[] pcolor;
            String[] psize;
            
            pcolor = pcolores.split(",");
            psize = psizees.split(",");
            
            for(int i=0; i<pcolor.length; i++) {
               System.out.println("배열 색상 ==> " + pcolor[i]);
            }
            
            // 색상 배열을 리스트로 변환
            List<String> pcolorList = new ArrayList<>();
            Collections.addAll(pcolorList, pcolor);
            
            for( String clist : pcolorList ) {
               System.out.println("색상 리스트 출력 ==> " + clist);
            }
      
            // 사이즈 배열을 리스트로 변환
            List<String> psizeList = new ArrayList<>();
            Collections.addAll(psizeList, psize);
            
            for(String slist : psizeList) {
               
               System.out.println("사이즈 리스트 출력 ==> " + slist);
            }
            
            // 사이즈의 개수가 컬러와 다르면 사이즈를 강제로 개수를 늘려야 한다. 
            
            List<String> nsizeList = new ArrayList<>(); // 개수를 늘린 사이즈 리스트를 새로 받는 리스트
            
            
            if(pcolorList.size() > nsizeList.size()) {
               // 색상리스트와 사이즈가 리스트의 크기보다 크다면 
               
               // free 인 경우
                for(int i=0; i<pcolorList.size(); i++) {
                        
                      nsizeList.add(psizeList.get(0));
                        
                        System.out.println("test사이즈 ==> " + nsizeList.get(0) );
                 }
            }
                        
            int prodInfo = 0;
            
            for(int i=0; i<pcolorList.size(); i++) {
               // 색상, 사이즈 업데이트 메소드(update)
               // prodInfo = pdao.productInfoUpdate(pdno, pcolorList.get(i), nsizeList.get(i));
            }
            
            HashMap<String, String> pdinfono = pdao.pdinfoseqSelect(pdno);
               
            String color = "";
            String size = "";
            int rownum = 0;
            String pinfonum = "";
            
            for(int j=0; j<pcolorList.size(); j++) { // 컬러리스트의 사이즈(길이)만큼
            
               for(int k=0; k<nsizeList.size(); k++) { // 사이즈리스트의 사이즈(길이)만큼
                  
                  color = pcolorList.get(j);
                  size = nsizeList.get(k);
                  rownum++; //rownum을 1씩 증가시킨다
                  pinfonum = pdinfono.get(String.valueOf(rownum));
                  
                  System.out.println("color, size, rownum, pinfonum" + color +","+size +","+rownum +","+pinfonum);
                  
                  prodInfo = pdao.productInfoUpdate(pinfonum, color, size);
                  
               }
            }
            
               
            String message = ""; // alert 내용
            String loc = "";     // 경로   
            
            if(prodUpdate * prodInfo == 1) {
               message = "상품 수정 완료!!";
               
               // message 띄운후 어느 페이지로 갈래?
               loc = request.getContextPath() + "/admin/productListAll.neige"; // 성공하면 리스트 페이지로 가자      
            }
            else {
               message = "상품 수정 실패!!";
               loc = request.getContextPath() + "/admin/productListAll.neige";
            }
            
            // paraMap = new HashMap<String, String>();
            
            // 변경된 값을 insert해주는 메소드
            
            
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