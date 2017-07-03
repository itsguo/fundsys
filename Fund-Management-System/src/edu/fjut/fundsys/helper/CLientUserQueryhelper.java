package edu.fjut.fundsys.helper;

import edu.fjut.fundsys.domain.ValueObject;

public class CLientUserQueryhelper extends ValueObject {
	private String transId;
	private String transType;
	private String targetId;
	private String isClientUser;

	public CLientUserQueryhelper(String transId, String transType,
			String targetId, String isClientUser) {
		super();
		this.transId = transId;
		this.transType = transType;
		this.targetId = targetId;
		this.isClientUser = isClientUser;
	}

	public String getIsClientUser() {
		return isClientUser;
	}

	public void setIsClientUser(String isClientUser) {
		this.isClientUser = isClientUser;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

}
