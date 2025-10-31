package com.example.formecho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FormEcho10Application {

	public static void main(String[] args) {
		SpringApplication.run(FormEcho10Application.class, args);
		System.out.println("起動: http://localhost:8080/");
	}
}
@RestController 
class EchoController {
	private static final String url = "jdbc:mysql://localhost:3306/inventory_db"
	        + "?useSSL=false&allowPublicKeyRetrieval=true"
	        + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
	private static final String user = "root";
	private static final String pass = "Fmps9632";
	private static String loginUser;   
	    	   @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	    		public String showForm() {
	    			return """
	    					      <!doctype html>
	    					      <html lang="ja">
	    					        <meta charset="utf-8">
	    					        <link rel="stylesheet" href="/styles.css">	    					        
	    					        <title>JDBCメモ（ブラウザ版・最小）</title>
	    					        <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">  
	    					         <h1>ログインメニュー</h1>	    					         
	    					          <div class="login">       	    					          
	    					          <form method="POST" action="/inventory">
	    					          <div class="email-and-pass">
	    					           <div class="email">
	    					            <div>Eメール</div>
	    					            <div><input type="text" name="email" placeholder="メールアドレスを入力" required style="flex:1; padding:.5rem;"></div>
	    					           </div><br>
	    					           <div class="pass">
	    					            <div>パスワード</div>
	    					            <div><input type="text" name="pass" placeholder="パスワードを入力" required style="flex:1; padding:.5rem;"></div>
	    					           </div>
	    					           </div><br>
	    					            <div class=button>
	    					            <button type="submit" style="padding:.5rem 1rem;">ログイン</button>
	    					            </div>
	    					          </form>
	    					        </body>
	    					      </html>
	    					""";
	    		}
	
	    	///トップページ
	    	@PostMapping(value="/inventory",
	    		consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,
	    			produces = MediaType.TEXT_HTML_VALUE)
	    		public String inventory(
	    			@RequestParam(name="email",defaultValue="")String address,	
	    	        @RequestParam(name="pass",defaultValue="")String password){
	    		System.out.println(address+" "+password);
	    		        if(login(address,password)) {
	    		        	StringBuilder list=selectAllStock();
	    		        	
	    		        	return """
		    					      <!doctype html>
		    					      <html lang="ja">
		    					        <meta charset="utf-8">
		    					         <link rel="stylesheet" href="/styles.css">
		    					        <title>JDBCメモ（ブラウザ版・最小）</title>
		    					        <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
		    					         <h1>トップページ</h1>				    
		    					         <div class="table-wrapper">"""+
                                          list
		    					          +"""
		    					         </div>	          
		    					          <div class=button>		    					          		
		    					         <form action="/register" method="post">
		    					         <button type="submit" style="padding:.5rem 1rem;">在庫登録</button>
		    					         </form>
		    					         </div>
		    					         <div class=button>
		    					         <form action="/update" method="post">
		    					         <button type="submit" style="padding:.5rem 1rem;">在庫更新</button>
		    					         </form>
		    					          </div>
		    					         <div class=button>
		    					         <form action="/delete" method="post">
		    					         <button type="submit" style="padding:.5rem 1rem;">在庫削除</button>
		    					         </form>
		    					          </div>
		    					         <div class=button>
		    					         <form action="/search" method="post">
		    					         <div class=button>
		    					         <button type="submit" style="padding:.5rem 1rem;">在庫検索</button>
		    					           </div>
		    					         </form>		    					        	    				         
		    					        </body>
		    					      </html>
		    					      """;
	    		        }else {
	    		        	return""" 
	    		        			
	    		        <!doctype html>
  					      <html lang="ja">
  					        <meta charset="utf-8">
  					        <title>JDBCメモ（ブラウザ版・最小）</title>
  					        <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
  					         <p>ログインに失敗しました。</p>
  					        </body>
  					      </html>
  					      """;
	    		        }
	    	             
	    					
	    	}   
	    	   
	    	   private boolean login(String email ,String password) {
	    		   try (Connection conn = DriverManager.getConnection(url, user, pass);
	    					PreparedStatement ps = conn
	    							.prepareStatement("select * from pass where email=? and password=?");) {
	    				ps.setString(1, email);
	    				ps.setString(2, password);

	    				try (ResultSet rs = ps.executeQuery()) {
	    					if (rs.next()) {
	    						loginUser=rs.getString("name");
	    						System.out.println("ログイン成功しました。");
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
	    	   
	    		    		    		    		    		    	
	    	///在庫登録ページ
	    	@PostMapping(value="/register",
		    		consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		    			produces = MediaType.TEXT_HTML_VALUE)
		    		public String register() {
	    	return """
				      <!doctype html>
				      <html lang="ja">
				        <meta charset="utf-8">
				        <link rel="stylesheet" href="/styles.css">
				        <title>JDBCメモ（ブラウザ版・最小）</title>
				        <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
				         <h1>在庫登録ページ</h1>
				          <form method="POST" action="/register-success" style="display:flex; gap:.5rem;"><br>
				          
				          <div class="regestar">
				          <p>名前</p>
				            <input type="text" name="name" placeholder="名前を入力" required style="flex:1; padding:.5rem;">	
				            </div>	
				            <div class="regestar">		          
				          <p>価格</p>				          
				            <input type="text" name="price" placeholder="価格を入力" required style="flex:1; padding:.5rem;">
				            </div>
				            <div class="regestar">					          
				          <p>数量</p>				         
				          <input type="text" name="quantity" placeholder="数量を入力" required style="flex:1; padding:.5rem;">
				          </div>
				          <div class=regestar-button>
				            <button type="submit" style="padding:.5rem 1rem;width:100px;height:50px;">在庫登録</button>
				            </div>
				          </form>
				        </body>
				      </html>
				""";
	    	}
	    	
	    	
	    	private StringBuilder selectAllStock() {
				int id = 0;
				String name = null;
				int price=0;
				int quantity = 0;
				String updater=null;
				StringBuilder list = new StringBuilder();
				list.append("<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\" style=\"border-collapse: collapse;\">\n"
						+ "  <tr style=\"background-color: #f2f2f2;\">\n"
						+ "    <th>id</th>\n"
						+ "    <th>品目</th>\n"
						+ "    <th>単価</th>\n"
						+ "    <th>数量</th>\n"
						+ "    <th>最終更新者</th>\n"
						+ "  </tr>\n");				
				try (Connection conn = DriverManager.getConnection(url, user, pass);
						PreparedStatement ps = conn.prepareStatement("SELECT * FROM itemとuser");) {
					try (ResultSet rs = ps.executeQuery();) {
						while (rs.next()) {						
							id = rs.getInt("id");
							name = rs.getString("name");
							price = rs.getInt("price");
							quantity=rs.getInt("quantity");
							updater=rs.getString("updater");
							
				            list.append("<tr><td>")
				                .append(id)
				                .append("</td><td>")
				                .append(name) // 学習用でも最低限の表示崩れ防止
				                .append(" </td><td>")				                
				                .append(price)
				                .append("</td><td>")
				                .append(quantity)
				                .append("</td><td>")
				                .append(updater)
				                .append("</td></tr>");
				             				             
						}
					}
					list.append("</table>");
					return list;
				} catch (SQLException e) {
					System.err.println("SELECT失敗: " + e.getMessage());
					return null;
				}
			}
   	
	    	@PostMapping(value = "/register-success",
	    			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
	    			produces = MediaType.TEXT_HTML_VALUE)
	    	public String registar(
	    			@RequestParam(name = "name", defaultValue = "") String name,
	    			@RequestParam(name = "price", defaultValue = "") int price,
	    			@RequestParam(name = "quantity", defaultValue = "") int quantity) {
	    		System.out.println(name+price+quantity);
	    		if(insertStock(name,price,quantity)) {
	    			return""" 
	    	    			<!doctype html>
	    	               <html lang="ja">
	    	                 <meta charset="utf-8">
	    	                 <title>JDBCメモ（ブラウザ版・最小）</title>
	    	                 <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
	    	                  <h1>在庫が登録されました。</h1>
	    	                 </body>
	    	               </html>
	    	                     """;
	    		}else {
	    			return""" 
	    	    			<!doctype html>
	    	               <html lang="ja">
	    	                 <meta charset="utf-8">
	    	                 <title>JDBCメモ（ブラウザ版・最小）</title>
	    	                 <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
	    	                  <h1>登録が失敗しました。</h1>
	    	                 </body>
	    	               </html>
	    	                     """;
	    		}
	    		
	    	}
	    	private boolean insertStock(String name,int price,int quantity) {
				try (Connection conn = DriverManager.getConnection(url, user, pass);
						PreparedStatement ps = conn.prepareStatement("insert into itemとuser (name,price,quantity,updater) values(?,?,?,?); ");) {
					ps.setString(1, name);  			
					ps.setInt(2, price);
					ps.setInt(3, quantity);
					ps.setString(4, loginUser);
					ps.executeUpdate();
					return true;
				} catch (SQLException e) {
					System.err.println("REGISTER失敗: " + e.getMessage());
					return false;
				}
			}
	    	

               //更新ページ
	    	@PostMapping(value="/update",
		    		consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		    			produces = MediaType.TEXT_HTML_VALUE)
		    		public String update() {
	    	return """
				      <!doctype html>
				      <html lang="ja">
				        <meta charset="utf-8">
				        <title>JDBCメモ（ブラウザ版・最小）</title>
				        <link rel="stylesheet" href="/styles.css">
				        <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
				         <h1>在庫更新ページ</h1>				         				         
				          <form method="POST" action="/update-success">
				          <div class=update>
				          <P>ID </p> 
				          <input type="text" name="id" placeholder="IDを入力" required style="flex:1; padding:.5rem;">
				          </div>
				          <div class=update>
				            <p>名前</p>				         
				            <input type="text" name="name" placeholder="名前を入力" required style="flex:1; padding:.5rem;">	
				            </div>
				           <div class=update> 			          
				          <p>価格</p>			          
				            <input type="text" name="price" placeholder="価格を入力" required style="flex:1; padding:.5rem;">	
				            </div>
				            <div class=update>			          
				          <p>数量</p>				         
				          <input type="text" name="quantity" placeholder="数量を入力" required style="flex:1; padding:.5rem;">
				          </div>
				          <div class="update-button">				         
				            <button type="submit" style="padding:.5rem 1rem;width:100px;height:50px;">在庫更新</button>
				          </div>
				          </form>
				        </body>
				      </html>
				""";
	    	}
	    	
   	
	    	@PostMapping(value = "/update-success",
	    			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
	    			produces = MediaType.TEXT_HTML_VALUE)
	    	public String update(
	    			@RequestParam(name = "id", defaultValue = "") int id,
	    			@RequestParam(name = "name", defaultValue = "") String name,
	    			@RequestParam(name = "price", defaultValue = "") int price,
	    			@RequestParam(name = "quantity", defaultValue = "") int quantity) {
	    		System.out.println(id+name+price+quantity);
	    		if(updateStock(id,name,price,quantity)) {
	    			return""" 
	    	    			<!doctype html>
	    	               <html lang="ja">
	    	                 <meta charset="utf-8">
	    	                 <title>JDBCメモ（ブラウザ版・最小）</title>
	    	                 <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
	    	                  <h1>在庫が更新されました。</h1>
	    	                 </body>
	    	               </html>
	    	                     """;
	    		}else {
	    			return""" 
	    	    			<!doctype html>
	    	               <html lang="ja">
	    	                 <meta charset="utf-8">
	    	                 <title>JDBCメモ（ブラウザ版・最小）</title>
	    	                 <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
	    	                  <h1>更新が失敗しました。</h1>
	    	                 </body>
	    	               </html>
	    	                     """;
	    		}	    		
	    	}
	    		    	
	    private boolean updateStock(int id,String name,int price,int quantity) {
			try (Connection conn = DriverManager.getConnection(url, user, pass);
					PreparedStatement ps = conn.prepareStatement("UPDATE itemとuser set name=?,price=?,quantity=?,name=? where id=? ");) {
				System.out.println(id+name+price+quantity);
				ps.setString(1, name);  			
				ps.setInt(2, price);
				ps.setInt(3, quantity);
				ps.setString(4, loginUser);
				ps.setInt(5, id);		
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				System.err.println("UPDATE失敗: " + e.getMessage());
				return false;
				}
		}
	    
              //削除ページ
			@PostMapping(value="/delete",
			consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,
				produces = MediaType.TEXT_HTML_VALUE)
			public String delete() {
			return """
			  <!doctype html>
			  <html lang="ja">
			    <meta charset="utf-8">
			    <title>JDBCメモ（ブラウザ版・最小）</title>
			    <link rel="stylesheet" href="/styles.css">
			    <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
			     <h1>在庫削除ページ</h1>				         
			     <P>ID</p>
			      <form method="POST" action="/delete-success" style="display:flex; gap:.5rem;">
			      <input type="text" name="id" placeholder="IDを入力" required style="flex:1; padding:.5rem;">
			        <button type="submit" style="padding:.5rem 1rem;">在庫削除</button>
			      </form>
			    </body>
			  </html>
			""";
			}

	    	@PostMapping(value = "/delete-success",
	    			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
	    			produces = MediaType.TEXT_HTML_VALUE)
	    	public String delete(
	    			@RequestParam(name = "id", defaultValue = "") String input) {
	    		int id =Integer.parseInt(input);
	    		if(deleteMemoById(id)) {
	    			return""" 
	    	    			<!doctype html>
	    	               <html lang="ja">
	    	                 <meta charset="utf-8">
	    	                 <title>JDBCメモ（ブラウザ版・最小）</title>
	    	                 <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
	    	                  <h1>在庫が削除されました。</h1>
	    	                 </body>
	    	               </html>
	    	                     """;
	    		}else {
	    			return""" 
	    	    			<!doctype html>
	    	               <html lang="ja">
	    	                 <meta charset="utf-8">
	    	                 <title>JDBCメモ（ブラウザ版・最小）</title>
	    	                 <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
	    	                  <h1>削除が失敗しました。</h1>
	    	                 </body>
	    	               </html>
	    	                     """;
	    		}
	    	}
	    	    	
	    	private boolean deleteMemoById(int id) {
	    		try (Connection conn = DriverManager.getConnection(url, user, pass);
	    				PreparedStatement ps = conn.prepareStatement("delete from itemとuser where id=? ");) {
	    			ps.setInt(1, id);
	    			ps.executeUpdate();
	    			return true;
	    		} catch (SQLException e) {
	    			System.err.println("DELETE失敗: " + e.getMessage());
	    			return false;
	    		}
	    	}
	    	//検索ページ
			@PostMapping(value="/search",
			consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,
				produces = MediaType.TEXT_HTML_VALUE)
			public String search() {
			return """
			  <!doctype html>
			  <html lang="ja">
			    <meta charset="utf-8">
			    <title>JDBCメモ（ブラウザ版・最小）</title>
			    <link rel="stylesheet" href="/styles.css">
			    <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
			     <h1>在庫検索ページ</h1>				         
			     <P>ID</p>
			      <form method="POST" action="/search-success" style="display:flex; gap:.5rem;">
			      <input type="text" name="id" placeholder="IDを入力" required style="flex:1; padding:.5rem;">
			        <button type="submit" style="padding:.5rem 1rem;">在庫検索</button>
			      </form>
			    </body>
			  </html>
			""";
			}

	    	@PostMapping(value = "/search-success",
	    			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
	    			produces = MediaType.TEXT_HTML_VALUE)
	    	public String search(
	    			@RequestParam(name = "id", defaultValue = "") String input) {
	    		int id =Integer.parseInt(input);
	    		String record=searchMemoById(id);
	    			return""" 
	    	    			<!doctype html>
	    	               <html lang="ja">
	    	                 <meta charset="utf-8">
	    	                 <title>JDBCメモ（ブラウザ版・最小）</title>
	    	                 <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
	    	                  <h1>検索終了しました。</h1>
	    	                   <p>%s</p>
	    	                 </body>
	    	               </html>
	    	                     """.formatted(record);
	    	}
	    	    	
	    	private String searchMemoById(int id) {
	    		try (Connection conn = DriverManager.getConnection(url, user, pass);
	    				PreparedStatement ps = conn.prepareStatement("select * from itemとuser where id=? ");) {
	    			ps.setInt(1, id);
	    			String name=null;
	    			int price=0;
	    			int quantity=0;
	    			try(ResultSet rs=ps.executeQuery();){
	    				while(rs.next()) {
	    				name=rs.getString("name");
	    				price=rs.getInt("price");
	    				quantity=rs.getInt("quantity");
	    				}
	    			}
	    			return name+":"+price+"円 ("+quantity+"個)";
	    		} catch (SQLException e) {
	    			System.err.println("SEARCH失敗: " + e.getMessage());
	    			return "検索に失敗しました。";
	    		}
	    	}
	    }
			
			
