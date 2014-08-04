package com.zswxsqxt.core.bo;

public class InstanceNodeBo {

	private String nodeId;//id
	private String name;//名称
	private Integer orderNum;//节点顺序
	
	private Integer result;//1：通过，2：未通过
	private String opinion;//审核意见
	private String auditUserId;//审核人
	private String auditUserName;//审核人姓名
	
	private Integer auditValue;//审核权限
	private Integer auditorNum;//审核人数量
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public Integer getAuditValue() {
		return auditValue;
	}
	public void setAuditValue(Integer auditValue) {
		this.auditValue = auditValue;
	}
	public Integer getAuditorNum() {
		return auditorNum;
	}
	public void setAuditorNum(Integer auditorNum) {
		this.auditorNum = auditorNum;
	}
	
}
