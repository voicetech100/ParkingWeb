package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.AdminRegistrationDao;
import model.AdminRegistrationTable;
import util.DbConnection;
import util.GetDateTime;

public class AdminRegistrationDaoImpl implements AdminRegistrationDao{

	//=====ForTestOnly==============================================================
	public static void main(String[] args) {
		AdminRegistrationTable adm = new AdminRegistrationTable();
		adm.setAccount("ppp");
		adm.setPassword("$2a$10$xlTgtBMKsWMJSnYOeKeaKOhdYFhROfNxYaDZxve18Sui/Zico/Dqe");
		adm.setName("Terence");
		adm.setPhone("123459");
		adm.setClass1("2");
		adm.setDate(new GetDateTime().nowDataTime());
		System.out.println(new AdminRegistrationDaoImpl().UpdateAccount(adm));
	}
	

	//==============================================================================
	Connection conn=DbConnection.getDb();
	
	
	@Override
	public int UpdateAccount(AdminRegistrationTable adminregistrationtable) {
		System.out.println("AdmDaoUpadateAccount=>進入");
		int i=0;
		if (adminregistrationtable != null) {
			String sql = "UPDATE admin_registration SET account = ? , password = ?, name = ?, phone = ?, date = ?, class1 =?  WHERE account = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, adminregistrationtable.getAccount());
				ps.setString(2, adminregistrationtable.getPassword());
	            ps.setString(3, adminregistrationtable.getName());
	            ps.setString(4, adminregistrationtable.getPhone());
	            ps.setString(5, adminregistrationtable.getDate());
	            ps.setString(6, adminregistrationtable.getClass1());
	            ps.setString(7, adminregistrationtable.getAccount());
				i=ps.executeUpdate();
	            return i;
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
	return i;
	}

	
	
	
	
	
	@Override		//20260818 add
	public List<AdminRegistrationTable> SelectAllManager(AdminRegistrationTable adminregistration) {
	List<AdminRegistrationTable> admList = new ArrayList<>();
		if (adminregistration != null) {
			String sql = "SELECT * FROM gjun.admin_registration where 1";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
	            while (rs.next()) { 
	            	AdminRegistrationTable adm= new AdminRegistrationTable(); 
	            	adm.setId(rs.getInt("id"));
	            	adm.setAccount(rs.getString("account"));
		            adm.setPassword(rs.getString("password"));
		            adm.setName(rs.getString("name"));
		            adm.setPhone(rs.getString("phone"));
		            adm.setDate(rs.getString("date"));
		            adm.setClass1(rs.getString("class1"));
		            admList.add(adm);
	            }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return admList;
	}

	
	
	
	

	@Override
	public List<AdminRegistrationTable> SelectAccountList(AdminRegistrationTable adminregistration) {
	List<AdminRegistrationTable> admList = new ArrayList<>();
		if (adminregistration != null) {
			String sql = "SELECT * FROM admin_registration WHERE account = ? ";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, adminregistration.getAccount());
				ResultSet rs = ps.executeQuery();
	            if (rs.next()) { 
	            	adminregistration.setId(rs.getInt("id"));
	            	adminregistration.setAccount(rs.getString("account"));
		            adminregistration.setPassword(rs.getString("password"));
		            adminregistration.setName(rs.getString("name"));
		            adminregistration.setPhone(rs.getString("phone"));
		            adminregistration.setDate(rs.getString("date"));
		            adminregistration.setClass1(rs.getString("class1"));
		            admList.add(adminregistration);
	            }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return admList;
	}


	
	
	

	@Override
	public int SelectAccountPassword(AdminRegistrationTable adminregistrationtable) {  //後續不用
		int i=0;
		if (adminregistrationtable != null) {
			String sql = "SELECT count(*) FROM admin_registration WHERE account = ? AND password = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, adminregistrationtable.getAccount());
				ps.setString(2, adminregistrationtable.getPassword());
				ResultSet rs = ps.executeQuery();
	            if (rs.next()) 
	            	return rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return i;
	}


	
	
	
	
	@Override
	public List<AdminRegistrationTable> SelectAccountPasswordWeb(AdminRegistrationTable adminregistrationtable) {  //20260820 add For Web
		System.out.println("@AdmRegDaoSelectAccountPasswordWeb=>進入");		
		List<AdminRegistrationTable> admList = new ArrayList<>();
		if (adminregistrationtable != null) {
			String sql = "SELECT * FROM admin_registration WHERE account = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, adminregistrationtable.getAccount());
				ResultSet rs = ps.executeQuery();
			    if (rs.next()) 
	            	adminregistrationtable.setId(rs.getInt("id"));
	            	adminregistrationtable.setAccount(rs.getString("account"));
		            adminregistrationtable.setPassword(rs.getString("password"));
		            adminregistrationtable.setName(rs.getString("name"));
		            adminregistrationtable.setPhone(rs.getString("phone"));
		            adminregistrationtable.setDate(rs.getString("date"));
		            adminregistrationtable.setClass1(rs.getString("class1"));
		            admList.add(adminregistrationtable);
		        return admList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return admList;
	}
	
	
	
	
	
	@Override
	public List<AdminRegistrationTable> SelectAccount(AdminRegistrationTable adminregistrationtable) {
		System.out.println("@AdmRegDaoSelectAccount=>進入");
		List<AdminRegistrationTable> list = new ArrayList<>();
		if (adminregistrationtable == null || adminregistrationtable.getAccount() == null) {
	        return list;
	    }
		AdminRegistrationTable admRegTable = new AdminRegistrationTable(); 
		String sql = "SELECT * FROM admin_registration WHERE account = ? ";
		try (PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, adminregistrationtable.getAccount());
			ResultSet rs = ps.executeQuery();
			System.out.println("要新增或更新的user\t"+ adminregistrationtable.getAccount());
			if (rs.next()) {
            	admRegTable.setId(rs.getInt("id"));
	            admRegTable.setAccount(rs.getString("account"));
	            admRegTable.setPassword(rs.getString("password"));
	            admRegTable.setName(rs.getString("name"));
	            admRegTable.setPhone(rs.getString("phone"));
	            admRegTable.setDate(rs.getString("date"));
	            admRegTable.setClass1(rs.getString("class1"));
	            list.add(admRegTable);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	

	
	@Override
	public int InsertAccount(AdminRegistrationTable adminregistrationtable) {
		System.out.println("@AdmResDaoInsertAccount=>進入");
		int i=0;
		if (adminregistrationtable != null) {
			String sql = "INSERT INTO admin_registration (account, password, name, phone, date, class1) VALUES (?, ?, ?, ?, ?, ?)";
	        	try {
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, adminregistrationtable.getAccount());
		            ps.setString(2, adminregistrationtable.getPassword());
		            ps.setString(3, adminregistrationtable.getName());
		            ps.setString(4, adminregistrationtable.getPhone());
		            ps.setString(5, adminregistrationtable.getDate());
		            ps.setString(6, adminregistrationtable.getClass1());
		            i=ps.executeUpdate();
		            return i;
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    }
		return i;
	}

	

		
		
		
	
	@Override
	public int DeleteAccount(AdminRegistrationTable adminregistrationtable) {
		int i=0;
		if (adminregistrationtable != null) {
		    String sql = "DELETE FROM admin_registration WHERE account = ?";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, adminregistrationtable.getAccount());
	            i=ps.executeUpdate();
	            return i;
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
	return i;
	}

	
	
	
	
	
}
