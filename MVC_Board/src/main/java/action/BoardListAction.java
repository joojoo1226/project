package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardDTO;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardList Action");
		
		ActionForward forward = null;
		
		int pageNum = 1; 
		int listLimit = 10;
		int pageLimit = 10; 

		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum")); 
		}
		
		BoardListService boardListService = new BoardListService();
		
		int listCount = boardListService.getListCount();
		
//		System.out.println("전체 게시물 수  : " + listCount);
		
		int maxPage = (int)Math.ceil((double)listCount / listLimit);
		int startPage = ((int)((double)pageNum / pageLimit + 0.9) - 1) * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pageInfo = new PageInfo(pageNum, maxPage, startPage, endPage, listCount);
		
		ArrayList<BoardDTO> boardList = boardListService.getBoardList(pageNum, listLimit);
		
//		System.out.println(boardList);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("boardList", boardList);
//		System.out.println(pageInfo.getListCount());
//		System.out.println(boardList);
		
		forward = new ActionForward();
		forward.setPath("board/qna_board_list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
