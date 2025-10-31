import java.sql.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

public class MainApp {
	static int userId;
	private static final String url = "jdbc:mysql://localhost:3306/work" + "?useSSL=false&allowPublicKeyRetrieval=true"
			+ "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
	private static final String user = "root";
	private static final String pass = "Fmps9632";

	public static void main(String[] args) throws Exception {
		try (Scanner sc = new Scanner(System.in)) {
			if (!login(sc)) {
				return;
			}
			while (true) {
				System.out.println("===勤怠管理 メニュー ===");
				System.out.println("1)出勤");
				System.out.println("2)退勤");
				System.out.println("3)勤怠一覧表示");
				System.out.println("0) 終了 … 終了");
				System.out.println("番号を選んでください。");

				String choice = sc.nextLine();
				switch (choice) {
				case "1":
					insertTimes(sc);
					break;
				case "2":
					updateTimes(sc);
					break;
					
				case "3":
					showAllAttendance(sc);
					break;
					
				case "0":
					System.out.println("終了します。");
					return;
				default:
					System.out.println("無効な入力です。");
				}
			}
		}
	}

	private static boolean login(Scanner sc) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn
						.prepareStatement("select * from user where email=? and password=?");) {
			System.out.println("メールアドレスを入力してください。");
			String email = sc.nextLine().trim();
			System.out.println("パスワードを入力してください。");
			String passWord = sc.nextLine().trim();
			ps.setString(1, email);
			ps.setString(2, passWord);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					System.out.println("ログイン成功しました。");
					userId = rs.getInt("id");
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.out.println("ユーザーが見つかりません");
			return false;
		}
	}

	private static void insertTimes(Scanner sc) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO attendance (user_id,start) VALUES(?,?)");) {
			ps.setInt(1, userId);
			LocalDateTime now=LocalDateTime.now();
			ps.setTimestamp(2, Timestamp.valueOf(now));
		    ps.executeUpdate();
			System.out.println("出勤登録されました。");
		} catch (SQLException e) {
			System.err.println("INSERT失敗: " + e.getMessage());
		}
	}

	private static void updateTimes(Scanner sc) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = conn.prepareStatement("update attendance set finish =? where id=?");) {
			LocalDateTime now=LocalDateTime.now();
			int id=getAttendanceRecord();
			ps.setTimestamp(1, Timestamp.valueOf(now));
			ps.setInt(2,id);
			System.out.println(ps.toString());
			ps.executeUpdate();
			System.out.println("退勤登録されました。");
		} catch (SQLException e) {
			System.err.println("UPDATE失敗: " + e.getMessage());
		}
	}
	
	private static int getAttendanceRecord() throws Exception{
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn.prepareStatement("select * from attendance where user_id=? and finish is null");) {
                ps.setInt(1, userId);
                try(ResultSet rs=ps.executeQuery()){
                	if(rs.next()) {
                		int id= rs.getInt("id");
                		return id;
                	}
                	return -1;
                }
		}catch (SQLException e) {
			System.err.println("UPDATE失敗: " + e.getMessage());
			   return -1;
		}
	}
	private static void showAllAttendance(Scanner sc) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn.prepareStatement("select * from attendance where user_id=?")){
			    System.out.println("勤怠一覧");
			    ps.setInt(1, userId);
			    try(ResultSet rs=ps.executeQuery()){
			        boolean any=false;
				     while (rs.next()) {
				    	 any=true;
				    	 int id=rs. getInt("user_id");
						 Timestamp start = rs.getTimestamp("start");
						 Timestamp finish=rs.getTimestamp("finish");
					     System.out.println("("+id + "　出勤:　" + start + "　退勤:　" + finish + ")");
	               }
				   if(!any) {
					   System.out.println("データがありません。");
				   }
		}
	}			
	}
}