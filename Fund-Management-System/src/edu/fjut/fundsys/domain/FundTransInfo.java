package edu.fjut.fundsys.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class FundTransInfo extends ValueObject {
	private Integer transId = 10000;
	private String transType;
	private Date createDate = new Date();
	private Double amount;

	private String clientId;
	private Integer fundNo;

	private ClientUser clientUser;
	private Fund fund;

	public String getClientId() {
		return clientUser.getClientId();
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getFundNo() {
		return fund.getFundNo();
	}

	public void setFundNo(Integer fundNo) {
		this.fundNo = fundNo;
	}

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

	@JSON(format = "yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public FundTransInfo() {
		super();
	}

	@Override
	public String toString() {
		return "FundTransInfo [transId=" + transId + ", transType=" + transType
				+ ", createDate=" + createDate + ", amount=" + amount
				+ ", clientId=" + clientId + ", fundNo=" + fundNo
				+ ", clientUser=" + clientUser + ", fund=" + fund + "]";
	}

}
