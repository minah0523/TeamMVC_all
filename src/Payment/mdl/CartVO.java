package Payment.mdl;

import myshop.mdl.ProductInfoVO;

public class CartVO {

	private String cartno;
	private String userid_fk;
	private String registerday;
	private int pinfono;
	private int pqty;

	ProductInfoVO pdinfovo;

	public CartVO(String cartno, String userid_fk, String registerday, int pinfono, int pqty, ProductInfoVO pdinfovo) {
		super();
		this.cartno = cartno;
		this.userid_fk = userid_fk;
		this.registerday = registerday;
		this.pinfono = pinfono;
		this.pqty = pqty;
		this.pdinfovo = pdinfovo;
	}

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

	
	public ProductInfoVO getPdinfovo() {
		return pdinfovo;
	}

	public void setPdinfovo(ProductInfoVO pdinfovo) {
		this.pdinfovo = pdinfovo;
	}

}