package Payment.mdl;

public class OrderVO {
	
	private String odrcode;
	private String userid_fk;
	private String odrtotalprice;
	private String odrtotalpoint;
	private String odrdate;
	
	public String getOdrcode() {
		return odrcode;
	}
	public void setOdrcode(String odrcode) {
		this.odrcode = odrcode;
	}
	public String getUserid_fk() {
		return userid_fk;
	}
	public void setUserid_fk(String userid_fk) {
		this.userid_fk = userid_fk;
	}
	public String getOdrtotalprice() {
		return odrtotalprice;
	}
	public void setOdrtotalprice(String odrtotalprice) {
		this.odrtotalprice = odrtotalprice;
	}
	public String getOdrtotalpoint() {
		return odrtotalpoint;
	}
	public void setOdrtotalpoint(String odrtotalpoint) {
		this.odrtotalpoint = odrtotalpoint;
	}
	public String getOdrdate() {
		return odrdate;
	}
	public void setOdrdate(String odrdate) {
		this.odrdate = odrdate;
	}

}
