<<<<<<< HEAD
package Payment.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import myshop.mdl.ImageVO;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;
import myshop.mdl.ProductInfoVO;
import myshop.mdl.ProductVO;

public class PayMentController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();// DAO 객체 생성
		
		HttpSession session = request.getSession();
		
		// 장바구니 테이블에서 보내준 제품번호를 받아온다.
		//request.getParameter("pdno");
		
		// 로그인된 유저의 아이디 정보를 받아온다.
		String userid = ((MemberVO)session.getAttribute("loginuser")).getUserid(); 
		//String userid = "admin";
		// 유저 로그인이 완료될경우 주석문 해제
		
		System.out.println(userid);
		
		// 캐러셀 이미지 name을 받아올 DTO객체를 생성
		List<ImageVO> productList = pdao.ImageCarouselSelectAll();
		
		// 장바구니 테이블에 존재하는 row를 특정유저(로그인된 유저)의 아이디와 제품번호를 사용하여
		// select 해온뒤 List에 담는다. List를 이용하여 반복문을 사용해 선택한 제품을 화면에 출력
		List<ProductVO> cartList = pdao.getCartList(userid);
		
		
		// 받아온 유저 ID를 기반으로 장바구니 테이블에 접근하여 제품번호, 색상, 사이즈를 얻어온다.
		List<ProductInfoVO> productInfoList = pdao.getSizeAndColor(userid);
		
		request.setAttribute("imgList", productList);
		request.setAttribute("productInfoList", productInfoList);
		request.setAttribute("cartList", cartList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/Payment/Payment.jsp");
		
	}

}
=======
package Payment.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.MemberVO;
import myshop.mdl.ImageVO;
import myshop.mdl.InterProductDAO;
import myshop.mdl.ProductDAO;
import myshop.mdl.ProductInfoVO;
import myshop.mdl.ProductVO;

public class PayMentController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InterProductDAO pdao = new ProductDAO();// DAO 객체 생성
		
		HttpSession session = request.getSession();
		
		// 장바구니 테이블에서 보내준 제품번호를 받아온다.
		//request.getParameter("pdno");
		
		// 로그인된 유저의 아이디 정보를 받아온다.
		String userid = ((MemberVO)session.getAttribute("loginuser")).getUserid(); 
		//String userid = "admin";
		// 유저 로그인이 완료될경우 주석문 해제
		
		System.out.println(userid);
		
		// 캐러셀 이미지 name을 받아올 DTO객체를 생성
		List<ImageVO> productList = pdao.ImageCarouselSelectAll();
		
		// 장바구니 테이블에 존재하는 row를 특정유저(로그인된 유저)의 아이디와 제품번호를 사용하여
		// select 해온뒤 List에 담는다. List를 이용하여 반복문을 사용해 선택한 제품을 화면에 출력
		List<ProductVO> cartList = pdao.getCartList(userid);
		
		
		// 받아온 유저 ID를 기반으로 장바구니 테이블에 접근하여 제품번호, 색상, 사이즈를 얻어온다.
		List<ProductInfoVO> productInfoList = pdao.getSizeAndColor(userid);
		
		request.setAttribute("imgList", productList);
		request.setAttribute("productInfoList", productInfoList);
		request.setAttribute("cartList", cartList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/Payment/Payment.jsp");
		
	}

}
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
