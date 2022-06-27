<%@page import="board.BoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

// String name = request.getParameter("name");
// String pass = request.getParameter("pass");
// String subject = request.getParameter("subject");
// String content = request.getParameter("content");
// String file = request.getParameter("file");
// out.println(name + ", " + pass + ", " + subject + ", " + content + ", " + file);

String uploadPath = "/upload";

// ServletContext context = request.getServletContext();

// String realPath = context.getRealPath(uploadPath);

String realPath = request.getServletContext().getRealPath(uploadPath);
// out.println(realPath);

// int fileSize = 10485760;
int fileSize = 1024 * 1024 * 10;

MultipartRequest multi = new MultipartRequest(
	request,
	realPath,
	fileSize,
	"UTF-8",
	new DefaultFileRenamePolicy()
);

// out.println(multi.getParameter("name"));

FileBoardDTO fileBoard = new FileBoardDTO();

fileBoard.setName(multi.getParameter("name"));
fileBoard.setPass(multi.getParameter("pass"));
fileBoard.setSubject(multi.getParameter("subject"));
fileBoard.setContent(multi.getParameter("content"));
fileBoard.setRealFile(multi.getFilesystemName("file"));
fileBoard.setOriginalFile(multi.getOriginalFileName("file"));
// out.println(fileBoard.getName() + ", " + fileBoard.getPass() + ", " + fileBoard.getSubject() + ", " + fileBoard.getContent());
// out.println(fileBoard.getRealFile() + ", " + fileBoard.getOriginalFile());

FileBoardDAO daoF = new FileBoardDAO();
int insertCount = daoF.insertFileBoard(fileBoard);

if(insertCount == 0) {
	%>
	<script>
		alert("글쓰기 실패!");
		history.back();
	</script>
	<%
} else {
	response.sendRedirect("driver.jsp");
}
%>