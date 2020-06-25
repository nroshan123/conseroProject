package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {

	private static Connection conn;
	private static Statement sqlStatement;
	private static ResultSet resultSet;
	static String accountNumber="";
	
	public static String openConnection( String sqlQuery) {
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://207.200.8.7:1433;databaseName=UAT_TEXPO_SEP_2019","sai","emP0wer2o!8CompareUATsai");
		
			sqlStatement = conn.createStatement();
			resultSet = sqlStatement.executeQuery(sqlQuery);
			while(resultSet.next()) {
				accountNumber = resultSet.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return accountNumber;
	}
}
