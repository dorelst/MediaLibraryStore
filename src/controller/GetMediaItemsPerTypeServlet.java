package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MediaItemService;
import service.MediaItemServiceImpl;

@WebServlet("/mediaitemspertype/*")
public class GetMediaItemsPerTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MediaItemService mediaItemService = new MediaItemServiceImpl();
		String message = mediaItemService.getMediaItemsPerType(request.getPathInfo());
		response.getWriter().append(message);
	}
}
