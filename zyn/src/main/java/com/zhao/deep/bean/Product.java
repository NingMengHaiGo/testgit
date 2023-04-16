package com.zhao.deep.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id;
    
    private Integer userId;

    private String userName;
    
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
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
    
    public Product(){}

	public Product(Integer id, Integer userId, String userName, Integer categoryId, String name, String subtitle,
			String mainImage, String subImage, String detail, BigDecimal price, Integer stock, Integer status,
			Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.categoryId = categoryId;
		this.name = name;
		this.subtitle = subtitle;
		this.mainImage = mainImage;
		this.subImage = subImage;
		this.detail = detail;
		this.price = price;
		this.stock = stock;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", userId=" + userId + ", userName=" + userName + ", categoryId=" + categoryId
				+ ", name=" + name + ", subtitle=" + subtitle + ", mainImage=" + mainImage + ", subImage=" + subImage
				+ ", detail=" + detail + ", price=" + price + ", stock=" + stock + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

    
    
    
}