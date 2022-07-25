package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
//import db.JdbcUtil;
import vo.BoardDTO;

//import static db.JdbcUtil.getConnection;
//import static db.JdbcUtil.close;
import static db.JdbcUtil.*;

public class BoardListService {

	public int getListCount() {
		System.out.println("getListCount");
		
		int listCount = 0;
		
//		Connection con = JdbcUtil.getConnection();
		Connection con = getConnection();
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.setConnection(con);
		
		listCount = dao.selectListCount();
		
//		JdbcUtil.close(con);
		close(con);
		
		return listCount;
		
	}
	
	public ArrayList<BoardDTO> getBoardList(int pageNum, int listLimit) {
		
		ArrayList<BoardDTO> boardList = null;
		
		Connection con = getConnection();
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.setConnection(con);
		
		boardList = dao.selectBoardList(pageNum, listLimit);
		
		close(con);
		
		return boardList;
		
	}
	
}
