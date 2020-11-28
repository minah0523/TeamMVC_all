package member.mdl;

 
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import Payment.mdl.OrderDetailVO;
import Payment.mdl.OrderVO;
import member.mdl.MemberVO;
import myshop.mdl.ProductVO;
import util.security.*;

public class MemberDAO implements InterMemberDAO {

	private DataSource ds;   // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public MemberDAO() {
		try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/teammvc_oracle");
		    
		    aes = new AES256(SecretMyKey.KEY);	
		    // SecretMyKey.KEY 은 우리가 만든 비밀키.
		    
		} catch(NamingException e) {
			e.printStackTrace();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기 
	private void close() {
		try {
			if(rs != null)    {rs.close();    rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn != null)  {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
	// 회원가입 메소드 (tbl_member 테이블에 insert)
	@Override
	public int registerMember(MemberVO member) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = "insert into tbl_member(userid, pwd, name, email, mobile, postcode, address, detailaddress, extraaddress, gender, birthday) "     
					   + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, Sha256.encrypt(member.getPwd()) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다. 
			pstmt.setString(3, member.getName());
			pstmt.setString(4, aes.encrypt( member.getEmail() ) );  // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(5, aes.encrypt( member.getMobile() ) ); // 휴대폰번호를 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(6, member.getPostcode());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getDetailaddress());
			pstmt.setString(9, member.getExtraaddress());
			pstmt.setString(10, member.getGender());
			pstmt.setString(11, member.getBirthday());
			
			result = pstmt.executeUpdate();
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}

	
	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true를 리턴해주고, userid 가 존재하지 않으면 false를 리턴한다) 
	@Override
	public boolean idDuplicateCheck(String userid) throws SQLException {
		
		boolean isExist = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			isExist = rs.next();  // 행이 있으면(중복된 userid) true, 
			                      // 행이 없으면(사용가능한 userid) false
		} finally {
			close();
		}
		
		return isExist;
	}

	// email 중복검사 (tbl_member 테이블에서 email 이 존재하면 true를 리턴해주고, email 이 존재하지 않으면 false를 리턴한다)
	@Override
	public boolean emailDuplicateCheck(String email) throws SQLException {

		boolean isExist = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select email "
					   + " from tbl_member "
					   + " where email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aes.encrypt(email));
			
			rs = pstmt.executeQuery();
			
			isExist = rs.next();  // 행이 있으면(중복된 email) true, 
			                      // 행이 없으면(사용가능한 email) false
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return isExist;
	}

	
	// 입력받은 paraMap 을 가지고 한명의 회원정보를 리턴시켜주는 메소드(로그인 처리)
	@Override
	public MemberVO selectOneMember(Map<String, String> paraMap) throws SQLException {
		
		MemberVO member = null; 
		
		try {
			conn = ds.getConnection();
			
			String sql = "SELECT userid, name, email, mobile, postcode, address, detailaddress, extraaddress, gender, "+
					"       birthyyyy, birthmm, birthdd, coin, point, registerday, pwdchangegap, "+
					"       nvl(lastlogingap, trunc( months_between(sysdate, registerday) ) ) AS lastlogingap "+
					"FROM "+
					"("+
					"select userid, name, email, mobile, postcode, address, detailaddress, extraaddress, gender "+
					"     , substr(birthday,1,4) AS birthyyyy, substr(birthday,6,2) AS birthmm, substr(birthday,9) AS birthdd "+
					"     , coin, point, to_char(registerday, 'yyyy-mm-dd') AS registerday "+
					"     , trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap "+
					"from tbl_member "+
					"where status = 1 and  userid = ? and pwd = ? "+
					") M "+
					"CROSS JOIN "+
					"("+
					"select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap "+
					"from tbl_loginhistory "+
					"where fk_userid = ? "+
					") H";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")) );
			pstmt.setString(3, paraMap.get("userid"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				
				member.setUserid(rs.getString(1));
				member.setName(rs.getString(2));
				member.setEmail( aes.decrypt(rs.getString(3)) ); // 복호화 
				member.setMobile( aes.decrypt(rs.getString(4)) ); // 복호화 
				member.setPostcode(rs.getString(5));
				member.setAddress(rs.getString(6));
				member.setDetailaddress(rs.getString(7));
				member.setExtraaddress(rs.getString(8));
				member.setGender(rs.getString(9));
				member.setBirthday(rs.getString(10) + rs.getString(11) + rs.getString(12));
				member.setCoin(rs.getInt(13));
				member.setPoint(rs.getInt(14));
				member.setRegisterday(rs.getString(15));
				
				if(rs.getInt(16) >= 3) {
					// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
					// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false	
					member.setRequirePwdChange(true); // 로그인시 암호를 변경해라는 alert 를 띄우도록 한다. 
				}
				
				if(rs.getInt(17) >= 12) {
					// 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 휴면으로 지정
					member.setIdle(1);
					
					// === tbl_member 테이블의 idle 컬럼의 값을 1 로 변경 하기 === //
					sql = " update tbl_member set idle = 1 "
						+ " where userid = ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, paraMap.get("userid"));
										
					pstmt.executeUpdate();
				}
				
				if(member.getIdle() != 1) {
				    // === tbl_loginhistory(로그인기록) 테이블에 insert 하기 === //
					sql = " insert into tbl_loginhistory(fk_userid, clientip) "
						+ " values(?, ?) ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, paraMap.get("userid"));
					pstmt.setString(2, paraMap.get("clientip"));
					
					pstmt.executeUpdate();
				}
				
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return member;
	}

	
	// 아이디 찾기(성명, 이메일을 입력받아서 해당 사용자의 아이디를 알려준다)
	@Override
	public String findUserid(Map<String, String> paraMap) throws SQLException {
		
		String userid = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where status = 1 and name = ? and email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("name"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")) ); 
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {  
			   userid = rs.getString(1);	
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		
		return userid;
	}

	
	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다)
	@Override
	public boolean isUserExist(Map<String, String> paraMap) throws SQLException {
		
		boolean isUserExist = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where status = 1 and userid = ? and email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")) ); 
			
			rs = pstmt.executeQuery();
			
			isUserExist = rs.next();
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		
		return isUserExist;
	}

	
	// 암호 변경하기 
	@Override
	public int pwdUpdate(Map<String, String> paraMap) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = "update tbl_member set pwd = ? "     
					   + "where userid = ? "; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, Sha256.encrypt( paraMap.get("pwd") ) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다. 
			pstmt.setString(2, paraMap.get("userid") );
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}		
		
		return result;
	}	
	

	// 회원의 개인 정보 변경하기  
	@Override
	public int updateMember(MemberVO member) throws SQLException {

		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = "update tbl_member set name = ? "
					   + "                    , email = ? " 
					   + "                    , mobile = ? "
					   + "                    , postcode = ? "
					   + "                    , address = ? "
					   + "                    , detailaddress = ? "
					   + "                    , extraaddress = ? "
					   + "where userid = ? "; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getName()); 
			pstmt.setString(2, aes.encrypt(member.getEmail()) );
			pstmt.setString(3, aes.encrypt(member.getMobile()) );
			pstmt.setString(4, member.getPostcode() );
			pstmt.setString(5, member.getAddress() );
			pstmt.setString(6, member.getDetailaddress() );
			pstmt.setString(7, member.getExtraaddress() );
			pstmt.setString(8, member.getUserid() );
						
			result = pstmt.executeUpdate();
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;		

	}
	
	
	// *** 페이징 처리를 한 모든 회원 또는 검색한 회원 목록 보여주기 *** //
	@Override
	public List<MemberVO> selectPagingMember(Map<String, String> paraMap)throws SQLException {
		
	List<MemberVO> memberList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = "select userid, name, email, gender "+
					"from "+
					"( "+
					"    select rownum AS rno, userid, name, email, gender "+
					"    from "+
					"    ( "+
					"        select userid, name, email, gender "+
					"        from tbl_member "+
					"        where userid != 'admin' "; 
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if( "email".equals(colname) ) {
				// 검색대상이 email 인 경우
				searchWord = aes.encrypt(searchWord);
			}
			
			
			if( searchWord != null && !searchWord.trim().isEmpty() ) {
			    
				sql += " and "+colname+" like '%'|| ? ||'%' ";
			}
			
			sql +=  "    order by registerday desc "+
					"    ) V "+
					") T  "+
					"where rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			int currentShowPageNo = Integer.parseInt( paraMap.get("currentShowPageNo") );
			int sizePerPage = Integer.parseInt( paraMap.get("sizePerPage") );
			
			if( searchWord != null && !searchWord.trim().isEmpty() ) {
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
				pstmt.setInt(3, (currentShowPageNo * sizePerPage)); // 공식 
			}
			else {
				pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1)); // 공식
				pstmt.setInt(2, (currentShowPageNo * sizePerPage)); // 공식 
			}
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {  
			   	
				MemberVO mvo = new MemberVO();
				mvo.setUserid(rs.getString(1));
				mvo.setName(rs.getString(2));
				mvo.setEmail(aes.decrypt(rs.getString(3))); // 복호화 
				mvo.setGender(rs.getString(4));
				
				memberList.add(mvo);
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		
		return memberList;		
	}

	
	
	// 페이징처리를 위해서 전체회원에 대한 총페이지 개수 알아오기(select)  
	@Override
	public int getTotalPage(Map<String, String> paraMap)throws SQLException {
		 
int totalPage = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select ceil( count(*)/ ? ) "
					   + " from tbl_member"
					   + " where userid != 'admin' "; 
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if( "email".equals(colname) ) {
				// 검색대상이 email 인 경우
				searchWord = aes.encrypt(searchWord);
			}
			
			
			if( searchWord != null && !searchWord.trim().isEmpty() ) {
			    
				sql += " and "+colname+" like '%'|| ? ||'%' ";
			}
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("sizePerPage"));
			
			if( searchWord != null && !searchWord.trim().isEmpty() ) {
				pstmt.setString(2, searchWord);
			}
						
			rs = pstmt.executeQuery();
			
			rs.next();
			
			totalPage = rs.getInt(1);
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		
		return totalPage;				
	}

	
	
	// userid 값을 입력받아서 회원1명에 대한 상세정보를 알아오기(select) 
	@Override
	public MemberVO memberOneDetail(String userid) throws SQLException {
		 
		MemberVO mvo = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid, name, email, mobile, postcode, address, detailaddress, extraaddress, gender " + 
					     "   	, substr(birthday,1,4) AS birthyyyy, substr(birthday,6,2) AS birthmm, substr(birthday,9) AS birthdd " + 
					     "		, coin, point, to_char(registerday, 'yyyy-mm-dd') AS registerday " + 
					     " from tbl_member " +
					     " where userid = ? "; 
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
						
			rs = pstmt.executeQuery();
			
			if(rs.next()) {  
			   	
				mvo = new MemberVO();
				mvo.setUserid(rs.getString(1));
				mvo.setName(rs.getString(2));
				mvo.setEmail(aes.decrypt(rs.getString(3))); // 복호화 
				mvo.setMobile( aes.decrypt(rs.getString(4)) ); // 복호화 
				mvo.setPostcode(rs.getString(5));
				mvo.setAddress(rs.getString(6));
				mvo.setDetailaddress(rs.getString(7));
				mvo.setExtraaddress(rs.getString(8));
				mvo.setGender(rs.getString(9));
				mvo.setBirthday(rs.getString(10) + rs.getString(11) + rs.getString(12));
				mvo.setCoin(rs.getInt(13));
				mvo.setPoint(rs.getInt(14));
				mvo.setRegisterday(rs.getString(15));
			}
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		
		return mvo;
	}

	
	//마이페이지에 필요한 내가 주문한 상품&주문정보 알아오기 (select) 
	@Override
	public List<OrderDetailVO> selectOrderList(String userid) throws SQLException {
		
		List<OrderDetailVO> orderList = new ArrayList<>();
		
		try {
			 conn = ds.getConnection();
			 
			 String sql = "SELECT   "+
							 "  a.pdname,  "+
							 "  c.odrcode,  "+
							 "  b.fk_pinfono,  "+
							 "  b.odrprice,   "+
							 "  b.deliverstatus, "+
							 "  to_char(b.deliverdate, 'yyyy-mm-dd') AS deliverdate  "+
						 
							 "  FROM Tbl_Product a  "+
							 " INNER JOIN tbl_orderdetail b  "+
							 "    ON a.pdno = b.fk_pinfono "+
							 " INNER JOIN tbl_order c  "+
							 "    ON b.fk_odrcode = c.odrcode "+
							 " WHERE c.userid_fk = ?  "
							 ;
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, userid);
			 
			 rs = pstmt.executeQuery();
			 
			 while( rs.next() ) {
				 
				 ProductVO pvo = new ProductVO();
				 pvo.setPdname(rs.getString(1)); //상품이름
				 
				 OrderVO ovo = new OrderVO();
				 ovo.setOdrcode(rs.getString(2)); //주문코드
				 
				 OrderDetailVO odvo = new OrderDetailVO();  
				 odvo.setFk_pinfono(rs.getInt(3)); //제품번호
				 odvo.setOdrprice(rs.getInt(4));   //주문가격
				 odvo.setDeliverstatus(rs.getInt(5)); //배송상태
				 odvo.setDeliverdate(rs.getString(6));   //배송날짜
				 	
				 odvo.setProductvo(pvo);
				 odvo.setOrdervo(ovo);
				 
				 orderList.add(odvo);
				 
			 }// end of while-----------------------------------------
			 
		} finally {
			close();
		}		
		
		
		return orderList;
	}

	
	
}
