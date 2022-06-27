package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JdbcUtil;

public class MemberDAO {

	public int insert(MemberDTO dtoM) {
		
		int insertCount = 0;
		
		Connection con = JdbcUtil.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO member VALUES (?, ?, ?, ?, ?, ?, ?, now())";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dtoM.getId());
			pstmt.setString(2, dtoM.getPass());
			pstmt.setString(3, dtoM.getName());
			pstmt.setString(4, dtoM.getEmail());
			pstmt.setString(5, dtoM.getAddress());
			pstmt.setString(6, dtoM.getPhone());
			pstmt.setString(7, dtoM.getMobile());
			
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			//3||4 단계에서 오류 발생 시 실행
			System.out.println("SQL 구문 오류 발생! - " + e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return insertCount;
		
	}
	
	public boolean login(MemberDTO dtoM) {
		
		boolean isLoginSuccess = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = JdbcUtil.getConnection();
		
		try {
			String sql = "SELECT * FROM member WHERE id=? AND pass=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dtoM.getId());
			pstmt.setString(2, dtoM.getPass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isLoginSuccess = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("login() 오류" + e.getMessage());
		} finally {
			
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
			
		}
		
		return isLoginSuccess;
	}
	
	public boolean checkUser(String id) {
		
		boolean isCheckId = false;
		
		Connection con = JdbcUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT id FROM member WHERE id=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				isCheckId = true;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			System.out.println("SQL 구문 오류");
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return isCheckId;
		
	}
	
	public ArrayList memberList() {
		
		MemberDTO dtoM = null;
		ArrayList list = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			String sql = "SELECT * FROM member";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()) {
				
				dtoM = new MemberDTO();
				
				dtoM.setId(rs.getString("id"));
				dtoM.setPass(rs.getString("pass"));
				dtoM.setName(rs.getString("name"));
				dtoM.setEmail(rs.getString("email"));
				dtoM.setAddress(rs.getString("address"));
				dtoM.setPhone(rs.getString("phone"));
				dtoM.setMobile(rs.getString("mobile"));
				dtoM.setDate(rs.getDate("date"));
				
				list.add(dtoM);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("memberList() 오류" + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return list;
		
	}
	
	public MemberDTO selectMember(String id) {
		
		MemberDTO member = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			String sql = "SELECT * FROM member WHERE id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				member = new MemberDTO();
				
				member.setId(rs.getString("id"));
				member.setPass(rs.getString("pass"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
				member.setPhone(rs.getString("phone"));
				member.setMobile(rs.getString("mobile"));
				member.setDate(rs.getDate("date"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("selectMember() 오류" + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return member;
		
	}
	
}
