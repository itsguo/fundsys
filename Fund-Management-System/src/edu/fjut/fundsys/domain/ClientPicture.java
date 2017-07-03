package edu.fjut.fundsys.domain;

public class ClientPicture extends ValueObject {
	private Integer pictureId = 10;
	private String pictureOldname;
	private String pictureNewname;
	private String clientId;

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getPictureOldname() {
		return pictureOldname;
	}

	public void setPictureOldname(String pictureOldname) {
		this.pictureOldname = pictureOldname;
	}

	public String getPictureNewname() {
		return pictureNewname;
	}

	public void setPictureNewname(String pictureNewname) {
		this.pictureNewname = pictureNewname;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
