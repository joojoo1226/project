package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MemberLoginProService;
import vo.ActionForward;
import vo.MemberDTO;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
		
		MemberDTO member = new MemberDTO();
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
		
		MemberLoginProService service = new MemberLoginProService();
		
		boolean isLoginSuccess = service.loginMember(member);
		
		if(!isLoginSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('로그인 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("sId", member.getId());
			
			forward = new ActionForward();
			forward.setPath("./");
			forward.setRedirect(true);
		}
		
		return forward;
	}

}
