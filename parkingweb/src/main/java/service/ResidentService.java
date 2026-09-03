package service;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import model.ResidentTable;

public interface  ResidentService{

	public String FindParkingSpaceNumberForCombobox();  //vvv
	public String FindAddressSimpleForCombobox(); //vvv
   
		
	public ResidentTable FindResidentByResidentId(String residentid);  //vvv
	public ResidentTable FindResidentByParkingSpaceNumber(String parkingspacenumber); //vvv
	public ResidentTable FindResidentByAddressSimple(String addresssimple);	//vvv
	public ResidentTable FindResidentByLicensePlateNumber(String licensplatenumber);  //vvv
	public List<ResidentTable> FindAllResident(); //pending
		
	public DefaultTableModel getAllResident();  //jfram使用,暫不需修改
	public List<ResidentTable> getAllResidentWeb();  //ForWeb用
	
	public String UpdateResidentByResidentId(String residentid, String parking_space_owner, String parking_space_owner_phone, String parking_space_user, String parking_space_user_phone); 
	//vvv改成軟刪除
	
	
	
}
