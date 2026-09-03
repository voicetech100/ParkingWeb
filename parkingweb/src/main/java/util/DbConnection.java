package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	public static void main(String[] args) {
		//System.out.println(DbConnection.getDb());//連線成功會看到記憶體位址 com.mysql.cj.jdbc.ConnectionImpl@2034b64c
	}

	public static Connection getDb() {  //需匯入import java.sql.Connection
		String serverAccount="root";
		String serverPassword="1234";
		String serverUrl="jdbc:mysql://localhost:3306/gjun";
		Connection conn=null; //要先宣告一個值
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(serverUrl, serverAccount, serverPassword);
		} catch (ClassNotFoundException e) {
			System.out.println("no Driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("no connection");
			e.printStackTrace();
		}
		return conn; //return回來的是一個物件
	}

}