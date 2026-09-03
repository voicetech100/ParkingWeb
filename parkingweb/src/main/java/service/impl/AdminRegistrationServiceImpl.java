package service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dao.AdminRegistrationDao;
import dao.impl.AdminRegistrationDaoImpl;
import model.AdminRegistrationTable;
import service.AdminRegistrationService;
import util.GetDateTime;

public class AdminRegistrationServiceImpl implements AdminRegistrationService{

	public static void main(String[] args) {
		//=====ForTestOnly===========================================
		AdminRegistrationTable adm = new AdminRegistrationTable();
		adm.setAccount("admin");
		adm.setPassword("123402222");
		adm.setName("Terence");
		adm.setPhone("123459");
		adm.setDate(new GetDateTime().nowDataTime());
		System.out.println(new AdminRegistrationServiceImpl().FindAccountList(adm));
		System.out.println(new AdminRegistrationServiceImpl().FindAccountList(adm).get(0).getPassword());
		
	}
	
	
	
	//===========================================================
	@Override  //20260818 add
	public List<AdminRegistrationTable> FindAllManager(AdminRegistrationTable adminregistration) {
		System.out.println("進入@AdmRegServiceFindAllManager");
		if (new AdminRegistrationServiceImpl().CheckAccount(adminregistration) == null){
			return Collections.emptyList();
		}		
		System.out.println("回到@AdmRegServiceFindAllManager");
		List<AdminRegistrationTable> admList = new AdminRegistrationDaoImpl().SelectAllManager(adminregistration);
		if (admList!=null) {
			admList.forEach(o->o.setPassword("***")); //20260901 change password to *				
			return admList;
		}
		System.out.println("回到@AdmRegServiceFindAllManager");
		return admList != null ? admList : Collections.emptyList();
	}

	
	
	
	
	@Override
	public List<AdminRegistrationTable> FindAccountList(AdminRegistrationTable adminregistration) {
		if (new AdminRegistrationServiceImpl().CheckAccount(adminregistration) != null){
			List<AdminRegistrationTable> admList = new ArrayList<>();
			admList = new AdminRegistrationDaoImpl().SelectAccountList(adminregistration);
			admList.forEach(o->o.setPassword("***")); //20260901 change password to *	
			return admList;
		}
		return null;
	}

	
	
	
	
	@Override
	public int UpdateAccount(AdminRegistrationTable adminregistration) {
		System.out.println("進入@AdmRegServiceUpdateAccount");
		int i=0;
		if (adminregistration != null && adminregistration.getAccount() != null && adminregistration.getAccount().trim().length() > 0) {
			 List<AdminRegistrationTable> list = new ArrayList<>();
			 list = new AdminRegistrationServiceImpl().CheckAccount(adminregistration);
			 //20260901 add Encryption for password
			 System.out.println("回到@AdmRegServiceUpdateAccount");
			 System.out.println("要更新的user查到為:\t"+ (list!=null&&list.size()>0));
			 if (list != null && list.size() > 0){
	    		String hasHDbPassword=list.get(0).getPassword(); //先取得原始資料庫加密的密碼
	    		String userPassword=adminregistration.getPassword();
	    		String userHashPassword=util.EncryptionUtil.hashPassword(userPassword);
	    		if (userPassword.equals("***")){
	    			adminregistration.setPassword(hasHDbPassword); //原資料庫密碼寫入
	    		} else {
	    			adminregistration.setPassword(userHashPassword); //新密碼寫入
	    		}
	    		adminregistration.setDate(new GetDateTime().nowDataTime());
	    		System.out.println("修改的user帳號\t"+ adminregistration.getAccount());
	    		System.out.println("修改的user密碼\t"+ userPassword);
	    		System.out.println("修改的user雜湊密碼\t"+ userHashPassword);
				System.out.println("DB密碼\t"+ hasHDbPassword);
	    		i=new AdminRegistrationDaoImpl().UpdateAccount(adminregistration);
				//JOptionPane.showMessageDialog(null, "更新成功", "系統提示", JOptionPane.INFORMATION_MESSAGE);
				return i; 
			} else {
				//JOptionPane.showMessageDialog(null, "更新失敗,帳號或密碼不吻合", "系統提示", JOptionPane.INFORMATION_MESSAGE);
			}
			return i;
		} else {
			//JOptionPane.showMessageDialog(null, "請輸入帳號查詢!!", "系統提示", JOptionPane.INFORMATION_MESSAGE);
		}
	return i;

	}

	
	
	
	
	@Override
	public String InsertAccount(AdminRegistrationTable adminregistration) {
		System.out.println("@AdmRegServiceInsert11111");
		int i = 0;
	    //阻斷非法的無效請求（防禦設計）,檢查必要欄位是否為空
	    if (adminregistration == null) {
	    	return "資料不能為空！";
	    }
	    if (adminregistration.getAccount() == null || adminregistration.getAccount().trim().isEmpty()) {
	    	return "請輸入帳號！";
	    }
	    if (adminregistration.getPassword() == null || adminregistration.getPassword().trim().isEmpty()) {
	    	return "請輸入密碼！";
	    }
	    if (adminregistration.getName() == null || adminregistration.getName().trim().isEmpty()) {
	    	return "請輸入姓名！";
	    }
	    if (adminregistration.getPhone() == null || adminregistration.getPhone().trim().isEmpty()) {
	    	return "請輸入電話！";
	    }
	    if (adminregistration.getClass1() == null || adminregistration.getClass1().trim().isEmpty()) {
	    	return "請輸入等級！";
	    }
	    
	    List<AdminRegistrationTable> list = new AdminRegistrationServiceImpl().CheckAccount(adminregistration);
	    System.out.println("判斷新增的user存在\t" + list != null);
	    if (list != null && !list.isEmpty()){ //必須同時判斷list不為null且裡面「有資料」
	    	return "帳號已存在!";
	    }
	    
	    AdminRegistrationDao admResDao = new AdminRegistrationDaoImpl();
        String hashPassword=util.EncryptionUtil.hashPassword(adminregistration.getPassword());//傳進來的密碼加密
    	adminregistration.setPassword(hashPassword);
        adminregistration.setDate(new GetDateTime().nowDataTime());
        System.out.println(adminregistration);
        i = admResDao.InsertAccount(adminregistration);
        System.out.println(i);
        return i>0 ?  "新增成功!" : "新增失敗"; 
    
	}
	
	
	
	
	@Override
	public List<AdminRegistrationTable> CheckAccount(AdminRegistrationTable adminregistration) {
		List<AdminRegistrationTable> list = new ArrayList<>();
		System.out.println("@AdmRegServiceCheckAccount22222");
		AdminRegistrationDao admResDao = new AdminRegistrationDaoImpl();
		list = admResDao.SelectAccount(adminregistration);
		//JOptionPane.showMessageDialog(null, "查無帳號!!", "系統提示", JOptionPane.INFORMATION_MESSAGE);
		return list;
		
		
	}



	
	
	@Override
	public int DeleteAccount(AdminRegistrationTable adminregistration) {
		int i=0;
		 if (adminregistration != null && adminregistration.getAccount() != null && adminregistration.getAccount().trim().length() > 0) {
			if (new AdminRegistrationServiceImpl().CheckAccount(adminregistration) != null){
				AdminRegistrationDao admResDao = new AdminRegistrationDaoImpl();
				i=admResDao.DeleteAccount(adminregistration);
				//JOptionPane.showMessageDialog(null, "刪除成功", "系統提示", JOptionPane.INFORMATION_MESSAGE);
				return i; 
			} else {
				//JOptionPane.showMessageDialog(null, "刪除失敗,無此帳號", "系統提示", JOptionPane.INFORMATION_MESSAGE);
			}
			return i;
		} else {
			//JOptionPane.showMessageDialog(null, "請輸入帳號查詢!!", "系統提示", JOptionPane.INFORMATION_MESSAGE);
		}
	return i;
	}

	
	
	
	
	@Override
	public List<AdminRegistrationTable> CheckAccountPasswordWeb(AdminRegistrationTable adminregistration) {
		System.out.println("@ServiceCheckAccountPasswordWeb=>進入");
		String userPassword = adminregistration.getPassword();
		AdminRegistrationDao admResDao = new AdminRegistrationDaoImpl();
		List<AdminRegistrationTable> list = admResDao.SelectAccountPasswordWeb(adminregistration);  //20260820 add for Web
		String hasHDbPassword ="";
		if (list !=null && !list.isEmpty()){
			AdminRegistrationTable dbadm=list.getFirst();
			hasHDbPassword = dbadm.getPassword();
		}
		
		boolean b = util.EncryptionUtil.checkPassword(userPassword, hasHDbPassword);
		System.out.println("@ServiceCheckAccountPasswordWeb=>返回");
		System.out.println("user帳號\t"+adminregistration.getAccount());
		System.out.println("user密碼\t"+userPassword);
		System.out.println("Db密碼:\t"+hasHDbPassword);
		System.out.println("帳密比對結果:\t" + b);
	
		return b? list : new ArrayList<AdminRegistrationTable>(); 
	}
	
}
	
	
