package guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guest.database.GuestDAO;

public class GuestDeleteCommand implements GuestInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = Integer.parseInt(request.getParameter("idx"));
		GuestDAO dao = new GuestDAO();
		int res = dao.delete(idx);
		if (0 < res) {
			request.setAttribute("msg", "guestDeleteOk");
		} else {
			request.setAttribute("msg", "guestDeleteNo");
		}
		request.setAttribute("url", request.getContextPath()+"/guestList.gu");
	}
}
