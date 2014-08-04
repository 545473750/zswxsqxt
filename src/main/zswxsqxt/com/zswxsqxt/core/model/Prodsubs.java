package com.zswxsqxt.core.model;

/**
 * 产品通断情况表
 */
public class Prodsubs {
	/**
	 * 主键
	 */
	private String psnr;
	
	/**
	 * 产品序号
	 */
	private String psproductnr;
	
	/**
	 * 客户序号
	 */
	private String pssubscribernr;
	
	/**
	 * 财务项目FINOPTNS表描述
	 * S:starte
	 * F:finish
	 */
	private String psfinanceoption;
	
	/**
	 * 代理商代码Subscrib表的cucustnr字段
	 */
	private String pscommissiondealer;
	
	
	private String pssubswarranty;
	
	/**
	 *  W:
	 *	A:active
	 *	D:disconnected
	 *	C
	 *	N
	 */
	private String psstatus;
	
	private String decscnr;
	
	/**
	 * 账户
	 */
	private String psaccnr;
	
	private String pscontractnr;
	
	private String psstartdate;
	
	private String pscontractenddate;

	public String getPsnr() {
		return psnr;
	}

	public void setPsnr(String psnr) {
		this.psnr = psnr;
	}

	public String getPsproductnr() {
		return psproductnr;
	}

	public void setPsproductnr(String psproductnr) {
		this.psproductnr = psproductnr;
	}

	public String getPssubscribernr() {
		return pssubscribernr;
	}

	public void setPssubscribernr(String pssubscribernr) {
		this.pssubscribernr = pssubscribernr;
	}

	public String getPsfinanceoption() {
		return psfinanceoption;
	}

	public void setPsfinanceoption(String psfinanceoption) {
		this.psfinanceoption = psfinanceoption;
	}

	public String getPscommissiondealer() {
		return pscommissiondealer;
	}

	public void setPscommissiondealer(String pscommissiondealer) {
		this.pscommissiondealer = pscommissiondealer;
	}

	public String getPssubswarranty() {
		return pssubswarranty;
	}

	public void setPssubswarranty(String pssubswarranty) {
		this.pssubswarranty = pssubswarranty;
	}

	public String getPsstatus() {
		return psstatus;
	}

	public void setPsstatus(String psstatus) {
		this.psstatus = psstatus;
	}

	public String getDecscnr() {
		return decscnr;
	}

	public void setDecscnr(String decscnr) {
		this.decscnr = decscnr;
	}

	public String getPsaccnr() {
		return psaccnr;
	}

	public void setPsaccnr(String psaccnr) {
		this.psaccnr = psaccnr;
	}

	public String getPscontractnr() {
		return pscontractnr;
	}

	public void setPscontractnr(String pscontractnr) {
		this.pscontractnr = pscontractnr;
	}

	public String getPsstartdate() {
		return psstartdate;
	}

	public void setPsstartdate(String psstartdate) {
		this.psstartdate = psstartdate;
	}

	public String getPscontractenddate() {
		return pscontractenddate;
	}

	public void setPscontractenddate(String pscontractenddate) {
		this.pscontractenddate = pscontractenddate;
	}

}
