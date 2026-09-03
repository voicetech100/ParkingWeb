package service;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.AccessTable;
import view.ComboResidentParking;
import view.LicensePlateNumberListEE;

public interface AccessService {

	public String InsertCarEntryLog(String licenseplatenumber);  //xxx
	public String InsertCarExitLog(String licenseplatenumber);   //xxx
	public String AuthorizationEntry(String licenseplatenumber); //xxx
	public String AuthorizationExit(String licenseplatenumber);  //xxx
	public DefaultTableModel getEntryExit(String startDate, String endDate, String licensePlateNumber);
	public DefaultTableModel getAllComboResidentParking();	//xxx
	
	public List<ComboResidentParking> getAllComboResidentParkingWeb(); //for Web
	
	public List<LicensePlateNumberListEE> getLicensePlateNumberEE();
	public List<AccessTable> getEntryExitWeb(String startDate, String endDate, String LicensePlateNumbet); //for Web
}
