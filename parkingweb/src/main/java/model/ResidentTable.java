package model;

public class ResidentTable {
	
		//fields
		private String residentId;
		private String addressSimple;
		private String addressComplete;
		private String parkingSpaceOwner;
		private String parkingSpaceOwnerPhone;
		private String parkingSpaceUser;
		private String parkingSpaceUserPhone;
		private String parkingSpaceNumber;
		private String parkingSpaceFloor;
		private String createDate;
		private String updateDate;
		
		
		//constructors
		public ResidentTable() {
			//System.out.println("===run resident table constructors===");
		}
		
		public ResidentTable(String parking_space_number) {
			//System.out.println("===run resident table constructors===");
		}
				
		public ResidentTable(String resident_id, String address_simple, String address_complete,
				String parking_space_owner, String parking_space_owner_phone, String parking_space_user,
				String parking_space_user_phone, String parking_space_number, String parking_space_floor,
				String create_date, String update_date) {
			this.residentId = resident_id;
			this.addressSimple = address_simple;
			this.addressComplete = address_complete;
			this.parkingSpaceOwner = parking_space_owner;
			this.parkingSpaceOwnerPhone = parking_space_owner_phone;
			this.parkingSpaceUser = parking_space_user;
			this.parkingSpaceUserPhone = parking_space_user_phone;
			this.parkingSpaceNumber = parking_space_number;
			this.parkingSpaceFloor = parking_space_floor;
			this.createDate = create_date;
			this.updateDate = update_date;
		}

		
		//methods
		public String getResidentId() {
			return residentId;
		}

		public void setResidentId(String residentid) {
			this.residentId = residentid;
		}
		
		public String getAddressSimple() {
			return addressSimple;
		}

		public void setAddressSimple(String addressSimple) {
			this.addressSimple = addressSimple;
		}

		public String getAddressComplete() {
			return addressComplete;
		}

		public void setAddressComplete(String addressComplete) {
			this.addressComplete = addressComplete;
		}

		public String getParkingSpaceOwner() {
			return parkingSpaceOwner;
		}

		public void setParkingSpaceOwner(String parkingSpaceOwner) {
			this.parkingSpaceOwner = parkingSpaceOwner;
		}

		public String getParkingSpaceOwnerPhone() {
			return parkingSpaceOwnerPhone;
		}

		public void setParkingSpaceOwnerPhone(String parkingSpaceOwnerPhone) {
			this.parkingSpaceOwnerPhone = parkingSpaceOwnerPhone;
		}

		public String getParkingSpaceUser() {
			return parkingSpaceUser;
		}

		public void setParkingSpaceUser(String parkingSpaceUser) {
			this.parkingSpaceUser = parkingSpaceUser;
		}

		public String getParkingSpaceUserPhone() {
			return parkingSpaceUserPhone;
		}

		public void setParkingSpaceUserPhone(String parkingSpaceUserPhone) {
			this.parkingSpaceUserPhone = parkingSpaceUserPhone;
		}

		public String getParkingSpaceNumber() {
			return parkingSpaceNumber;
		}

		public void setParkingSpaceNumber(String parkingSpaceNumber) {
			this.parkingSpaceNumber = parkingSpaceNumber;
		}

		public String getParkingSpaceFloor() {
			return parkingSpaceFloor;
		}

		public void setParkingSpaceFloor(String parkingSpaceFloor) {
			this.parkingSpaceFloor = parkingSpaceFloor;
		}

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

		public String getUpdateDate() {
			return updateDate;
		}

		public void setUpdateDate(String updateDate) {
			this.updateDate = updateDate;
		}

		
		
		
		
		
		
		
}
