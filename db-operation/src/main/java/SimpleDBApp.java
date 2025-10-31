import java.sql.*;
import java.util.Scanner;
public class SimpleDBApp {

	public static void main(String[] args)throws Exception {
		// TODO 自動生成されたメソッド・スタブ
        String url = "jdbc:mysql://localhost:3306/school_db"
                + "?useSSL=false&allowPublicKeyRetrieval=true"
                + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
        String user = "root";
        String pass = "Fmps9632";
        Connection conn = DriverManager.getConnection(url, user, pass);
        Statement st = conn.createStatement();
        // SQL（データベースに送る命令）を実行するための準備をする
        // Statement はシンプルな SQL 実行用のクラス

        // --- テーブル作成（なければ） ---
        // 「memo」という名前のテーブルを作る
        // すでに存在する場合は何もしない（IF NOT EXISTS）
        // id列：自動で番号が増える（AUTO_INCREMENT）
        // text列：メモ本文を入れる
        // created_at列：自動的に現在の時刻が入る
        st.execute("CREATE TABLE IF NOT EXISTS memo("
                 + "id INT AUTO_INCREMENT PRIMARY KEY,"
                 + "text VARCHAR(200),"
                 + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );
        Scanner sc = new Scanner(System.in);
        System.out.print("メモを入力してください: ");
        String text = sc.nextLine();        
        PreparedStatement ps = conn.prepareStatement(
	            "INSERT INTO memo(text) VALUES(?)"
        		);
        ps.setString(1, text);
        ps.executeUpdate();
        ResultSet rs = st.executeQuery("select id,text,created_at from memo");		 
        		 
		 while (rs.next()) {
			 int id=rs. getInt("id");
			 String memoText = rs.getString("text");
			 Timestamp created_at=rs.getTimestamp("created_at");
		     System.out.println(id + ":" + memoText + "(" + created_at + ")");
		 };	
		 rs.close(); 
		 ps.close();
	     st.close();  
	     conn.close(); 
	}
}