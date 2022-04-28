package guest;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.gu")
public class GuestController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestInterface command = null;
		String viewPage = "/WEB-INF";
		String uri = request.getRequestURI();
		String com = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));
		
		if (com.equals("guestList")) {
			command = new GuestListCommand();
			command.execute(request, response);
			viewPage += "/guest/guestList.jsp";
		} 
		else if (com.equals("guestInput")) {
			viewPage += "/guest/guestInput.jsp";
		}
		else if (com.equals("")) {
			command = null;
		}
		else if (com.equals("")) {
			command = null;
		}
		else if (com.equals("")) {
			command = null;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
