package dao;

import java.util.List;
import model.CarRegistrationTable;

public interface CarRegistrationDao {
	
	public List<CarRegistrationTable> SelectCarRegistrationByLicensePlateNumber(String licenseplatenumber);
	public List<CarRegistrationTable> SelectCarRegistrationByResidentId(String residentId);
	public List<CarRegistrationTable> SelectAllCarRegistration();
	
	public int SelectCarRegistrationQuantityByResidentId(String residentid);
	public String SelectCarRegistrationExistByLicensePlateNumber(String license_plate_number);
	public String SelectWhoParkingByResidentId(String residentid);
	
	
	public int DeleteCarRegistrationByLicensePlateNumberResidentId(String licenseplatenumber,String residentid);
	
	public int InsertCarRegistration(CarRegistrationTable carregistrationtable);
	
	public void UpdateCarRegistrationByResidentId(CarRegistrationTable carregistrationtable);
	public void UpdateCarRegistrationByLicensePlateNumber(CarRegistrationTable carregistrationtable);
	
	
	
	
	

	
	
	
}
