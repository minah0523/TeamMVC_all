package myshop.mdl;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.mdl.MemberVO;

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
	
	   
	   // 메인페이지의 캐러셀에 보여지는 상품이미지파일명을 모두 조회(SELECT) 하는 메소드 (JIEUN)
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

	   // 메인페이지에 보여지는 상품 이미지 tbl_product 에서 조회해오는 메소드 (JIEUN)
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

	            sql += " where pdgender = ? " 
	                 + " ORDER BY saleprice asc ";
	         } 
	         else if ("sortNewProduct".equals(paraMap.get("sortType"))) {
	            // 값이 있는데 그 값이 sortNewProduct라면 (신상품 조회)

	            sql += " where pdgender = ? and pdinputdate >= (sysdate - 31)";
	            
	         } 
	         else {
	            
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

	  // 카테고리 목록 보여주는 메소드(코트, 자켓, 점퍼, 무스탕, 가디건)(JIEUN)
	  @Override
	  public List<CategoryVO> CategoryListSelectAll() throws SQLException {

		  List<CategoryVO> categoryList = new ArrayList<CategoryVO>();

 		  try {

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

		    }
		    finally {
			close();
		    }

		return categoryList;
	   }

	// 카테고리 목록 클릭시 카테고리 코드에 따라 조회 및 정렬하는 메소드(JIEUN)
	@Override
	public List<ProductVO> categoryProducClickSelectAll(Map<String, String> paraMap) throws SQLException {

		List<ProductVO> categoryProducClickList = new ArrayList<ProductVO>();

		try {

			conn = ds.getConnection();

			String sql = " select pdno, pdname, pdcategory_fk, pdimage2, price, saleprice, pdgender "; 

			if ("sortHighPrice".equals(paraMap.get("sort"))) {
				// 정렬 중 sortHighPrice 클릭 시 (높은 가격)

				sql += " from tbl_product "
					 + "where pdgender = ? and pdcategory_fk = ? "
					 + " order by saleprice desc ";
			} else if ("sortLowPrice".equals(paraMap.get("sort"))) {
				// 정렬 중 sortLowPrice 클릭 시 (낮은 가격)

				sql += " from tbl_product "
					 + " where pdgender = ? and pdcategory_fk = ? "
					 + " order by saleprice asc ";
			} else if ("sortNewProduct".equals(paraMap.get("sort"))) {
				// 정렬 중 sortNewProduct 클릭 시 (신상품)

				sql += " from tbl_product "
					 + " where pdgender = ? and pdcategory_fk = ? and pdinputdate >= (sysdate - 31)";
			}
			else if("sortBestProduct".equals(paraMap.get("sort"))) {
				// 정렬 중 sortBestProduct 클릭 시 (인기상품)
				
				sql += " , nvl(ordersum,0) as ordersum "
						 + " from tbl_product " 
						 + " left join " 
						 + " ( " 
						 + "	select pdno_fk, ordersum " 
						 + "    from view_ordercodedetail " 
						 + " )N " 
						 + " on pdno = N.pdno_fk "
						 + " where pdgender = ? and pdcategory_fk = ? "
						 + " order by ordersum desc ";					
				
			}
			
			else {
				
				if("0".equals(paraMap.get("pdcategory_fk"))) {
					// 카테고리를 전체(0)을 클릭한 경우
					sql += "from tbl_product "
						 + " where pdgender = ? "; 
					
				}
				else {
					// 카테고리 전체 제외한 것 클릭한 경우
					sql += " from tbl_product "
						 + " where pdgender = ? and pdcategory_fk = ? ";
				}
			}			

			
			pstmt = conn.prepareStatement(sql);
			
			
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
			}
			else if("sortBestProduct".equals(paraMap.get("sort"))) {
				// 정렬 중 sortBestProduct 클릭 시 (인기상품)
				pstmt.setString(1, paraMap.get("gender"));
				pstmt.setString(2, paraMap.get("pdcategory_fk"));			
			}
			else {
				if("0".equals(paraMap.get("pdcategory_fk"))) {
					pstmt.setString(1, paraMap.get("gender"));
				}
				else {
					pstmt.setString(1, paraMap.get("gender"));
					pstmt.setString(2, paraMap.get("pdcategory_fk"));
				}
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
	   public int product_info_insert(int pdno, String pcolor, String psize) throws SQLException {
	      
	      int productInfo = 0;
	      
	      try {
	         
	         conn = ds.getConnection(); 
	         

	         String sql = " insert into tbl_product_info(pinfono, pdno_fk, pcolor, psize) " + 
	                 " values(seq_product_info_pinfono.nextval, ?, ?, ? ) ";
	      
	         pstmt = conn.prepareStatement(sql);      
	         
	         pstmt.setInt(1, pdno);
	         pstmt.setString(2, pcolor);
	         pstmt.setString(3, psize);
	         
	         productInfo = pstmt.executeUpdate();
	         
	      } finally {
	         close();
	      }      
	      
	      return productInfo;
	      
	   }
	   
	   // 관리자 페이지에서 상품(번호, 카테고리, 상품명, 재고, 가격, 성별) 리스트 가져오는(select) 메소드(JIEUN)
	   @Override
	   public List<ProductVO> adminProductListAll() throws SQLException {
	      
	      List<ProductVO> adminProdList = new ArrayList<ProductVO>();
	      
	      try {
	         
	         conn = ds.getConnection();
	               
	         String sql = " select p.pdno, c.cgname, p.pdname, p.pdqty, p.price, p.pdgender " +
	                    " from tbl_product p join tbl_category c " +
	                    " on p.pdcategory_fk = c.cgno ";
	         
	         pstmt = conn.prepareStatement(sql); 

	         rs = pstmt.executeQuery(); // select 되어진 결과를 resultSet에 받는다.

	         while (rs.next()) {

	            ProductVO prodvo = new ProductVO();
	            prodvo.setPdno(rs.getInt(1)); 
	            
	            CategoryVO catevo = new CategoryVO(); 
	            catevo.setCgname(rs.getString(2));
	            
	            prodvo.setCatevo(catevo);
	            
	            prodvo.setPdname(rs.getString(3));
	            prodvo.setPdqty(rs.getInt(4));
	            prodvo.setPrice(rs.getInt(5));
	            prodvo.setPdgender(rs.getString(6));
	            
	            adminProdList.add(prodvo); 

	         } // end of while(rs.next()) ---------------------------
	         
	         
	      } finally {
	         close();
	      }
	      
	      return adminProdList;
	   }
	   
	   // 관리자 페이지에서 상품(번호, 카테고리, 상품명, 재고, 가격, 성별) 리스트 가져오는(select) 메소드(검색결과도 같이 조회 할 수 있도록) (JIEUN)
	   @Override
	   public List<ProductVO> adminProductListAll(Map<String, String> paraMap) throws SQLException {
	      
	      List<ProductVO> adminprodList = new ArrayList<ProductVO>();
	      
	      try {
	         
	         conn = ds.getConnection();
	         
	         String sql = " select pdno, pdcategory_fk, cgname, pdname, pdimage1, pdimage2, pdqty, price, saleprice, pdinputdate, pdgender "+
	                   " from "+
	                   "      ( "+
	                   "         select rownum AS rno, pdno, pdcategory_fk, cgname, pdname, pdimage1, pdimage2, pdqty, price, saleprice, pdinputdate, pdgender "+
	                   "         from "+
	                   "         ( "+
	                   "           select p.pdno, p.pdcategory_fk, c.cgname, p.pdname, p.pdimage1, p.pdimage2, p.pdqty, p.price, p.saleprice, p.pdinputdate, p.pdgender ";

	         
	         String searchType = paraMap.get("searchType");
	         String prodRegType = paraMap.get("prodRegType");
	         
	         
	         if( "pdname".equals(searchType)) {
	         
	            searchType = "p.pdname like '%'|| ? ||'%' ";
	            
	         }
	         else if ("cgname".equals(searchType)) {
	            
	            searchType = "c.cgname like '%'|| ? ||'%' ";
	            
	         }
	         else { // 전체인 경우에는 상품명 또는 카테고리명을 동시에 검색
	            
	            searchType = " p.pdname like '%'|| ? ||'%' or c.cgname like '%'|| ? ||'%' ";
	            
	         }
	         
	         if( "week".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > (sysdate - 7) ";
	         }
	         else if( "oneM".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -1), 'yy/mm/dd') ";
	         }
	         else if( "thrM".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -3), 'yy/mm/dd') ";
	         }
	         else if( "sixM".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -6), 'yy/mm/dd') ";
	         }
	         else if( "year".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -12), 'yy/mm/dd') ";
	         }         
	         
	         
	         if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender")) ) {
	            // 성별이 여자 또는  남자라면

		       if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;                     
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;                     
	                  
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;                        
	                  
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? ";   
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType ;
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------
	            
	         }
	         else {
	            // 성별이 전체라면
	            
	            if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;                     
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;                     
	                  
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;                        
	                  
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno ";
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType ;
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------            
	            
	         } // 성별 전체 끝---------------------------------------------------
	         
	         sql += "    ) V"  + 
	               " ) T " +
	               " where rno between ? and ? ";   

	         pstmt = conn.prepareStatement(sql);
	         
	         int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo")); // 보내준 현재 페이지 번호
	         int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage"));   // 보내준 페이지당 목록 개수         
	         
	         if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender")) ) {
	            // 성별이 여자 또는  남자라면
	            
	            if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  pstmt.setString(1, paraMap.get("pdgender"));
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); // 공식
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  pstmt.setString(1, paraMap.get("pdgender"));
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); // 공식               
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  pstmt.setString(1, paraMap.get("pdgender"));
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); // 공식
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  pstmt.setString(1, paraMap.get("pdgender"));
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); // 공식                  
	                  
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  pstmt.setString(1, paraMap.get("pdgender"));
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); // 공식         
	                  
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)

	                  pstmt.setString(1, paraMap.get("pdgender"));
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(3, (currentShowPageNo * sizePerPage) ); // 공식   
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------
	            
	         }
	         else {
	            // 성별이 전체라면
	            
	            if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) ); // 공식
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) ); // 공식               
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) ); // 공식
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) ); // 공식                  
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) ); // 공식                     
	                  
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)

	                  pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                  pstmt.setInt(2, (currentShowPageNo * sizePerPage) ); // 공식
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setInt(3, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) ); // 공식
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("pdgender"));
	                     pstmt.setString(2, paraMap.get("searchWord"));
	                     pstmt.setString(3, paraMap.get("searchWord"));                     
	                     pstmt.setInt(4, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
	                     pstmt.setInt(5, (currentShowPageNo * sizePerPage) ); // 공식         
	                  }
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------            
	            
	         } // 성별 전체 끝---------------------------------------------------         
	         
	         
	         
	          rs = pstmt.executeQuery();
	             
	           while(rs.next()) {
	                
	              ProductVO pvo = new ProductVO();
	              // pdno, pdcategory_fk, cgname, pdname, pdimage1, pdimage2, pdqty, price, saleprice, pdinputdate, pdgender
	              
	               pvo.setPdno( rs.getInt(1) ); 
	              pvo.setPdcategory_fk(rs.getString(2));
	              
	              CategoryVO catevo = new CategoryVO();
	              catevo.setCgname(rs.getString(3));
	              
	              pvo.setCatevo(catevo);
	              
	              pvo.setPdname( rs.getString(4) );
	              pvo.setPdimage1(rs.getString(5));
	              pvo.setPdimage2(rs.getString(6));
	              pvo.setPdqty(rs.getInt(7));
	              pvo.setPrice(rs.getInt(8));
	              pvo.setSaleprice(rs.getInt(9));
	              pvo.setPdinputdate(rs.getString(10));
	              pvo.setPdgender(rs.getString(11));
	                
	              adminprodList.add(pvo);
	                
	           }// end of while-------------------------         

	         
	      } finally {
	         close();
	      }
	      
	      return adminprodList;
	   }
	   
	   // 페이징 처리를 위해서 총 페이지 개수를  알아오기(select) (JIEUN)
	   @Override
	   public int getTotalPage(Map<String, String> paraMap) throws SQLException {
	      
	      int totalPage = 0;
	      
	      try {
	         
	         conn = ds.getConnection();
	         
	         String sql = " select ceil( count(*)/ ? ) ";
	             
	         String searchType = paraMap.get("searchType");
	         String prodRegType = paraMap.get("prodRegType");
	         
	         
	         if( "pdname".equals(searchType)) {
	            searchType = "p.pdname like '%'|| ? ||'%' ";
	         }
	         else if ("cgname".equals(searchType)) {
	            searchType = "c.cgname like '%'|| ? ||'%' ";
	         }
	         else { // 전체인 경우에는 상품명 또는 카테고리명을 동시에 검색
	            searchType = " p.pdname like '%'|| ? ||'%' or c.cgname like '%'|| ? ||'%' ";
	         }
	         
	         if( "week".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > (sysdate - 7) ";
	         }
	         else if( "oneM".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -1), 'yy/mm/dd') ";
	         }
	         else if( "thrM".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -3), 'yy/mm/dd') ";
	         }
	         else if( "sixM".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -6), 'yy/mm/dd') ";
	         }
	         else if( "year".equals(prodRegType)) {
	            prodRegType = " p.pdinputdate > to_char(add_months(sysdate, -12), 'yy/mm/dd') ";
	         }         

	         if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender")) ) {
	            // 성별이 여자 또는  남자라면
	            
	            if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;                     
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;                     
	                  
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and " + prodRegType;                        
	                  
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? ";   
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면

	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType +" and " + prodRegType;
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where p.pdgender = ? and "+ searchType ;
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------
	            
	         }
	         else {
	            // 성별이 전체라면
	            System.out.println("성별이 전체");
	            
	            if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               System.out.println("성별이 전체이고 검색 키워드가 없다");
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 없고 상품등록일이 일주일이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 없고 상품등록일이 1달이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;                     
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 없고 상품등록일이 3달이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 없고 상품등록일이 6달이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;                     
	                  
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 없고 상품등록일이 12달이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where " + prodRegType;                        
	                  
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  System.out.println("성별이 전체이고 검색 키워드가 없고 상품등록일이 전체이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno ";
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               System.out.println("성별이 전체이고 검색 키워드가 있다");
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 있고 상품등록일이 일주일이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 있고 상품등록일이 한달이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 있고 상품등록일이 3달이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 있고 상품등록일이 6달이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  System.out.println("성별이 전체이고 검색 키워드가 있고 상품등록일이 12이라면");
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType +" and " + prodRegType;
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  sql +=  " from tbl_product p join tbl_category c "+
	                        " on p.pdcategory_fk = c.cgno " +      
	                          " where "+ searchType ;
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------            
	            
	         } // 성별 전체 끝---------------------------------------------------

	         pstmt = conn.prepareStatement(sql);
	         
	         if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender")) ) {
	            // 성별이 여자 또는  남자라면
	            
	            if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	                  pstmt.setString(2, paraMap.get("pdgender"));
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	                  pstmt.setString(2, paraMap.get("pdgender"));
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	                  pstmt.setString(2, paraMap.get("pdgender"));
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	                  pstmt.setString(2, paraMap.get("pdgender"));
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	                  pstmt.setString(2, paraMap.get("pdgender"));
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)

	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	                  pstmt.setString(2, paraMap.get("pdgender"));
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                     
	                  }
	               
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                        
	                  }
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                        
	                  }
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                        
	                  }
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                     
	                  }
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));   
	                                       
	                  }
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------
	            
	         }
	         else {
	            // 성별이 전체라면
	            
	            if("".equals(paraMap.get("searchWord")) || paraMap.get("searchWord") == null) {
	               // 검색 키워드(검색명)가 없을때 (전체 검색)
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	                  
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면            
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));   
	                                 
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  pstmt.setString(1, paraMap.get("sizePerPage"));
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  pstmt.setString(1, paraMap.get("sizePerPage"));   
	               }
	               
	               
	            } // 검색 키워드(검색명)이 없을때 끝----------------------------------
	            
	            else {
	               // 검색 키워드(검색명)가 있을때
	               
	               if("week".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일주일 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                  
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                     
	                        
	                  }
	               }
	               else if("oneM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 한달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     
	                  } 
	                  else {
	                     // 검색분류가 전체인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                     
	                        
	                  }
	               }
	               else if("thrM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 세달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));

	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                     
	   
	                  }
	               }
	               else if("sixM".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 여섯달 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));

	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                     
	      
	                  }
	               }
	               else if("year".equals(paraMap.get("prodRegType")) ) {
	                  // 상품등록일이 일년 이라면
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));

	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                        
	      
	                  }
	               }
	               else {
	                  // 상품등록일이 전체이라면(조건없음)
	                  
	                  if( "pdname".equals(paraMap.get("searchType")) || "cgname".equals(paraMap.get("searchType")) ) { 
	                     // 검색분류가 pdname이나 chname인 경우
	                     
	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));

	                  } 
	                  else {
	                     // 검색분류가 전체인 경우

	                     pstmt.setString(1, paraMap.get("sizePerPage"));
	                     pstmt.setString(2, paraMap.get("pdgender"));
	                     pstmt.setString(3, paraMap.get("searchWord"));
	                     pstmt.setString(4, paraMap.get("searchWord"));                     
	      
	                  }
	                  
	               }               
	               
	            } // 검색 키워드(검색명)이 있을때 끝----------------------------------            
	            
	         } // 성별 전체 끝---------------------------------------------------         
	         
	         
	         
	          rs = pstmt.executeQuery();
	             
	          rs.next();
	                
	          totalPage = rs.getInt(1); // 첫번째 컬럼인  결과 받아서 totalPage에 넘겨주자   
	         
	         
	      } finally {
	         close();
	      }
	      
	      
	      return totalPage;
	   }
	   
	   
	   // 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 상품정보 조회해서 받아오자(JIEUN)
	   @Override
	   public ProductVO adminProductDetail(String pdno) throws SQLException {
	      
	      ProductVO pvo = null;
	      
	      try {
	         
	         conn = ds.getConnection();
	         
	         String sql = " select pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, texture, pdgender "+
	                    " from tbl_product "+
	                    " where pdno = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, Integer.parseInt(pdno));
	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            
	            String pdname =  rs.getString(1);
	            String pdcategory_fk = rs.getString(2);
	            String pdimage1 = rs.getString(3);
	            String pdimage2 = rs.getString(4);
	            int pdqty = rs.getInt(5);
	            int price = rs.getInt(6);
	            int saleprice = rs.getInt(7);
	            String pdcontent = rs.getString(8);
	            String texture = rs.getNString(9);
	            String pdgender = rs.getNString(10);
	            
	            pvo = new ProductVO();
	            
	               pvo.setPdname( pdname );
	               pvo.setPdcategory_fk(pdcategory_fk);
	               pvo.setPdimage1(pdimage1);
	               pvo.setPdimage2(pdimage2);
	               pvo.setPdqty(pdqty);
	               pvo.setPrice(price);
	               pvo.setSaleprice(saleprice);
	               pvo.setPdcontent(pdcontent);
	               pvo.setTexture(texture);
	               pvo.setPdgender(pdgender);
	         }
	         
	         
	      } finally {
	         close();
	      }
	      
	      return pvo;
	   }
	   
	   // 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 색상, 사이즈 가져오기 (JIEUN)
	   @Override
	   public List<ProductInfoVO> productInfoDetail(String pdno) throws SQLException {
	      
	      List<ProductInfoVO> prodInfoList = new ArrayList<ProductInfoVO>();
	      
	      try {
	         
	         conn = ds.getConnection();
	         
	         String sql = " select pcolor, psize " + 
	                   " from tbl_product_info " + 
	                   " where pdno_fk = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, pdno);
	         
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            
	            ProductInfoVO pdinfovo = new ProductInfoVO();
	            
	            String pcolor = rs.getString(1);
	            String psize = rs.getString(2);
	            
	            pdinfovo.setPcolor(pcolor);
	            pdinfovo.setPsize(psize);
	            
	            prodInfoList.add(pdinfovo);
	            
	         }
	         
	         
	      } finally {
	         close();
	      }
	      
	      return prodInfoList;
	   }
	   
	   
	   // 관리자페이지의 상품 관리 리스트 중 하나 클릭 했을때 pdno로 데이터를 받아서 추가 이미지 파일 가져오기  (JIEUN)
	   @Override
	   public ProductImageFileVO addProductImageFileDetail(String pdno) throws SQLException {
	      
	      ProductImageFileVO pimgvo = null;
	      
	      try {
	         
	         conn = ds.getConnection();
	         
	         String sql = " select imgfileno, pdno_fk, imgfilename "+
	                    " from tbl_product_imagefile "+
	                    " where pdno_fk = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, Integer.parseInt(pdno));
	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            
	            int imgfileno =  rs.getInt(1);
	            int pdno_fk = rs.getInt(2);
	            String imgfilename = rs.getString(3);
	            
	            pimgvo = new ProductImageFileVO();
	            
	            pimgvo.setImgfileno(imgfileno);
	            pimgvo.setPdno_fk(pdno_fk);
	            pimgvo.setImgfilename(imgfilename);

	         }
	         
	         
	      } finally {
	         close();
	      }
	      
	      return pimgvo;      
	      
	      
	   }
	
	
	// 상품 업데이트 메소드(update) (JIEUN)
	@Override
	public int productUpdate(ProductVO pvo) throws SQLException  {
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			 String sql = " UPDATE tbl_product SET pdcategory_fk = ?, pdname = ? ";
			
			/*
			String sql = " UPDATE tbl_product "
					   + " SET pdcategory_fk = ?, pdname = ? , pdqty = ?, price = ?, saleprice = ?, "
					   + " pdcontent = ?, texture = ?, pdgender = ? "
					   + " where pdno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pvo.getPdcategory_fk());
			pstmt.setString(2, pvo.getPdname());
			pstmt.setInt(3, pvo.getPdqty());
			pstmt.setInt(4, pvo.getPrice());
			pstmt.setInt(5, pvo.getSaleprice());
			pstmt.setString(6, pvo.getPdcontent());
			pstmt.setString(7, pvo.getTexture());
			pstmt.setString(8, pvo.getPdgender());
			pstmt.setInt(9, pvo.getPdno());
			
			*/
			 
			if(  !"".equals(pvo.getPdimage1()) ||  !"".equals(pvo.getPdimage2())) {
				// 제품이미지1, 2가 null값이 아닌 경우에 
				sql += " , pdimage1 = ?, pdimage2 = ?, pdqty = ?, price = ?, saleprice = ?, pdcontent = ?, texture = ?, pdgender = ? ";
				System.out.println("이미지 ==> " + pvo.getPdimage1());
			}
			else {
				sql += " , pdqty = ?, price = ?, saleprice = ?, pdcontent = ?, texture = ?, pdgender = ? ";
				System.out.println("이미지가 없는 경우==> " + pvo.getPdimage2());
			}
			
			sql += " where pdno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			if( !"".equals(pvo.getPdimage1()) ||  !"".equals(pvo.getPdimage2())) {
				// 제품이미지1, 2가 null값이 아닌 경우에 
				pstmt.setString(1, pvo.getPdcategory_fk());
				pstmt.setString(2, pvo.getPdname());
				pstmt.setString(3, pvo.getPdimage1());
				pstmt.setString(4, pvo.getPdimage2());
				pstmt.setInt(5, pvo.getPdqty());
				pstmt.setInt(6, pvo.getPrice());
				pstmt.setInt(7, pvo.getSaleprice());
				pstmt.setString(8, pvo.getPdcontent());
				pstmt.setString(9, pvo.getTexture());
				pstmt.setString(10, pvo.getPdgender());
				pstmt.setInt(11, pvo.getPdno());
				
			}
			else {
				
				pstmt.setString(1, pvo.getPdcategory_fk());
				pstmt.setString(2, pvo.getPdname());
				pstmt.setInt(3, pvo.getPdqty());
				pstmt.setInt(4, pvo.getPrice());
				pstmt.setInt(5, pvo.getSaleprice());
				pstmt.setString(6, pvo.getPdcontent());
				pstmt.setString(7, pvo.getTexture());
				pstmt.setString(8, pvo.getPdgender());
				pstmt.setInt(9, pvo.getPdno());
				
			}
			
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close();
		}
		
		return result;
	}
	
	// 색상, 사이즈 업데이트 메소드(update) (JIEUN)
	@Override
	public int productInfoUpdate(String pdno, String pcolor, String psize) throws SQLException {
		
		
		int productInfo = 0;
		
		try {
			
			conn = ds.getConnection(); 
			

			String sql = " UPDATE tbl_product_info "
					   + " SET pcolor = ?, psize = ? " 
					   + " where pinfono = ? ";
			
			pstmt = conn.prepareStatement(sql);		
			
			pstmt.setString(1, pcolor);
			pstmt.setString(2, psize);
			pstmt.setInt(3, Integer.parseInt(pdno));
			
			productInfo = pstmt.executeUpdate();
			
		} finally {
			close();
		}		
		
		return productInfo;
		
	}
	
	// pdno에 해당하는 제품 삭제 메소드
	@Override
	public int productDelete(String pdno) throws SQLException {
		
		int result = 0;
		
		try {
		
			conn = ds.getConnection(); 
			
			String sql = " delete from tbl_product "
					   + " where pdno = ? ";
			
			pstmt = conn.prepareStatement(sql);		
			
			pstmt.setInt(1, Integer.parseInt(pdno));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}
	
	// pdno에 해당하는 추가첨부파일 삭제 메소드 (JIEUN)
	@Override
	public int pdImgDelete(String pdno) throws SQLException {

		int result = 0;
		
		try {
		
			conn = ds.getConnection(); 

			String sql = " delete from tbl_product_imagefile "
					   + " where pdno_fk = ? ";
			
			pstmt = conn.prepareStatement(sql);		
			
			pstmt.setInt(1, Integer.parseInt(pdno));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
		
	}
	
	// pdno에 해당하는 색상과 사이즈 삭제 메소드 (JIEUN)
	@Override
	public int pdInfoDelete(String pdno) throws SQLException {

		int result = 0;
		
		try {

			conn = ds.getConnection(); 

			String sql = " delete from tbl_product_info "
					   + " where pdno_fk = ? ";
			
			pstmt = conn.prepareStatement(sql);		
			
			pstmt.setInt(1, Integer.parseInt(pdno));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
		
	}
	
	// 추가 이미지 파일은 있을 수도 있고 없을 수도 있기 때문에 select로 개수를 알아오는 메소드(JIEUN)
	@Override
	public int pdImgSelected(String pdno) throws SQLException {
		

		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select count(*) "+
					     " from tbl_product_imagefile "+
					     " where pdno_fk = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(pdno));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		} finally {
			close();
		}
		
		return result;		
		
		
	}
	
	// 색상, 사이즈 일련번호 구해오기 (JIEUN)
	@Override
	public 	HashMap<String, String> pdinfoseqSelect(String pdno) throws SQLException {
		
		HashMap<String, String> infoMap = new HashMap<String, String>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select rownum as rno, pinfono "+
					     " from tbl_product_info "+
					     " where pdno_fk=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(pdno));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String rno = String.valueOf(rs.getInt(1));
				String pinfono = String.valueOf(rs.getInt(2));
				
				infoMap.put(rno, pinfono);
				
			}
			
			
		} finally {
			close();
		}
		
		return infoMap;	
		
	}	

	// 관리자 페이지에서 주문 리스트 가져오는(select) 메소드
	@Override
	public List<OrderDetailVO> adminOrderListAll() throws SQLException {
		
		List<OrderDetailVO> orderDetailList = new ArrayList<OrderDetailVO>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select odrseqnum, fk_odrcode, o.odrdate, m.name, p.pdname, odrprice, deliverstatus, deliverdate "+
					" from tbl_orderdetail od join tbl_order o "+
					" on od.fk_odrcode = o.odrcode "+
					" join tbl_member m "+
					" on o.userid_fk = m.userid "+
					" join tbl_product p "+
					" on p.pdno = od.fk_pinfono "+
					" order by odrdate desc ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				OrderDetailVO orderDetailvo = new OrderDetailVO();
				
				orderDetailvo.setOdrseqnum(rs.getInt(1));
				orderDetailvo.setFk_odrcode(rs.getNString(2));
				
				OrderVO ordervo = new OrderVO();
				
				ordervo.setOdrdate(rs.getString(3));
				orderDetailvo.setOrdervo(ordervo);
				
				MemberVO membervo = new MemberVO();
				membervo.setName(rs.getString(4));
				orderDetailvo.setMembervo(membervo);			
				
				ProductVO productvo = new ProductVO();
				productvo.setPdname(rs.getString(5));				
				orderDetailvo.setProductvo(productvo);
				
				orderDetailvo.setOdrprice(rs.getInt(6));
				orderDetailvo.setDeliverstatus(rs.getInt(7));
				orderDetailvo.setDeliverdate(rs.getString(8));

				orderDetailList.add(orderDetailvo);

			}
			
			
		} 
		finally {
			close();
		}
		
		return orderDetailList;
	}	
	

		////////////////////////////////////////////////////////////김민아//////////////////////////////////////////////////////////////////////
		
		
	//search 페이지에 보여지는 상품이미지파일명을 모두 조회(select)하는 메소드 (MINA)
	   @Override
	   public List<ProductVO> searchProduct(Map<String, String> paraMap) throws SQLException {
	      
	      List<ProductVO> searchProductList = new ArrayList<>();

	      try {
	          conn = ds.getConnection();
	          
	          
	          String sql = "";
	          
	          // 인기상품순 정렬이 아닌 경우 ordersum(판매수량)은 필요가 없으므로 조인하지 않는다
	          if(!("sortBestProduct".equalsIgnoreCase(paraMap.get("sort")))) {
	          sql = " select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender "
	          		+ "from "+
					"( "+
					"    select rownum AS rno, pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender  "+
					"    from "+
					"    ( "+
					"        select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender "
					+"		 from tbl_product ";
	          
	          
	          }else if("sortBestProduct".equalsIgnoreCase(paraMap.get("sort"))) {
	        	  // 인기상품순 정렬을 선택한 경우 주문수량을 구하기 위한 sql join 부분을 추가한다
	        	   sql = " select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender, ordersum "
	  	          		+ "from "
	        			+ "( "
	  	          		+ " select rownum AS rno, pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender, ordersum  "
	        			+"  from "
	  	          		+"  ( "
	        			+"  	select pdno, pdname, pdcategory_fk, pdimage1, pdimage2, price, saleprice, pdinputdate, pdgender, nvl(ordersum,0) as ordersum  "
	        	  		+" 		from tbl_product "
        				+ "		left join   "
  		          		+ "				view_ordercodedetail "
  		          		+ "		on pdno = view_ordercodedetail.pdno_fk" ;
	          }
	          				
	          
	          if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender"))) { // gender에 성별을 '여성(2)' '남성(1)' 입력했다면 
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " where pdgender = ? ";
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " where pdcategory_fk = ? and pdgender = ? ";
	                }
	             }
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " where pdname like '%'|| ? ||'%' and pdgender = ?  ";
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql +=" where pdcategory_fk = ? and pdname like '%'|| ? ||'%' and pdgender = ? ";   
	                }
	             }
	             
	          }// end of if( "1".equals(paraMap.get("pdgender")) || "2".equals(paraMap.get("pdgender")))-----------------------------
	         
	          else { // gender에 성별을 입력하지 않았거나 '전체'를 입력했다면,
	             
	             if(paraMap.get("searchname") == null ) { //searchname(키워드)에 아무것도 입력하지 않았다면,
	                
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " ";
	                }
	                else { //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " where pdcategory_fk = ? ";
	                }
	             }
	             else { //searchname(키워드)에 입력이 되었다면,
	                if ( "0".equals(paraMap.get("pdcategory_fk")) ) { //pdcategory_fk(카테고리) 중 0(전체)을 선택했다면
	                   sql += " where pdname like '%'|| ? ||'%' ";
	                }
	                else {   //pdcategory_fk(카테고리) 중 0(전체)외에 다른 카테고리를 선택했다면
	                   sql += " where pdcategory_fk = ? and pdname like '%'|| ? ||'%' ";   
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
	          // 인기상품순을 클릭했다면
	          else if("sortBestProduct".equalsIgnoreCase(paraMap.get("sort"))) {
	             sql += " order by ordersum desc  ";
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
		 * // 물품 상세정보( DTO객체 / VO객체를 가져온다) primary key > pdno (승의)
		 * 
		 * @Override public List<ProductVO> ProductList() throws SQLException {
		 * 
		 * List<ProductVO> productList = new ArrayList<>(); try { conn =
		 * ds.getConnection();
		 * 
		 * String sql =
		 * " SELECT pdno, pdname, pdcategory_fk, pdimage1, pdimage2, pdqty, price, saleprice, pdcontent, point, texture FROM tbl_product WHERE pdno = 1 ORDER BY pdno ASC "
		 * ;
		 * 
		 * pstmt = conn.prepareStatement(sql);
		 * 
		 * rs = pstmt.executeQuery();
		 * 
		 * while (rs.next()) {
		 * 
		 * // System.out.println("DAO-Checked"); ProductVO pdvo = new ProductVO();
		 * 
		 * pdvo.setPdno(rs.getInt(1)); pdvo.setPdname(rs.getString(2));
		 * pdvo.setPdcategory_fk(rs.getInt(3)); pdvo.setPdimage1(rs.getString(4));
		 * pdvo.setPdimage2(rs.getString(5)); pdvo.setPdqty(rs.getInt(6));
		 * pdvo.setPrice(rs.getInt(7)); pdvo.setSaleprice(rs.getInt(8));
		 * pdvo.setPdcontent(rs.getString(9)); pdvo.setPoint(rs.getInt(10));
		 * pdvo.setTexture(rs.getString(11));
		 * 
		 * productList.add(pdvo);
		 * 
		 * // System.out.println(pdvo.getPdname());
		 * 
		 * } // end of while------------------------------------
		 * 
		 * } finally { close(); }
		 * 
		 * return productList;
		 * 
		 * }
		 */

	//물품 상세정보 pdno key 값의 정보를 불러온다. (승의)
		@Override
		public List<ProductVO> ProductList(String pdno) throws SQLException {

			List<ProductVO> productList = new ArrayList<>();

			try {
				conn = ds.getConnection();

				String sql = "SELECT PDNO, PDNAME, PDCATEGORY_FK, PDIMAGE1, PDIMAGE2, PDQTY, PRICE, SALEPRICE, PDCONTENT, POINT, TEXTURE   "
						+ "FROM TBL_PRODUCT  " + "WHERE PDNO = ? ";
				/*
				 * String sql = "SELECT  " +
				 * "PD.PDNO, PD.PDNAME, PD.PDCATEGORY_FK, PD.PDIMAGE1, PD.PDIMAGE2,  " +
				 * "PD.PDQTY, PD.PRICE, PD.SALEPRICE, PD.PDCONTENT, PD.POINT,  " +
				 * "PD.TEXTURE, PDINFO.PINFONO, PDINFO.PCOLOR, PDINFO.PSIZE  " + "FROM ( " +
				 * "SELECT PDNO, PDNAME, PDCATEGORY_FK, PDIMAGE1, PDIMAGE2, PDQTY, PRICE, SALEPRICE, PDCONTENT, POINT, TEXTURE  FROM TBL_PRODUCT ) PD "
				 * +
				 * "JOIN  ( SELECT PINFONO, PDNO_FK , PCOLOR, PSIZE FROM TBL_PRODUCT_INFO  ) PDINFO "
				 * + "ON PD.PDNO = PDINFO.PDNO_FK WHERE PDNO = ? " + "ORDER BY PD.PDNO ASC ";
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
	// System.out.println("dao-check HSU");

					/*
					 * pdvo.setPinfono(rs.getInt(12)); // pdvo.setPdno_fk( rs.getString(13) );
					 * pdvo.setPcolor(rs.getString(13)); pdvo.setPsize(rs.getString(14));
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

	//물품상세페이지에서의 사이즈,색상 (승의)
		@Override
		public List<ProductInfoVO> ProductInfoList(String pdno) throws SQLException {

			List<ProductInfoVO> productinfoList = new ArrayList<>();

			try {
				conn = ds.getConnection();

				String sql = "SELECT PINFONO, PDNO_FK, PCOLOR, PSIZE " + "FROM TBL_PRODUCT_INFO " + "WHERE PDNO_FK = ? "
						+ "ORDER BY PINFONO ASC";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pdno);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					ProductInfoVO pdinfovo = new ProductInfoVO();

					pdinfovo.setPinfono(rs.getInt(1));
					pdinfovo.setPdno_fk(rs.getInt(2));
					pdinfovo.setPcolor(rs.getString(3));
					pdinfovo.setPsize(rs.getString(4));

					productinfoList.add(pdinfovo);
					System.out.println("DAO-Checked INFOVO : " + pdinfovo);
				} // end of while------------------------------------

			} finally {
				close();
			}

			return productinfoList;

		}

	//특정 회원이 특정 제품에 대해 좋아요에 투표하기(insert) 
		@Override
		public int like(Map<String, String> paraMap) throws SQLException {

			int n = 0;

			try {
				conn = ds.getConnection();

				conn.setAutoCommit(false); // 수동커밋으로 전환

				String sql = " insert into tbl_product_like(fk_userid, fk_pdno) " + " values(?, ?) ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paraMap.get("userid"));
				pstmt.setString(2, paraMap.get("pdno"));

				pstmt.executeUpdate();

				if (n == 1) {
					conn.commit();
				}

			} catch (SQLIntegrityConstraintViolationException e) {
				conn.rollback();
			} finally {
				close();
			}

			return n;
		}

	//특정 제품에 대한 좋아요수 (select)
		@Override
		public Map<String, Integer> getLikeCnt(String pdno) throws SQLException {

			Map<String, Integer> map = new HashMap<>();

			try {
				conn = ds.getConnection();

				String sql = " select count(*) " + " from tbl_product_like " + " where pdno = ? " + " from dual ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pdno);

				rs = pstmt.executeQuery();

				rs.next();

				map.put("likecnt", rs.getInt(1));

			} finally {
				close();
			}

			return map;

		}

	// 장바구니 값 전달

		@Override
		public int addCart(Map<String, String> paraMap) throws SQLException {

			int result = 0;

			try {
				conn = ds.getConnection();
				String sql = "select pinfono from TBL_PRODUCT_INFO where  pdno_fk = '?' and pcolor = '?' ";
				/*
				 * String sql =
				 * " insert into tbl_cart ( CARTNO, USERID_FK, PINFONO, PQTY, REGISTERDAY ) " +
				 * " values(?,?,?,?,?) ";
				 */

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paraMap.get("pdno_fk"));
				pstmt.setString(2, paraMap.get("pcolor"));

				pstmt.executeQuery(sql);

				sql = " insert into tbl_cart (cartno, userid_fk, pinfono, pqty, to_char(registerday, 'yyyy-mm-dd hh24:mi:ss') AS registerday ) "
						+ "values ( default, ?, ?, ?, default) ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paraMap.get("userid"));
				pstmt.setString(2, paraMap.get("pinfono"));
				pstmt.setString(3, paraMap.get("pqty"));

				result = pstmt.executeUpdate();
			} catch (SQLIntegrityConstraintViolationException e) {
				conn.commit();
			} finally {
				close();
			}

			return result;
		}

		/*
		 * 1124
		 * //장바구니 값 전달
		 * 
		 * @Override public int addCart(Map<String, String> paraMap) throws SQLException
		 * {
		 * 
		 * int result = 0;
		 * 
		 * try { conn = ds.getConnection(); String sql =
		 * "select pinfono from TBL_PRODUCT_INFO where  pdno_fk = '?' and pcolor = '?' "
		 * ;
		 * 
		 * String sql =
		 * " insert into tbl_cart ( CARTNO, USERID_FK, PINFONO, PQTY, REGISTERDAY ) " +
		 * " values(?,?,?,?,?) ";
		 * 
		 * 
		 * pstmt = conn.prepareStatement(sql); pstmt.setString(1, paraMap.get("pdno_fk")
		 * ); pstmt.setString(2, paraMap.get("pcolor"));
		 * 
		 * pstmt.executeQuery(sql);
		 * 
		 * sql =
		 * " insert into tbl_cart (cartno, userid_fk, pinfono, pqty, to_char(registerday, 'yyyy-mm-dd hh24:mi:ss') AS registerday ) "
		 * + "values ( default, ?, ?, ?, default) ";
		 * 
		 * pstmt= conn.prepareStatement(sql); pstmt.setString(1, paraMap.get("userid")
		 * ); pstmt.setString(2, paraMap.get("pinfono") ); pstmt.setString(3,
		 * paraMap.get("pqty") );
		 * 
		 * result = pstmt.executeUpdate(); }
		 * catch(SQLIntegrityConstraintViolationException e) { conn.commit(); } finally
		 * { close(); }
		 * 
		 * return result; }
		 */

	//Ajax 를 이용한 특정 제품의 상품후기를 입력(insert)하기 
		@Override
		public int addComment(PurchaseReviewsVO previewvo) throws SQLException {

			int n = 0;

			try {
				conn = ds.getConnection();

				String sql = " insert into tbl_purchase_reviews(review_seq, fk_userid, fk_pdno, contents, writeDate, starpoint) "
						+ " values(seq_purchase_reviews.nextval, ?, ?, ?, default, ?) ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, previewvo.getFk_userid());
				pstmt.setInt(2, previewvo.getFk_pdno());
				pstmt.setString(3, previewvo.getContents());
				pstmt.setString(4, previewvo.getStarpoint());

				n = pstmt.executeUpdate();

			} finally {
				close();
			}

			return n;
		}

		// Ajax 를 이용한 특정 제품의 상품후기를 조회(select)하기
		@Override
		public List<PurchaseReviewsVO> commentList(String fk_pdno) throws SQLException {

			List<PurchaseReviewsVO> CommentList = new ArrayList<>();

			try {
				conn = ds.getConnection();

				String sql = "select review_seq, name, fk_pdno, contents, to_char(writeDate, 'yyyy-mm-dd hh24:mi:ss') AS writeDate, starpoint "
						+ "from tbl_purchase_reviews R join tbl_member M " + "on R.fk_userid = M.userid  "
						+ "where R.fk_pdno = ? " + "order by review_seq desc";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, fk_pdno);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					String contents = rs.getString("contents");
					String name = rs.getString("name");
					String writeDate = rs.getString("writeDate");
					String starpoint = rs.getString("starpoint");

					PurchaseReviewsVO previewvo = new PurchaseReviewsVO();
					previewvo.setContents(contents);

					MemberVO mvo = new MemberVO();
					mvo.setName(name);

					previewvo.setMvo(mvo);
					previewvo.setWriteDate(writeDate);

					CommentList.add(previewvo);
				}

			} finally {
				close();
			}

			return CommentList;
		}


	///////////////////////////////////////김동휘/////////////////////////////////////
	
	// 장바구니 버튼을 누를경우 상품의 리스트를 받아온뒤 화면상에 출력 (동휘)
	@Override
	public List<ProductVO> getCartList(String userid) throws SQLException {
		
		List<ProductVO> cartList = new ArrayList<ProductVO>();
		
		try {
			conn = ds.getConnection(); // DBCP에서 connection 받아오기
			
			String sql = "select pd.pdno, pdname, PDCATEGORY_FK, PDIMAGE1, PDIMAGE2, pdqty, price, saleprice, pdcontent, point, pdinputdate, texture, pdgender, w.userid_fk, w.pinfono, w.pqty, w.pcolor, w.psize\n"+
					"from\n"+
					"(\n"+
					"select v.userid_fk, v.pinfono, v.pqty, pdno_fk, pcolor, psize\n"+
					"from\n"+
					"(\n"+
					"select cartno, userid_fk, pinfono, pqty, registerday\n	"+
					"from TBL_cart \n"+
					"where userid_fk = ?\n"+
					")v inner join tbl_product_info p\n"+
					"on v.pinfono = p.PINFONO\n"+
					")w inner join tbl_product pd\n"+
					"on w.pdno_fk = pd.pdno";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1, userid);
			
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
	
	// 장바구니 비우기 버튼 메서드(동휘)
	@Override
	public int productAllDelete(int pdno, String userid_fk) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection(); // DBCP에서 connection 받아오기
			
			String sql = " delete from TBL_CART where userid_fk = ? and PINFONO = ? ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			//pstmt.setString(1, userid_fk);
			pstmt.setInt(2, pdno);
			pstmt.setString(1, userid_fk);
			
			int n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
		
	}
	
	// 상품 개별삭제 버튼을 누를경우 유저의 ID와 해당 제품의 번호를 받아와 DB테이블에서 삭제해주는 메서드
	@Override
	public int productOneDelete(String pinfono, String userid_fk) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection(); // DBCP에서 connection 받아오기
			
			String sql = " delete from TBL_CART where userid_fk = ? and PINFONO = ? ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			//pstmt.setString(1, userid_fk);
			pstmt.setString(1, userid_fk);
			pstmt.setString(2, pinfono);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}
	
	// 선택된 상품들 삭제메서드(동휘)
	@Override
	public int productChoiceDelete(int Pinfono, String userid_fk) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection(); // DBCP에서 connection 받아오기
			
			String sql = " delete from TBL_CART where userid_fk = ? and Pinfono = ? ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			//pstmt.setString(1, userid_fk);
			pstmt.setString(1, userid_fk);
			pstmt.setInt(2, Pinfono);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
		
	}
	
	// 사이즈와 색상정보는 product 테이블이 아닌 product_info 테이블에 있기때문에 
	// 장바구니 화면에 출력할 상품들의 정보를 받아온다. ( 동휘 )
	@Override
	public List<ProductInfoVO> getSizeAndColor(String userid) throws SQLException {
		
		List<ProductInfoVO> productInfoList = new ArrayList<ProductInfoVO>();
		
		try {
			conn = ds.getConnection(); // DBCP에서 connection 받아오기
			
			String sql = " select pi.PINFONO, PDNO_FK, pcolor, psize "+
					" from "+
					" ( "+
					" select pinfono "+
					" from tbl_cart "+
					" where userid_fk = ? "+
					" )v inner join tbl_product_info pi "+
					" on v.pinfono = pi.pinfono ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery(); // select 되어진 결과를 resultSet에 받는다.
			
			while(rs.next()) {
				ProductInfoVO pinfovo = new ProductInfoVO();
				
				pinfovo.setPinfono(rs.getInt(1));
				pinfovo.setPdno_fk(rs.getInt(2));
				pinfovo.setPcolor(rs.getString(3));
				pinfovo.setPsize(rs.getString(4));
				
				productInfoList.add(pinfovo);
			} // end of while(rs.next()) ---------------------------
		} finally {
			close();
		}
		
		return productInfoList;
	}
	
	// (트랜젝션) - 주문을 완료하는 경우 일어나는 일련의 행위들 (동휘)
	// 1. 주문테이블 insert -> 2. 주문테이블에서 받아온 주문코드를 주문상세테이블로 전달 -> 3. 주문상세테이블 insert ->
	// 4. 장바구니테이블의 데이터삭제 (주문을 했기때문에 더이상 장바구니에는 필요없음)
	// 5. 유저 테이블의 주문을 시킨 유저의 Point 변화 ( 주문포인트만큼 추가, 사용포인트만큼 감소)
	@Override
	public String RecordOrder(Map<String, String> paraMap) throws SQLException {
		
		String result = "";
		
		try {
			// 주문테이블에 해당 상품의 데이터를 기입한다.
			conn = ds.getConnection(); // DBCP에서 connection 받아오기
			
			String sql = " insert into tbl_order(odrcode, userid_fk, ODRTOTALPRICE, ODRTOTALPOINT) "
					   + " values (?,?,?,?) ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1,paraMap.get("ordCode"));
			pstmt.setString(2,paraMap.get("userid_fk"));
			pstmt.setString(3,paraMap.get("finalPrice"));
			pstmt.setString(4,paraMap.get("addPoint"));
			
			pstmt.executeUpdate();
			
			//==================================================================================//
			// 주문코드를 받아온다. ( 주문코드가 주문상세의 Foreign Key이기 때문에 필요하다. )
			sql = " select odrcode "+
				  " from "+
				  " ( "+
				  " select odrcode, userid_fk, ODRTOTALPRICE, ODRTOTALPOINT, ODRDATE "+
				  " from tbl_order "+
				  " where userid_fk = ? "+
				  " order by ODRDATE desc "+
				  " )v "+
				  " where rownum = 1 ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1, paraMap.get("userid_fk"));
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			String odrcode = rs.getString(1); 
			
			//==================================================================================//
			// order_detail 테이블에 상품의 정보를 기입한다.
			
			  sql = " insert into TBL_ORDERDETAIL(ODRSEQNUM, FK_ODRCODE, FK_PINFONO, OQTY, ODRPRICE, DELIVERSTATUS) "
				  + " values (seq_tbl_orderdetail.nextval,?,?,?,?,?) ";
			  
			  pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			  
			  pstmt.setString(1, odrcode);
			  pstmt.setString(2, paraMap.get("pinfono"));
			  pstmt.setString(3, paraMap.get("productamount"));
			  pstmt.setString(4, paraMap.get("price"));
			  pstmt.setString(5, "1");
			  
			  pstmt.executeUpdate();
			
			//==================================================================================//
			// 주문을 했기때문에 장바구니 테이블에 주문된 상품들의 데이터를 삭제
			sql = " delete from TBL_CART where userid_fk = ? ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1, paraMap.get("userid_fk"));
			
			pstmt.executeUpdate();
			
			//==================================================================================//
			// 유저 테이블에 사용된 포인트만큼 감소
			sql = "update TBL_member set POINT = (POINT-?) where USERID = ?";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1, paraMap.get("usePoint"));
			pstmt.setString(2, paraMap.get("userid_fk"));
			
			pstmt.executeUpdate();
			
			//==================================================================================//
			// 유저의 포인트를 결제된 상품의 가산포인트만큼 추가
			sql = "update TBL_member set POINT = (POINT+?) where USERID = ?";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1, paraMap.get("addPoint"));
			pstmt.setString(2, paraMap.get("userid_fk"));
			
			pstmt.executeUpdate();
			
			// 혹시 모르기때문에 가만 두시오.
			/* 
			 * sql = " select to_char(ODRDATE,'yyyy-mm-dd hh24') "+ " from "+ " ( "+
			 * " select * "+ " from tbl_order "+ " where userid_fk = ? "+
			 * " order by odrdate desc "+ " ) "+ " where rownum = 1 ";
			 * 
			 * pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			 * 
			 * pstmt.setString(1,paraMap.get("userid_fk"));
			 * 
			 * rs = pstmt.executeQuery();
			 * 
			 * while(rs.next()) { result = rs.getString(1); }
			 */
		} finally {
			close();
		}
		
		return result;
	}
	
	// 주문완료페이지에 주문정보(tbl_order)를 받아서 전송해주는 메서드(동휘)
	@Override
	public OrderVO getOrderInfo(String userid) throws SQLException{
		
		OrderVO ovo = new OrderVO();
		
		try {
			conn = ds.getConnection(); // DBCP에서 connection 받아오기
			
			String sql = " select ODRCODE, USERID_FK, ODRTOTALPRICE, ODRTOTALPOINT, ODRDATE "+
						 " from tbl_order "+
						 " where USERID_FK = ? "+
						 " order by ODRDATE ";
			
			pstmt = conn.prepareStatement(sql); // prepareStatment로 sql을 보낸다.
			
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ovo.setOdrcode(rs.getString(1));
				ovo.setUserid_fk(rs.getString(2));
				ovo.setOdrtotalprice(rs.getString(3));
				ovo.setOdrtotalpoint(rs.getString(4));
				ovo.setOdrdate(rs.getString(5));
			}
		} finally {
			close();
		}
		
		return ovo;
	}


}




























