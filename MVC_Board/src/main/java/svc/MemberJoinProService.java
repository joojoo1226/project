package svc;

import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import dao.BoardDAO;
import dao.MemberDAO;
import vo.MemberDTO;

import static db.JdbcUtil.*;

public class MemberJoinProService {
	
	public boolean joinMember(MemberDTO member) {

		boolean isJoinSuccess = false;
		
		Connection con = getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		int insertCount = dao.insertMember(member);
		
		if(insertCount > 0) {
			commit(con);
			isJoinSuccess = true;
		} else {
			rollback(con);
		}

		close(con);
		
		return isJoinSuccess;
	}

	
	
}
