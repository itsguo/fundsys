package edu.fjut.fundsys.domain;

public class ClientUser extends ValueObject {
	private String ClientId;
	private String ClientPwd;
	private String ClientName;
	private String Sex;
	private String Phone;
	private String Address;
	private String Email;
	private Double Balance = 20000.00;
	private Double totolEarnings = 0.000;
	private Boolean Active = true;

	public Double getTotolEarnings() {
		return totolEarnings;
	}

	public void setTotolEarnings(Double totolEarnings) {
		this.totolEarnings = totolEarnings;
	}

	public String getClientId() {
		return ClientId;
	}

	public void setClientId(String clientId) {
		ClientId = clientId;
	}

	public String getClientPwd() {
		return ClientPwd;
	}

	public void setClientPwd(String clientPwd) {
		ClientPwd = clientPwd;
	}

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Double getBalance() {
		return Balance;
	}

	public void setBalance(Double balance) {
		Balance = balance;
	}

	public Boolean getActive() {
		return Active;
	}

	public void setActive(Boolean active) {
		Active = active;
	}

	@Override
	public String toString() {
		return "ClientUser [ClientId=" + ClientId + ", ClientPwd=" + ClientPwd
				+ ", ClientName=" + ClientName + ", Sex=" + Sex + ", Phone="
				+ Phone + ", Address=" + Address + ", Email=" + Email
				+ ", Balance=" + Balance + ", Active=" + Active + "]";
	}

}
