import java.sql.*;
import java.util.Scanner;

public class UltraSimpleDBApp {
   // 自分の環境に合わせて変更してください
   private static final String url =
       "jdbc:mysql://localhost:3306/school_db"
       + "?useSSL=false&allowPublicKeyRetrieval=true"
       + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
   private static final String user = "root";
   private static final String pass = "Fmps9632";


   public static void main(String[] args) throws Exception {
       Scanner sc = new Scanner(System.in);
       System.out.print("メモを入力してください: ");
       String text = sc.nextLine(); 
       sc.close();
       insertMemo(text);
       showAllMemos();
   }


   // 全件SELECTして表示する用のメソッド
   private static void showAllMemos() throws Exception {
		 Connection conn = DriverManager.getConnection(url, user, pass);
		 Statement st = conn.createStatement();
		 ResultSet rs = st.executeQuery("select id,text,created_at from memo order by id desc");		 
	     while (rs.next()) {
			  int id=rs. getInt("id");
			  String memoText = rs.getString("text");
			  Timestamp created_at=rs.getTimestamp("created_at");
		      System.out.println(id + ":" + memoText + "(" + created_at + ")");
	     };	   
		 rs.close(); 
	     st.close();  
	     conn.close(); 
   }
   // INSERTするメソッド
   private static void insertMemo(String text) throws Exception {
	    Connection conn = DriverManager.getConnection(url, user, pass);
	    PreparedStatement ps = conn.prepareStatement(
	            "INSERT INTO memo(text) VALUES(?)"
	    		);
	    ps.setString(1, text);
	    ps.executeUpdate();
		ps.close();  
	    conn.close(); 
 
   }
}