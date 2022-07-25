package controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

@WebServlet("*.do")
public class BoardFrontController2 extends HttpServlet {
	
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
		
		ActionForward forward = null;
		
		if(command.equals("/BoardWriteForm.do")) {
			
			forward = new ActionForward();
			forward.setPath("board/qna_board_write.jsp");
			forward.setRedirect(false);
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/board/qna_board_write.jsp");
//			dispatcher.forward(request, response);
			
		} else if(command.equals("/BoardWritePro.bo")) {

			forward = new ActionForward();
			forward.setPath("BoardList.bo");
			forward.setRedirect(true);
			
		} else if(command.equals("/BoardList.do")) {
			forward = new ActionForward();
			forward.setPath("board/qna_board_list.jsp");
			forward.setRedirect(false);
		}
		
		if(forward != null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else { //dispatcher
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

}
