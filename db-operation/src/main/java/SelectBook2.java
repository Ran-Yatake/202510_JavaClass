import java.sql.*;

		// MySQL（データベース）とやりとりするための標準ライブラリ
		import java.sql.*;

		// ユーザーからキーボード入力を受け取るためのクラス
		import java.util.Scanner;

		public class SelectBook2 {
		    public static void main(String[] args) throws Exception {

		        // === 1. MySQLに接続するための情報を用意 ===
		        // JDBC URL：どのデータベースに接続するかを指定
		        // 「localhost」は自分のPC上のMySQL、「sample」はデータベース名
		        // その後ろの ? 以降は接続オプション（文字コード・時刻設定など）
		        String url = "jdbc:mysql://localhost:3306/school_db"
		                   + "?useSSL=false&allowPublicKeyRetrieval=true"
		                   + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";

		        // MySQLに接続するためのユーザー名とパスワード
		        String user = "root";
		        String pass = "Fmps9632";

		        // === 2. コンソールからデータを入力してもらう ===
		        // Scannerを使ってキーボード入力を受け取る
		        Scanner sc = new Scanner(System.in);

		        // 果物の名前を入力
		        System.out.print("本のタイトルを入力してください: ");
		        String title = sc.nextLine();

		        // 価格を入力（数値なので nextInt() を使う）
		        System.out.print("著者名を入力してください: ");
		        String author = sc.nextLine();

		        // === 3. MySQLに接続する ===
		        // DriverManager.getConnection()でデータベースに接続し、
		        // その接続を管理するConnectionオブジェクトを取得する。
		        Connection conn = DriverManager.getConnection(url, user, pass);

		        // === 4. SQL文を準備する（INSERT文） ===
		        // PreparedStatementを使うと、「?」の部分に値を後から安全に入れられる。
		        // この方法はSQLインジェクション（不正なSQL操作）を防ぐのに有効。
		        PreparedStatement ps = conn.prepareStatement(
		            "INSERT INTO books(title,author) VALUES(?, ?)"
		        );

		        // 1つ目の「?」に果物の名前をセット
		        ps.setString(1, title);

		        // 2つ目の「?」に価格をセット
		        ps.setString(2, author);

		        // SQLを実行（INSERT文なのでデータが追加される）
		        // executeUpdate()はINSERT/UPDATE/DELETE文に使うメソッド。
		        ps.executeUpdate();

		        // === 5. 登録完了メッセージを表示 ===
		        System.out.println("1件登録しました。");

		        // === 6. 後片付け（リソースの解放） ===
		        // 開いた順番の逆に閉じるのが基本ルール。
		        ps.close();      // SQL実行用オブジェクトを閉じる
		        conn.close();    // データベースとの接続を閉じる
		        sc.close();      // 入力用Scannerを閉じる

		        // これでプログラム終了。新しい果物データがfruitsテーブルに追加されます！
		    }
		}

