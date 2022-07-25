package controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardDeleteProAction;
import action.BoardDetailAction;
import action.BoardListAction;
import action.BoardModifyFormAction;
import action.BoardModifyProAction;
import action.BoardReplyFormAction;
import action.BoardReplyProAction;
import action.BoardWriteProAction;
import vo.ActionForward;

@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
	
	//get방식
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	//post방식
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
		
		if(command.equals("/BoardWriteForm.bo")) {
//			RequestDispatcher dispatcher = request.getRequestDispatcher("board/qna_board_write.jsp");
//			dispatcher.forward(request, response);
			
			forward = new ActionForward();
			forward.setPath("board/qna_board_write.jsp");
			forward.setRedirect(false);
			
		} else if(command.equals("/BoardWritePro.bo")) {
//			System.out.println("글쓰기 비즈니스 로직!");
			
//			response.sendRedirect("BoardList.bo");
			
			//컨트롤러에서 직접 이동
//			forward = new ActionForward();
//			forward.setPath("BoardList.bo");
//			forward.setRedirect(true);
			
			//action 클래스 거쳐서 이동
			//action은 이동 + 준비작업
			try {
				action = new BoardWriteProAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - /BoardWritePro.bo 오류");
			}
			
		} else if(command.equals("/BoardList.bo")) {
			try {
				action = new BoardListAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - /BoardList.bo 오류");
			}
		} else if(command.equals("/BoardDetail.bo")) {
//			System.out.println("글 상세정보 조회!");
			
			try {
				action = new BoardDetailAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - /BoardDetail.bo 오류");
			}
		} else if(command.equals("/BoardReplyForm.bo")) {
			try {
				action = new BoardReplyFormAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController /BoardReplyForm.bo 오류");
			}
		} else if(command.equals("/BoardReplyPro.bo")) {
			try {
				action = new BoardReplyProAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController /BoardReplypro.bo 오류");
			}
		} else if(command.equals("/BoardModifyForm.bo")) {
			action = new BoardModifyFormAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - /BoardModifyForm.bo 오류");
			}
		} else if(command.equals("/BoardModifyPro.bo")) {
			action = new BoardModifyProAction();
			try {
				action = new BoardModifyProAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController /BoardModifyPro.bo 오류");
			}
			
		} else if(command.equals("/BoardDeleteForm.bo")) {
			forward = new ActionForward();
			forward.setPath("board/qna_board_delete.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/BoardDeletePro.bo")) {
			try {
				action = new BoardDeleteProAction();
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("frontController - /BoardDeletePro.bo 오류");
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
