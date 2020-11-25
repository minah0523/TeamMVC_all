package myshop.mdl;

public class ProductImageFileVO {
	
	private int imgfileno; 		 // 추가 이미지 고유 번호
	private int pdno_fk;   		 // 제품 번호 참조
	private String imgfilename;  // 이미지명
	
	public ProductImageFileVO() {	} // 기본 생성자

	public ProductImageFileVO(int imgfileno, int pdno_fk, String imgfilename) {
		super();
		this.imgfileno = imgfileno;
		this.pdno_fk = pdno_fk;
		this.imgfilename = imgfilename;
	}

	public int getImgfileno() {
		return imgfileno;
	}

	public void setImgfileno(int imgfileno) {
		this.imgfileno = imgfileno;
	}

	public int getPdno_fk() {
		return pdno_fk;
	}

	public void setPdno_fk(int pdno_fk) {
		this.pdno_fk = pdno_fk;
	}

	public String getImgfilename() {
		return imgfilename;
	}

	public void setImgfilename(String imgfilename) {
		this.imgfilename = imgfilename;
	}
	
	
	

}
