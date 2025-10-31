import java.sql.*;
import java.util.Scanner;

public class InventoryApp6 {
	static String userName;
	private static final String url = "jdbc:mysql://localhost:3306/inventory_app"
			+ "?useSSL=false&allowPublicKeyRetrieval=true" + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
	private static final String user = "root";
	private static final String pass = "Fmps9632";
	
	   public static void main(String[] args) throws Exception {
	       try(Scanner sc=new Scanner(System.in)) {
	    	   if(!login(sc)) {
	    		   return;
	    	   }

			while (true) {
				System.out.println("===在庫管理 メニュー ===");
				System.out.println("1)登録 … 商品名と価格、数量を入力");
				System.out.println("2)一覧 … 登録済み商品を表示");
				System.out.println("3)更新 …商品の在庫数更新");
				System.out.println("4)削除 …商品の削除");
				System.out.println("0) 終了 … 終了");
				System.out.println("番号を選んでください。");

				String choice = sc.nextLine();
				switch (choice) {
				case "1":
					insertItems(sc);
					break;
				case "2":
					showAllItems(sc);
					break;
				case "3":
					showAllItems(sc);
					update(sc);
					break;
				case "4":
					showAllItems(sc);
					delete(sc);
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
						.prepareStatement("select name,email,password from users where email=? and password=?");) {
			System.out.println("メールアドレスを入力してください。");
			String email = sc.nextLine().trim();
			System.out.println("パスワードを入力してください。");
			String passWord = sc.nextLine().trim();
			ps.setString(1, email);
			ps.setString(2, passWord);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					System.out.println("ログイン成功しました。");
					userName=rs.getString("name");
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

	private static void showAllItems(Scanner sc) throws Exception {
		String sql = "select id,name,quantity,updater from items";
		System.out.println("絞り込み条件を条件式で入力してください。");
		String where = sc.nextLine();
		sql += " where " + where;
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);) {
			boolean any = false;
			while (rs.next()) {
				any = true;
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				String updater=rs.getString("updater");
				System.out.println(id + ":" + name + "(" + quantity + ")"+updater);
			}
			if (!any) {
				System.out.println("データがありません。");
			}
		} catch (SQLException e) {
			System.err.println("SELECT失敗: " + e.getMessage());
		}
	}

	private static void insertItems(Scanner sc) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO items (name,quantity,updater) VALUES(?,?,?)");) {
			System.out.print(" 商品名を入力してください。");
			String itemName = sc.nextLine();
			System.out.println("数字を入力してください。");
			int quantity = Integer.parseInt(sc.nextLine());
			if (quantity < 0) {
				System.out.println("0以上の数字を入力してください");
				quantity = Integer.parseInt(sc.nextLine());
			}
			ps.setString(1, itemName);
			ps.setInt(2, quantity);
			ps.setString(3,userName);
			int num = ps.executeUpdate();
			System.out.println(num + "件登録されました");
		} catch (SQLException e) {
			System.err.println("INSERT失敗: " + e.getMessage());
		}
	}

	private static void update(Scanner sc) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn.prepareStatement("UPDATE items set quantity=? ,updater=? where id=? ");) {
			System.out.println("商品IDを選択してください。");
			int id = Integer.parseInt(sc.nextLine());
			System.out.println("商品の個数を入力してください。");
			int quantity = Integer.parseInt(sc.nextLine());
			ps.setInt(1, quantity);
			ps.setString(2,userName);
			ps.setInt(3, id);
			int num = ps.executeUpdate();
			System.out.println(num + "件登録されました。");
		} catch (SQLException e) {
			System.err.println("UPDATE失敗: " + e.getMessage());
		}
	}

	private static void delete(Scanner sc) throws Exception {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn.prepareStatement("delete from items where id=? ");) {
			System.out.println("商品IDを選択してください。");
			int id = Integer.parseInt(sc.nextLine());
			ps.setInt(1, id);
			int num = ps.executeUpdate();
			System.out.println(num + "件削除されました。");
		} catch (SQLException e) {
			System.err.println("DELETE失敗: " + e.getMessage());
		}
	}
}