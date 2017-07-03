package edu.fjut.fundsys.domain;

import org.apache.struts2.json.annotations.JSON;

public class FundHold extends ValueObject {
	private Integer HID = 100000;
	private Double amount;
	private ClientUser clientUser;
	private Fund fund;
	private Double yestodayEarn;
	private String clientId;
	private Integer fundId;
	private String fundName;
	private String fundTypeName;

	public String getFundName() {
		return fund.getFundName();
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundTypeName() {
		return fund.getFundType().getFundTypeName();
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	public String getClientId() {
		return clientUser.getClientId();
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getFundId() {
		return fund.getFundNo();
	}

	public void setFundId(Integer fundId) {
		this.fundId = fundId;
	}

	public Double getYestodayEarn() {
		return yestodayEarn;
	}

	public void setYestodayEarn(Double yestodayEarn) {
		this.yestodayEarn = yestodayEarn;
	}

	public Integer getHID() {
		return HID;
	}

	public void setHID(Integer hID) {
		HID = hID;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@JSON(serialize = false)
	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	@JSON(serialize = false)
	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	@Override
	public String toString() {
		return "FundHold [HID=" + HID + ", amount=" + amount + ", clientUser="
				+ clientUser + ", fund=" + fund + "]";
	}

}
