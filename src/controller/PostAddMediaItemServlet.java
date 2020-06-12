package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MediaItemService;
import service.MediaItemServiceImpl;
import service.UserServiceImpl;

@WebServlet("/addmediaitem")
@MultipartConfig
public class PostAddMediaItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MediaItemService mediaItemService = new MediaItemServiceImpl();
		String message = mediaItemService.postAddMediaItem(request.getParameterMap());
		response.getWriter().append(message);
	}

}
