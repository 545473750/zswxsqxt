package com.zswxsqxt.core.model;

import java.util.Date;
import java.util.List;

import com.opendata.organiz.model.User;
import com.zswxsqxt.core.bo.InstanceNodeBo;


/**
 * 客户信息
 */
public class Subscrib implements java.io.Serializable {

	private static final long serialVersionUID = 693466785001951459L;
	public static final String TABLE_ALIAS = "客户信息";
	
	private String id;
	
	/**
	 * 客户代码
	 */
	private String cucustnr;
	
	/**
	 * 姓名
	 */
	private String addrcareOfName;
	
	/**
	 * 身份证号码
	 */
	private String curefnumber;
	
	/**
	 * 客户类型
	 */
	private String cutype;
	
	/**
	 * 客户状态
	 */
	private String custatus;
	
	/**
	 * 客户星级
	 */
	private String culanguage;
	
	/**
	 * 生日
	 */
	private String cubirthdate;
	
	private String cufiscalcodeType;
	
	private String cutype2;
	
	private String cuunused3u1;
	
	/**
	 * 类型
	 * D: 经销商
	 * M:
	 * T:
	 * P:
	 */
	private String cuunused4u7;
	
	/**
	 * 地址
	 */
	private String addrstreet;
	
	/**
	 * 邮编
	 */
	private String addrPostcode;
	
	/**
	 * 国家代码
	 */
	private String addrCountry;
	
	private String addrsmallcity;
	
	/**
	 * 事件号
	 */
	private String addreVentnr;
	
	private String addrbigcity;
	
	/**
	 * 电话号码
	 */
	private String addrphoneh;
	
	/**
	 * 电话号码
	 */
	private String addrphonew;
	
	/**
	 * 传真号码
	 */
	private String addrfax1;
	
	/**
	 * 传真号码
	 */
	private String addrfax2;
	
	/**
	 * 省代号
	 */
	private String addrProvinceNumber;
	
	public String getCucustnr() {
		return cucustnr;
	}
	public void setCucustnr(String cucustnr) {
		this.cucustnr = cucustnr;
	}
	public String getAddrcareOfName() {
		return addrcareOfName;
	}
	public void setAddrcareOfName(String addrcareOfName) {
		this.addrcareOfName = addrcareOfName;
	}
	public String getCurefnumber() {
		return curefnumber;
	}
	public void setCurefnumber(String curefnumber) {
		this.curefnumber = curefnumber;
	}
	public String getCutype() {
		return cutype;
	}
	public void setCutype(String cutype) {
		this.cutype = cutype;
	}
	public String getCustatus() {
		return custatus;
	}
	public void setCustatus(String custatus) {
		this.custatus = custatus;
	}
	public String getCulanguage() {
		return culanguage;
	}
	public void setCulanguage(String culanguage) {
		this.culanguage = culanguage;
	}
	public String getCubirthdate() {
		return cubirthdate;
	}
	public void setCubirthdate(String cubirthdate) {
		this.cubirthdate = cubirthdate;
	}
	public String getCufiscalcodeType() {
		return cufiscalcodeType;
	}
	public void setCufiscalcodeType(String cufiscalcodeType) {
		this.cufiscalcodeType = cufiscalcodeType;
	}
	public String getCutype2() {
		return cutype2;
	}
	public void setCutype2(String cutype2) {
		this.cutype2 = cutype2;
	}
	public String getCuunused3u1() {
		return cuunused3u1;
	}
	public void setCuunused3u1(String cuunused3u1) {
		this.cuunused3u1 = cuunused3u1;
	}
	public String getCuunused4u7() {
		return cuunused4u7;
	}
	public void setCuunused4u7(String cuunused4u7) {
		this.cuunused4u7 = cuunused4u7;
	}
	public String getAddrstreet() {
		return addrstreet;
	}
	public void setAddrstreet(String addrstreet) {
		this.addrstreet = addrstreet;
	}
	public String getAddrPostcode() {
		return addrPostcode;
	}
	public void setAddrPostcode(String addrPostcode) {
		this.addrPostcode = addrPostcode;
	}
	public String getAddrCountry() {
		return addrCountry;
	}
	public void setAddrCountry(String addrCountry) {
		this.addrCountry = addrCountry;
	}
	public String getAddrsmallcity() {
		return addrsmallcity;
	}
	public void setAddrsmallcity(String addrsmallcity) {
		this.addrsmallcity = addrsmallcity;
	}
	public String getAddreVentnr() {
		return addreVentnr;
	}
	public void setAddreVentnr(String addreVentnr) {
		this.addreVentnr = addreVentnr;
	}
	public String getAddrbigcity() {
		return addrbigcity;
	}
	public void setAddrbigcity(String addrbigcity) {
		this.addrbigcity = addrbigcity;
	}
	public String getAddrphoneh() {
		return addrphoneh;
	}
	public void setAddrphoneh(String addrphoneh) {
		this.addrphoneh = addrphoneh;
	}
	public String getAddrphonew() {
		return addrphonew;
	}
	public void setAddrphonew(String addrphonew) {
		this.addrphonew = addrphonew;
	}
	public String getAddrfax1() {
		return addrfax1;
	}
	public void setAddrfax1(String addrfax1) {
		this.addrfax1 = addrfax1;
	}
	public String getAddrfax2() {
		return addrfax2;
	}
	public void setAddrfax2(String addrfax2) {
		this.addrfax2 = addrfax2;
	}
	public String getAddrProvinceNumber() {
		return addrProvinceNumber;
	}
	public void setAddrProvinceNumber(String addrProvinceNumber) {
		this.addrProvinceNumber = addrProvinceNumber;
	}
	public String getAddrProvinceuserke() {
		return addrProvinceuserke;
	}
	public void setAddrProvinceuserke(String addrProvinceuserke) {
		this.addrProvinceuserke = addrProvinceuserke;
	}
	/**
	 * 省名称
	 */
	private String addrProvinceuserke;
	
	/**
	 * 创建时间
	 */
	private Date ts;
	
	/**
	 * 创建人
	 */
	private User creationUser;
	
	/**
	 * 最后修改时间
	 */
	private Date changeTime;
	
	/**
	 * 最后修改人
	 */
	private User changeUser;
	
	/**
	 * 收视频道（列表）
	 */
	private List<Channel> channels;
	
	/**
	 * 审核节点
	 */
	private List<InstanceNodeBo> nodes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Channel> getChannels() {
		return channels;
	}
	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public User getChangeUser() {
		return changeUser;
	}
	public void setChangeUser(User changeUser) {
		this.changeUser = changeUser;
	}
	public User getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(User creationUser) {
		this.creationUser = creationUser;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public List<InstanceNodeBo> getNodes() {
		return nodes;
	}
	public void setNodes(List<InstanceNodeBo> nodes) {
		this.nodes = nodes;
	}
}