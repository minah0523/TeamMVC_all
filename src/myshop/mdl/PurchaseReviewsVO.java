package myshop.mdl;

import member.mdl.*;

public class PurchaseReviewsVO {

   private int review_seq; 
   private String fk_userid;
   private int fk_pdno; 
   private String contents; 
   private String writeDate;
   private String starpoint;
   
   private MemberVO mvo;
   private ProductVO pdvo;
   
   public PurchaseReviewsVO() { }

   public PurchaseReviewsVO(int review_seq, String fk_userid, int fk_pdno, String contents, String writeDate, String starpoint,
         MemberVO mvo, ProductVO pdvo) {
      this.review_seq = review_seq;
      this.fk_userid = fk_userid;
      this.fk_pdno = fk_pdno;
      this.contents = contents;
      this.writeDate = writeDate;
      this.starpoint = starpoint;
      this.mvo = mvo;
      this.pdvo = pdvo;
   }

   public int getReview_seq() {
      return review_seq;
   }

   public void setReview_seq(int review_seq) {
      this.review_seq = review_seq;
   }

   public String getFk_userid() {
      return fk_userid;
   }

   public void setFk_userid(String fk_userid) {
      this.fk_userid = fk_userid;
   }

   public int getFk_pdno() {
      return fk_pdno;
   }

   public void setFk_pdno(int fk_pdno) {
      this.fk_pdno = fk_pdno;
   }

   public String getContents() {
      return contents;
   }

   public void setContents(String contents) {
      this.contents = contents;
   }

   public String getWriteDate() {
      return writeDate;
   }

   public void setWriteDate(String writeDate) {
      this.writeDate = writeDate;
   }

   public String getStarpoint() {
      return starpoint;
   }

   public void setStarpoint(String starpoint) {
      this.starpoint = starpoint;
   }

   public MemberVO getMvo() {
      return mvo;
   }

   public void setMvo(MemberVO mvo) {
      this.mvo = mvo;
   }

   public ProductVO getPdvo() {
      return pdvo;
   }

   public void setPdvo(ProductVO pdvo) {
      this.pdvo = pdvo;
   }
   
   
      
}