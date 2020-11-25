package myshop.mdl;

public class ImageVO {
	
	private int imgno;
	
	private String imgfilename;
	
	private String pdName;
	
	private String pdContent;
	
	// 기본 생성자 생략되어 있다.

	public int getImgno() {
		return imgno;
	}

	public void setImgno(int imgno) {
		this.imgno = imgno;
	}

	public String getImgfilename() {
		return imgfilename;
	}

	public void setImgfilename(String imgfilename) {
		this.imgfilename = imgfilename;
	}
	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	public String getPdContent() {
		return pdContent;
	}

	public void setPdContent(String pdContent) {
		this.pdContent = pdContent;
	}
	

}
