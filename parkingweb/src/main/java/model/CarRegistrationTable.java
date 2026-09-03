package model;

public class CarRegistrationTable {
	
	//fields
	private String residentId;
	private String addressSimple;
	private String carSerialNumber;
	private String licensePlateNumber;
	private String occupiedAvailable;
	private String carUser;
	private String carUserPhone;
	private String carRegistrationDate;
	
	//constructors
	public CarRegistrationTable() {
		//System.out.println("===run carregistration table constructors===");
	}
	
	public CarRegistrationTable(String resident_id, String address_simple,
			String car_serial_number, String license_plate_number, String occupied_available, String car_user,
			String car_user_phone, String car_registration_date) {
			this.residentId = resident_id;
			this.addressSimple = address_simple;
			this.carSerialNumber = car_serial_number;
			this.licensePlateNumber = license_plate_number;
			this.occupiedAvailable = occupied_available;
			this.carUser = car_user;
			this.carUserPhone = car_user_phone;
			this.carRegistrationDate = car_registration_date;
	}

	public String getResidentId() {
		return residentId;
	}

	public void setResidentId(String residentId) {
		this.residentId = residentId;
	}

	public String getAddressSimple() {
		return addressSimple;
	}

	public void setAddressSimple(String addressSimple) {
		this.addressSimple = addressSimple;
	}

	public String getCarSerialNumber() {
		return carSerialNumber;
	}

	public void setCarSerialNumber(String carSerialNumber) {
		this.carSerialNumber = carSerialNumber;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getOccupiedAvailable() {
		return occupiedAvailable;
	}

	public void setOccupiedAvailable(String occupiedAvailable) {
		this.occupiedAvailable = occupiedAvailable;
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

	public String getCarRegistrationDate() {
		return carRegistrationDate;
	}

	public void setCarRegistrationDate(String carRegistrationDate) {
		this.carRegistrationDate = carRegistrationDate;
	}

	
	//methods
	
	
	
	
}
