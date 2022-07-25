package svc;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardDTO;

import static db.JdbcUtil.*;

public class BoardDetailService {

	//조회수 증가 요청
	public void increaseReadcount(int board_num) {
		System.out.println("increaseReadcount");
		
		Connection con = getConnection();
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.setConnection(con);
		
		//dao 가보자고
		dao.updateReadcount(board_num);
		
		commit(con);
		
		close(con);
		
	}
	
	//상세정보 조회 요청
	public BoardDTO getBoard(int board_num) {
		System.out.println("getBoard");
		
		BoardDTO board = null;
		
		Connection con = getConnection();
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.setConnection(con);
		
		//dao 가보자고
		board = dao.selectBoard(board_num);
		
		commit(con);
		
		close(con);
		
		return board;
		
	}
	
}
