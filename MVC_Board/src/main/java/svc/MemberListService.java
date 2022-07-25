package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.MemberDAO;
import vo.MemberDTO;

import static db.JdbcUtil.*;


public class MemberListService {

	public ArrayList<MemberDTO> getMemberList() {

		ArrayList<MemberDTO> list = null;
		
		Connection con = getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		list = dao.getMemberList();
		
		close(con);
		
		return null;
	}

	
	
}
