package guest.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	private GuestVO vo = null;
	
	String url = "jdbc:mysql://localhost:3306/javagreen";
	String user = "root";
	String password = "1234";
	
	//생성자 DB연동
	public GuestDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패~~");
		} catch (SQLException e) {
			System.out.println("데이타베이스 연동 실패~~");
		}
	}
	
	public void pstmtClose() {
		if (null != pstmt) {
			try { pstmt.close(); } 
			catch (SQLException e) {e.getMessage();}
		}
	}
	public void rsClose() {
		if (null != rs) {
			try { rs.close(); } 
			catch (SQLException e) {e.getMessage();}
		}
	}

	public List<GuestVO> searchGuestList(int startIndexNo, int pageSize) {
		List<GuestVO> vos = new ArrayList();
		try {
			sql = "select * from guest order by idx desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startIndexNo);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new GuestVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setHomepage(rs.getString("homepage"));
				vo.setvDate(rs.getString("vDate"));
				vo.setHostIp(rs.getString("hostIp"));
				vo.setContent(rs.getString("content"));
				vos.add(vo);
			}
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			rsClose();
			pstmtClose();
		}
		return vos;
	}

	public int insert(GuestVO vo) {
		int res = 0;
		try {
			sql = "insert into guest values (default, ?, ? , ?, default, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getHomepage());
			pstmt.setString(4, vo.getHostIp());
			pstmt.setString(5, vo.getContent());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

	public int delete(int idx) {
		int res = 0;
		try {
			sql = "delete from guest where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}

	//페이징 총 레코드건수
	public int totRecCnt() {
		int totRecCnt = 0;
		try {
			sql = "select count(*) as totRecCnt from guest";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next(); //ResultSet레코드움직이기(count함수는 무조건 0값조차 가져옴)
			totRecCnt = rs.getInt("totRecCnt");
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			rsClose();
			pstmtClose();
		}
		return totRecCnt;
	}
}