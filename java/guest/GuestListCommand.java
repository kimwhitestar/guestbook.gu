package guest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guest.database.GuestDAO;
import guest.database.GuestVO;

public class GuestListCommand implements GuestInterface {
	private GuestDAO dao = new GuestDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		 * 
		 *   페이징 처리
		 *   0. 현 페이지 구하기 : page = 1
		 *   1. 한 페이지 분량을 정한다 : pageSize = 5
		 *   2. 총 레코드 건수를 구하기 : totRecCnt (SQL함수 count())
		 *   3. 총 페이지 수 구하기 : totPage => totRecCnt % pageSize (몫의 정수값이 0이면 정수, 몫의 정수값이 아니면 정수값 + 1)
		 * 	 4. 현재페이지의 시작 인덱스 : startIndexNo => (page -1) * pageSize
		 * 	 5. 화면에 보이는 방문소감의 시작번호 : curScrStartNo = totRecCnt - startIndexNo
		 *	 6. 블록페이징처리
		 *
		 */
		
		//0. 현 페이지
		int pageNo = request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		
		//1. 한 페이지 분량
		int pageSize = 5;
		
		//2. 총 레코드 건수
		int totRecCnt = dao.totRecCnt();
		
		//3. 총 페이지 수
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize)+1;
		
		//4. 현재페이지 시작 인덱스
		int startIndexNo = (pageNo -1) * pageSize;
		
		//5. 방문소감의 시작번호
		int curScrStartNo = totRecCnt - startIndexNo;

		//6. 블록페이징처리시 아래 내용 추가 (한블럭에 3page씩 보여준다) : blockSize
		int blockSize = 3;
		
		//7. 현재 블럭 위치 (첫번째 블럭 = 0) : curBlock 
		int curBlock = (pageNo - 1) / blockSize;
		
		//8. 마지막 블럭 : lastBlock
		int lastBlock = (totPage % blockSize)==0 ? (totPage / blockSize) - 1 : (totPage / blockSize);
		
		
		//한 페이징에 표시할 레코드 검색
		List<GuestVO> vos = dao.searchGuestList(startIndexNo, pageSize);
		request.setAttribute("vos", vos);
		request.setAttribute("curScrStartNo", curScrStartNo);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("totPage", totPage);
		//request.setAttribute("pageSize", pageSize);
		request.setAttribute("blockSize", blockSize);
		request.setAttribute("curBlock", curBlock);
		request.setAttribute("lastBlock", lastBlock);
	}
}
