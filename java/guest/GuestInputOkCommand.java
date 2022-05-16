package guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import guest.database.GuestDAO;
import guest.database.GuestVO;

public class GuestInputOkCommand implements GuestInterface {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestVO vo = new GuestVO();
		HttpSession session = request.getSession();
		String sMid = session.getAttribute("sMid")==null?"":(String)session.getAttribute("sMid");
		vo.setMid(sMid);
		vo.setName(request.getParameter("name"));
		vo.setEmail(request.getParameter("email"));
		vo.setHomepage(request.getParameter("homepage"));
		vo.setHostIp(request.getParameter("hostIp"));
		vo.setContent(request.getParameter("content"));
		
		GuestDAO dao = new GuestDAO();
		int res = dao.insert(vo);
		String msg = null;
		String url = null;
		if (0 < res) {
			msg = "guestInputOk";
			url = request.getContextPath() + "/guestList.gu";
		} else {
			msg = "guestInputNo";
			url = request.getContextPath() + "/guestInput.gu";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		request.setAttribute("idx", request.getAttribute("idx"));//회원idx
	}
}
