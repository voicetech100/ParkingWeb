package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CarRegistrationDao;
import model.CarRegistrationTable;
import util.DbConnection;

public class CarRegistrationDaoImpl implements CarRegistrationDao {
	
	public static void main(String[] agrs){
		//System.out.println(new CarRegistrationDaoImpl().DeleteCarRegistrationByLicensePlateNumberResidentId("ONE-0001", "A57H01F01"));
		CarRegistrationDaoImpl dao = new CarRegistrationDaoImpl();
		List<CarRegistrationTable> list = dao.SelectCarRegistrationByLicensePlateNumber("ONE-0001");
		//		for (CarRegistrationTable l:list) {
//			for(String o:l) {
			System.out.println("@!!!!!!!!");
			System.out.println(list.size());
//			}
		}
	
	
		
	//=========================================================================
	Connection conn=DbConnection.getDb();
	
	
	
	
	//車號查住戶
	@Override  //
	public List<CarRegistrationTable> SelectCarRegistrationByLicensePlateNumber(String licensplatenumber) {
		
		List<CarRegistrationTable> list = new ArrayList<>();
		String sql = "SELECT * FROM car_registration WHERE  license_plate_number = ?";  //20260807 change
        try (Connection conn = DbConnection.getDb();
        		
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		         pstmt.setString(1, licensplatenumber);
		         ResultSet rs = pstmt.executeQuery();
		         while (rs.next()) {
		             CarRegistrationTable car = new CarRegistrationTable();
		             car.setResidentId(rs.getString("resident_id"));
		             car.setAddressSimple(rs.getString("address_simple"));
		             car.setCarSerialNumber(rs.getString("car_serial_number"));
		             car.setLicensePlateNumber(rs.getString("license_plate_number"));
		             car.setOccupiedAvailable(rs.getString("occupied_available"));
		             car.setCarUser(rs.getString("car_user"));
		             car.setCarUserPhone(rs.getString("car_user_phone"));
		             car.setCarRegistrationDate(rs.getString("car_registration_date"));
		             list.add(car);
		         }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return list;
       }
	
	
	
	
	
	
	@Override
	public int DeleteCarRegistrationByLicensePlateNumberResidentId(String licenseplatenumber,String residentid) {
		//String sql="delete from car_registration where license_plate_number=? and resident_id =?";
		String sql="update car_registration set delete_registration='yes' where license_plate_number=? and resident_id =?";//20260807改成軟刪除
		
		int i=0;
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, licenseplatenumber);
			ps.setString(2, residentid);
			i=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
		
	}
	
	
	
	
	
	
	@Override   
	public void UpdateCarRegistrationByResidentId(CarRegistrationTable car) {
		String sql="update car_registration set resident_id=?, address_simple=?, car_serial_number=?, license_plate_number=?, occupied_available=?, car_user=?, car_User_phone=?, car_registration_date=? where resident_id =?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,car.getResidentId());
			ps.setString(2,car.getAddressSimple());
			ps.setString(3,car.getCarSerialNumber());
			ps.setString(4,car.getLicensePlateNumber());
			ps.setString(5,car.getOccupiedAvailable());
			ps.setString(6,car.getCarUser());
			ps.setString(7,car.getCarUserPhone());
			ps.setString(8,car.getCarRegistrationDate());
			ps.setString(9,car.getResidentId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	
	
	
	
	
	
	
	@Override
	public int SelectCarRegistrationQuantityByResidentId(String residentid) {
		String sql="select count(*) from car_registration where (delete_registration <> 'yes' or delete_registration is null) and resident_id = ?";
		try {
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, residentid);
		ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count=0;
				System.out.println(rs.getInt(1));//ResultSet欄位索引從1開始計算
				count=rs.getInt(1);
				return(count);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	
	
	
	

	
	
	
	
	@Override //(delete_registration <> 'yes' or delete_registration is null) and
    public List<CarRegistrationTable> SelectCarRegistrationByResidentId(String residentId) {
		List<CarRegistrationTable> list = new ArrayList<>();
        String sql = "SELECT * FROM gjun.car_registration WHERE (delete_registration <> 'yes' or delete_registration is null) and resident_id = ? ORDER BY car_serial_number";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, residentId);
            ResultSet rs = pstmt.executeQuery();
            //System.out.println("@1111111");
            while (rs.next()) {
                CarRegistrationTable car = new CarRegistrationTable();
                car.setResidentId(rs.getString("resident_id"));
                car.setAddressSimple(rs.getString("address_simple"));
                car.setCarSerialNumber(rs.getString("car_serial_number"));
                car.setLicensePlateNumber(rs.getString("license_plate_number"));
                car.setOccupiedAvailable(rs.getString("occupied_available"));
                car.setCarUser(rs.getString("car_user"));
                car.setCarUserPhone(rs.getString("car_user_phone"));
                car.setCarRegistrationDate(rs.getString("car_registration_date"));
                list.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

	
	
	
	
	
	
	

	
	

	@Override
	public String SelectCarRegistrationExistByLicensePlateNumber(String license_plate_number) {
		String sql="select resident_id from car_registration where (delete_registration <> 'yes' or delete_registration is null) and license_plate_number = ?"; //20260807
		try {
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, license_plate_number);
		ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return(rs.getString("resident_id"));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	


	@Override
	public String SelectWhoParkingByResidentId(String residentid) {
		String sql="select * from car_registration where resident_id = ?";
		try {
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, residentid);
		ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return(rs.getString("license_plate_number"));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	
	



	
	

	@Override
	public int InsertCarRegistration(CarRegistrationTable carregistrationtable) {
		String sql="insert into car_registration (resident_id, address_simple, car_serial_number, license_plate_number, occupied_available, car_user, car_User_phone, car_registration_date) value (?,?,?,?,?,?,?,?)";
		int i=0;
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,carregistrationtable.getResidentId());
			ps.setString(2,carregistrationtable.getAddressSimple());
			ps.setString(3,carregistrationtable.getCarSerialNumber());
			ps.setString(4,carregistrationtable.getLicensePlateNumber());
			ps.setString(5,carregistrationtable.getOccupiedAvailable());
			ps.setString(6,carregistrationtable.getCarUser());
			ps.setString(7,carregistrationtable.getCarUserPhone());
			ps.setString(8,carregistrationtable.getCarRegistrationDate());
			i=ps.executeUpdate();
			System.out.println(i);
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
		
	}
	
	
	
	
	
	
	


	
	
	@Override   
	public void UpdateCarRegistrationByLicensePlateNumber(CarRegistrationTable car) {
		String sql="update car_registration set resident_id=?, address_simple=?, car_serial_number=?, license_plate_number=?, occupied_available=?, car_user=?, car_User_phone=?, car_registration_date=? where license_plate_number =?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,car.getResidentId());
			ps.setString(2,car.getAddressSimple());
			ps.setString(3,car.getCarSerialNumber());
			ps.setString(4,car.getLicensePlateNumber());
			ps.setString(5,car.getOccupiedAvailable());
			ps.setString(6,car.getCarUser());
			ps.setString(7,car.getCarUserPhone());
			ps.setString(8,car.getCarRegistrationDate());
			ps.setString(9,car.getLicensePlateNumber());
			//System.out.println(car.getResidentId()); //For debug..
			//System.out.println(car.getCarUser());
			//System.out.println(car.getOccupiedAvailable());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





	@Override
	public List<CarRegistrationTable> SelectAllCarRegistration() {
		List<CarRegistrationTable> carTableList = new ArrayList<>();
        String sql = "SELECT * FROM gjun.car_registration where (delete_registration is null or delete_registration <> 'yes')  ORDER BY resident_id;";
        try (Connection conn = DbConnection.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CarRegistrationTable carTable = new CarRegistrationTable();
                carTable.setResidentId(rs.getString("resident_id"));
                carTable.setAddressSimple(rs.getString("address_simple"));
                carTable.setCarSerialNumber(rs.getString("car_serial_number"));
                carTable.setLicensePlateNumber(rs.getString("license_plate_number"));
                carTable.setOccupiedAvailable(rs.getString("occupied_available"));
                carTable.setCarUser(rs.getString("car_user"));
                carTable.setCarUserPhone(rs.getString("car_user_phone"));
                carTable.setCarRegistrationDate(rs.getString("car_registration_date"));
                carTableList.add(carTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carTableList;
	}




	


}
