package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MemberListService;
import vo.ActionForward;
import vo.BoardDTO;
import vo.MemberDTO;

public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberListAction");
		ActionForward forward = null;
		
		MemberListService service = new MemberListService();
		
		ArrayList<MemberDTO> list = service.getMemberList();
		
		forward = new ActionForward();
		forward.setPath("./");
		forward.setRedirect(false);
		
		return forward;
	}

}
