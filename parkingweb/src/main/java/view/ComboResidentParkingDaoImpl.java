package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DbConnection;

public class ComboResidentParkingDaoImpl implements ComboResidentParkingDao{

	public static void main(String[] args) {
	}

	@Override
	public List<ComboResidentParking> selectAllComResidentParking() {
		Connection conn= DbConnection.getDb(); 
		List<ComboResidentParking> resComTableList = new ArrayList<>();
		String sql="select * from vo_resident_parking";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				ComboResidentParking comRP = new ComboResidentParking();
				comRP.setResidentId(rs.getString("resident_id"));
				comRP.setAddressSimple(rs.getString("address_simple"));
				comRP.setParkingSpaceOwner(rs.getString("parking_space_owner"));
				comRP.setParkingSpaceOwnerPhone(rs.getString("parking_space_owner_phone"));
				comRP.setLicensePlateNumber(rs.getString("license_plate_number"));
				comRP.setOccupiedAvailable(rs.getString("occupied_available"));
				comRP.setParkingSpaceNumber(rs.getString("parking_space_number"));
				comRP.setParkingSpaceFloor(rs.getString("parking_space_floor"));
				comRP.setCarUser(rs.getString("car_user"));
				comRP.setCarUserPhone(rs.getString("car_user_phone"));
				resComTableList.add(comRP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resComTableList;
	}
}