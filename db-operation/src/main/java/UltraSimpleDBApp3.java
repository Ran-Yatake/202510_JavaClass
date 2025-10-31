import java.sql.*;
import java.util.Scanner;

public class UltraSimpleDBApp3 {

	private static final String url =
		       "jdbc:mysql://localhost:3306/school_db"
		       + "?useSSL=false&allowPublicKeyRetrieval=true"
		       + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
		   private static final String user = "root";
		   private static final String pass = "Fmps9632";

		   public static void main(String[] args) throws Exception {
		       try(Scanner sc = new Scanner(System.in);) {
			       System.out.print("メモを入力してください: ");
			       String text = sc.nextLine(); 
			       insertMemo(text);
			       showAllMemos();	
		        } 
		    }
		   // 全件SELECTして表示する用のメソッド
		   private static void showAllMemos() throws Exception {
			   try(Connection conn = DriverManager.getConnection(url, user, pass);
				   Statement st = conn.createStatement();
				   ResultSet rs = st.executeQuery("select id,text,created_at from memo order by id desc");) {
				     while (rs.next()) {
						  int id=rs. getInt("id");
						  String memoText = rs.getString("text");
						  Timestamp created_at=rs.getTimestamp("created_at");
					      System.out.println(id + ":" + memoText + "(" + created_at + ")");
	            }
	        } catch (SQLException e) {
	        	 System.err.println("SELECT失敗: " + e.getMessage());
	        } 
	        }
		   // INSERTするメソッド
		   private static void insertMemo(String text) throws Exception {
		       try (Connection conn = DriverManager.getConnection(url, user, pass);
		  		     PreparedStatement ps = conn.prepareStatement( "INSERT INTO memo(text) VALUES(?)");){
			//ここでデータべ－スの操作
		    ps.setString(1, text);
		    ps.executeUpdate();
		       } catch (SQLException e) {
		           // 例外発生時はメッセージを出す
		    	   System.err.println("INSERT失敗: " + e.getMessage());
		       }		 
		   }
		}