package edu.fjut.fundsys.domain;

import java.util.Date;

public class ClientUserTrans extends ValueObject {
	private Integer transId = 10001;
	private String transType;
	private Double transPrice;
	private String targetId;
	private Date createDate;
	private ClientUser clientUser;

	public Integer getTransId() {
		return transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Double getTransPrice() {
		return transPrice;
	}

	public void setTransPrice(Double transPrice) {
		this.transPrice = transPrice;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public ClientUserTrans() {
		super();
		this.createDate = new Date();
	}

	@Override
	public String toString() {
		return "ClientUserTrans [transId=" + transId + ", transType="
				+ transType + ", transPrice=" + transPrice + ", targetId="
				+ targetId + ", createDate=" + createDate + ", clientUser="
				+ clientUser + "]";
	}

}
