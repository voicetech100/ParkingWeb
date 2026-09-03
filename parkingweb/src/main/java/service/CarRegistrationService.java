package service;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.CarRegistrationTable;

public interface CarRegistrationService{
	             
	public String CheckCarRegistrationExistByLicensePlateNumber(String license_plate_number); //vvv 20260807改成軟刪除
	
	public List<CarRegistrationTable> GetCarRegistrationByResidentId(String residentId); 
    public List<CarRegistrationTable> GetCarRegistrationByLicensePlateNumber(String licenseplatenumber);
        
    public DefaultTableModel getAllCarRegistration(); //For jfram使用
    public List<CarRegistrationTable> getAllCarRegistrationWeb(); //forWeb用
    	
	public int InsertCarRegistration(   //vvv
			String residentid,
			String addresssimple,
			String car_serialnumber,
			String licenseplatenumber,
			String occupiedavailable,
			String caruser,
			String caruser_phone,
			String carregistrationdate);
			
	public int DeleteCarRegistrationByLicensePlateNumberResidentId(String licenseplatenumber,String residentid); //vvv 20260807 改成軟刪除
	
	public void UpdateCarRegistrationByResidentId(CarRegistrationTable carregistrationtable);
	public void UpdateCarRegistrationByLicensePlateNumber(CarRegistrationTable carregistrationtable);
	
	
}
