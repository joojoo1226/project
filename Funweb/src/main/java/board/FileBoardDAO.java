package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;

public class FileBoardDAO {
	
	public int insertFileBoard(FileBoardDTO fileBoard) {
		
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			int idx = 1;
			
			String sql = "SELECT MAX(idx) FROM file_board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				idx = rs.getInt(1) + 1;
				
			}
			
			String sql2 = "INSERT INTO file_board VALUES (?, ?, ?, ?, ?, ?, ?, now(), 0)";
			
			pstmt2 = con.prepareStatement(sql2);
			
			pstmt2.setInt(1, idx);
			pstmt2.setString(2, fileBoard.getName());
			pstmt2.setString(3, fileBoard.getPass());
			pstmt2.setString(4, fileBoard.getSubject());
			pstmt2.setString(5, fileBoard.getContent());
			pstmt2.setString(6, fileBoard.getRealFile());
			pstmt2.setString(7, fileBoard.getOriginalFile());
			
			insertCount = pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insertFileBoard 오류" + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(con);
		}
		
		return insertCount;
		
	}
	
	// 전체 게시물 수 조회를 수행하는 selectListCount() 메서드 정의
	// => 파라미터 : 없음, 리턴타입 : int(listCount)
	public int selectListCount() {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			// 3단계
			String sql = "SELECT COUNT(*) FROM file_board";
			pstmt = con.prepareStatement(sql);
			
			// 4단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 발생! - " + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return listCount;
	}
	
	public int selectListCount(String searchField, String keyword) {
		int listCount = 0;
		String sql = "";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			// 3단계
//			String sql = "SELECT COUNT(*) FROM file_board WHERE subject LIKE ?";
			
			if(searchField.equals("subject")) {
				sql = "SELECT COUNT(*) FROM board WHERE subject LIKE ?";
			} else if(searchField.equals("name")) {
				sql = "SELECT COUNT(*) FROM board WHERE name LIKE ?";
			} else if(searchField.equals("content")) {
				sql = "SELECT COUNT(*) FROM board WHERE content LIKE ?";
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			
			// 4단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("selectListCount(file) 오류" + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return listCount;
	}
	
	// 게시물 목록을 조회하는 selectBoardList() 메서드 정의
	// => 파라미터 : 현재 페이지 번호, 페이지 당 게시물 수
	// => 리턴타입 : List(ArrayList 객체)
	public List selectBoardList(int pageNum, int listLimit) {
		
		List boardList = null;
		
		// 현재 페이지 번호를 활용하여 조회 시 시작행 번호 계산
		int startRow = (pageNum - 1) * listLimit;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			String sql = "SELECT * FROM file_board ORDER BY idx DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 List(ArrayList) 객체 생성
			boardList = new ArrayList(); // ArrayList -> List 업캐스팅
			
			while(rs.next()) {
				// 1개 게시물(레코드)을 저장할 BoardDTO 객체 생성
				FileBoardDTO fileBoard = new FileBoardDTO();
				
				fileBoard.setIdx(rs.getInt("idx"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setPass(rs.getString("pass"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setContent(rs.getString("content"));
				fileBoard.setRealFile(rs.getString("real_file"));
				fileBoard.setOriginalFile(rs.getString("original_file"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
				
				// 1개 게시물을 다시 전체 게시물 저장 객체(boardList)에 추가
				boardList.add(fileBoard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 발생! - selectBoardList() : " + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardList;
	}
	
	public List selectBoardList(String searchField, String keyword, int pageNum, int listLimit) {
		
		List boardList = null;
		
		String sql ="";
		
		// 현재 페이지 번호를 활용하여 조회 시 시작행 번호 계산
		int startRow = (pageNum - 1) * listLimit;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
//			String sql = "SELECT * FROM file_board WHERE subject LIKE ? ORDER BY idx DESC LIMIT ?,?";
			
			if(searchField.equals("subject")) {
				sql = "SELECT * FROM file_board WHERE subject LIKE ? ORDER BY idx DESC LIMIT ?,?";
			} else if(searchField.equals("name")) {
				sql = "SELECT * FROM file_board WHERE name LIKE ? ORDER BY idx DESC LIMIT ?,?";
			} else if(searchField.equals("content")) {
				sql = "SELECT * FROM file_board WHERE content LIKE ? ORDER BY idx DESC LIMIT ?,?";
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 List(ArrayList) 객체 생성
			boardList = new ArrayList(); // ArrayList -> List 업캐스팅
			
			while(rs.next()) {
				// 1개 게시물(레코드)을 저장할 BoardDTO 객체 생성
				FileBoardDTO fileBoard = new FileBoardDTO();
				fileBoard.setIdx(rs.getInt("idx"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setPass(rs.getString("pass"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setContent(rs.getString("content"));
				fileBoard.setRealFile(rs.getString("real_file"));
				fileBoard.setOriginalFile(rs.getString("original_file"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
				
				System.out.println(fileBoard.getIdx() + ", " + fileBoard.getSubject());
				
				// 1개 게시물을 다시 전체 게시물 저장 객체(boardList)에 추가
				boardList.add(fileBoard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 발생! - selectBoardList() : " + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardList;
	}
	
	// 게시물 조회수 증가를 수행하는 updateReadcount() 메서드 정의
	// => 파라미터 : 글번호(idx), 리턴타입 : void
	public void updateReadcount(int idx) {
		PreparedStatement pstmt = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			String sql = "UPDATE file_board SET readcount=readcount+1 WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - updateReadcount() : " + e.getMessage());
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
	}
		
	// 게시물 1개 정보 조회를 수행하는 selectBoard() 메서드 정의
	// => 파라미터 : 글번호(idx), 리턴타입 : BoardDTO(board)	
	public FileBoardDTO selectBoard(int idx) {
		
		FileBoardDTO fileBoard = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			String sql = "SELECT * FROM file_board WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 1개 게시물(레코드)을 저장할 BoardDTO 객체 생성
				fileBoard = new FileBoardDTO();
				fileBoard.setIdx(rs.getInt("idx"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setPass(rs.getString("pass"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setContent(rs.getString("content"));
				fileBoard.setRealFile(rs.getString("real_file"));
				fileBoard.setOriginalFile(rs.getString("original_file"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 발생! - selectBoard() : " + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return fileBoard;
	}
	
	// 작성자가 입력한 패스워드 확인
	// => board 테이블의 idx 가 일치하는 게시물의 패스워드를 비교
	// => 파라미터 : BoardDTO 객체, 리턴타입 : boolean(isCorrectPass)
	public boolean checkPass(FileBoardDTO fileBoard) {
		boolean isCorrectPass = false;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			// idx 와 pass 가 모두 일치하는 레코드 검색
			String sql = "SELECT * FROM file_board WHERE idx=? AND pass=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fileBoard.getIdx());
			pstmt.setString(2, fileBoard.getPass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 게시물 조회 성공(번호와 패스워드 일치)
				isCorrectPass = true; // 성공 표시
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - checkPass() : " + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return isCorrectPass;
	}
	
	// 글 수정 작업 수행하는 updateBoard() 메서드 정의
	// => 파라미터 : BoardDTO 객체, 리턴타입 : int(updateCount)
	public int updateBoard(FileBoardDTO fileBoard) {
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			// 글번호에 해당하는 게시물을 찾아 이름, 제목, 내용 수정
			String sql = "UPDATE file_board SET name=?,subject=?,content=? WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fileBoard.getName());
			pstmt.setString(2, fileBoard.getSubject());
			pstmt.setString(3, fileBoard.getContent());
			pstmt.setInt(4, fileBoard.getIdx());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - updateBoard() : " + e.getMessage());
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return updateCount;
	}
	
	// 게시물 삭제 작업 수행하는 deleteBoard() 메서드 정의
	// => 파라미터 : BoardDTO 객체, 리턴타입 : int(deleteCount)
	public int deleteBoard(FileBoardDTO fileBoard) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			// 글번호에 해당하는 게시물을 찾아 삭제
			String sql = "DELETE FROM file_board WHERE idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fileBoard.getIdx());
			
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 - deleteBoard() - file : " + e.getMessage());
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return deleteCount;
	}
	
	// 최근 게시물 5개 조회
	// => 파라미터 : 없음, 리턴타입 : List(recentNoticeList)
	public List selectRecentDriverList() {
		List boardList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = JdbcUtil.getConnection();
		
		try {
			// idx 기준 내림차순 정렬 후 5개만 조회
			String sql = "SELECT * FROM file_board ORDER BY idx DESC LIMIT 5";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 List(ArrayList) 객체 생성
			boardList = new ArrayList(); // ArrayList -> List 업캐스팅
			
			while(rs.next()) {
				// 1개 게시물(레코드)을 저장할 BoardDTO 객체 생성
				FileBoardDTO fileBoard = new FileBoardDTO();
				fileBoard.setIdx(rs.getInt("idx"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setPass(rs.getString("pass"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setContent(rs.getString("content"));
				fileBoard.setRealFile(rs.getString("real_file"));
				fileBoard.setOriginalFile(rs.getString("original_file"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
				
				// 1개 게시물을 다시 전체 게시물 저장 객체(boardList)에 추가
				boardList.add(fileBoard);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 구문 오류 발생! - selectRecentNoticeList() : " + e.getMessage());
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardList;
	}
	
}

