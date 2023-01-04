package com.movie.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MovieDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public Connection getConn() {
		String url = "jdbc:mariadb://localhost:3306/FinalTestWeb";
		String user = "dltkdgns00";
		String password = "sang0604";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getConn() Exception!!!");
		}
		return conn;
	} // getConn()

	// 등록된 글의 총 개수
	public int getListCount() {
		conn = getConn();
		String sql = "SELECT COUNT(*) FROM movielist";
		// NULL이 들어갈 수 있는 필드도 있으므로 모든 필드를 센 다음에 가장 많이 나온 필드를 기준으로 한다.
		int listCount = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				listCount = rs.getInt(1); // movie_num 필드는 NULL값이 들어올 수 없으니 movie_num을 가져온다.
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getListCount() Exception!!!");
		} finally {
			dbClose();
		}
		return listCount;
	} // getListCount()

	// 전체 글 목록 조회(Feat : 페이징
	public ArrayList<MovieDTO> getMovieList(int page, int limit) {
		conn = getConn();
		String sql = "SELECT * FROM movielist;";
		ArrayList<MovieDTO> list = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_name(rs.getString("movie_name"));
				list.add(dto);

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getMovieList() Exception!!!");
		} finally {
			dbClose();
		}
		return list;

	}

	// DB 종료
	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("dbClose() Exception!!!");
		}
	} // dbClose()
} // class()