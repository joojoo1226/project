package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardWriteProAction;
import action.MemberCheckDuplicateIdAction;
import action.MemberJoinProAction;
import action.MemberListAction;
import action.MemberLoginProAction;
import action.MemberLogoutAction;
import vo.ActionForward;

@WebServlet("*.me")
public class MemberFrontController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getRemoteAddr());
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/MemberJoinForm.me")) {
			forward = new ActionForward();
			forward.setPath("member/member_join.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberCheckIdForm.me")) {
			forward = new ActionForward();
			forward.setPath("member/check_id.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberJoinPro.me")) {
			try {
				action = new MemberJoinProAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - /MemberJoinProAction.me 오류");
			}
		} else if(command.equals("/CheckDuplicateId.me")) {
			action = new MemberCheckDuplicateIdAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - /CheckDuplicateId.me 오류");
			}
		} else if(command.equals("/MemberLoginForm.me")) {
			forward = new ActionForward();
			forward.setPath("member/member_login.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberLoginPro.me")) {
			try {
				action = new MemberLoginProAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - MemberLoginPro 오류");
			}
		} else if(command.equals("/MemberLogout.me")) {
			try {
				action = new MemberLogoutAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - MemberLogout 오류");
			}
		} else if(command.equals("/AdminMain.me")) {
			try {
				action = new MemberListAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - AdminMain 오류");
			}
		}
		
		if(forward != null) {
			if(forward.isRedirect()) { //Redirect
				response.sendRedirect(forward.getPath());
			} else { //Dispatcher
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
		
	}

}
