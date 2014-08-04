package com.zswxsqxt.wf.query;

import java.util.Date;

import com.zswxsqxt.wf.model.WfProject;
import com.opendata.common.base.BaseQuery;
import com.opendata.organiz.model.Organization;
/*
describe:单位流程表查询

*/
public class WfProjectOrgQuery extends BaseQuery {
	private String id;//id
	private String orgId;//单位id
	private String wfProjectId;//流程Id
	private Date ts;//创建时间
	private WfProject wfProject;
	private Organization org;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getWfProjectId() {
		return wfProjectId;
	}
	public void setWfProjectId(String wfProjectId) {
		this.wfProjectId = wfProjectId;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public WfProject getWfProject() {
		return wfProject;
	}
	public void setWfProject(WfProject wfProject) {
		this.wfProject = wfProject;
	}
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	
}
