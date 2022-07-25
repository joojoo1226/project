package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardDTO;

public class BoardModifyProService {

	public boolean isBoardWriter(int board_num, String board_pass) {

		boolean isBoardWriter = false;
		
		Connection con = getConnection();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		isBoardWriter = dao.isBoardWriter(board_num, board_pass);
		
		close(con);
		
		return isBoardWriter;
	}

	public boolean modifyBoard(BoardDTO board) {

		boolean isModifySuccess = false;
		
		Connection con = getConnection();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		int updateCount = dao.updateBoard(board);
		
		if(updateCount > 0) {
			commit(con);
			isModifySuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isModifySuccess;
	}
	
}
