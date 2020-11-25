package myshop.mdl;

public class CartVO {
   
   private int cartno;
   private String userid_fk;
   private int pdno_fk;
   private int pqty;
   private String registerday;
   
   public CartVO() {}	// 기본 생성자
   
   public CartVO(int cartno, String userid_fk , int pdno_fk, int pqty, String registerday ) {
	   
	   	this.cartno = cartno;
	   	this.userid_fk = userid_fk;
	   	this.pdno_fk = pdno_fk;
	   	this.pqty = pqty;
	   	this.registerday = registerday;
	   
   }

public int getCartno() {
	return cartno;
}

public void setCartno(int cartno) {
	this.cartno = cartno;
}

public String getUserid_fk() {
	return userid_fk;
}

public void setUserid_fk(String userid_fk) {
	this.userid_fk = userid_fk;
}

public int getPdno_fk() {
	return pdno_fk;
}

public void setPdno_fk(int pdno_fk) {
	this.pdno_fk = pdno_fk;
}

public int getPqty() {
	return pqty;
}

public void setPqty(int pqty) {
	this.pqty = pqty;
}

public String getRegisterday() {
	return registerday;
}

public void setRegisterday(String registerday) {
	this.registerday = registerday;
}
   


   
}