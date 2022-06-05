package com.station.dao;

import static com.station.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.station.model.vo.GasStation;

public class GasStationDao {

	//주유소 추가
	public int insert(Connection conn, GasStation station) {
		try {
			String sql = "INSERT INTO "
					+ "TBL_GAS_STATION(G_STATION_ID, G_STATION_TRADEMARK, G_STATION_NAME, G_STATION_ADDRESS, G_STATION_TEL, "
					+ "	   REGION_CODE, MAINT_YN, WASH_YN, CVS_YN, GASOLINE_PRICE, DIESEL_PRICE) " + "VALUES("
					+ "		   ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

			PreparedStatement pstmt = conn.prepareStatement(sql);

			int cnt = 1;
			pstmt.setString(cnt++, station.getStationId());
			pstmt.setString(cnt++, station.getStationTm());
			pstmt.setString(cnt++, station.getStationNm());
			pstmt.setString(cnt++, station.getStationAddr());
			pstmt.setString(cnt++, station.getStationTel());
			pstmt.setString(cnt++, station.getRegionCd());
			pstmt.setString(cnt++, station.getMaintYN());
			pstmt.setString(cnt++, station.getWashYN());
			pstmt.setString(cnt++, station.getCvsYN());
			pstmt.setInt(cnt++, station.getGasolinePrice());
			pstmt.setInt(cnt++, station.getDieselPrice());

			int result = pstmt.executeUpdate();
			pstmt.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//지역명 검색 -> 추가
	public int insert2(Connection conn, GasStation station2) {
		try {
			String sql = "INSERT INTO "
					+ "TBL_REGION(REGION_CODE, REGION_NAME) " + "VALUES("
					+ "		   ?, ?) ";		
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			int cnt = 1;
			pstmt.setString(cnt++, station2.getRegionCd());
			pstmt.setString(cnt++, station2.getRegionNm());

			int result = pstmt.executeUpdate();
			pstmt.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// 지점 아이디로 검색
	public GasStation selectByStationId(Connection conn, String stationId) {
		GasStation info = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM TBL_GAS_STATION WHERE G_STATION_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stationId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int count = 1;
				String stationId2 = rs.getString(count++);
				String stationTm = rs.getString(count++);
				String stationNm = rs.getString(count++);
				String stationAddr = rs.getString(count++);
				String stationTel = rs.getString(count++);
				String regionCd = rs.getString(count++);
				String maintYN = rs.getString(count++);
				String washYN = rs.getString(count++);
				String cvsYN = rs.getString(count++);
				int gasolinePrice = rs.getInt(count++);
				int dieselPrice = rs.getInt(count++);

				info = new GasStation(stationId2, stationTm, stationNm, stationAddr, stationTel, regionCd, maintYN,
						washYN, cvsYN, gasolinePrice, dieselPrice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		return info;
	}

	// 상호로 검색
	public GasStation selectByStationName(Connection conn, String stationNm) {
		GasStation info = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM TBL_GAS_STATION WHERE G_STATION_NAME LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + stationNm + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int count = 1;
				String stationId = rs.getString(count++);
				String stationTm = rs.getString(count++);
				String stationNm2 = rs.getString(count++);
				String stationAddr = rs.getString(count++);
				String stationTel = rs.getString(count++);
				String regionCd = rs.getString(count++);
				String maintYN = rs.getString(count++);
				String washYN = rs.getString(count++);
				String cvsYN = rs.getString(count++);
				int gasolinePrice = rs.getInt(count++);
				int dieselPrice = rs.getInt(count++);

				info = new GasStation(stationId, stationTm, stationNm2, stationAddr, stationTel, regionCd, maintYN,
						washYN, cvsYN, gasolinePrice, dieselPrice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		return info;
	}

	public GasStation selectByGasRegion(Connection conn, String regionCd) {
		GasStation info = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM TBL_REGION WHERE REGION_CODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, regionCd);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int count = 1;
				String regionCd2 = rs.getString(count++);
				String regionNm = rs.getString(count++);

				info = new GasStation(regionCd2, regionNm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		return info;
	}

	// 최저가 조회
	// 평균가 조회

}
