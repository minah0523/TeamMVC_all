package my.util;

import javax.servlet.http.HttpServletRequest;

public class MyUtil {
	

	// *** ? 다음의 데이터까지 포함한 현재 URL 주소를 알려주는 메소드를 생성 *** //
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString();
		// [수업내용 참고용] http://localhost:9090/TeamMVC/member/memberList.up
		
		String queryString = request.getQueryString();
		// [수업내용 참고용] currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=홍승의 
		
		currentURL += "?" + queryString;
		// [수업내용 참고용] http://localhost:9090/TeamMVC/member/memberList.up?currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=홍승의 
		
		String ctxPath = request.getContextPath();
		//     /TeamMVC
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
		//   27        =           21                +        6
		
		currentURL = currentURL.substring(beginIndex + 1);
		//        [수업내용 참고용]  member/memberList.up?currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=홍승의 
		
		return currentURL;
	}

	
	// **** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드/secure code) 작성하기 **** //
    public static String secureCode(String str) {

       str = str.replaceAll("<", "&lt;");
       str = str.replaceAll(">", "&gt;");

       return str;
    }
}
