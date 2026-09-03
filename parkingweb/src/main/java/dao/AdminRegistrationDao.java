package dao;

import java.util.List;

import model.AdminRegistrationTable;

public interface AdminRegistrationDao {

	public int InsertAccount(AdminRegistrationTable adminregistration);
	public int DeleteAccount(AdminRegistrationTable adminregistration);
	public int SelectAccountPassword(AdminRegistrationTable adminregistration);
	public List<AdminRegistrationTable> SelectAccount(AdminRegistrationTable adminregistration);
	public List<AdminRegistrationTable> SelectAccountList(AdminRegistrationTable adminregistration);
	public int UpdateAccount(AdminRegistrationTable adminregistration);
	public List<AdminRegistrationTable> SelectAllManager(AdminRegistrationTable adminregistration); //將不使用
	public List<AdminRegistrationTable> SelectAccountPasswordWeb(AdminRegistrationTable adminregistrationtable); //ForWeb 20260820add
	
}
