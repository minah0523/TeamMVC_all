package notice.mdl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class NoticeDAO implements InterNoticeDAO{
	
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public NoticeDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/teammvc_oracle"); // 이름(web.xml에 res-ref-name에 해당하는 것)
		} catch (NamingException e) {
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//페이징 처리한 게시물 목록 보여주기 //
	@Override
	public List<NoticeVO> getList(String currentShowPageNo) throws SQLException {

			List<NoticeVO> noticeList = new ArrayList<>();
			
			try {
				conn = ds.getConnection();
				
				String sql = "select noticeno, title, writeday, contents "+
						"from "+
						"( "+
						"    select rownum AS rno, noticeno, title, writeday, contents "+
						"    from "+
						"    ( "+
						"        select noticeno, title, to_char(writeday, 'yyyy-mm-dd') AS writeday, contents "+
						"        from tbl_notice "+
				        "        order by noticeno desc "+
						"    ) V "+
						") T  "+
						"where rno between ? and ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				int n_currentShowPageNo = Integer.parseInt(currentShowPageNo);
				
				pstmt.setInt(1, (n_currentShowPageNo * 10) - (10 - 1)); // 공식
				pstmt.setInt(2, (n_currentShowPageNo * 10)); // 공식 
							
				rs = pstmt.executeQuery();
				
				while(rs.next()) {  
					NoticeVO nvo = new NoticeVO();
					nvo.setNoticeno(rs.getInt(1));
					nvo.setTitle(rs.getString(2));
					nvo.setWriteday(rs.getString(3));
					nvo.setContents(rs.getString(4));
					
					noticeList.add(nvo);
				}
				
			} finally {
				close();
			}		
			
			return noticeList;		
		}

		
		// 페이징처리를 위해서 전체게시물에 대한 총페이지 개수 알아오기(select) 
		@Override
		public int getTotalPage() throws SQLException {

			int totalPage = 0;
			
			try {
				conn = ds.getConnection();
				
				String sql = " select ceil( count(*)/ 10 ) "
						   + " from tbl_notice ";
						  
				pstmt = conn.prepareStatement(sql);
							
				rs = pstmt.executeQuery();
				
				rs.next();
				
				totalPage = rs.getInt(1);
				
			} finally {
				close();
			}		
			
			return totalPage;				
		}
	
		// 리스트 하나를 클릭했을 때 해당 게시물 보여주기
		@Override
		public NoticeVO selectOneListByNoticeno(String noticeno) throws SQLException {
			
			NoticeVO nvo = null;
			
			try {
				 conn = ds.getConnection(); 
				 
				 String sql = " SELECT title, to_char(writeday, 'yyyy-mm-dd') AS writeday, contents "
				 			+ "	FROM tbl_notice WHERE noticeno = ? ";
				 
				 pstmt = conn.prepareStatement(sql);
				 pstmt.setString(1, noticeno);
				 
				 rs = pstmt.executeQuery();
				 
				 if(rs.next()) {
					 
					 String title = rs.getString(1);     // 제품명
					 String writeday = rs.getString(2);  // 제조회사명
					 String contents = rs.getString(3);  // 제품설명
					 
					 nvo = new NoticeVO(); 
					 
					 nvo.setNoticeno(Integer.parseInt(noticeno));
					 nvo.setTitle(title);
					 nvo.setWriteday(writeday);
					 nvo.setContents(contents);
					 
					 
				 }// end of if-----------------------------
				 
			} finally {
				close();
			}
			
			return nvo;			
		
		}
		
	
		// 공지사항 작성하기
		@Override
		public int NoticeInsert(NoticeVO nvo) throws SQLException {
			
			int result = 0;

			try {
			
				conn = ds.getConnection();

				String sql = " INSERT INTO tbl_notice(noticeno, title, contents) "
						+ " VALUES(seq_tbl_notice_noticeno.nextval, ?, ?) ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, nvo.getTitle());
				pstmt.setString(2, nvo.getContents());
				
			    result = pstmt.executeUpdate();
			    
			} finally {
				close();
			}
			
			return result;	
		}

		
		// 공지사항 업데이트하기
		@Override
		public int updateNotice(NoticeVO nvo) throws SQLException {

			int result = 0;

			try {
			
				conn = ds.getConnection();

				String sql = " UPDATE tbl_notice SET title = ?, contents = ? WHERE noticeno = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, nvo.getTitle());
				pstmt.setString(2, nvo.getContents());
				pstmt.setInt(3, nvo.getNoticeno());
				
			    result = pstmt.executeUpdate();
			    
			} finally {
				close();
			}
			return result;	
		}

		
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getNext() {
		String sql = "SELECT noticeno FROM tbl_notice ORDER BY noticeno DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // 첫 번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String title, String contents) {
		String sql = " INSERT INTO tbl_notice(noticeno, title, contents) "
						+ " VALUES(?, ?, ?) ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, title);
			pstmt.setString(3, contents);
			return pstmt.executeUpdate();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	
	public boolean nextPage(int pageNumber) {
		
		String sql = "SELECT * FROM tbl_notice WHERE noticeno < ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; //데이터베이스 오류
	}
	
	public NoticeVO getNotice(int noticeno) {
		String sql = "SELECT * FROM tbl_notice WHERE noticeno = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, noticeno);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				NoticeVO nvo = new NoticeVO();
				nvo.setNoticeno(rs.getInt(1));
				nvo.setTitle(rs.getString(2));
				nvo.setContents(rs.getString(3));
				return nvo;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int noticeno, String title, String contents) {
		String sql = "UPDATE tbl_notice SET title = ?, contents = ? WHERE noticeno = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setInt(3, noticeno);
			return pstmt.executeUpdate();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int delete(int noticeno) {
		String sql = "UPDATE tbl_notice SET title = 0 WHERE noticeno = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeno);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}

	


	
}
