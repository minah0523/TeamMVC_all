package myshop.mdl;

public class CategoryVO {
   
   // 카테고리 VO
   
   private int cgno;      // 카테고리대분류 코드
   
   private String cgcode; // 카테고리 코드
   
   private String cgname; // 카테고리 이름
   
   public CategoryVO() {
      // TODO Auto-generated constructor stub
   }

   public CategoryVO(int cgno, String cgcode, String cgname) {
      super();
      this.cgno = cgno;
      this.cgcode = cgcode;
      this.cgname = cgname;
   }

   public int getCgno() {
      return cgno;
   }

   public void setCgno(int cgno) {
      this.cgno = cgno;
   }

   public String getCgcode() {
      return cgcode;
   }

   public void setCgcode(String cgcode) {
      this.cgcode = cgcode;
   }

   public String getCgname() {
      return cgname;
   }

   public void setCgname(String cgname) {
      this.cgname = cgname;
   }
}