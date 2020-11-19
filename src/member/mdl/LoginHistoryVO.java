package member.mdl;

public class LoginHistoryVO {

	private String fk_userid;  // 사용자 ID
	private String logindate;  // 로그인한 날짜 및 시간
	private String clientip;   // 로그인한 클라이언트 IP주소
	
	public String getFk_userid() {
		return fk_userid;
	}
	
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	
	public String getLogindate() {
		return logindate;
	}
	
	public void setLogindate(String logindate) {
		this.logindate = logindate;
	}
	
	public String getClientip() {
		return clientip;
	}
	
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	
}
