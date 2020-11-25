package myshop.mdl;

public class ProductInfoVO {
	
	private int pinfono;
	
	private int pdno_fk;
	
	private String pcolor;
	
	private String psize;

	public ProductInfoVO() { }
	
	public ProductInfoVO(int pinfono, int pdno_fk, String pcolor, String psize) {
		
		this.pinfono = pinfono;
		this.pdno_fk = pdno_fk;
		this.pcolor = pcolor;
		this.psize = psize;
	}

	public int getPinfono() {
		return pinfono;
	}

	public void setPinfono(int pinfono) {
		this.pinfono = pinfono;
	}

	public int getPdno_fk() {
		return pdno_fk;
	}

	public void setPdno_fk(int pdno_fk) {
		this.pdno_fk = pdno_fk;
	}

	public String getPcolor() {
		return pcolor;
	}

	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}

	public String getPsize() {
		return psize;
	}

	public void setPsize(String psize) {
		this.psize = psize;
	}
	
}
