
// MySQL（データベース）とやりとりするための標準ライブラリ
import java.sql.*;

public class SelectBook {
    public static void main(String[] args) throws Exception {

        // === 1. MySQLに接続するための情報を準備 ===
        // JDBC URL：接続先データベースの場所と設定を表す文字列。
        // localhost：自分のPC、3306：MySQLのポート番号、
        // sample：接続するデータベース名。
        // ?以降はSSLや文字コードなどのオプション設定。
        String url = "jdbc:mysql://localhost:3306/school_db"
                   + "?useSSL=false&allowPublicKeyRetrieval=true"
                   + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";

        // MySQLにログインするユーザー名とパスワード
        String user = "root";
        String pass = "Fmps9632";

        // === 2. MySQLに接続する ===
        // DriverManager.getConnection()で指定したURLに接続。
        // 成功すると、接続を表す「Connection」オブジェクトが返る。
        Connection conn = DriverManager.getConnection(url, user, pass);

        // === 3. SQL文を送るための準備 ===
        // 「Statement」オブジェクトを使ってMySQLにSQL文を送る。
        // ここではシンプルなSELECT文を実行するために使う。
        Statement st = conn.createStatement();

        // === 4. SELECT文を実行する ===
        // fruitsテーブルからid・name・priceの3列を取得。
        // executeQuery()はSELECT文専用で、結果はResultSetで受け取る。
        ResultSet rs = st.executeQuery("SELECT id, title, author FROM books");

        // === 5. 取得したデータを1行ずつ読み取る ===
        // ResultSetは検索結果の「表」を扱うオブジェクト。
        // rs.next()で「次の行」に進み、データがあればtrueを返す。
        while (rs.next()) {

            // 各列の値を取り出す
            // getInt("列名")：整数を取得
            // getString("列名")：文字列を取得
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");

            // 取り出したデータを整形して出力
            System.out.println(
                "id: " + id +
                ",title:"+title+
                ", author: " + author
            );
        }

        // === 6. 後片付け（開いた順番の逆に閉じる） ===
        // ResultSet → Statement → Connection の順に閉じるのが基本。
        rs.close();   // 検索結果を閉じる
        st.close();   // SQL実行用のStatementを閉じる
        conn.close(); // MySQLとの接続を閉じる

        // これでプログラム終了。テーブル内のデータがコンソールに表示されます。
    }
}

