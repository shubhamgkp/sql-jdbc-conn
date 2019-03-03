package jdbcConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class JDBC_Connection {

	public static void main(String[] args) {

		Connection conn = null;
		try {

			String userName = "root";
			String password = "admin";
			String query = "select * from student where marks=50";

			String url = "jdbc:mysql://localhost:3306/studentdb?useSSL=false";
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Database connection established!!");

			Statement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				driver.get("https://www.facebook.com/");
				driver.manage().window().maximize();
				driver.findElement(By.name("email")).sendKeys(rs.getString("firstname"));
				driver.findElement(By.name("pass")).sendKeys(rs.getString("lastname"));
			}

		} catch (Exception e) {
			System.err.println("Cannot connect to database server!!");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {

			if (conn != null) {
				try {
					conn.close();
					System.out.println("Database Connection Terminated!!");
				} catch (Exception e) {
				}
			}
		}
	}
}
