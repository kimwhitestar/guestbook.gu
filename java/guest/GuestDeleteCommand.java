package guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import guest.database.GuestDAO;

public class GuestDeleteCommand implements GuestInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sMid = session.getAttribute("sMid")==null?"":(String)session.getAttribute("sMid");
		int sLevel = session.getAttribute("sLevel")==null?99:Integer.parseInt((String)session.getAttribute("sLevel"));
		int idx = Integer.parseInt(request.getParameter("idx"));
		GuestDAO dao = new GuestDAO();
		int res = dao.delete(idx, sMid, sLevel);
		if (0 < res) {
			request.setAttribute("msg", "guestDeleteOk");
		} else {
			request.setAttribute("msg", "guestDeleteNo");
		}
		request.setAttribute("url", request.getContextPath()+"/guestList.gu");
		request.setAttribute("idx", request.getAttribute("idx"));
	}
}
