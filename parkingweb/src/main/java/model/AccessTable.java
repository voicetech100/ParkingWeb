package model;

public class AccessTable {

	//fields
	private String date;
	private String licensePlateNumber; 
	private String carUser;
	private String carUserPhone; 
	private String entryExit;
	private String reason;
	private String alert;
	
	//contruct
	public AccessTable() {
		super();
	}

	public AccessTable(String date, String licensePlateNumber, String carUser, String carUserPhone, String entryExit,
			String reason, String alert) {
		super();
		this.date = date;
		this.licensePlateNumber = licensePlateNumber;
		this.carUser = carUser;
		this.carUserPhone = carUserPhone;
		this.entryExit = entryExit;
		this.reason = reason;
		this.alert = alert;
	}

	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getCarUser() {
		return carUser;
	}

	public void setCarUser(String carUser) {
		this.carUser = carUser;
	}

	public String getCarUserPhone() {
		return carUserPhone;
	}

	public void setCarUserPhone(String carUserPhone) {
		this.carUserPhone = carUserPhone;
	}

	public String getEntryExit() {
		return entryExit;
	}

	public void setEntryExit(String entryExit) {
		this.entryExit = entryExit;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	
	
	
	
	
	
}
