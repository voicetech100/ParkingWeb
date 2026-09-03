package model;

public class AdminRegistrationTable {
	
	int id;
	String account;
	String password;
	String name;
	String phone;
	String date;
	String class1;
			
	public AdminRegistrationTable() {
		super();
	}

	
	public AdminRegistrationTable(int id, String account, String password, String name, String phone, String date,
			String class1) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.date = date;
		this.class1 = class1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClass1() {
		return class1;
	}

	public void setClass1(String class1) {
		this.class1 = class1;
	}
	
	
	

}
