package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ResidentDao;
import model.AccessTable;
import model.CarRegistrationTable;
import model.ResidentTable;
import service.impl.ResidentServiceImpl;
import util.DbConnection;



public class  ResidentDaoImpl implements ResidentDao{
	
	public static void main(String[] args) {
		
		//=====測試驗證===================================================================
		//ResidentTable residenttable=new ResidentTable("CEC-1501"); 
		//System.out.println(new ResidentDaoImpl().SelectResidentByParkingSpaceNumber("C1501")); //model.ResidentTable@239a307b
		//System.out.println(new ResidentDaoImpl().SelectResidentByAddressSimple("57號15樓-1")); //model.ResidentTable@683dbc2c
		//System.out.println(new ResidentDaoImpl().SelectAllResident("2026-01-01","2026-12-31"));
		//System.out.println(new ResidentServiceImpl().FindAllResident());
		
		/*
		ResidentTable r = new ResidentDaoImpl().SelectResidentByResidentId("A57H15F01");
				r.setResidentId("A57H15F01");
				r.setAddressSimple("57號15樓-1"); 
				r.setAddressComplete("新北市汐止區幸福測試路57號15樓-1");
				r.setParkingSpaceOwner("Terence1501");
				r.setParkingSpaceOwnerPhone("00001501");
				r.setParkingSpaceUser("Terence1501");
				r.setParkingSpaceUserPhone("11111501");
				r.setParkingSpaceNumber("C1501");
				r.setParkingSpaceFloor("B3");
				r.setCreateDate("20260619 1501******");
				r.setUpdateDate("20260619 1501******");
		System.out.println(r.getResidentId() +"\n"+
				r.getAddressSimple()+"\n"+ 
				r.getAddressComplete() +"\n"+ 
				r.getParkingSpaceOwner()+"\n"+
				r.getParkingSpaceOwnerPhone()+"\n"+
				r.getParkingSpaceUser()+"\n"+
				r.getParkingSpaceUserPhone() +"\n"+
				r.getParkingSpaceNumber()+"\n");*/
		
		
		
	}

	

	//==============================================
	Connection conn=DbConnection.getDb();	
	
	
	
	@Override  //車位查住戶
	public ResidentTable SelectResidentByParkingSpaceNumber(String parkingspacenumber) {
  		ResidentTable residenttable = null;
		String sql="select * from resident where parking_space_number = ?";//order by parking_space_number";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, parkingspacenumber);
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
				residenttable=new ResidentTable();
				residenttable.setResidentId(rs.getString("resident_id"));
				residenttable.setAddressSimple(rs.getString("address_simple"));
				residenttable.setAddressComplete(rs.getString("address_complete"));
				residenttable.setParkingSpaceOwner(rs.getString("parking_space_owner"));
				residenttable.setParkingSpaceOwnerPhone(rs.getString("parking_space_owner_phone"));
				residenttable.setParkingSpaceUser(rs.getString("parking_space_user"));
				residenttable.setParkingSpaceUserPhone(rs.getString("parking_space_user_phone"));
				residenttable.setParkingSpaceNumber(rs.getString("parking_space_number"));
				residenttable.setParkingSpaceFloor(rs.getString("parking_space_floor"));
				residenttable.setCreateDate(rs.getString("create_date"));
				residenttable.setUpdateDate(rs.getString("update_date"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return residenttable;
	}


	
	
		
	@Override //地址查住戶
	public ResidentTable SelectResidentByAddressSimple(String addresssimple) {
  		ResidentTable residenttable = null;
		String sql="select * from resident where address_simple = ?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, addresssimple);
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
				residenttable=new ResidentTable();
				residenttable.setResidentId(rs.getString("resident_id"));
				residenttable.setAddressSimple(rs.getString("address_simple"));
				residenttable.setAddressComplete(rs.getString("address_complete"));
				residenttable.setParkingSpaceOwner(rs.getString("parking_space_owner"));
				residenttable.setParkingSpaceOwnerPhone(rs.getString("parking_space_owner_phone"));
				residenttable.setParkingSpaceUser(rs.getString("parking_space_user"));
				residenttable.setParkingSpaceUserPhone(rs.getString("parking_space_user_phone"));
				residenttable.setParkingSpaceNumber(rs.getString("parking_space_number"));
				residenttable.setParkingSpaceFloor(rs.getString("parking_space_floor"));
				residenttable.setCreateDate(rs.getString("create_date"));
				residenttable.setUpdateDate(rs.getString("update_date"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return residenttable;
	}

	
	
	
	
	
	@Override //ID查住戶
	public ResidentTable SelectResidentByResidentId(String residentid) {
  		ResidentTable residenttable = null;
		String sql="select * from resident where resident_id = ?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, residentid);
			ResultSet rs=ps.executeQuery();
			if (rs.next()){
				residenttable=new ResidentTable();
				residenttable.setResidentId(rs.getString("resident_id"));
				residenttable.setAddressSimple(rs.getString("address_simple"));
				residenttable.setAddressComplete(rs.getString("address_complete"));
				residenttable.setParkingSpaceOwner(rs.getString("parking_space_owner"));
				residenttable.setParkingSpaceOwnerPhone(rs.getString("parking_space_owner_phone"));
				residenttable.setParkingSpaceUser(rs.getString("parking_space_user"));
				residenttable.setParkingSpaceUserPhone(rs.getString("parking_space_user_phone"));
				residenttable.setParkingSpaceNumber(rs.getString("parking_space_number"));
				residenttable.setParkingSpaceFloor(rs.getString("parking_space_floor"));
				residenttable.setCreateDate(rs.getString("create_date"));
				residenttable.setUpdateDate(rs.getString("update_date"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return residenttable;
	}
	
	

	

	
	@Override
	public void UpdateResidentByResidentId(ResidentTable res) {
  		String sql="update resident set"
  				+ " resident_id=?,"
  				+ " address_simple=?,"
				+ " address_complete=?,"
				+ " parking_space_owner=?,"
				+ " parking_space_owner_phone=?,"
				+ " parking_space_user=?,"
				+ " parking_space_user_phone=?,"
				+ " parking_space_number=?,"
				+ " parking_space_floor=?,"
				+ " create_date=?,"	
				+ " update_date=?"
				+ " where resident_id =?";
  		try {
			PreparedStatement ps=conn.prepareStatement(sql);
  			ps.setString(1,res.getResidentId());
  			ps.setString(2,res.getAddressSimple());
			ps.setString(3,res.getAddressComplete());
			ps.setString(4,res.getParkingSpaceOwner());
			ps.setString(5,res.getParkingSpaceOwnerPhone());
			ps.setString(6,res.getParkingSpaceUser());
			ps.setString(7,res.getParkingSpaceUserPhone());
			ps.setString(8,res.getParkingSpaceNumber());
			ps.setString(9,res.getParkingSpaceFloor());
			ps.setString(10,res.getCreateDate());
			ps.setString(11,res.getUpdateDate());
			ps.setString(12,res.getResidentId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	



	// 工具欄Combobox
	@Override
	public ArrayList<String> SelectAddressSimpleForCombobox() {
		ArrayList<String> arraylist=new ArrayList<>();
		String sql="SELECT * FROM resident";// order by address_simple";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			arraylist.add(rs.getString("address_simple"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arraylist;
	}



	
	
	// 工具欄Combobox
	@Override
	public ArrayList<String> SelectParkingSpaceNumberForCombobox() {
		ArrayList<String> arraylist=new ArrayList<>();
		String sql="SELECT * FROM resident";// order by parking_space_number";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				arraylist.add(rs.getString("parking_space_number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arraylist;
	}



	
	
	

	//查所有住戶
	@Override
	public List<ResidentTable> SelectAllResident() {
		List<ResidentTable> resTableList = new ArrayList<>();
		String sql="SELECT * FROM resident order by id desc";
		try {
			PreparedStatement  ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResidentTable resTable =new ResidentTable();
				resTable.setResidentId(rs.getString("resident_id"));
				resTable.setAddressSimple(rs.getString("address_simple"));
				resTable.setAddressComplete(rs.getString("address_complete"));
				resTable.setParkingSpaceOwner(rs.getString("parking_space_owner"));
				resTable.setParkingSpaceOwnerPhone(rs.getString("parking_space_owner_phone"));
				resTable.setParkingSpaceUser(rs.getString("parking_space_user"));
				resTable.setParkingSpaceUserPhone(rs.getString("parking_space_user_phone"));
				resTable.setParkingSpaceNumber(rs.getString("parking_space_number"));
				resTable.setParkingSpaceFloor(rs.getString("parking_space_floor"));
				resTable.setCreateDate(rs.getString("create_date"));
				resTable.setUpdateDate(rs.getString("update_date"));
				resTableList.add(resTable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resTableList;
	}





	
	





	
}

