package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.AccessDao;
import model.AccessTable;
import service.impl.AccessServiceImpl;
import util.DbConnection;

public class AccessDaoImpl implements AccessDao{
	
	public static void main(String[] args) {
		new AccessDaoImpl().selectAllAccess("2026-01-01 00:00:01","2026-12-31 23:59:59","*");
	}                                        

	
	
	//=========================================================================
	Connection conn=DbConnection.getDb();
	
	@Override
	public int InsertAccessLog(AccessTable accesslogtable) {
		/*
		String date="20260626";
		//String license_plate_number=""; 
		String car_user="Terence";
		String car_user_phone="0988116116"; 
		String entry_exit= "entry";
		String reason="normal";
		String alert="normal";
		*/
		int i=0;
		String sql="insert into access_logs (date, license_plate_number, car_user, car_user_phone, entry_exit, reason, alert) values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,accesslogtable.getDate());
			ps.setString(2,accesslogtable.getLicensePlateNumber());
			ps.setString(3,accesslogtable.getCarUser());
			ps.setString(4,accesslogtable.getCarUserPhone());
			ps.setString(5,accesslogtable.getEntryExit()); 
			ps.setString(6,accesslogtable.getReason());
			ps.setString(7,accesslogtable.getAlert());
			ps.executeUpdate();
			return i;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	
	
	
	
	
	
	@Override
	public List<AccessTable> selectAllAccess(String startDate, String endDate, String licensePlateNumber) {
		List<AccessTable> accTableList = new  ArrayList<>();
		String sql = "";
		if (licensePlateNumber.equals("all")){
			sql="select * from access_logs where date>=? and date<=? order by id";
		} else {
			sql="select * from access_logs where date>=? and date<=? and license_plate_number=? order by id";
		}
		try (Connection conn = DbConnection.getDb();
               PreparedStatement ps = conn.prepareStatement(sql)) {
        	   ps.setString(1, startDate); 
        	   ps.setString(2, endDate);
        	   if (!licensePlateNumber.equals("all"))
        		   ps.setString(3, licensePlateNumber);
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
            	   AccessTable accTable = new AccessTable(); 
            	   accTable.setDate(rs.getString("date")); 
            	   accTable.setLicensePlateNumber(rs.getString("license_plate_number")); 
            	   accTable.setCarUser(rs.getString("car_user"));
            	   accTable.setCarUserPhone(rs.getString("car_user_phone")); 
            	   accTable.setEntryExit(rs.getString("entry_exit"));
            	   accTable.setReason(rs.getString("reason"));
            	   accTable.setAlert(rs.getString("alert"));
            	   accTableList.add(accTable);
               }
        }
	catch (SQLException e) {
		e.printStackTrace();
	}
		return accTableList;
	}	

}
	
	
