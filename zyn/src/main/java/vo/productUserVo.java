package vo;

import java.math.BigDecimal;
import java.util.Date;

public class productUserVo {	
		//商品属性
	 	private Integer id;
	    private Integer userId;
	    private Integer categoryId;
	    private String name;
	    private String subtitle;
	    private String mainImage;
	    private String subImage;
	    private String detail;
	    private BigDecimal price;
	    private Integer stock;
	    private Integer status;
	    private Date createTime;
	    private Date updateTime;
	    //用户属性
	    private String username;
	    private String email;
	    private String phone;
	    private String activecode;
	    private Integer state;
	    private Date UcreateTime;
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public Integer getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}

		public String getMainImage() {
			return mainImage;
		}

		public void setMainImage(String mainImage) {
			this.mainImage = mainImage;
		}

		public String getSubImage() {
			return subImage;
		}

		public void setSubImage(String subImage) {
			this.subImage = subImage;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getStock() {
			return stock;
		}

		public void setStock(Integer stock) {
			this.stock = stock;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getActivecode() {
			return activecode;
		}

		public void setActivecode(String activecode) {
			this.activecode = activecode;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		public Date getUcreateTime() {
			return UcreateTime;
		}

		public void setUcreateTime(Date ucreateTime) {
			UcreateTime = ucreateTime;
		}

	    

}
