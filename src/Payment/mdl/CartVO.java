package Payment.mdl;

public class CartVO {
	
	private String cartno;
	public String getCartno() {
		return cartno;
	}
	public void setCartno(String cartno) {
		this.cartno = cartno;
	}
	public String getUserid_fk() {
		return userid_fk;
	}
	public void setUserid_fk(String userid_fk) {
		this.userid_fk = userid_fk;
	}
	public String getRegisterday() {
		return registerday;
	}
	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}
	public int getPinfono() {
		return pinfono;
	}
	public void setPinfono(int pinfono) {
		this.pinfono = pinfono;
	}
	public int getPqty() {
		return pqty;
	}
	public void setPqty(int pqty) {
		this.pqty = pqty;
	}
	private String userid_fk;
	private String registerday;
	private int pinfono;
	private int pqty;

	
}
