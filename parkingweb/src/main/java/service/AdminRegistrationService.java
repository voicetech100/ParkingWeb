package service;

import java.util.List;

import model.AdminRegistrationTable;

public interface AdminRegistrationService {

	public String InsertAccount(AdminRegistrationTable adminregistration); //vvv
	public int DeleteAccount(AdminRegistrationTable adminregistration);
	public List<AdminRegistrationTable> CheckAccount(AdminRegistrationTable adminregistration);
	public int UpdateAccount(AdminRegistrationTable adminregistration);  //vvv
	
	public List<AdminRegistrationTable> FindAccountList(AdminRegistrationTable adminregistration);
	public List<AdminRegistrationTable> FindAllManager(AdminRegistrationTable adminregistration);  //20260818 add For Web
	public List<AdminRegistrationTable> CheckAccountPasswordWeb(AdminRegistrationTable adminregistration); //20260820 add For Web
	
	
	
	
}
