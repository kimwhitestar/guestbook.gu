package guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import study.database.LoginDAO;

public class AdminLoginOkCommand implements GuestInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mid = request.getParameter("mid");
		String pwd = request.getParameter("pwd");
		LoginDAO dao = new LoginDAO();
		HttpSession session = request.getSession();
		
		if (mid.equals("admin")) {
			int res = dao.checkAdmin(mid, pwd);
			if (0 < res) {
				session.setAttribute("sAdmin", "adminOk");
				request.setAttribute("msg", "adminOk");
				request.setAttribute("url", "guestList.gu");
				return;
			}
		}
		request.setAttribute("msg", "adminNo");
		request.setAttribute("url", "adminLogin.gu");
	}
}
