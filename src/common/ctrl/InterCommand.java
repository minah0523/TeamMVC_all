package common.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InterCommand {
	
	// 데이터베이스에서 select 하는 기능을 하는 메소드
	void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
