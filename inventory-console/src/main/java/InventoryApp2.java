import java.sql.*;
import java.util.Scanner;

public class InventoryApp2 {

	private static final String url =
		       "jdbc:mysql://localhost:3306/inventory_app"
		       + "?useSSL=false&allowPublicKeyRetrieval=true"
		       + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
		   private static final String user = "root";
		   private static final String pass = "Fmps9632";
		   
		   public static void main(String[] args) throws Exception {
		       try(Scanner sc=new Scanner(System.in)) {
		    	   while(true) {
		    		   System.out.println("===在庫管理 メニュー ===");
		    		   System.out.println("1)登録 … 商品名と価格、数量を入力");
		    		   System.out.println("2)一覧 … 登録済み商品を表示");
		    		   System.out.println("3)更新 …商品の在庫数更新");
		    		   System.out.println("0) 終了 … 終了");
		    		   System.out.println("番号を選んでください。");

		    		   String choice=sc.nextLine();
		    		   switch(choice) {
		    		   case "1":
		    		   	insertItems(sc);
		    		       break;
		    		   case "2":
		    		   	showAllItems();
		    		   	sc.nextLine();
		    		       break;
		    		    case "3":   
		    		    showAllItems();
		    		    update(sc);
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
		   private static void showAllItems() throws Exception {
			   try(Connection conn = DriverManager.getConnection(url, user, pass);
				   Statement st = conn.createStatement();
				   ResultSet rs = st.executeQuery("select id,name,quantity from items ");) {
				     while (rs.next()) {		
				    	 int id=rs. getInt("id");
						  String name = rs.getString("name");
						  int quantity=rs.getInt("quantity");
					      System.out.println(id + ":" + name + "(" + quantity + ")");
	            }
	        } catch (SQLException e) {
	        	 System.err.println("SELECT失敗: " + e.getMessage());
	        } 
	        }
		   private static void insertItems(Scanner sc) throws Exception {
		       try (Connection conn = DriverManager.getConnection(url, user, pass);
		  		    PreparedStatement ps = conn.prepareStatement( "INSERT INTO items (name,quantity) VALUES(?,?)");){
			       System.out.print(" 商品名を入力してください。");
			       String itemName= sc.nextLine();
			       System.out.println("数字を入力してください。");
			       int quantity=Integer.parseInt(sc.nextLine()); 
	    		   	if(quantity<0) {
	    		   		System.out.println("0以上の数字を入力してください");
	    		   		quantity=Integer.parseInt(sc.nextLine());
	    		   	}  
		    ps.setString(1, itemName);
		    ps.setInt(2,quantity);
		    int num =ps.executeUpdate();
		   	System.out.println(num+"件登録されました");
		       } catch (SQLException e) {
		    	   System.err.println("INSERT失敗: " + e.getMessage());
		       }		 
		   }
		   private static void update(Scanner sc) throws Exception {
			   try(Connection conn = DriverManager.getConnection(url, user, pass);
					   PreparedStatement ps = conn.prepareStatement( "UPDATE items set quantity=? where id=? ");){   
				   System.out.println("商品IDを選択してください。");
				   int id=Integer.parseInt(sc.nextLine());
				   System.out.println("商品の個数を入力してください。");
				   int quantity=Integer.parseInt(sc.nextLine());
				   ps.setInt(1,quantity);
				   ps.setInt(2,id);
				   int num=ps.executeUpdate();
				   System.out.println(num+"件登録されました。");
			   }catch (SQLException e) {
				   System.err.println("UPDATE失敗: " + e.getMessage());
			   }
		   }
	}
