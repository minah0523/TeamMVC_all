package Payment.mdl;

public class OrderDetailVO {
	private int odrseqnum;     /* 주문상세일련번호(sequence) */
	private String fk_odrcode; /* 주문코드 */
	private int fk_pinfono;    /* 제품 상세번호 */
	private int oqty;		   /* 주문량 */
	private int odrprice;	   /* 주문가격 */
	private int deliverstatus; /* 배송상태 */
	private int deliverdate;   /* 배송완료일자 */
	
	   ///////////////////////////////////////////

	   private OrderVO ordervo; //주문객체
	   private ProductVO productvo;//제품객체
	   private MemberVO membervo;  // 회원객체

	   public OrderVO getOrdervo() {
	      return ordervo;
	   }
	   public void setOrdervo(OrderVO ordervo) {
	      this.ordervo = ordervo;
	   }
	   public ProductVO getProductvo() {
	      return productvo;
	   }
	   public void setProductvo(ProductVO productvo) {
	      this.productvo = productvo;
	   }
	   public MemberVO getMembervo() {
		   return membervo;
	   }
	   public void setMembervo(MemberVO membervo) {
		   this.membervo = membervo;
	   }
	///////////////////////////////////////////
	
	public int getOdrseqnum() {
		return odrseqnum;
	}
	public void setOdrseqnum(int odrseqnum) {
		this.odrseqnum = odrseqnum;
	}
	public String getFk_odrcode() {
		return fk_odrcode;
	}
	public void setFk_odrcode(String fk_odrcode) {
		this.fk_odrcode = fk_odrcode;
	}
	public int getFk_pinfono() {
		return fk_pinfono;
	}
	public void setFk_pinfono(int fk_pinfono) {
		this.fk_pinfono = fk_pinfono;
	}
	public int getOqty() {
		return oqty;
	}
	public void setOqty(int oqty) {
		this.oqty = oqty;
	}
	public int getOdrprice() {
		return odrprice;
	}
	public void setOdrprice(int odrprice) {
		this.odrprice = odrprice;
	}
	public int getDeliverstatus() {
		return deliverstatus;
	}
	public void setDeliverstatus(int deliverstatus) {
		this.deliverstatus = deliverstatus;
	}
	public int getDeliverdate() {
		return deliverdate;
	}
	public void setDeliverdate(int deliverdate) {
		this.deliverdate = deliverdate;
	}
	
}
