package edu.fjut.fundsys.domain;

public class AdminUser extends ValueObject {
	private String userNo;
	private String userPwd;
	private String userIsSuperAdmin;

	public String getUserIsSuperAdmin() {
		return userIsSuperAdmin;
	}

	public void setUserIsSuperAdmin(String userIsSuperAdmin) {
		this.userIsSuperAdmin = userIsSuperAdmin;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

}
