package com.zswxsqxt.core.model;

public class Channel implements java.io.Serializable{
	private static final long serialVersionUID = 693466785001951459L;
	public static final String TABLE_ALIAS = "频道信息";
	
	private String id;
	private String name;
	private Double price;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
