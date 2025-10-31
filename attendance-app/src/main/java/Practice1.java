import java.sql.*;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Practice1 {

	private static final String url =
		       "jdbc:mysql://localhost:3306/simple"
		       + "?useSSL=false&allowPublicKeyRetrieval=true"
		       + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
		   private static final String user = "root";
		   private static final String pass = "Fmps9632";
		   
		   public static void main(String[] args) throws Exception {
		       try(Scanner sc=new Scanner(System.in)) {
		    	   while(true) {
		    		   System.out.println("=== メモアプリ メニュー ===");
		    		   System.out.println("1)メモを追加");
		    		   System.out.println("2)メモ一覧を表示");
		    		   System.out.println("3)メモ詳細を表示");
		    		   System.out.println("4)メモ更新");
		    		   System.out.println("5)メモ削除");
		    		   System.out.println("0)終了");
		    		   System.out.println("番号を選んでください。");

		    		   String choice=sc.nextLine();
		    		   switch(choice) {
		    		   case "1":
		    		   	insertMemos(sc);
		    		       break;
		    		   case "2":
		    		   	showAllMemos(sc);
		    		   	showText(sc);
		    		       break;
		    		   case"3":
		    			   showAllMemos(sc);
		    		   case "4":
		    			   showAllMemos(sc);
							update(sc);
							break;
		    		   case"5":
							showAllMemos(sc);
							delete(sc);
							break;
		    		   case "0":
		    			System.out.println("終了します。");
		    		   	return;
		    		   	default:
		    		   	System.out.println("不正な入力です。");
		    		   }
		    	   }	
		        } 
		    }
		   private static void showAllMemos(Scanner sc) throws Exception {
			   String sql = "SELECT id, title FROM memo";
				try (Connection conn = DriverManager.getConnection(url, user, pass);
			             PreparedStatement ps = conn.prepareStatement(sql);
			             ResultSet rs = ps.executeQuery()
						) {
					boolean any = false;
					while (rs.next()) {
						any = true;
						int getId=rs.getInt("id");
						System.out.print(getId);
						String title = rs.getString("title");
						System.out.println(":"+title);
					}
					if (!any) {
						System.out.println("データがありません。");
					}
				} catch (SQLException e) {
					System.err.println("SELECT失敗: " + e.getMessage());
				}
			}
		   private static void showText(Scanner sc) {
			   System.out.println("IDを表示してください。");
			   int id=Integer.parseInt(sc.nextLine());
			   try(Connection conn = DriverManager.getConnection(url,user,pass);
			       PreparedStatement ps = conn.prepareStatement("select id ,title,text from memo where id=?")){
				   ps.setInt(1,id);
				   try (ResultSet rs= ps.executeQuery()){
					   if(!rs.next()) {
						   System.out.println("該当IDのメモはありません。");
						   return;
					   }
					   System.out.println("\n--- メモ詳細 ---");
		                System.out.println("id: " + rs.getInt("id"));
		                System.out.println("title: " + rs.getString("title"));
		                System.out.println("text: \n" + rs.getString("text")); 
				   }
			   }catch(SQLException e){
				   System.err.println("取得失敗:"+e.getMessage());
			   }
		   }
		   private static void insertMemos(Scanner sc) throws Exception {
		       try (Connection conn = DriverManager.getConnection(url, user, pass);
		  		     PreparedStatement ps = conn.prepareStatement( "INSERT INTO memo(title,text) VALUES(?,?)");){
		    	   System.out.println("タイトルを入力してください: ");
		    	   String title=sc.nextLine();
		    	   System.out.println("メモを入力してください: ");       
			       String text = sc.nextLine();
			        ps.setString(1, title);
				    ps.setString(2, text);
				    ps.executeUpdate();
		       } catch (SQLException e) {		           
		    	   System.err.println("INSERT失敗: " + e.getMessage());
		       }		 
		   }   	
    	private static void update(Scanner sc) throws Exception {
    		try (Connection conn = DriverManager.getConnection(url, user, pass);
    				PreparedStatement ps = conn.prepareStatement("UPDATE memo set text=? where id=? ");) {
    			System.out.println("メモIDを選択してください。");
    			int id = Integer.parseInt(sc.nextLine());
    			System.out.println("新しい内容を入力してください。");
    			String text = sc.nextLine();
    			ps.setString(1, text);  			
    			ps.setInt(2, id);
    			int num = ps.executeUpdate();
    			System.out.println(num + "件登録されました。");
    		} catch (SQLException e) {
    			System.err.println("UPDATE失敗: " + e.getMessage());
    		}
    	}

    	private static void delete(Scanner sc) throws Exception {
    		try (Connection conn = DriverManager.getConnection(url, user, pass);
    				PreparedStatement ps = conn.prepareStatement("delete from memo where id=? ");) {
    			System.out.println("メモIDを選択してください。");
    			int id = Integer.parseInt(sc.nextLine());
    			ps.setInt(1, id);
    			int num = ps.executeUpdate();
    			System.out.println(num + "件削除されました。");
    		} catch (SQLException e) {
    			System.err.println("DELETE失敗: " + e.getMessage());
    		}
    	}
    }