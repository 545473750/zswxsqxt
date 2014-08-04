package com.zswxsqxt.core.model;

/**
 * 客户类型
 */
public class Substype {
	
	/**
	 * 类型代码
	 */
	private String subscriberType;
	
	/**
	 * 类型名称
	 */
	private String subsTypename;
	
	private String useincontrcapture;
	
	private String substypeactiveyn;
	private String forcemuddetails;
	private String longdescription;
	private String minmudunits;
	
	public String getSubscriberType() {
		return subscriberType;
	}
	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}
	public String getSubsTypename() {
		return subsTypename;
	}
	public void setSubsTypename(String subsTypename) {
		this.subsTypename = subsTypename;
	}
	public String getUseincontrcapture() {
		return useincontrcapture;
	}
	public void setUseincontrcapture(String useincontrcapture) {
		this.useincontrcapture = useincontrcapture;
	}
	public String getSubstypeactiveyn() {
		return substypeactiveyn;
	}
	public void setSubstypeactiveyn(String substypeactiveyn) {
		this.substypeactiveyn = substypeactiveyn;
	}
	public String getForcemuddetails() {
		return forcemuddetails;
	}
	public void setForcemuddetails(String forcemuddetails) {
		this.forcemuddetails = forcemuddetails;
	}
	public String getLongdescription() {
		return longdescription;
	}
	public void setLongdescription(String longdescription) {
		this.longdescription = longdescription;
	}
	public String getMinmudunits() {
		return minmudunits;
	}
	public void setMinmudunits(String minmudunits) {
		this.minmudunits = minmudunits;
	}
	public String getMindaysfactor() {
		return mindaysfactor;
	}
	public void setMindaysfactor(String mindaysfactor) {
		this.mindaysfactor = mindaysfactor;
	}
	public String getMinpricefactor() {
		return minpricefactor;
	}
	public void setMinpricefactor(String minpricefactor) {
		this.minpricefactor = minpricefactor;
	}
	private String mindaysfactor;
	private String minpricefactor;
}
