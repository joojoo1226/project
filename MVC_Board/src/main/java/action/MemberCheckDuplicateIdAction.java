package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MemberCheckDuplicateIdService;
import vo.ActionForward;

public class MemberCheckDuplicateIdAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberCheckDuplicateIdAction");
		
		ActionForward forward = null;
		
		String id = request.getParameter("id");
//		System.out.println(id);
		
		MemberCheckDuplicateIdService service = new MemberCheckDuplicateIdService();
		boolean isDuplicate = service.checkDuplicateId(id);
		
		forward = new ActionForward();
		forward.setPath("MemberCheckIdForm.me?id=" + id + "&isDuplicate=" + isDuplicate);
		forward.setRedirect(true);
		
		return forward;
	}

}
