package guest.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conn.MysqlConn;

public class GuestDAO {
	private final MysqlConn instance = MysqlConn.getInstance();
	private final Connection conn = instance.getConn();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private GuestVO vo = null;
	private String sql = new String("");
	
	//생성자 DB연동
	public GuestDAO() {}
	
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
			instance.rsClose();
			instance.pstmtClose();
		}
		return totRecCnt;
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
			instance.rsClose();
			instance.pstmtClose();
		}
		return vos;
	}

	public int searchGuestWriteCnt(String mid, String name, String nickname) {
		int cnt = 0;
		try {
			sql = "select count(*) as count from guest where mid = ? or name = ? or nickName = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, name);
			pstmt.setString(3, nickname);
			rs = pstmt.executeQuery();
			rs.next(); //count()는 데이타가 없으면 '0'값을 취득하면서 rs도 같이 리턴하므로, 레코드를 읽는 목적으로 rs.next()사용
			cnt = rs.getInt("count"); 
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
			instance.rsClose();
		}
		return cnt;
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
			instance.pstmtClose();
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
			instance.pstmtClose();
		}
		return res;
	}
}