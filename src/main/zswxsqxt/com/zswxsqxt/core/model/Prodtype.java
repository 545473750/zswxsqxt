package com.zswxsqxt.core.model;

/**
 * 订购类型
 */
public class Prodtype {
	
	private String id;
	
	/**
	 * 处理类型
	 */
	private String productType;
	
	/**
	 * 详细描述
	 */
	private String reasonDescription;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}
}
