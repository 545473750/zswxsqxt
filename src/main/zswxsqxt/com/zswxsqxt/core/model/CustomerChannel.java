package com.zswxsqxt.core.model;



/**
 * 客户频道
 */
public class CustomerChannel implements java.io.Serializable {

	private static final long serialVersionUID = 693466785001951459L;
	public static final String TABLE_ALIAS = "客户频道";
	
	private String id;
	
	/**
	 * 客户ID
	 */
	private String customerId;
	
	/**
	 * 频道ID
	 */
	private String channelId;
	
	/**
	 * 客户信息
	 */
	private Subscrib customerInfo;
	
	/**
	 * 频道
	 */
	private Channel channel;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Subscrib getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(Subscrib customerInfo) {
		this.customerInfo = customerInfo;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}