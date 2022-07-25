package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import db.JdbcUtil;
import vo.BoardDTO;

import static db.JdbcUtil.*;

public class BoardDAO {

//	private BoardDAO() {}
	
//	static BoardDAO instance = new BoardDAO();

//	public static BoardDAO getInstance() {
//		return instance;
//	}
	
	private static BoardDAO instance = new BoardDAO();
	
	private BoardDAO() {}

	public static BoardDAO getInstance() {
		return instance;
	}
	
	private Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}
	
	public int insertBoard(BoardDTO board) {
		System.out.println("boardDAO - insertBoard()");
		
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		int num = 1;
		
		try {
			String sql = "SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt(1) + 1; 
			}
			
			close(pstmt);
			
			sql = "INSERT INTO board VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBoard_name());
			pstmt.setString(3, board.getBoard_pass());
			pstmt.setString(4, board.getBoard_subject());
			pstmt.setString(5, board.getBoard_content());
			pstmt.setString(6, board.getBoard_file());
			pstmt.setString(7, board.getBoard_real_file());
			pstmt.setInt(8, num); // board_re_ref
			pstmt.setInt(9, 0); // board_re_lev
			pstmt.setInt(10, 0); // board_re_seq
			pstmt.setInt(11, 0); // board_readcount
			
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - insertBoard() 오류");
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
		
	}
	
	public int selectListCount() {
		
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT COUNT(*) FROM board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - selectListCount() 오류" + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return listCount;
		
	}
	
	public ArrayList<BoardDTO> selectBoardList(int pageNum, int listLimit) {
		
		ArrayList<BoardDTO> boardList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int startRow = (pageNum - 1) * listLimit;
		
		try {
//			String sql = "SELECT * FROM board "
//					+ "ORDER BY board_num DESC "
//					+ "LIMIT ?,?";
			
			String sql = "SELECT * FROM board "
					+ "ORDER BY board_re_ref DESC, board_re_seq ASC "
					+ "LIMIT ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<BoardDTO>();
			
			while(rs.next()) {
				
				BoardDTO board = new BoardDTO();
				
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_pass(rs.getString("board_pass"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_file(rs.getString("board_file"));
				board.setBoard_real_file(rs.getString("board_real_file"));
				board.setBoard_re_ref(rs.getInt("board_re_ref"));
				board.setBoard_re_lev(rs.getInt("board_re_lev"));
				board.setBoard_re_seq(rs.getInt("board_re_seq"));
				board.setBoard_re_readcount(rs.getInt("board_re_readcount"));
				board.setBoard_date(rs.getDate("board_date"));
				
				boardList.add(board);
				
			}
			
//			System.out.println(boardList.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - selectBoardList() 오류" + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return boardList;
		
	}

	public void updateReadcount(int board_num) {

		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE board SET board_re_readcount=board_re_readcount+1 WHERE board_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - updateReadcount(int) 오류");
		} finally {
			close(pstmt);
		}
		
	}

	public BoardDTO selectBoard(int board_num) {

		BoardDTO board = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board WHERE board_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				board = new BoardDTO();
				
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_pass(rs.getString("board_pass"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_file(rs.getString("board_file"));
				board.setBoard_real_file(rs.getString("board_real_file"));
				board.setBoard_re_ref(rs.getInt("board_re_ref"));
				board.setBoard_re_lev(rs.getInt("board_re_lev"));
				board.setBoard_re_seq(rs.getInt("board_re_seq"));
				board.setBoard_re_readcount(rs.getInt("board_re_readcount"));
				board.setBoard_date(rs.getDate("board_date"));
				
//				System.out.println(board);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - selectBoard(int) 오류");
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return board;
	}

	public boolean isBoardWriter(int board_num, String board_pass) {

		boolean isBoardWriter = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM board WHERE board_num=? AND board_pass=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.setString(2, board_pass);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isBoardWriter = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - isBoardWriter 오류");
		}
		
		return isBoardWriter;
		
	}

	public int deleteBoard(int board_num) {

		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM board WHERE board_num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			deleteCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BoardDAO - deleteBoard 오류");
		}
		
		return deleteCount;
		
	}

	public int updateBoard(BoardDTO board) {

		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "UPDATE board "
					+ "SET board_name=?, board_subject=?, board_content=? "
					+ "WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getBoard_name());
			pstmt.setString(2, board.getBoard_subject());
			pstmt.setString(3, board.getBoard_content());
			pstmt.setInt(4, board.getBoard_num());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("dao - updateBoard() 오류");
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public int insertReplyBoard(BoardDTO board) {
		
		int insertCount = 0;
		
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		
		int num = 1;
		
		try {
			String sql = "SELECT MAX(board_num) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt(1) + 1; 
			}
			
			sql = "UPDATE board SET board_re_seq=board_re_seq+1 "
					+ "WHERE board_re_ref=? AND board_re_seq>?";
			
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, board.getBoard_re_ref());
			pstmt2.setInt(2, board.getBoard_re_seq());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO board VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";
			
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, board.getBoard_name());
			pstmt2.setString(3, board.getBoard_pass());
			pstmt2.setString(4, board.getBoard_subject());
			pstmt2.setString(5, board.getBoard_content());
			pstmt2.setString(6, "");
			pstmt2.setString(7, "");
			pstmt2.setInt(8, board.getBoard_re_ref()); 
			pstmt2.setInt(9, board.getBoard_re_lev() + 1); 
			pstmt2.setInt(10, board.getBoard_re_seq() + 1);
			pstmt2.setInt(11, 0); 
			
			insertCount = pstmt2.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("dao - insertReplyBoard 오류");
		} finally {
			close(pstmt);
			close(rs);
		}
		
		return insertCount;
	}
	
}
