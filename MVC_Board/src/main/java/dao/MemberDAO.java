package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.omg.PortableInterceptor.InvalidSlot;

import vo.MemberDTO;

import static db.JdbcUtil.*;

public class MemberDAO {
	
	private MemberDAO() {}
	
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private Connection con;
	
	public void setConnection(Connection con) {
		this.con = con;
	}

	public ArrayList<MemberDTO> getMemberList() {
		
		ArrayList<MemberDTO> list = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM member";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<MemberDTO>();
			
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setName(req);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberDAO - getMemberList 오류");
		}
		
		
		
		return list;
	}

	public boolean selectDuplicateId(String id) {

		boolean isDuplicate = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT id FROM member WHERE id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isDuplicate = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MemberDAO - selectDuplicateId 오류");
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return isDuplicate;
	}

	public int insertMember(MemberDTO member) {

		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO member VALUES(?, ?, ?, ?, ?, ?, ?, now())";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getId());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPost_code());
			pstmt.setString(6, member.getAddress1());
			pstmt.setString(7, member.getAddress2());
			
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MemberDAO - insertMember 오류");
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}

	public boolean selectMember(MemberDTO member) {

		boolean isLoginSuccess = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM member WHERE id=? AND passwd=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isLoginSuccess = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MemberDAO - selectMember 오류");
		}
		
		return isLoginSuccess;
	}
	
}
