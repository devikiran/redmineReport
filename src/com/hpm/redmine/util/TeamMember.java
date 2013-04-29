package com.hpm.redmine.util;

public class TeamMember {
	private String ProjectName;
	private String TeamMember;
	private String ManagerName;
	private String PO;// Purchase Order
	private String ProjectCode;
	private float TaskCode;
	private String CostCenter;
	private String IssueIds;
	public TeamMember()
	{
		
	}
	public TeamMember(String ProjectName,String TeamMember,String ManagerName,String PO,String ProjectCode,float TaskCode,String CostCenter)
	{
		this.ProjectName=ProjectName;
		this.CostCenter=CostCenter;
		this.PO=PO;
		this.ManagerName=ManagerName;
		this.TaskCode=TaskCode;
		this.ProjectCode=ProjectCode;
		this.TeamMember=TeamMember;
		
	}
	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}

	public String getTeamMember() {
		return TeamMember;
	}

	public void setTeamMember(String teamMember) {
		TeamMember = teamMember;
	}

	public String getManagerName() {
		return ManagerName;
	}

	public void setManagerName(String managerName) {
		ManagerName = managerName;
	}

	public String getPO() {
		return PO;
	}

	public void setPO(String pO) {
		PO = pO;
	}

	public String getProjectCode() {
		return ProjectCode;
	}

	public void setProjectCode(String projectCode) {
		ProjectCode = projectCode;
	}

	public float getTaskCode() {
		return TaskCode;
	}

	public void setTaskCode(float taskCode) {
		TaskCode = taskCode;
	}

	public String getCostCenter() {
		return CostCenter;
	}

	public void setCostCenter(String costCenter) {
		CostCenter = costCenter;
	}

	public String getIssueIds() {
		return IssueIds;
	}

	public void setIssueIds(String issueIds) {
		IssueIds = issueIds;
	}

}
