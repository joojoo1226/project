package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
	// 데이터베이스 접속 수행 후 접속 성공 시 Connection 객체를 리턴하는 getConnection() 메서드
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/funweb";
			String username = "root";
			String password = "1234";
			
			// 1단계. 드라이버 로드
			Class.forName(driver);
			
			// 2단계. DB 연결
			con = DriverManager.getConnection(url, username, password);
			System.out.println("getConnection() - DB 접속 성공");
		} catch (ClassNotFoundException e) {
			// Class.forName(driver) 코드에서 예외(오류) 발생 시 실행할 코드를 기술하는 블록
			// => 드라이버 클래스를 찾지 못할 경우 발생
			System.out.println("드라이버 로드 실패!");
			e.printStackTrace();
		} catch (SQLException e) {
			// DriverManager.getConnection(url, username, password); 코드에서 예외 발생 시
			// => DB 접속 정보가 잘못되었거나, DB 문제로 접속에 실패할 경우 발생
			System.out.println("DB 연결 실패!");
			e.printStackTrace();
		}
		
		return con;
	}
	
	// 자원 반환을 수행하는 close() 메서드 정의 - Connection, PreparedStatement, ResultSet 오버로딩
	public static void close(Connection con) {
		try {
			if(con != null) { 
				con.close(); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null) { 
				pstmt.close(); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			if(rs != null) { 
				rs.close(); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}













