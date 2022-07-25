package svc;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardDTO;
import static db.JdbcUtil.*;

public class BoardReplyProService {

	public boolean replyBoard(BoardDTO board) {

		boolean isReplySuccess = false;
		
		Connection con = getConnection();
		BoardDAO dao = BoardDAO.getInstance();
		dao.setConnection(con);
		
		int insertCount = dao.insertReplyBoard(board);
		
		if(insertCount > 0) {
			commit(con);
			isReplySuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isReplySuccess;
	}

	
	
}
