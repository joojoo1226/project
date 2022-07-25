package svc;

import java.sql.Connection;

import dao.MemberDAO;

import static db.JdbcUtil.*;

public class MemberCheckDuplicateIdService {

	public boolean checkDuplicateId(String id) {

		boolean isDuplicate = false;
		
		Connection con = getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		isDuplicate = dao.selectDuplicateId(id);
		
		close(con);
		
		return isDuplicate;
	}

}
