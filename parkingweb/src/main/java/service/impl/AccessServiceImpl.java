package service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import dao.AccessDao;
import dao.impl.AccessDaoImpl;
import model.AccessTable;
import model.CarRegistrationTable;
import service.AccessService;
import service.CarRegistrationService;
import util.GetDateTime;
import view.ComboResidentParking;
import view.ComboResidentParkingDao;
import view.ComboResidentParkingDaoImpl;
import view.LicensePlateNumberListEE;
import view.LicensePlateNumberListEEDaoImpl;

public class AccessServiceImpl implements AccessService{
	
	public static void main(String[] args) {
		//====驗證測試====
		//new AccessLogServiceImpl().InsertCarEntryAndExitLog("AAA-1111");
		//new AccessLogServiceImpl().AuthorizationEntry("BBB-4444");
		//new AccessLogServiceImpl().AuthorizationExit("BBB-3333");
		new AccessServiceImpl().getEntryExit("2026-01-01","2026-12-31", "AAA-1111");
	}

	
	
	//======================================================================================
	
	
	@Override
	public String AuthorizationEntry(String licenseplatenumber) {
		String carCheckResult = new CarRegistrationServiceImpl().CheckCarRegistrationExistByLicensePlateNumber(licenseplatenumber);
		if (carCheckResult != null) { //車牌登記ok
			//System.out.println("@1111111");
			List<CarRegistrationTable> carlist = new CarRegistrationServiceImpl().GetCarRegistrationByLicensePlateNumber(licenseplatenumber);
			//System.out.println("@222222");														
				if (!carlist.isEmpty()) {  //判定登記數量
					//System.out.println(carlist.get(0).get(0));//residentid
					//System.out.println("@1111111");//occupied_available
					List<CarRegistrationTable> residentId=new CarRegistrationServiceImpl().GetCarRegistrationByResidentId(carlist.get(0).getResidentId());
					boolean entryOk = true;
					
					for (CarRegistrationTable o:residentId) {  //判定停車位使用
						//System.out.println(o.get(3)+"\t\t"+o.get(4)); //license_plate_number & occupied_available
						if (o.getOccupiedAvailable().equals("Inside")) 
							entryOk = false;
					}
					//System.out.println(carlist.get(0).get(3)); //車牌
					if (entryOk) { //true表示可以進入停車
						//更新停車狀態
						List<CarRegistrationTable> carnumberArr=new CarRegistrationServiceImpl().GetCarRegistrationByLicensePlateNumber(carlist.get(0).getLicensePlateNumber());//車牌號;
						CarRegistrationTable carregistrationtable = new CarRegistrationTable();
						for (CarRegistrationTable o:carnumberArr) {
							//System.out.println(o.get(5));
							carregistrationtable.setResidentId(o.getResidentId());
							carregistrationtable.setAddressSimple(o.getAddressSimple());
							carregistrationtable.setCarSerialNumber(o.getCarSerialNumber());
							carregistrationtable.setLicensePlateNumber(o.getLicensePlateNumber());
							carregistrationtable.setOccupiedAvailable("Inside");//carregistrationtable.setOccupiedAvailable("CarInside");
							carregistrationtable.setCarUser(o.getCarUser());
							carregistrationtable.setCarUserPhone(o.getCarUserPhone());
							carregistrationtable.setCarRegistrationDate(o.getCarRegistrationDate());
						}
						
						new CarRegistrationServiceImpl().UpdateCarRegistrationByLicensePlateNumber(carregistrationtable);
						new AccessServiceImpl().InsertCarEntryLog(licenseplatenumber);
						new util.ConnSsh().openParkingGate();
						
						//new CarRegistrationServiceImpl()carCheckResult.getCarRegistrationByLicensePlateNumber(licenseplatenumber);
						//new AccessLogServiceImpl
						return "授權成功~(入口!)~";
						//JOptionPane.showMessageDialog(null, "授權開門進", "系統提示", JOptionPane.INFORMATION_MESSAGE);
					
					} else {
						return "授權失敗~(禁入!)~車位已在使用中";
						//JOptionPane.showMessageDialog(null, "禁止!    訊息:車位已在使用中!!    \n\n(acc3)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}else {
					return "授權失敗~(禁入!)~拒絕未登記車輛進入";
					//JOptionPane.showMessageDialog(null, "禁止!   訊息:拒絕未登記車輛進!!     \n\n(acc2)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
				} 
		} else {
			return "授權失敗~(禁入!)~非登記車輛";
			//JOptionPane.showMessageDialog(null, "禁止!   訊息:拒絕未登記車輛進入!!    \n\n(acc1)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
		}
		//return null;
	}
	
	
	





	@Override
	public String AuthorizationExit(String licenseplatenumber) {
		String carCheckResult = new CarRegistrationServiceImpl().CheckCarRegistrationExistByLicensePlateNumber(licenseplatenumber);
		if (carCheckResult != null) { //車牌登記ok
			List<CarRegistrationTable> carlist = new CarRegistrationServiceImpl().GetCarRegistrationByLicensePlateNumber(licenseplatenumber);
				if (!carlist.isEmpty()) {  //判定登記數量
					//System.out.println(carlist.get(0).get(0));//residentid
					//System.out.println(carlist.get(0).get(4));//occupied_available
					//ArrayList<ArrayList<String>> residentId=new CarRegistrationServiceImpl().getCarRegistrationByResidentId(carlist.get(0).get(0));
					//boolean entryOk = true;
					
					//for (ArrayList<String> o:residentId) {  //判定停車位使用
					//	//System.out.println(o.get(3)+"\t\t"+o.get(4)); //license_plate_number & occupied_available
					//	if (o.get(4).equals("CarOutside")) 
					//		entryOk = false;
					//}
					//System.out.println(carlist.get(0).get(3)); //車牌
					//if (entryOk) { //true表示可以進入停車
						
					//更新停車狀態
						List<CarRegistrationTable> carnumberArr=new CarRegistrationServiceImpl().GetCarRegistrationByLicensePlateNumber(licenseplatenumber);//車牌號;
						CarRegistrationTable carregistrationtable = new CarRegistrationTable();
						for (CarRegistrationTable o:carnumberArr) {
							//System.out.println(o.get(5));
							carregistrationtable.setResidentId(o.getResidentId());
							carregistrationtable.setAddressSimple(o.getAddressSimple());
							carregistrationtable.setCarSerialNumber(o.getCarSerialNumber());
							carregistrationtable.setLicensePlateNumber(o.getLicensePlateNumber());
							carregistrationtable.setOccupiedAvailable("");//carregistrationtable.setOccupiedAvailable("CarInside");
							carregistrationtable.setCarUser(o.getCarUser());
							carregistrationtable.setCarUserPhone(o.getCarUserPhone());
							carregistrationtable.setCarRegistrationDate(o.getCarRegistrationDate());
						}
						
						new CarRegistrationServiceImpl().UpdateCarRegistrationByLicensePlateNumber(carregistrationtable);
						new AccessServiceImpl().InsertCarExitLog(licenseplatenumber);
						new util.ConnSsh().openParkingGate();
						//new CarRegistrationServiceImpl()carCheckResult.getCarRegistrationByLicensePlateNumber(licenseplatenumber);
						//new AccessLogServiceImpl
						return "授權成功~(出口)~";
						//JOptionPane.showMessageDialog(null, "授權開門出", "系統提示", JOptionPane.INFORMATION_MESSAGE);
					
					//} else {
					//	JOptionPane.showMessageDialog(null, "禁止!    訊息:車未已在使用中!!    \n\n(acc3)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
					//}
				} else {
					return "授權失敗~(禁出)~非登記車輛";
					//JOptionPane.showMessageDialog(null, "禁止!   訊息:拒絕未登記車輛進出!!     \n\n(acc2)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
				} 
		} else {
			return "授權失敗~(禁出)~非登記車輛";
			//JOptionPane.showMessageDialog(null, "禁止!   訊息:拒絕未登記車輛進出!!    \n\n(acc1)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
		}
		//return null;
		
	}
	
	
	
	
	
	
	
	@Override
	public String InsertCarEntryLog(String licenseplatenumber) {
		CarRegistrationService carService = new CarRegistrationServiceImpl();
		//從Service抓...先抓取原本車牌登記資料
		List<CarRegistrationTable> carRegService = carService.GetCarRegistrationByLicensePlateNumber(licenseplatenumber);
		//System.out.println(carRegService);
		if (carRegService != null && !carRegService.isEmpty()) {  //確認
			for (CarRegistrationTable o:carRegService) {
				//System.out.println(o.get(0));//.getResidentId());
				//System.out.println(o.get(1));//.getAddressSimple());
				//System.out.println(o.get(2));//.getCarSerialNumber());
				//System.out.println(o.get(3));//.getLicensePlateNumber());//v
				//System.out.println(o.get(4));//.getOccupiedAvailable());
				//System.out.println(o.get(5));//.getCarUser());//v
				//System.out.println(o.get(6));//.getCarUserPhone());//v
				//System.out.println(o.get(7));//.getCarRegistrationDate());
				//產生記憶體及寫入
				AccessTable accesslogtable = new AccessTable(); 
				accesslogtable.setDate(new GetDateTime().nowDataTime());
				accesslogtable.setLicensePlateNumber(licenseplatenumber);
				accesslogtable.setCarUser(o.getCarUser()); //.getCarUser());
				accesslogtable.setCarUserPhone(o.getCarUserPhone());//.getCarUserPhone());
				accesslogtable.setEntryExit("Entry"); 
				accesslogtable.setReason("normal");
				accesslogtable.setAlert("normal");
				//執行寫入DB
				AccessDao accdao = new AccessDaoImpl();
				accdao.InsertAccessLog(accesslogtable);
				//JOptionPane.showMessageDialog(null, "Log寫入成功!!      \n\n訊息:(acc)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
				return "success";
			}
		}
		//JOptionPane.showMessageDialog(null, "EntryLogWriteFail!!\n\n訊息:(acc)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
		return null;
	}

	
	
	
	
	
	
	@Override
	public String InsertCarExitLog(String licenseplatenumber) {
		CarRegistrationService carService = new CarRegistrationServiceImpl();
		//從Service抓...先抓取原本車牌登記資料
		List<CarRegistrationTable> carRegService = carService.GetCarRegistrationByLicensePlateNumber(licenseplatenumber);
		//System.out.println(carRegService);
		if (carRegService != null && !carRegService.isEmpty()) {  //確認
			for (CarRegistrationTable o:carRegService) {
				//System.out.println(o.get(0));//.getResidentId());
				//System.out.println(o.get(1));//.getAddressSimple());
				//System.out.println(o.get(2));//.getCarSerialNumber());
				//System.out.println(o.get(3));//.getLicensePlateNumber());//v
				//System.out.println(o.get(4));//.getOccupiedAvailable());
				//System.out.println(o.get(5));//.getCarUser());//v
				//System.out.println(o.get(6));//.getCarUserPhone());//v
				//System.out.println(o.get(7));//.getCarRegistrationDate());
				//產生記憶體及寫入
				AccessTable accesslogtable = new AccessTable(); 
				accesslogtable.setDate(new GetDateTime().nowDataTime());
				accesslogtable.setLicensePlateNumber(licenseplatenumber);
				accesslogtable.setCarUser(o.getCarUser()); //.getCarUser());
				accesslogtable.setCarUserPhone(o.getCarUserPhone());//.getCarUserPhone());
				accesslogtable.setEntryExit("Exit"); 
				accesslogtable.setReason("normal");
				accesslogtable.setAlert("normal");
				//執行寫入DB
				AccessDao accdao = new AccessDaoImpl();
				accdao.InsertAccessLog(accesslogtable);
				//JOptionPane.showMessageDialog(null, "Log寫入成功!!    \n\n訊息:(acc)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
				return "success";
			}
		}
		//JOptionPane.showMessageDialog(null, "ExitLogWriteFail!!\n\n訊息:(acc)", "系統提示", JOptionPane.INFORMATION_MESSAGE);
		return null;
	}



	
	
	
	
	
	@Override
	public DefaultTableModel getEntryExit(String startDate, String endDate, String LicensePlateNumbet ) {
		AccessDao accDao = new AccessDaoImpl();
		List<AccessTable> accTableList = accDao.selectAllAccess(startDate, endDate, LicensePlateNumbet);
		DefaultTableModel accJtable = new DefaultTableModel();
		//建立欄位標題
		accJtable.addColumn("進出日期");
		accJtable.addColumn("車牌");
		accJtable.addColumn("使用人");
		accJtable.addColumn("使用人電話");
		accJtable.addColumn("進或出");
		accJtable.addColumn("進出狀態");
		accJtable.addColumn("警示訊息");
		if (accTableList != null && !accTableList.isEmpty()) { 
			for(AccessTable accTable : accTableList)
			{
				Object[] row=new Object[]{	accTable.getDate(),
											accTable.getLicensePlateNumber(),
											accTable.getCarUser(),
											accTable.getCarUserPhone(),
											accTable.getEntryExit(),
											accTable.getReason(),
											accTable.getAlert()};
				accJtable.addRow(row);
			}
		 }
		return accJtable;
	}


	
	
	
	@Override
	public List<AccessTable> getEntryExitWeb(String startDate, String endDate, String LicensePlateNumbet ) {
		AccessDao accDao = new AccessDaoImpl();
		List<AccessTable> accTableList = accDao.selectAllAccess(startDate, endDate, LicensePlateNumbet);
		return accTableList;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//================================================================
	@Override
	public DefaultTableModel getAllComboResidentParking() {
		ComboResidentParkingDao comParkingDao = new ComboResidentParkingDaoImpl();
		List<ComboResidentParking> comResParkingTableList = new ArrayList<>();
		comResParkingTableList = comParkingDao.selectAllComResidentParking();
		DefaultTableModel accJtable = new DefaultTableModel();
		accJtable.addColumn("住戶ID");
		accJtable.addColumn("門牌");
		accJtable.addColumn("車位號碼");
		accJtable.addColumn("停車狀態");
		accJtable.addColumn("停車號");
		accJtable.addColumn("車位樓層");
		accJtable.addColumn("持有人");
		accJtable.addColumn("持有人電話");
		accJtable.addColumn("使用者");
		accJtable.addColumn("使用者電話");
		if ( comResParkingTableList != null && !comResParkingTableList.isEmpty()) {
			for (ComboResidentParking comResParkingTable:comResParkingTableList) {
				Object[] row = new Object[]{comResParkingTable.getResidentId(),
											comResParkingTable.getAddressSimple(),
											comResParkingTable.getParkingSpaceNumber(),
											comResParkingTable.getOccupiedAvailable(),
											comResParkingTable.getLicensePlateNumber(),
											comResParkingTable.getParkingSpaceFloor(),
											comResParkingTable.getParkingSpaceOwner(),
											comResParkingTable.getParkingSpaceOwnerPhone(),
											comResParkingTable.getCarUser(),
											comResParkingTable.getCarUserPhone()};
				accJtable.addRow(row);
			}
		}
		return accJtable;
	}


	
	//=====vo已是更新的資料,抓vo就可以了======
	@Override
	public List<ComboResidentParking> getAllComboResidentParkingWeb() {
		ComboResidentParkingDao comParkingDao = new ComboResidentParkingDaoImpl();
		return comParkingDao.selectAllComResidentParking();
		}
		
	
	
	
//================================================================================


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	@Override
	public List<LicensePlateNumberListEE> getLicensePlateNumberEE() {
		return new LicensePlateNumberListEEDaoImpl().selectAllLicencePlateNumberEntryExit();
	}



	
	
	
}
