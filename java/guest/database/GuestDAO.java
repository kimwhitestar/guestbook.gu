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
	
	//방명록 전체목록 페이징(총 레코드 건수) - 검색조건
	public int guestListTotRecCnt(char kindYmd, int term, String searchConditionKey, String searchConditionValue) {
		int totRecCnt = 0;
		try {
			int prepareIdx = 0;
			boolean isExistSearchCondition = isExistSearchCondition(searchConditionKey, searchConditionValue);
			boolean isExistInterval = isExistInterval(kindYmd, term);
			sql = "select count(*) as totRecCnt from guest ";
			if (isExistSearchCondition || isExistInterval) sql += "where "; 
			String addPrepareSQL1 = searchConditionKey + " like ? ";
			if (isExistSearchCondition) sql += addPrepareSQL1;
			String addPrepareSQL2 = makeIntervalSQL(kindYmd, term, "vDate");
			if (isExistSearchCondition && isExistInterval) {
				sql = sql + "and " + addPrepareSQL2;
			} else if (!isExistSearchCondition && isExistInterval) {
				sql += addPrepareSQL2;
			}
			pstmt = conn.prepareStatement(sql);
			if (isExistSearchCondition) pstmt.setString(++prepareIdx, "%"+searchConditionValue+"%");
			if (isExistInterval) pstmt.setInt(++prepareIdx, term);
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

	private boolean isExistSearchCondition(String searchConditionKey, String searchConditionValue) {
		boolean isExistSearchCondition = false;
		if (null != searchConditionKey && searchConditionKey.trim().length() > 0 
			&& null != searchConditionValue && searchConditionValue.trim().length() > 0)
			isExistSearchCondition = true;
		return isExistSearchCondition;
	}
	private boolean isExistInterval(char kindYmd, int term) {
		boolean isExistInterval = false;
		if ((0 != kindYmd 
			&& ('Y' == kindYmd || 'M' == kindYmd || 'D' == kindYmd)) 
			&& 0 < term) 
			isExistInterval = true;
		return isExistInterval;
	}
	//기간별 조회 SQL 조건문 추가 - Interval ex) interval 5 day, interval 1 Month
	private String makeIntervalSQL(char kindYmd, int term, String columnName) {
		String sqlInterval = null;
		if (isExistInterval(kindYmd, term)) {
			String sqlIntervalDate = new String("interval ? ");
			switch(kindYmd) {
				case 'Y' : sqlIntervalDate += "year"; break;
				case 'M' : sqlIntervalDate += "month"; break;
				case 'W' : sqlIntervalDate += "week"; break;
				case 'D' : sqlIntervalDate += "day"; break;
				default : break;
			}
			String sqlTerm = new String("date_sub(now(), " + sqlIntervalDate + ")");
			sqlInterval = new String(sqlTerm + " <= " + columnName + " and " + columnName +" <= now() ");
		} else {
			sqlInterval = new String("");
		}
		return sqlInterval;
	}
	
	//방명록 전체목록 - 검색조건
	//select * from guest
	//where searchConditionKey like '%searchConditionValue%' --검색조건(작성자,글내용)
	//and date_sub(now(), interval term kindYmd) <= vDate and vDate <= now() --기간별조회(daily, weekly, monthly, yearly)
	//order by idx desc limit startIndexNo, pageSize ;
	public List<GuestVO> searchGuestList(char kindYmd, int term, String searchConditionKey, String searchConditionValue, int startIndexNo, int pageSize) {
		List<GuestVO> vos = new ArrayList<>();
		try {
			int prepareIdx = 0;
			boolean isExistSearchCondition = isExistSearchCondition(searchConditionKey, searchConditionValue);
			boolean isExistInterval = isExistInterval(kindYmd, term);
			sql = "select * from guest ";
			if (isExistSearchCondition || isExistInterval) sql += "where "; 
			String addPrepareSQL1 = searchConditionKey + " like ? ";
			if (isExistSearchCondition) sql += addPrepareSQL1;
			String addPrepareSQL2 = makeIntervalSQL(kindYmd, term, "vDate");
			if (isExistSearchCondition && isExistInterval) {
				sql = sql + "and " + addPrepareSQL2;
			} else if (!isExistSearchCondition && isExistInterval) {
				sql += addPrepareSQL2;
			}
			sql += "order by idx desc limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			if (isExistSearchCondition) pstmt.setString(++prepareIdx, "%"+searchConditionValue+"%");
			if (isExistInterval) pstmt.setInt(++prepareIdx, term);
			pstmt.setInt(++prepareIdx, startIndexNo);
			pstmt.setInt(++prepareIdx, pageSize);
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

	//회원의 방명록에 올린 글 수
	public int searchGuestWriteCnt(String mid, String nickName) {
		int cnt = 0;
		try {
			sql = "select count(idx) as count from guest where mid = ? and name = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, nickName);
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
			sql = "insert into guest values (default, ?, ?, ?, ?, default, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getHomepage());
			pstmt.setString(5, vo.getHostIp());
			pstmt.setString(6, vo.getContent());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}

	public int delete(int idx, String mid, int level) {
		int res = 0;
		try {
			sql = "delete from guest where idx = ? ";
			if (0 != level) sql = sql + "and mid = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			if (0 != level) pstmt.setString(2, mid);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 에러 : " + e.getMessage());
		} finally {
			instance.pstmtClose();
		}
		return res;
	}
}