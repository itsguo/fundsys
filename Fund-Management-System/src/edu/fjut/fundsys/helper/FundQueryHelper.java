package edu.fjut.fundsys.helper;

public class FundQueryHelper {
	private String qryFundNo;
	private String qryFundName;
	private String qryFundStatus;
	private String qryfundTypeId;

	public FundQueryHelper(String qryFundNo, String qryFundName,
			String qryFundStatus, String qryfundTypeId) {
		super();
		this.qryFundNo = qryFundNo;
		this.qryFundName = qryFundName;
		this.qryFundStatus = qryFundStatus;
		this.qryfundTypeId = qryfundTypeId;
	}

	public String getQryFundNo() {
		return qryFundNo;
	}

	public void setQryFundNo(String qryFundNo) {
		this.qryFundNo = qryFundNo;
	}

	public String getQryFundName() {
		return qryFundName;
	}

	public void setQryFundName(String qryFundName) {
		this.qryFundName = qryFundName;
	}

	public String getQryFundStatus() {
		return qryFundStatus;
	}

	public void setQryFundStatus(String qryFundStatus) {
		this.qryFundStatus = qryFundStatus;
	}

	public String getQryfundTypeId() {
		return qryfundTypeId;
	}

	public void setQryfundTypeId(String qryfundTypeId) {
		this.qryfundTypeId = qryfundTypeId;
	}

}
