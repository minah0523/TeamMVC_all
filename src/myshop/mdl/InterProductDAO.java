package myshop.mdl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface InterProductDAO {
	
	   // 메인페이지의 캐러셀에 보여지는 상품이미지파일명을 모두 조회(SELECT) 하는 메소드 (JIEUN)
	   List<ImageVO> ImageCarouselSelectAll() throws SQLException;
	      
	   // 메인페이지에 보여지는 상품 이미지 tbl_product 에서 조회해오는 메소드  (JIEUN)
	   // List<ProductVO> ProductMainImageSelectAll(String sortType) throws SQLException;
	   List<ProductVO> ProductMainImageSelectAll(Map<String, String> paraMap) throws SQLException;
	   
	   // 메인페이지에서 보여지는 상품 색상 tbl_product_info에서 조회 및 정렬해오는 메소드(JIEUN)
	   List<String> ProductMainColorSelectAll(String pdno) throws SQLException;
	   
	   // 카테고리 목록 보여주는 메소드(코트, 자켓, 점퍼, 무스탕, 가디건)  (JIEUN)
	   List<CategoryVO> CategoryListSelectAll() throws SQLException;

	   // 카테고리 목록 클릭시 카테고리 코드에 따라 조회 및 정렬하는 메소드  (JIEUN)
	   List<ProductVO> categoryProducClickSelectAll(Map<String, String> paraMap) throws SQLException;
	   
	   // ============= 상품 등록 하는 메소드  ==================== //
	   // tbl_prodcut에 상품을 등록하는(insert) 메소드(JIEUN)
	   int ProdutcRegisterAll(ProductVO product) throws SQLException;   
	      
	   // 제품번호 채번해오는 메소드(JIEUN)
	   int getPnumOfProduct() throws SQLException;
	   
	   // 추가 이미지 파일 insert하는 메서드(JIEUN)
	   int product_imagefile_Insert(int pdno, String plusPdimage) throws SQLException;
	   
	   // 색상과 사이즈를 insert하는 메소드(JIEUN)
	   // int product_info_insert(Map<String, Object> paraMap) throws SQLException;
	   int product_info_insert(int pdno, String pcolor, String psize) throws SQLException;
	   
	   // 관리자 페이지에서 상품(번호, 카테고리, 상품명, 재고, 가격, 성별) 리스트 가져오는(select) 메소드(JIEUN)
	   List<ProductVO> adminProductListAll() throws SQLException;
	   
	   // 관리자 페이지에서 상품(번호, 카테고리, 상품명, 재고, 가격, 성별) 리스트 가져오는(select) 메소드(검색결과도 같이 조회 할 수 있도록)(JIEUN)
	   List<ProductVO> adminProductListAll(Map<String, String> paraMap) throws SQLException;
	   
	   // 페이징처리를 위해서 총 제품 개수와 페이지 개수 알아오기(select) (JIEUN)
	   int getTotalPage(Map<String, String> paraMap) throws SQLException;   
	   
	   // 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 상품정보 조회해서 받아오자(JIEUN)
	   ProductVO adminProductDetail(String pdno) throws SQLException;   
	   
	   // 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 색상, 사이즈 가져오기 (JIEUN)
	   List<ProductInfoVO> productInfoDetail(String pdno) throws SQLException;   
	   
	   // 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 추가 이미지 파일 가져오기  (JIEUN)
	   ProductImageFileVO addProductImageFileDetail(String pdno) throws SQLException;      
	
	// search페이지에 보여지는 상품이미지파일명을 모두 조회(select)하는 메소드 (MINA)
	List<ProductVO> searchProduct(Map<String, String> paraMap) throws SQLException;

	// 제품별 컬러 리스트를 조회하는 메서드 (MINA)
	List<String> selectProductColor(String pdno) throws SQLException;

	// 제품별 사이즈 리스트를 조회하는 메서드 (MINA)
	List<String> selectProductSize(String pdno) throws SQLException;

	// 페이징처리를 위해서 전체회원에 대한 총 제품 개수와 페이지 개수 알아오기(select) (MINA)
	Map<String, String> getTotal(Map<String, String> paraMap) throws SQLException;
	
	// 승의
	/*
	 * // 물품상세페이지에 ProductList 모두 조회 (승의) 
	 * List<ProductVO> ProductList() throwsSQLException;
	 */

	// 물품상세페이지에 ProductList의 해당 pdno의 정보 조회 (승의)
	List<ProductVO> ProductList(String pdno) throws SQLException;

	// 물품상세페이지에서의 사이즈,색상 (승의)
	List<ProductInfoVO> ProductInfoList(String pdno) throws SQLException;

	// 특정 회원이 특정 제품에 대해 좋아요에 투표하기(insert)
	int like(Map<String, String> paraMap) throws SQLException;

	// 특정 제품에 대한 좋아요의 개수결과(select)
	Map<String, Integer> getLikeCnt(String pdno) throws SQLException;

	// ------
	// 현재 페이지의 제품의 pdno를 받아와 장바구니에 insert해준다.
	int addCart(Map<String, String> paraMap) throws SQLException;

	// ProductVO selectOneProductByPnum(String pdno) throws SQLException;
	
   // Ajax 를 이용한 특정 제품의 상품후기를 입력(insert)하기
   int addComment(PurchaseReviewsVO previewvo) throws SQLException;

   // Ajax 를 이용한 특정 제품의 상품후기를 조회(select)하기
   List<PurchaseReviewsVO> commentList(String fk_pdno) throws SQLException;
	
	// 승의 끝
	
	// 장바구니 테이블에 존재하는 row를 특정유저(로그인된 유저)의 아이디와 제품번호를 사용하여
	// select 해온뒤 List에 담는다. 그 뒤 해당 List를 이용하여 반복문을 사용해 선택한 제품을 화면에 출력  (동휘)
	List<ProductVO> getCartList(String userid) throws SQLException;
	
	// 장바구니 테이블 안에 userid_fk로 입력받은 사람의 모든 열을 삭제, 전체삭제버튼 (동휘)
	void productAllDelete(int arrPdno, String userid_fk) throws SQLException;
	
	// 상품 개별삭제 버튼을 누를경우 유저의 ID와 해당 제품의 번호를 받아와 DB테이블에서 삭제해주는 메서드(동휘)
	int productOneDelete(String pdno, String userid_fk) throws SQLException;
	
	// 장바구니에서 선택상품들을 DB테이블에서 삭제시키는 메서드(동휘)
	void productChoiceDelete(int pdno, String userid_fk) throws SQLException;
	
	// 장바구니테이블의 userid를 기반으로 상품상세테이블에서 사이즈, 색상들을 받아오는 메서드(동휘)
	List<ProductInfoVO> getSizeAndColor(String userid) throws SQLException;

	// 주문이 완료된뒤 총가격, 총 적립 포인트 등을 주문 테이블에 insert하는 메서드(동휘)
	String RecordOrder(Map<String, String> paraMap) throws SQLException;

	// 주문완료페이지에 완료정보를 뿌려주기 위한 메서드(동휘)
	OrderVO getOrderInfo(String userid) throws SQLException;
	
}
