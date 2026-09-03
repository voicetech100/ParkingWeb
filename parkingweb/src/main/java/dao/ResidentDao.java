package dao;

import java.util.List;
import model.ResidentTable;

public interface ResidentDao {

	public List<String> SelectAddressSimpleForCombobox();
	public List<String> SelectParkingSpaceNumberForCombobox();
	public List<ResidentTable> SelectAllResident();
		
	public ResidentTable SelectResidentByResidentId(String residentid);
	public ResidentTable SelectResidentByParkingSpaceNumber(String parkingspacenumber);
	public ResidentTable SelectResidentByAddressSimple(String addresssimple);
	
	public void UpdateResidentByResidentId(ResidentTable residenttable);
	
	
}
