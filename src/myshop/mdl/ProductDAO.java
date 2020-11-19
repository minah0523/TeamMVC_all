package myshop.mdl;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductDAO implements InterProductDAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ProductDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/teammvc_oracle"); // 이름(web.xml에 res-ref-name에 해당하는 것)
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 사용한 자원을 반납하는 close() 매소드 생성하기
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	////////////////////////////////////////////////////////////이지은//////////////////////////////////////////////////////////////////////
	
	// 메인페이지의 캐러셀에 보여지는 상품이미지파일명을 모두 조회(SELECT) 하는 메소드
		@Override
		public List<ImageVO> ImageCarouselSelectAll() throws SQLException {

			List<ImageVO> imageCarouselList = new ArrayList<ImageVO>();

			try {

				conn = ds.getConnection(); // DBCP에서 connection 받아오기

				String sql = " select imgno, imgfilename " + " from tbl_carousel_image " + " order by imgno asc ";

				pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.

				rs = pstmt.executeQuery(); // select 되어진 결과를 resultSet에 받는다.

				while (rs.next()) {

					ImageVO imgvo = new ImageVO();
					imgvo.setImgno(rs.getInt(1)); // 받아온 rs 첫번째 컬럼을 imgvo의 imgno에 setter해준다.
					imgvo.setImgfilename(rs.getString(2));

					imageCarouselList.add(imgvo); // imageList에 imgvo를 보내준다.

				} // end of while(rs.next()) ---------------------------

			} finally {
				close();
			}

			return imageCarouselList;
		}

		// 메인페이지에 보여지는 상품 이미지 tbl_product 에서 조회해오는 메소드
		@Override
		// public List<ProductVO> ProductMainImageSelectAll(String sortType) throws
		// SQLException {
		public List<ProductVO> ProductMainImageSelectAll(Map<String, String> paraMap) throws SQLException {

			List<ProductVO> productMainImageList = new ArrayList<ProductVO>();

			try {

				conn = ds.getConnection();			
				
				String sql = " select  pdno, pdname, pdcategory_fk, pdimage1, price, saleprice "
					 	   + " from tbl_product ";
				
				if("sortHighPrice".equals(paraMap.get("sortType"))) {
					// 값이 있는데 그 값이 sortHighPrice라면 (높은 가격)
					
					System.out.println("성별?????" + paraMap.get("gender"));
					System.out.println("정렬타입?????" + paraMap.get("sortType"));

					sql += " where pdgender = ? " 
						 + " ORDER BY saleprice desc ";
					
				}
				else if ("sortLowPrice".equals(paraMap.get("sortType"))) {
					// 값이 있는데 그 값이 sortLowPrice라면 (낮은 가격)

					System.out.println("성별?????" + paraMap.get("gender"));
					System.out.println("정렬타입?????" + paraMap.get("sortType"));

					sql += " where pdgender = ? " 
					     + " ORDER BY saleprice asc ";
				} 
				else if ("sortNewProduct".equals(paraMap.get("sortType"))) {
					// 값이 있는데 그 값이 sortNewProduct라면 (신상품 조회)

					System.out.println("성별?????" + paraMap.get("gender"));
					System.out.println("정렬타입?????" + paraMap.get("sortType"));

					sql += " where pdgender = ? and pdinputdate >= (sysdate - 31)";
					
				} 
				else {
					System.out.println("성별????? & 조건 없음 " + paraMap.get("gender"));
					System.out.println("정렬타입?????" + paraMap.get("sortType"));
					
					sql += " where pdgender = ? ";
				}

				pstmt = conn.prepareStatement(sql);
				
				if("sortHighPrice".equals(paraMap.get("sortType"))) {
					// 값이 있는데 그 값이 sortHighPrice라면 (높은 가격)
					pstmt.setNString(1, paraMap.get("gender"));			
				}
				else if ("sortLowPrice".equals(paraMap.get("sortType"))) {
					// 값이 있는데 그 값이 sortLowPrice라면 (낮은 가격)
					pstmt.setNString(1, paraMap.get("gender"));	
				} 
				else if ("sortNewProduct".equals(paraMap.get("sortType"))) {
					// 값이 있는데 그 값이 sortNewProduct라면 (신상품 조회)
					pstmt.setNString(1, paraMap.get("gender"));					
				} 
				else {
					pstmt.setNString(1, paraMap.get("gender"));	
				}			

				rs = pstmt.executeQuery();

				while (rs.next()) {

					ProductVO pdvo = new ProductVO();

					pdvo.setPdno(rs.getInt(1));
					pdvo.setPdname(rs.getString(2));
					pdvo.setPdcategory_fk(rs.getString(3));
					pdvo.setPdimage1(rs.getString(4));
					pdvo.setPrice(rs.getInt(5));
					pdvo.setSaleprice(rs.getInt(6));

					productMainImageList.add(pdvo);

				}

			} finally {
				close();
			}

			return productMainImageList;
		}

		// 메인페이지에서 보여지는 상품 색상 tbl_product_info에서 조회해오는 메소드(JIEUN)
		@Override
		public List<String> ProductMainColorSelectAll(String pdno) throws SQLException {

			List<String> ProductMainColorSelect = new ArrayList<>();

			try {

				conn = ds.getConnection();

				String sql = " select distinct pcolor " + " from tbl_product_info " + " where pdno_fk = ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pdno); // 위치홀더에 String으로 받아온 pdno를 추가해준다.

				rs = pstmt.executeQuery(); // 이걸 Query 보내서 ResultSet에 담는다.

				while (rs.next()) {
					ProductMainColorSelect.add(rs.getString(1)); // ProductMainColorSelect에 첫번째인 pcolor를 추가한다.
				}

			} finally {
				close();
			}

			return ProductMainColorSelect;
		}

		// 카테고리

		// 카테고리 목록 보여주는 메소드(코트, 자켓, 점퍼, 무스탕, 가디건)
		@Override
		public List<CategoryVO> CategoryListSelectAll() throws SQLException {

			List<CategoryVO> categoryList = new ArrayList<CategoryVO>();

			conn = ds.getConnection();

			String sql = " select cgno, cgcode, cgname " + " from tbl_category ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(); // select 되어진 결과를 resultSet에 받는다.

			while (rs.next()) {

				CategoryVO categvo = new CategoryVO();
				categvo.setCgno(rs.getInt(1));
				categvo.setCgcode(rs.getString(2));
				categvo.setCgname(rs.getString(3));

				categoryList.add(categvo); // imageList에 imgvo를 보내준다.

			} // end of while(rs.next()) ---------------------------

			return categoryList;
		}

		// 카테고리 목록 클릭시 카테고리 코드에 따라 조회 및 정렬하는 메소드
		@Override
		public List<ProductVO> categoryProducClickSelectAll(Map<String, String> paraMap) throws SQLException {

			List<ProductVO> categoryProducClickList = new ArrayList<ProductVO>();

			try {

				conn = ds.getConnection();

				String sql = " select pdno, pdname, pdcategory_fk, pdimage2, price, saleprice, pdgender "
						   + " from tbl_product ";
				
				if (paraMap.get("sort") == null) {
					// 정렬타입이 없는 경우

					// System.out.println("정렬이 null 인경우 /////");

					if ("1".equals(paraMap.get("gender"))) {
						System.out.println("정렬이 null 이면서 성별이 남자 인 경우");

						sql += " where pdgender = ? and pdcategory_fk = ? ";
					} else {
						// 여자

						System.out.println("정렬이 null 이면서 성별이 여자 인 경우");
						sql += " where pdgender = ? and pdcategory_fk = ? ";
					}
				}
				else {
					// 정렬이 있는 경우

					System.out.println("정렬이 있는경우 ~~~~~");

					if ("1".equals(paraMap.get("gender"))) {
						// 남자 페이지
						System.out.println("정렬이 있고 gender값이 남자인 경우 ");

						if ("sortHighPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortHighPrice 클릭 시 (높은 가격)

							System.out.println("정렬이 있고 gender값이 남자이고 정렬타입이 sortHighPrice 인 경우 ");

							sql += " where pdgender = ? and pdcategory_fk = ? "
								 + " order by saleprice desc ";
						} else if ("sortLowPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortLowPrice 클릭 시 (낮은 가격)

							System.out.println("정렬이 있고 gender값이 남자이고 정렬타입이 sortLowPrice 인 경우 ");

							sql += " where pdgender = ? and pdcategory_fk = ? "
								 + " order by saleprice asc ";
						} else if ("sortNewProduct".equals(paraMap.get("sort"))) {
							// 정렬 중 sortNewProduct 클릭 시 (신상품)

							System.out.println("정렬이 있고 gender값이 남자이고 정렬타입이 sortNewProduct 인 경우 ");

							sql += " where pdgender = ? and pdcategory_fk = ? and pdinputdate >= (sysdate - 31)";
						} else {
							;
						}
					} 
					else {
						// 여자 메인페이지
						System.out.println("정렬이 있고 gender값이 여자인 경우 ");

						if ("sortHighPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortHighPrice 클릭 시 (높은 가격)

							System.out.println("정렬이 있고 gender값이 여자이고 정렬타입이 sortHighPrice 인 경우 ");

							sql += " where pdgender = ? and pdcategory_fk = ? "
								 + " order by saleprice desc ";
						} else if ("sortLowPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortLowPrice 클릭 시 (낮은 가격)
							System.out.println("정렬이 있고 gender값이 여자이고 정렬타입이 sortLowPrice 인 경우 ");

							sql += " where pdgender = ? and pdcategory_fk = ? "
								 + " order by saleprice asc ";
						} else if ("sortNewProduct".equals(paraMap.get("sort"))) {
							// 정렬 중 sortNewProduct 클릭 시 (신상품)
							System.out.println("정렬이 있고 gender값이 여자이고 정렬타입이 sortNewProduct 인 경우 ");

							sql += " where pdgender = ? and pdcategory_fk = ? and pdinputdate >= (sysdate - 31)";
						} else {
							;
						}
					} // 여자메인페이지 끝-----------------------------

				} // 정렬이 있는 경우 끝--------------------------------

				pstmt = conn.prepareStatement(sql);

				if (paraMap.get("sort") == null) {
					// 정렬타입이 없는 경우

					if ("1".equals(paraMap.get("gender"))) {
						pstmt.setString(1, paraMap.get("gender"));
						pstmt.setString(2, paraMap.get("pdcategory_fk"));

					} else {
						// 여자
						pstmt.setString(1, paraMap.get("gender"));
						pstmt.setString(2, paraMap.get("pdcategory_fk"));
					}
				} else {
					// 정렬이 있는 경우
					if ("1".equals(paraMap.get("gender"))) {
						// 남자 페이지
						if ("sortHighPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortHighPrice 클릭 시 (높은 가격)
							pstmt.setString(1, paraMap.get("gender"));
							pstmt.setString(2, paraMap.get("pdcategory_fk"));
						} else if ("sortLowPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortLowPrice 클릭 시 (낮은 가격)
							pstmt.setString(1, paraMap.get("gender"));
							pstmt.setString(2, paraMap.get("pdcategory_fk"));
						} else if ("sortNewProduct".equals(paraMap.get("sort"))) {
							// 정렬 중 sortNewProduct 클릭 시 (신상품)
							pstmt.setString(1, paraMap.get("gender"));
							pstmt.setString(2, paraMap.get("pdcategory_fk"));
						} else {
							;
						}
					} 
					else {
						// 여자 메인페이지
						if ("sortHighPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortHighPrice 클릭 시 (높은 가격)
							pstmt.setString(1, paraMap.get("gender"));
							pstmt.setString(2, paraMap.get("pdcategory_fk"));

						} else if ("sortLowPrice".equals(paraMap.get("sort"))) {
							// 정렬 중 sortLowPrice 클릭 시 (낮은 가격)
							pstmt.setString(1, paraMap.get("gender"));
							pstmt.setString(2, paraMap.get("pdcategory_fk"));
						} else if ("sortNewProduct".equals(paraMap.get("sort"))) {
							// 정렬 중 sortNewProduct 클릭 시 (신상품)
							pstmt.setString(1, paraMap.get("gender"));
							pstmt.setString(2, paraMap.get("pdcategory_fk"));
						} else {
							;
						}
					} // 여자메인페이지 끝-----------------------------

				}

				rs = pstmt.executeQuery();

				while (rs.next()) {

					ProductVO pdvo = new ProductVO();
					pdvo.setPdno(rs.getInt(1));
					pdvo.setPdname(rs.getString(2));
					pdvo.setPdcategory_fk(rs.getString(3));
					pdvo.setPdimage2(rs.getString(4));
					pdvo.setPrice(rs.getInt(5));
					pdvo.setSaleprice(rs.getInt(6));
					pdvo.setPdgender(rs.getString(7));

					categoryProducClickList.add(pdvo);

				}

			} finally {
				close();
			}

			return categoryProducClickList;
		}
		
		
		
		// =========== 상품 관련 메소드 ============ // 
		
		// 제품번호 채번해오는 메소드(JIEUN)
		@Override
		public int getPnumOfProduct() throws SQLException {
			int pdno = 0;
			
			try {
				 conn = ds.getConnection();
				 
				 String sql = " select  seq_product_pdno.nextval as pdno " +
						      " from dual ";
						   
				 pstmt = conn.prepareStatement(sql);
				 rs = pstmt.executeQuery();
				 			 
				 rs.next();
				 pdno = rs.getInt(1);
			
			} finally {
				close();
			}
			
			return pdno;
		}	
		
		// tbl_prodcut에 상품을 등록하는(insert) 메소드(JIEUN)
		@Override
		public int ProdutcRegisterAll(ProductVO product) throws SQLException {
			// TODO Auto-generated method stub
			
			int result = 0;
			
			try {
				
				conn = ds.getConnection(); 
				
				String sql = " insert into tbl_product(pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture, pdgender) " 
						   + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, product.getPdno());
				pstmt.setString(2, product.getPdname());
				pstmt.setString(3, product.getPdcategory_fk()); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다. (암호는 절대로 평문으로 만들면 안되니까 단일문 사용)
				pstmt.setString(4, product.getPdimage1());
				pstmt.setString(5, product.getPdimage2());  // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다. 
				pstmt.setInt(6, product.getPdqty()); // 퓨대폰번호를 AES256 알고리즘으로 양방향 암호화 시킨다.
				pstmt.setInt(7, product.getPrice());
				pstmt.setInt(8, product.getSaleprice());			
				pstmt.setString(9, product.getPdcontent());
				pstmt.setInt(10, product.getPoint());
				pstmt.setString(11, product.getTexture());
				pstmt.setString(12, product.getPdgender());
							
				result = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
			return result;
		}
			
			
		// 추가 이미지 파일 insert하는 메서드(JIEUN)
		@Override
		public int product_imagefile_Insert(int pdno, String plusPdimage) throws SQLException {
			
			int imgfile = 0;
			
			try {
				conn = ds.getConnection();
				
				String sql = " insert into tbl_product_imagefile(imgfileno, pdno_fk, imgfilename) "+ 
						     " values(seq_product_imagefile_imgno.nextval, ?, ?) ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, pdno);
				pstmt.setString(2, plusPdimage);
				
				imgfile = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
			return imgfile;
			
		}
			
		// 색상과 사이즈를 insert하는 메소드(JIEUN)
		@Override
		public int product_info_insert(Map<String, Object> paraMap) throws SQLException {
			
			int productInfo = 0;
			
			try {
				
				conn = ds.getConnection(); 
				

				String sql = " insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize) " + 
					     " values(seq_product_info_pinfono.nextval, ?, ?, ? ) ";
			
				pstmt = conn.prepareStatement(sql);			
				
				/*
				for(int i=0; i<pcolors.length; i++) {
				}
				*/ 
				
				pstmt.setInt(1, Integer.parseInt((String) paraMap.get("pdno")));
				pstmt.setString(2, paraMap.get("pcolors").toString());
				pstmt.setString(3, paraMap.get("psizes").toString());
				
				productInfo = pstmt.executeUpdate();
				
			} finally {
				close();
			}		
			
			System.out.println("색상과 사이즈 테이블 insert하는 부분 결과가 뭐가 나올까??? ==> " + productInfo);
			
			return productInfo;
		}


		////////////////////////////////////////////////////////////김민아//////////////////////////////////////////////////////////////////////
		
		
	//search 페이지에 보여지는 상품이미지파일명을 모두 조회(select)하는 메소드 (MINA)
	   @Override
	   public List<ProductVO> searchProduct(Map<String, String> paraMap) throws SQLException {
	      
	      List<ProductVO> searchProductList = new ArrayList<>();

	      try {
	          conn = ds.getConnection();
	          
	          String sql = " select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender "
	          		+ "from "+
					"( "+
					"    select rownum AS rno, pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender  "+
					"    from "+
					"    ( "+
					"        select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender  ";
	          
	          
	          if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender"))) { // gender에 성별을 '여성(2)' '남성(1)' 입력했다면 
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product "
	                         + " where pdgender = ? ";
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                        + " where pdcategory_fk = ? and pdgender = ? ";
	                }
	             }
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product "
	                       + " where pdname like '%'|| ? ||'%' and pdgender = ?  ";
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                      + " where pdcategory_fk = ? and pdname like '%'|| ? ||'%' and pdgender = ? ";   
	                }
	             }
	             
	          }// end of if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender")))-----------------------------
	         
	          else { // gender에 성별을 입력하지 않았거나 '전체'를 입력했다면,
	             
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product ";
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                        + " where pdcategory_fk = ? ";
	                }
	             }
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product "
	                       + " where pdname like '%'|| ? ||'%' ";
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                      + " where pdcategory_fk = ? and pdname like '%'|| ? ||'%' ";   
	                }
	             }
	             
	          }// end of else -----------
	          
	          
	          // 신상품순을 클릭했다면
	          if( paraMap.get("sort") == null ) {
	             sql += " order by pdinputdate desc  ";
	          }
	          else if("sortNewProduct".equalsIgnoreCase(paraMap.get("sort"))) {
	             sql += " order by pdinputdate desc ";
	          }
	          // 낮은가격순을 클릭했다면
	          else if("sortLowPrice".equalsIgnoreCase(paraMap.get("sort"))) {
	             sql += " order by saleprice ";
	          }
	          // 높은가격순을 클릭했다면
	          else if("sortHighPrice".equalsIgnoreCase(paraMap.get("sort"))) {
	             sql += " order by saleprice desc ";
	          }
	          // 인기상품순을 클릭했다면 ------------- 현재 신상품순으로 정렬되고 있음 (수정필요)
	          else if("sortBestProduct".equalsIgnoreCase(paraMap.get("sort"))) {
	             sql += " order by pdinputdate desc  ";
	          }
	          else { // 아무것도 클릭하지 않았다면 신상품순으로 정렬
	             sql += " order by pdinputdate desc  ";
	          }
	          
	          
	          sql +=  "  	) V "+
						") T  "+
						"where rno between ? and ? ";
	          
	          pstmt = conn.prepareStatement(sql);
	          
	          
	          // *** neige의 경우 1페이지당 아이템 16개씩 보여주기로 한다 *** //
	          int currentShowPageNo = Integer.parseInt( paraMap.get("currentShowPageNo") );
		      int sizePerPage = 16;
	          
	          if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender"))) {  // gender에 성별을 '여성(2)' '남성(1)' 입력했다면 
	             
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   pstmt.setString(1, paraMap.get("pdgender"));
	                   pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	   				   pstmt.setInt(3, (currentShowPageNo * sizePerPage)); // 공식 
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                   pstmt.setString(2, paraMap.get("pdgender"));
	                   pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	   				   pstmt.setInt(4, (currentShowPageNo * sizePerPage)); // 공식 
	                }
	             }    
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   pstmt.setString(1, paraMap.get("searchname"));
	                   pstmt.setString(2, paraMap.get("pdgender"));
	                   pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	   				   pstmt.setInt(4, (currentShowPageNo * sizePerPage)); // 공식 
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                   pstmt.setString(2, paraMap.get("searchname"));
	                   pstmt.setString(3, paraMap.get("pdgender"));
	                   pstmt.setInt(5, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	   				   pstmt.setInt(6, (currentShowPageNo * sizePerPage)); // 공식 
	                }
	             }
	                
	          }
	          else {  // gender에 성별을 입력하지 않았거나 '전체'를 입력했다면,
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                if ( !"0".equals(paraMap.get("pdcategory_fk")) ) {  //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                   pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	   				   pstmt.setInt(3, (currentShowPageNo * sizePerPage)); // 공식 
	                }
	             }    
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   pstmt.setString(1, paraMap.get("searchname"));
	                   pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	   				   pstmt.setInt(3, (currentShowPageNo * sizePerPage)); // 공식 
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                   pstmt.setString(2, paraMap.get("searchname"));
	                   pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	   				   pstmt.setInt(4, (currentShowPageNo * sizePerPage)); // 공식 
	                }
	             }
	          }
	          
	          
	          rs = pstmt.executeQuery();
	          
	          while(rs.next()) {
	             
	             ProductVO pvo = new ProductVO();
	      
	             pvo.setPdno( rs.getInt(1) ); 
	             pvo.setPdname( rs.getString(2) );
	             pvo.setPdcategory_fk(rs.getString(3));
	             pvo.setPdimage1(rs.getString(4));
	             pvo.setPdimage2(rs.getString(5));
	             pvo.setPrice(rs.getInt(6));
	             pvo.setSaleprice(rs.getInt(7));
	             pvo.setPdinputdate(rs.getString(8));
	             pvo.setPdgender(rs.getString(9));
	             
	             searchProductList.add(pvo);
	             
	          }// end of while-------------------------
	          
	      } finally {
	         close();
	      }
	      
	      return searchProductList;
	   }

	   
	   // search페이지에 보여지는 상품이미지에 대한 색상을 모두 조회(select)하는 메소드 (MINA)
	   @Override
	   public List<String> selectProductColor(String pdno) throws SQLException {

	      List<String> prodInfoList = new ArrayList<>();
	      
	      try {
	          conn = ds.getConnection();
	          
	          String sql = "select distinct pcolor "+
	                     "from tbl_product_info  "+
	                     "where pdno_fk = ? "; 
	           
	          pstmt = conn.prepareStatement(sql);
	          pstmt.setString(1, pdno);
	          
	          rs = pstmt.executeQuery();
	          
	          while(rs.next()) {
	             prodInfoList.add( rs.getString(1) );
	          }// end of while-------------------------
	          
	      } finally {
	         close();
	      }
	      
	      return prodInfoList;
	   }


	   // search페이지에 보여지는 상품이미지에 대한 사이즈를 모두 조회(select)하는 메소드 (MINA)
		@Override
	      public List<String> selectProductSize(String pdno) throws SQLException {

	         List<String> prodInfoList = new ArrayList<>();
	         
	         try {
	             conn = ds.getConnection();
	             
	             String sql = " select distinct psize "+
	                       " from tbl_product_info  "+
	                       " where pdno_fk = ? "+
	                       " order by psize desc "; 
	              
	             pstmt = conn.prepareStatement(sql);
	             pstmt.setString(1, pdno);
	             
	             rs = pstmt.executeQuery();
	             
	             while(rs.next()) {
	                prodInfoList.add( rs.getString(1) );
	             }// end of while-------------------------
	             
	         } finally {
	            close();
	         }
	         
	         return prodInfoList;
	      }

		// 페이징처리를 위해서 전체회원에 대한 총 제품 개수와 페이지 개수 알아오기(select) (Mina)
		@Override
		public Map<String, String> getTotal(Map<String, String> paraMap) throws SQLException {
			
			int totalSearchProduct = 0;
			int totalPage = 0;
		          
			conn = ds.getConnection();
			          
	          // *** neige의 경우 1페이지당 아이템 16개씩 보여주기로 한다 *** //
	          String sql = " select count(*), ceil( count(*)/ 16 ) ";
	          
	          
	          if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender"))) { // gender에 성별을 '여성(2)' '남성(1)' 입력했다면 
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product "
	                         + " where pdgender = ? ";
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                        + " where pdcategory_fk = ? and pdgender = ? ";
	                }
	             }
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product "
	                       + " where pdname like '%'|| ? ||'%' and pdgender = ?  ";
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                      + " where pdcategory_fk = ? and pdname like '%'|| ? ||'%' and pdgender = ? ";   
	                }
	             }
	             
	          }// end of if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender")))-----------------------------
	         
	          else { // gender에 성별을 입력하지 않았거나 '전체'를 입력했다면,
	             
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product ";
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                        + " where pdcategory_fk = ? ";
	                }
	             }
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " from tbl_product "
	                       + " where pdname like '%'|| ? ||'%' ";
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " from tbl_product "
	                      + " where pdcategory_fk = ? and pdname like '%'|| ? ||'%' ";   
	                }
	             }
	             
	          }// end of else -----------
	          
	          
	          pstmt = conn.prepareStatement(sql);
	          
	          
	          if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender"))) {  // gender에 성별을 '여성(2)' '남성(1)' 입력했다면 
	             
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   pstmt.setString(1, paraMap.get("pdgender"));
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                   pstmt.setString(2, paraMap.get("pdgender"));
	                }
	             }    
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   pstmt.setString(1, paraMap.get("searchname"));
	                   pstmt.setString(2, paraMap.get("pdgender"));
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                   pstmt.setString(2, paraMap.get("searchname"));
	                   pstmt.setString(3, paraMap.get("pdgender"));
	                }
	             }
	                
	          }
	          else {  // gender에 성별을 입력하지 않았거나 '전체'를 입력했다면,
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                if ( !"0".equals(paraMap.get("pdcategory_fk")) ) {  //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                }
	             }    
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   pstmt.setString(1, paraMap.get("searchname"));
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   pstmt.setString(1, paraMap.get("pdcategory_fk"));
	                   pstmt.setString(2, paraMap.get("searchname"));
	                }
	             }
	          }
	          
	          
	          rs = pstmt.executeQuery();
	          
	          Map<String, String> totalMap = new HashMap<>();
	          
	          while(rs.next()){
	        	  totalMap.put("totalSearchProduct", String.valueOf(rs.getInt(1)));
	        	  totalMap.put("totalPage", String.valueOf(rs.getInt(2)));
	          }     
             
			return totalMap;		


	} 
		
		
		
/////////////////////////////////////////////////////////////////////////////홍승의/////////////////////////////////////////////////////////////////////////////////
		
		/*
		// 물품 상세정보( DTO객체 / VO객체를 가져온다) primary key > pdno (승의)
		@Override
		public List<ProductVO> ProductList() throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		try {
		conn = ds.getConnection();
		
		String sql = " SELECT pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture FROM tbl_product WHERE pdno = 1 ORDER BY pdno ASC ";
		
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
		
		// System.out.println("DAO-Checked");
		ProductVO pdvo = new ProductVO();
		
		pdvo.setPdno(rs.getInt(1));
		pdvo.setPdname(rs.getString(2));
		pdvo.setPdcategory_fk(rs.getInt(3));
		pdvo.setPdimage1(rs.getString(4));
		pdvo.setPdimage2(rs.getString(5));
		pdvo.setPdqty(rs.getInt(6));
		pdvo.setPrice(rs.getInt(7));
		pdvo.setSaleprice(rs.getInt(8));
		pdvo.setPdcontent(rs.getString(9));
		pdvo.setPoint(rs.getInt(10));
		pdvo.setTexture(rs.getString(11));
		
		productList.add(pdvo);
		
		// System.out.println(pdvo.getPdname());
		
		} // end of while------------------------------------
		
		} finally {
		close();
		}
		
		return productList;
		
		}
		*/
		// 물품 상세정보 pdno key 값의 정보를 불러온다. (승의)
		@Override
		public List<ProductVO> ProductList(String pdno) throws SQLException {
		
		List<ProductVO> productList = new ArrayList<>();
		
		try {
		conn = ds.getConnection();
		
		String sql = "SELECT PDNO, PDNAME, PDCATEGORY_FK, PDIMAGE1, PDIMAGE2, PDQTY, PRICE, SALEPRICE, PDCONTENT, POINT, TEXTURE   "+
		"FROM TBL_PRODUCT  "+
		"WHERE PDNO = ? ";
		/*			
		String sql = "SELECT  " + "PD.PDNO, PD.PDNAME, PD.PDCATEGORY_FK, PD.PDIMAGE1, PD.PDIMAGE2,  "
		+ "PD.PDQTY, PD.PRICE, PD.SALEPRICE, PD.PDCONTENT, PD.POINT,  "
		+ "PD.TEXTURE, PDINFO.PINFONO, PDINFO.PCOLOR, PDINFO.PSIZE  " + "FROM ( "
		+ "SELECT PDNO, PDNAME, PDCATEGORY_FK, PDIMAGE1, PDIMAGE2, PDQTY, PRICE, SALEPRICE, PDCONTENT, POINT, TEXTURE  FROM TBL_PRODUCT ) PD "
		+ "JOIN  ( SELECT PINFONO, PDNO_FK , PCOLOR, PSIZE FROM TBL_PRODUCT_INFO  ) PDINFO "
		+ "ON PD.PDNO = PDINFO.PDNO_FK WHERE PDNO = ? " + "ORDER BY PD.PDNO ASC ";
		*/
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pdno);
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
		
		// System.out.println("DAO-Checked");
		ProductVO pdvo = new ProductVO();
		
		pdvo.setPdno(rs.getInt(1));
		pdvo.setPdname(rs.getString(2));
		pdvo.setPdcategory_fk(rs.getString(3));
		pdvo.setPdimage1(rs.getString(4));
		pdvo.setPdimage2(rs.getString(5));
		
		pdvo.setPdqty(rs.getInt(6));
		pdvo.setPrice(rs.getInt(7));
		pdvo.setSaleprice(rs.getInt(8));
		pdvo.setPdcontent(rs.getString(9));
		pdvo.setPoint(rs.getInt(10));
		
		pdvo.setTexture(rs.getString(11));
		//System.out.println("dao-check HSU");
		/*
		pdvo.setPinfono(rs.getInt(12));
		// pdvo.setPdno_fk( rs.getString(13) );
		pdvo.setPcolor(rs.getString(13));
		pdvo.setPsize(rs.getString(14));
		*/
		productList.add(pdvo);
		
		} // end of while------------------------------------
		
		} finally {
		close();
		}
		
		return productList;
		
		}
		
		/*
		* // 물품상세페이지에서 장바구니 (승의)
		* 
		* @Override public List<CartVO> CartList() throws SQLException {
		* 
		* List<CartVO> cartList = new ArrayList<>(); try { conn = ds.getConnection();
		* 
		* String sql = " SELECT CARTNO, USERID_FK, PDNO_FK, PQTY, REGISTERDAY "+
		* "FROM TBL_CART "+ "FROM TBL_CART "+ "WHERE pdno = ? ORDER BY CARTNO ASC ";
		* 
		* pstmt = conn.prepareStatement(sql);
		* 
		* rs = pstmt.executeQuery();
		* 
		* while (rs.next()) {
		* 
		* // System.out.println("DAO-Checked"); CartVO cvo = new CartVO();
		* 
		* cvo.setCartno(rs.getInt(1)); cvo.setUserid_fk(rs.getString(2));
		* cvo.setPdno_fk(rs.getInt(3)); cvo.setPqty(rs.getInt(4));
		* cvo.setRegisterday(rs.getString(5));
		* 
		* 
		* cartList.add(cvo);
		* 
		* } // end of while------------------------------------
		* 
		* } finally { close(); }
		* 
		* return cartList;
		* 
		* }
		*/
		
		// 물품상세페이지에서의 사이즈,색상 (승의)
		@Override
		public List<ProductInfoVO> ProductInfoList( ) throws SQLException {
		
		List<ProductInfoVO> productinfoList = new ArrayList<>();
		
		try {
		conn = ds.getConnection();
		
		String sql = "SELECT PINFONO, PDNO_FK, PCOLOR, PSIZE " + "FROM TBL_PRODUCT_INFO " + "WHERE PDNO_FK = 1 "
		+ "ORDER BY PINFONO ASC";
		
		pstmt = conn.prepareStatement(sql);
		//pstmt.setInt(1, PINFONO); 
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
		
		// System.out.println("DAO-Checked");
		ProductInfoVO productinfovo = new ProductInfoVO();
		
		productinfovo.setPinfono(rs.getInt(1));
		productinfovo.setPdno_fk(rs.getInt(2));
		productinfovo.setPcolor(rs.getString(3));
		productinfovo.setPsize(rs.getString(4));
		
		productinfoList.add(productinfovo);
		
		} // end of while------------------------------------
		
		} finally {
		close();
		}
		
		return productinfoList;
		
		}


	///////////////////////////////////////김동휘/////////////////////////////////////

		// 장바구니 버튼을 누를경우 상품의 리스트를 받아온뒤 화면상에 출력 (동휘)
		@Override
		public List<ProductVO> getCartList(String userid) throws SQLException {
			
			List<ProductVO> cartList = new ArrayList<ProductVO>();
			
			try {
				conn = ds.getConnection(); // DBCP에서 connection 받아오기
				
				String sql = " select p.pdno, pdname, PDCATEGORY_FK, PDIMAGE1, PDIMAGE2, pdqty, price, saleprice, pdcontent, point, pdinputdate, texture, pdgender \n "+
							 " from \n "+
							 " ( \n "+
							 " select pdno_fk \n "+
							 " from TBL_CART  \n "+
							 " where userid_fk = ? "+
							 " )v inner join TBL_PRODUCT p \n " +
							 " on v.pdno_fk = p.pdno ";
				
				pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
				
				pstmt.setString(1, "siasia");
				
				rs = pstmt.executeQuery(); // select 되어진 결과를 resultSet에 받는다.
				
				while(rs.next()) {
					ProductVO pdvo = new ProductVO();
					
					pdvo.setPdno(rs.getInt(1));
					pdvo.setPdname(rs.getString(2));
					pdvo.setPdcategory_fk(rs.getString(3));
					pdvo.setPdimage1(rs.getString(4));
					pdvo.setPdimage2(rs.getString(5));
					pdvo.setPdqty(rs.getInt(6));
					pdvo.setPrice(rs.getInt(7));
					pdvo.setSaleprice(rs.getInt(8));
					pdvo.setPdcontent(rs.getString(9));
					pdvo.setPoint(rs.getInt(10));
					pdvo.setPdinputdate(rs.getString(11));
					pdvo.setTexture(rs.getString(12));
					pdvo.setPdgender(rs.getString(13));
					
					cartList.add(pdvo);
				} // end of while(rs.next()) ---------------------------
			} finally {
				close();
			}
			
			return cartList;
		}
		
		@Override
		public void productAllDelete(int pdno, String userid_fk) throws SQLException {
			
			try {
				conn = ds.getConnection(); // DBCP에서 connection 받아오기
				
				String sql = " delete from TBL_CART where userid_fk = ? and pdno_fk = ? ";
				
				pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
				
				//pstmt.setString(1, userid_fk);
				pstmt.setInt(2, pdno);
				pstmt.setString(1, "siasia");
				
				int n = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
		}
		
		// 상품 개별삭제 버튼을 누를경우 유저의 ID와 해당 제품의 번호를 받아와 DB테이블에서 삭제해주는 메서드
		@Override
		public int productOneDelete(String pdno, String userid_fk) throws SQLException {
			int result = 0;
			
			try {
				conn = ds.getConnection(); // DBCP에서 connection 받아오기
				
				String sql = " delete from TBL_CART where userid_fk = ? and pdno_fk = ? ";
				
				pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
				
				//pstmt.setString(1, userid_fk);
				pstmt.setString(1, "siasia");
				pstmt.setString(2, pdno);
				
				result = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
			return result;
		}

		@Override
		public void productChoiceDelete(int pdno, String userid_fk) throws SQLException {
			
			try {
				conn = ds.getConnection(); // DBCP에서 connection 받아오기
				
				String sql = " delete from TBL_CART where userid_fk = ? and pdno_fk = ? ";
				
				pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
				
				//pstmt.setString(1, userid_fk);
				pstmt.setString(1, "siasia");
				pstmt.setInt(2, pdno);
				
				int result = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
		}


}




























