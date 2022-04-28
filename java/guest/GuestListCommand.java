package guest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guest.database.GuestDAO;
import guest.database.GuestVO;

public class GuestListCommand implements GuestInterface {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestDAO dao = new GuestDAO();
		List<GuestVO> vos = dao.searchGuestList();
		request.setAttribute("vos", vos);
	}
}
