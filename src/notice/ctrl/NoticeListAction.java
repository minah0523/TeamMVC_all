<<<<<<< HEAD
package notice.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.*;
import notice.mdl.*;

public class NoticeListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String currentShowPageNo = request.getParameter("currentShowPageNo");

		if (currentShowPageNo == null) {
			currentShowPageNo = "1";
		}

		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자 아닌 문자를 입력한 경우 또는
		// int 범위를 초과한 숫자를 입력한 경우라면 currentShowPageNo 는 1 페이지로 만들도록 한다. ==== //
		try {
			Integer.parseInt(currentShowPageNo);
		} catch (NumberFormatException e) {
			currentShowPageNo = "1";
		}

		InterNoticeDAO ndao = new NoticeDAO();

		List<NoticeVO> noticeList = ndao.getList(currentShowPageNo);

		request.setAttribute("noticeList", noticeList);

		// **** ========= 페이지바 만들기 ========= **** //
		// 페이징처리를 위해서 전체게시물에 대한 총페이지 개수 알아오기(select)
		int totalPage = ndao.getTotalPage();

		String pageBar = "";

		int blockSize = 10;
		// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.

		int loop = 1;
		// loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.

		int pageNo = 0;
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.

		// !!!! 다음은 pageNo 를 구하는 공식이다. !!!! //
		pageNo = ((Integer.parseInt(currentShowPageNo) - 1) / blockSize) * blockSize + 1;

		// **** [맨처음][이전] 만들기 **** //
		// pageNo-1 == 11 - 1 == 10 ==> currentShowPageNo
		if (pageNo != 1) {
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=1'>[맨처음]</a>&nbsp;";
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a>&nbsp;";
		}

		while (!(loop > blockSize || pageNo > totalPage)) {

			if (pageNo == Integer.parseInt(currentShowPageNo)) {
				pageBar += "&nbsp;<span style='border:solid 1px #7B5232; font-weight: bold; color:#7B5232; padding: 2px 4px;'>" + pageNo
						+ "</span>&nbsp;";
			} else {
				pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + pageNo + "'>" + pageNo + "</a>&nbsp;";
			}

			loop++;
			pageNo++;
		} // end of while---------------------------------

		// **** [다음][마지막] 만들기 **** //
		// pageNo ==> 11
		if (!(pageNo > totalPage)) {
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + pageNo + "'>[다음]</a>&nbsp;";
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + totalPage + "'>[마지막]</a>&nbsp;";
		}

		request.setAttribute("pageBar", pageBar);

		super.setViewPage("/WEB-INF/notice/notice.jsp");

	}

}
=======
package notice.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ctrl.AbstractController;
import member.mdl.*;
import notice.mdl.*;

public class NoticeListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String currentShowPageNo = request.getParameter("currentShowPageNo");

		if (currentShowPageNo == null) {
			currentShowPageNo = "1";
		}

		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자 아닌 문자를 입력한 경우 또는
		// int 범위를 초과한 숫자를 입력한 경우라면 currentShowPageNo 는 1 페이지로 만들도록 한다. ==== //
		try {
			Integer.parseInt(currentShowPageNo);
		} catch (NumberFormatException e) {
			currentShowPageNo = "1";
		}

		InterNoticeDAO ndao = new NoticeDAO();

		List<NoticeVO> noticeList = ndao.getList(currentShowPageNo);

		request.setAttribute("noticeList", noticeList);

		// **** ========= 페이지바 만들기 ========= **** //
		// 페이징처리를 위해서 전체게시물에 대한 총페이지 개수 알아오기(select)
		int totalPage = ndao.getTotalPage();

		String pageBar = "";

		int blockSize = 10;
		// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.

		int loop = 1;
		// loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.

		int pageNo = 0;
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.

		// !!!! 다음은 pageNo 를 구하는 공식이다. !!!! //
		pageNo = ((Integer.parseInt(currentShowPageNo) - 1) / blockSize) * blockSize + 1;

		// **** [맨처음][이전] 만들기 **** //
		// pageNo-1 == 11 - 1 == 10 ==> currentShowPageNo
		if (pageNo != 1) {
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=1'>[맨처음]</a>&nbsp;";
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a>&nbsp;";
		}

		while (!(loop > blockSize || pageNo > totalPage)) {

			if (pageNo == Integer.parseInt(currentShowPageNo)) {
				pageBar += "&nbsp;<span style='border:solid 1px #7B5232; font-weight: bold; color:#7B5232; padding: 2px 4px;'>" + pageNo
						+ "</span>&nbsp;";
			} else {
				pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + pageNo + "'>" + pageNo + "</a>&nbsp;";
			}

			loop++;
			pageNo++;
		} // end of while---------------------------------

		// **** [다음][마지막] 만들기 **** //
		// pageNo ==> 11
		if (!(pageNo > totalPage)) {
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + pageNo + "'>[다음]</a>&nbsp;";
			pageBar += "&nbsp;<a href='notice.neige?currentShowPageNo=" + totalPage + "'>[마지막]</a>&nbsp;";
		}

		request.setAttribute("pageBar", pageBar);

		super.setViewPage("/WEB-INF/notice/notice.jsp");

	}

}
>>>>>>> 642543c879f749d082a1e969d8b35798f725c4b9
