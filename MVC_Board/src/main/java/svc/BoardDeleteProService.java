package svc;

import java.sql.Connection;

import dao.BoardDAO;
import static db.JdbcUtil.*;

public class BoardDeleteProService {

	public boolean isBoardWriter(int board_num, String board_pass) {
		
		boolean isBoardWriter = false;
		
		Connection con = getConnection();
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.setConnection(con);
		
		isBoardWriter = dao.isBoardWriter(board_num, board_pass);
		
		close(con);
		
		return isBoardWriter;
	}

	public boolean removeBoard(int board_num) {
		
		boolean isDeleteSuccess = false;
		int deleteCount = 0;
		
		Connection con = getConnection();
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.setConnection(con);
		
		deleteCount = dao.deleteBoard(board_num);
		
		if(deleteCount > 0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}

	
	
}
