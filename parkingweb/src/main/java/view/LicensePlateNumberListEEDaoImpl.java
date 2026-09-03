package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DbConnection;

public class LicensePlateNumberListEEDaoImpl implements LicensePlateNumberListEEDao {

	@Override
	public List<LicensePlateNumberListEE> selectAllLicencePlateNumberEntryExit() {
		Connection conn = DbConnection.getDb();
		String sql = "select * from vo_license_plate_number_list";
		List<LicensePlateNumberListEE> lpneeList= new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LicensePlateNumberListEE lpnlee = new LicensePlateNumberListEE(); 
				lpnlee.setLicenseplatenumberlist(rs.getString(1));//Sql: ResultSet索引值從1開始
				lpneeList.add(lpnlee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lpneeList;
	}

}
