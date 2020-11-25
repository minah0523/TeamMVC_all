package notice.mdl;

import java.sql.SQLException;
import java.util.*;

public interface InterNoticeDAO {

	//페이징 처리한 게시물 목록 보여주기 //
	List<NoticeVO> getList(String currentShowPageNo) throws SQLException;
	
	// 페이징처리를 위해서 전체게시물에 대한 총페이지 개수 알아오기(select) 
	int getTotalPage() throws SQLException;

	// 리스트 하나를 클릭했을 때 해당 게시물 보여주기
	NoticeVO selectOneListByNoticeno(String noticeno) throws SQLException;

	// 공지사항 작성하기
	int NoticeInsert(NoticeVO nvo) throws SQLException;

	// 공지사항 업데이트하기
	int updateNotice(NoticeVO nvo) throws SQLException;

	// 공지사항 삭제하기
	int deleteNotice(String noticeno) throws SQLException;

	
}
