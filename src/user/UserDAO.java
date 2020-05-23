package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC";
//			String dbURL = "jdbc:mysql://localhost:3306/BBS";
//			String dbURL = "jdbc:mysql://localhost:3306/BBS?autoReconnect=true&amp;useSSL=false";
			String dbID = "root";
			String dbPassword = "0619";
			System.out.println(dbURL +"그리고" + dbID +"그리고"+dbPassword);
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("class.for네임");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			System.out.println("드라이버매니져");
			
		} catch(Exception e){
			System.out.println("퍼블릭 다오 시작했는데 catch");
			e.printStackTrace();
		}
	}
	
	public int login(String userID,String userPassword) {
		System.out.println(userID + "," + userPassword);
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; //로그인 성공
				}
				else {
					return 0; //패스워드 불일치
				}
			}
			return -1; //아이디가 없음
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("이기야");
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}

}
