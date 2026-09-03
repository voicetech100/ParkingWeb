package service.impl;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.ResidentDao;
import dao.impl.ResidentDaoImpl;
import model.ResidentTable;
import service.CarRegistrationService;
import service.ResidentService;
import util.GetDateTime;

public class ResidentServiceImpl implements ResidentService{
	

	public static void main (String[] args) {
	}

	
	
	
	//=================================================================================
	CarRegistrationService carService = new CarRegistrationServiceImpl(); 
	ResidentDao resDao = new ResidentDaoImpl(); 
	//=================================================================================
	
	
	
	// ========== 查所有住戶 ==========
	@Override
	public List<ResidentTable> FindAllResident() {
		return resDao.SelectAllResident();
	}
	
	
	
	
	 // ========== 車號查住戶 ==========
    @Override
    public ResidentTable FindResidentByLicensePlateNumber(String licensplatenumber) {
        if (licensplatenumber == null || licensplatenumber.length() <= 6) {
            //  ////JOptionPane.showMessageDialog(null, "請輸入車號,車號不要空白", "系統提示", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        String residentId = carService.CheckCarRegistrationExistByLicensePlateNumber(licensplatenumber);
        if (residentId != null) {
            return FindResidentByResidentId(residentId);
        }
        return null;
    }

    // ========== ID查住戶 ==========
    @Override
    public ResidentTable FindResidentByResidentId(String residentid) {
        if (residentid == null || residentid.length() != 9) {
            return null;
        }
        return resDao.SelectResidentByResidentId(residentid);
    }

    // ========== 停車位查住戶 ==========
    @Override
    public ResidentTable FindResidentByParkingSpaceNumber(String parkingspacenumber) {
        if (parkingspacenumber == null || parkingspacenumber.length() != 5) {
            //  ////JOptionPane.showMessageDialog(null, "請選擇車位號碼!!", "系統提示", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return resDao.SelectResidentByParkingSpaceNumber(parkingspacenumber);
    }

    // ========== 地址查住戶 ==========
    @Override
    public ResidentTable FindResidentByAddressSimple(String addresssimple) {
        if (addresssimple == null || addresssimple.length() != 8) {
            // ////JOptionPane.showMessageDialog(null, "請選擇門牌號碼!!", "系統提示", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return resDao.SelectResidentByAddressSimple(addresssimple);
    }

	
	
	
	
	
	@Override
	public String UpdateResidentByResidentId(String residentid, String parking_space_owner, String parking_space_owner_phone, String parking_space_user, String parking_space_user_phone) {
		if 	(residentid.trim().length() > 6) {
		//ResidentService resService = new ResidentServiceImpl(); //多餘的
		//ResidentTable updateResident = resService.FindResidentByResidentIdWithObject(residentid); //多餘的
			ResidentTable updateResident = resDao.SelectResidentByResidentId(residentid);
			if (updateResident != null) { 
				/*residenttable.getResidentId(resident_id);*/
	            /*residenttable.setAddressSimple(address_simple);*/
	            /*residenttable.setAddressComplete(address_complete);*/
				updateResident.setParkingSpaceOwner(parking_space_owner);
				updateResident.setParkingSpaceOwnerPhone(parking_space_owner_phone);
				updateResident.setParkingSpaceUser(parking_space_user);
				updateResident.setParkingSpaceUserPhone(parking_space_user_phone);
		        /*updateResident.setParkingSpaceNumber(parking_space_number);*/
		        /*updateResident.setParkingSpaceFloor(parking_space_floor);*/
		        /*updateResident.setCreateDate(create_date);*/
		        updateResident.setUpdateDate(new GetDateTime().nowDataTime());
	            resDao.UpdateResidentByResidentId(updateResident);
				//JOptionPane.showMessageDialog(null, "更新成功", "系統提示", JOptionPane.INFORMATION_MESSAGE);
				return "success"; 
			} else {
					//JOptionPane.showMessageDialog(null, "更新失敗", "系統提示", JOptionPane.INFORMATION_MESSAGE);
					return null;
			}
		} else {
			//    JOptionPane.showMessageDialog(null, "請先查詢資料在更新!!", "系統提示", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
	}
		
	
	
	@Override //修改return為String
	public String FindAddressSimpleForCombobox() {
		List<String> list=resDao.SelectAddressSimpleForCombobox();
		String csvtxt="請選擇門牌,";
		list.sort(null);  //Arrays.sort()是給Array用的不能放入List
		for (String o:list) {
			csvtxt= csvtxt + o + ",";
		}
		return csvtxt;
	}

	
	
	


	@Override //修改return為String
	public String FindParkingSpaceNumberForCombobox() {
		List<String> list=resDao.SelectParkingSpaceNumberForCombobox();
		String csvtxt="請選擇車位,";
		list.sort(null);
		for (String o:list) {
			csvtxt= csvtxt + o + ",";
		}
		return csvtxt;
	}

	
	
	
	
	
	
	//=============================================================
	@Override
	public DefaultTableModel getAllResident() {
		List<ResidentTable> resTableList = resDao.SelectAllResident();
		DefaultTableModel resJtable = new DefaultTableModel();
		//建立欄位標題
		resJtable.addColumn("住戶ID");
		resJtable.addColumn("門牌");
		//resJtable.addColumn("地址門牌");
		resJtable.addColumn("車位所有人");
		resJtable.addColumn("所有人電話");
		resJtable.addColumn("緊急聯絡人");
		resJtable.addColumn("聯絡人電話");
		resJtable.addColumn("車位號碼");
		resJtable.addColumn("車位樓層");
		resJtable.addColumn("建立日期");
		resJtable.addColumn("更新日期");
		if (resTableList != null && !resTableList.isEmpty()) { 
			for(ResidentTable resTable : resTableList)
			{
				//將資料逐筆建立一維
				Object[] row=new Object[]{	resTable.getResidentId(),
											resTable.getAddressSimple(),
											resTable.getParkingSpaceOwner(),
											resTable.getParkingSpaceOwnerPhone(),
											resTable.getParkingSpaceUser(),
											resTable.getParkingSpaceUserPhone(),
											resTable.getParkingSpaceNumber(),
											resTable.getParkingSpaceFloor(),
											resTable.getCreateDate(),
											resTable.getUpdateDate()};
				//將一維陣列放入Jtable中
				resJtable.addRow(row);
			}
		 }
		return resJtable;
	}



//==================
	@Override
	public List<ResidentTable> getAllResidentWeb() {
		return resDao.SelectAllResident();
	}

//=============================================================


}
	

	
	
	
