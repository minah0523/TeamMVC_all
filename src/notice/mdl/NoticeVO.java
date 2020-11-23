package notice.mdl;

public class NoticeVO {

	private int noticeno;
	private String title;
	private String writeday;
	private String contents;

	
	public NoticeVO(){ }			// 기본 생성자
	
	public NoticeVO(int noticeno, String title, String writeday, String contents) {
		this.noticeno = noticeno;
		this.title = title;
		this.writeday = writeday;
		this.contents = contents;
	}
	
	
	public NoticeVO(int noticeno, String title, String contents) {
		this.noticeno = noticeno;
		this.title = title;
		this.contents = contents;
	}

	public int getNoticeno() {
		return noticeno;
	}

	public void setNoticeno(int noticeno) {
		this.noticeno = noticeno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriteday() {
		return writeday;
	}

	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	
	
	
}
