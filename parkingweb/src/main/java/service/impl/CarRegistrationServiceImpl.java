package service.impl;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.impl.CarRegistrationDaoImpl;
import model.CarRegistrationTable;
import service.CarRegistrationService;
import util.GetDateTime;

public class CarRegistrationServiceImpl implements CarRegistrationService {
	public static void main(String[] args) {
	int i= new CarRegistrationServiceImpl().DeleteCarRegistrationByLicensePlateNumberResidentId("ONE-0001", "A57H01F01");
	System.out.println(i);
	}
	
	
	
	
	//================================================================================
	@Override
	public int DeleteCarRegistrationByLicensePlateNumberResidentId(String licenseplatenumber, String residentid) {
		return new CarRegistrationDaoImpl().DeleteCarRegistrationByLicensePlateNumberResidentId(licenseplatenumber, residentid);
	}
	
	
	
	
	@Override
	 public List<CarRegistrationTable> GetCarRegistrationByResidentId(String residentId) {
		
	        return new CarRegistrationDaoImpl().SelectCarRegistrationByResidentId(residentId);
	    }
	
	
	
	
	
    // ★★★ 實作新方法 ★★★
    @Override
    public List<CarRegistrationTable> GetCarRegistrationByLicensePlateNumber(String licenseplatenumber) {
    	
        return new CarRegistrationDaoImpl().SelectCarRegistrationByLicensePlateNumber(licenseplatenumber);
    }
	
	
	
	
	

	
	

	
	
	@Override
	public String CheckCarRegistrationExistByLicensePlateNumber(String license_plate_number) {
		String residentId = new CarRegistrationDaoImpl().SelectCarRegistrationExistByLicensePlateNumber(license_plate_number);
		return residentId;
	}
	
	
	
	
	
	
	@Override
	public int InsertCarRegistration(String residentid, String addresssimple, String car_serialnumber,
			String licenseplatenumber, String occupiedavailable, String caruser, String caruser_phone,
			String carregistrationdate) {
			int i=0;	

			if (residentid.length()==9) {
				if (licenseplatenumber.length()>5) {
					if (new CarRegistrationDaoImpl().SelectCarRegistrationExistByLicensePlateNumber(licenseplatenumber) != null)
						{
							return i; //Car Existed
						} else {
							i = new CarRegistrationDaoImpl().SelectCarRegistrationQuantityByResidentId(residentid);//找Car登記數量
							if (i<2) {
								CarRegistrationTable carregistrationtable = new CarRegistrationTable();
								carregistrationtable.setResidentId(residentid);
								carregistrationtable.setAddressSimple(addresssimple);
								carregistrationtable.setCarSerialNumber(car_serialnumber);
								carregistrationtable.setLicensePlateNumber(licenseplatenumber.toUpperCase());
								carregistrationtable.setOccupiedAvailable(occupiedavailable); /////////////////////////////////////
								carregistrationtable.setCarUser(caruser);
								carregistrationtable.setCarUserPhone(caruser_phone);
								carregistrationtable.setCarRegistrationDate(new GetDateTime().nowDataTime());
								i = new CarRegistrationDaoImpl().InsertCarRegistration(carregistrationtable);
								return i;
							} else {
								JOptionPane.showMessageDialog(null, "新增失敗!! ~超過登記數量,需<=2~","系統提示",JOptionPane.INFORMATION_MESSAGE);
								return 0;
							}
						}
				} else {
					JOptionPane.showMessageDialog(null, "新增失敗!! ~車號不要空白&大於5碼~","系統提示",JOptionPane.INFORMATION_MESSAGE);
					return 0;
				}
			} else {
				JOptionPane.showMessageDialog(null, "新增失敗!! ~(請先查詢住戶在新增)~","系統提示",JOptionPane.INFORMATION_MESSAGE);
				return 0;
			}

	
	
	
	
	
	}

	
	
	
	@Override
	public void UpdateCarRegistrationByResidentId(CarRegistrationTable carregistrationtable) {
		new CarRegistrationDaoImpl().UpdateCarRegistrationByResidentId(carregistrationtable);
	}
		
	
	
	
	
	@Override
	public void UpdateCarRegistrationByLicensePlateNumber(CarRegistrationTable carregistrationtable) {
		new CarRegistrationDaoImpl().UpdateCarRegistrationByLicensePlateNumber(carregistrationtable);
	}



	
	
	
	
//=====================================================================
	@Override
	public DefaultTableModel getAllCarRegistration() {
		List<CarRegistrationTable> carTableList = new CarRegistrationDaoImpl().SelectAllCarRegistration();
		DefaultTableModel carJtable = new DefaultTableModel();
		carJtable.addColumn("住戶ID");
		carJtable.addColumn("門牌");
		//carJtable.addColumn("序號");
		carJtable.addColumn("車牌");
		carJtable.addColumn("停車狀況");
		carJtable.addColumn("使用人");
		carJtable.addColumn("使用人電話");
		carJtable.addColumn("登記入期");
		
		if (carTableList !=null && !carTableList.isEmpty()){
			for (CarRegistrationTable carTable:carTableList) {
				String[] row = new String[] {carTable.getResidentId(),
						carTable.getAddressSimple(),
						//carTable.getCarSerialNumber(),
						carTable.getLicensePlateNumber(),
						carTable.getOccupiedAvailable(),
						carTable.getCarUser(),
						carTable.getCarUserPhone(),
						carTable.getCarRegistrationDate()};
						carJtable.addRow(row);
			}
		}
		return carJtable;
	}



//=====抓vo為更新資料 vo table即可=====
	@Override
	public List<CarRegistrationTable>  getAllCarRegistrationWeb() {
		return new CarRegistrationDaoImpl().SelectAllCarRegistration();
	}	
//==========================================================================


}		
	

