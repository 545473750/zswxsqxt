package com.opendata.component.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("rawtypes")

@Namespace("/comp")
@Results({
	
	@Result(name="demo", location="/WEB-INF/pages/component/demo.jsp", type="dispatcher"),
	@Result(name="choiceUserPage", location="/WEB-INF/pages/component/choiceUserPage.jsp", type="dispatcher"),
	@Result(name="choiceOrganizationPage", location="/WEB-INF/pages/component/choiceOrganization.jsp", type="dispatcher"),
@Result(name="list",location="/WEB-INF/pages/sys/Dictitem/list.jsp", type = "dispatcher"),
@Result(name="query",location="/WEB-INF/pages/sys/Dictitem/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/sys/Dictitem/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/sys/Dictitem/edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/sys/Dictitem/show.jsp", type = "dispatcher"),
@Result(name="listAction",location="..//sys/Dictitem!list.do", type = "redirectAction")
})
public class ComponentAction extends BaseStruts2Action implements Preparable, ModelDriven {

	private static final long serialVersionUID = 1L;
	
	private UserManager userManager;
	
	/* 是否带排序功能 */
	private String withSortNumber;
	/* 已经选中的数据Ids */
	private String selectedIds;
	private String selectedNames;
	private String selectedUserHTML;
	
	// 选择用户
	public String choiceUserPage() {
		// 根据用户传递的selectedIds,组装selectedNames,html
		StringBuffer sbName = new StringBuffer();
		StringBuffer sbHTML = new StringBuffer();
		if(this.selectedIds != null && !this.selectedIds.equals("")) {
			String[] ids = this.selectedIds.split(",");
			for(String id : ids) {
				User user = this.userManager.getById(id);
				sbName.append(user.getUsername() + ",");
				sbHTML.append("<span id='" + id + "'>" + user.getUsername() + "&nbsp;<a onclick=\"delItem('" + id + "', '" + user.getUsername() + "')\" href='#'>X</a>&nbsp;&nbsp;</span>");
			}
		}

		String name = sbName.toString();
		if(name != null && !name.equals("")) {
			this.selectedNames = name.substring(0, name.length() - 1);
		}
		
		this.selectedUserHTML = sbHTML.toString();
		return "choiceUserPage";
	}
	
	// 选择组织机构
	public String choiceOrganizationPage() {
		return "choiceOrganizationPage";
	}
	
	
	public String getWithSortNumber() {
		return withSortNumber;
	}
	public void setWithSortNumber(String withSortNumber) {
		this.withSortNumber = withSortNumber;
	}
	public String getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	public String getSelectedNames() {
		return selectedNames;
	}
	public void setSelectedNames(String selectedNames) {
		this.selectedNames = selectedNames;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public String getSelectedUserHTML() {
		return selectedUserHTML;
	}
	public void setSelectedUserHTML(String selectedUserHTML) {
		this.selectedUserHTML = selectedUserHTML;
	}

	
	public String demo() {
		return "demo";
	}
	
	public Object getModel() {
		return null;
	}
	
	public void prepare() throws Exception {
	}
}
