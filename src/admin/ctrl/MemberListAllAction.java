package admin.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
import javax.servlet.http.*;

import common.ctrl.AbstractController;
import member.mdl.*;
 
import my.util.MyUtil;

public class MemberListAllAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// == 관리자(admin)로 로그인 했을 때만 조회가 가능하도록 한다. == //
				HttpSession session = request.getSession();
				
				MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
				//로그인 유저의 정보를 받아와서 MemberVO 타입의 loginuser 변수에 넣어주도록 한다 
				if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {
					// 로그인 유저가 null 이 아니고, 관리자(admin)계정으로 로그인 했을 경우
					
					InterMemberDAO mdao = new MemberDAO();
					
					String searchType = request.getParameter("searchType");
					String searchWord = request.getParameter("searchWord");
					//검색 타입과 검색한글자를 받아와서 변수에 지정한다.
					
					Map<String,String> paraMap = new HashMap<>();
					paraMap.put("searchType", searchType);
					paraMap.put("searchWord", searchWord);
					//맵에 검색타입 / 검색한 글자를 쌓아준다.
					
					// *** 페이징 처리를 한 모든 회원 또는 검색한 회원 목록 보여주기 *** //
					String currentShowPageNo = request.getParameter("currentShowPageNo");
					// currentShowPageNo 은 관리자가 클릭한 페이지 바의 번호.					
					String sizePerPage = request.getParameter("sizePerPage");
					// 한 페이지당 화면상에 보여줄 회원의 개수
					
					if( currentShowPageNo == null ) {
						currentShowPageNo = "1";
						// 메뉴에서 회원목록만 클릭했을 경우, currentShowPageNo 은 null 이 된다.
						// currentShowPageNo 이 null 이라면 currentShowPageNo 을 1 페이지로 바꾼다.
					}
					
					if( sizePerPage == null || 
						!( "3".equals(sizePerPage) || "5".equals(sizePerPage) || "10".equals(sizePerPage) ) ) { 
						//몇명씩 볼지 선택한게 3도 5도 10도 아니라면 
						sizePerPage = "10"; //10으로 지정!! 
						// 메뉴에서 회원목록 만을 클릭했을 경우에는 sizePerPage 는 null 이 된다.
						// sizePerPage 가 null 이라면 sizePerPage 를 10 으로 바꾸어야 한다.>> 선택하지 않았을때의 기본 선택지는 10이다.
					}
					
					
					
					// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자 아닌 문자를 입력한 경우 또는 
					//     int 범위를 초과한 숫자를 입력한 경우라면 currentShowPageNo 는 1 페이지로 만들도록 한다. ==== // 
					try {
						Integer.parseInt(currentShowPageNo);
					} catch(NumberFormatException e) {
						currentShowPageNo = "1";
					}
					
					
					paraMap.put("currentShowPageNo", currentShowPageNo);
					paraMap.put("sizePerPage", sizePerPage);
					//paraMap에 페이지당 몇 명을 볼 지와 페이지바의 번호를 선택한 값을 넣고 
					List<MemberVO> memberList = mdao.selectPagingMember(paraMap);
					//그걸 List에 넣는다. 
					
					//request에 set함. 
					request.setAttribute("memberList", memberList);
					request.setAttribute("searchType", searchType);
					request.setAttribute("searchWord", searchWord);
					request.setAttribute("sizePerPage", sizePerPage);
					
					// **** ========= 페이지바 만들기 ========= **** //
					/*
					    1개 블럭당 10개씩 잘라서 페이지 만든다.
					    1개 페이지당 3개행 또는 5개행 또는  10개행을 보여주는데
					        만약에 1개 페이지당 5개행을 보여준다라면 
					        총 몇개 블럭이 나와야 할까? 
					        총 회원수가 207명 이고, 1개 페이지당 보여줄 회원수가 5 이라면
					    207/5 = 41.4 ==> 42(totalPage)        
					        
					    1블럭               1 2 3 4 5 6 7 8 9 10 [다음]
					    2블럭   [이전] 11 12 13 14 15 16 17 18 19 20 [다음]
					  				....
					    5블럭   [이전] 41 42 
					 */
					
					// 페이징처리를 위해서 전체회원에 대한 총페이지 개수 알아오기(select)  
					int totalPage = mdao.getTotalPage(paraMap);
					
				//	System.out.println("~~~ 확인용 totalPage => " + totalPage);
					
					// ==== !!! 공식 !!! ==== // 
				/*
				    1  2  3  4  5  6  7  8  9  10  -- 첫번째 블럭의 페이지번호 시작값(pageNo)은  1 이다.
				    11 12 13 14 15 16 17 18 19 20  -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다.	
				    21 22 23 24 25 26 27 28 29 30  -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
				    
				    currentShowPageNo      pageNo
				    --------------------------------------------------------------------------------------
				      
				 */
					
					String pageBar = "";
					
					int blockSize = 10;
					// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
					
					int loop = 1;
					// loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다. 
					
					int pageNo = 0;
					// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
					
					// !!!! 다음은 pageNo 를 구하는 공식이다. !!!! // 
					pageNo = ( ( Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
								
					if( searchType == null ) {
						searchType = "";
					}
					
					if( searchWord == null ) {
						searchWord = "";
					}
					
					
					// **** [맨처음][이전] 만들기 **** //
					// pageNo-1 == 11 - 1 == 10 ==> currentShowPageNo
					if( pageNo != 1 ) {
						pageBar += "&nbsp;<a href='memberListAll.neige?currentShowPageNo=1&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[맨처음]</a>&nbsp;";
						pageBar += "&nbsp;<a href='memberListAll.neige?currentShowPageNo="+(pageNo-1)+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[이전]</a>&nbsp;";
					}
					
					while( !(loop > blockSize || pageNo > totalPage ) ) {
						
						if( pageNo == Integer.parseInt(currentShowPageNo) ) {
							pageBar += "&nbsp;<span style='border:solid 1px gray; color:orange; padding: 2px 4px;'>"+pageNo+"</span>&nbsp;";
						}
						else {
							pageBar += "&nbsp;<a href='memberListAll.neige?currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>"+pageNo+"</a>&nbsp;"; 
						}
						
						loop++;   // 1 2 3 4 5 6 7 8 9 10 
						          
						pageNo++; //  1  2  3  4  5  6  7  8  9 10 
						          // 11 12 13 14 15 16 17 18 19 20 
						          // 21 
					}// end of while---------------------------------
					
					
					// **** [다음][마지막] 만들기 **** //
					// pageNo ==> 11
					if( !( pageNo > totalPage ) ) {
						pageBar += "&nbsp;<a href='memberListAll.neige?currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[다음]</a>&nbsp;";
						pageBar += "&nbsp;<a href='memberListAll.neige?currentShowPageNo="+totalPage+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[마지막]</a>&nbsp;";
					}
					
					
					request.setAttribute("pageBar", pageBar);
					

					// *** 현재 페이지를 돌아갈 페이지(goBackURL)로 주소 지정하기 *** // 
					String currentURL = MyUtil.getCurrentURL(request);
					// 회원조회를 했을시 현재 그 페이지로 그대로 되돌아가길 위한 용도로 쓰임.
					
				//	System.out.println("currentURL => " + currentURL);
				//  currentURL => member/memberList.up?currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=%ED%99%8D%EC%8A%B9%EC%9D%98
					
					currentURL = currentURL.replaceAll("&", " ");
				//	System.out.println("currentURL => " + currentURL); 
				//  currentURL => member/memberList.up?currentShowPageNo=15 sizePerPage=5 searchType=name searchWord=%ED%99%8D%EC%8A%B9%EC%9D%98	
					
					request.setAttribute("goBackURL", currentURL);
					
				//	super.setRedirect(false);
					super.setViewPage("/WEB-INF/member/memberListAll.jsp");
				}
				else {
					// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
					String message = "관리자만 접근이 가능합니다.";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
				//	super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}

	}
 
}
