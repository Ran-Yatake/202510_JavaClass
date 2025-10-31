package com.example.formecho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FormEcho9Application {

	public static void main(String[] args) {
		SpringApplication.run(FormEcho9Application.class, args);
		System.out.println("起動: http://localhost:8080/");
	}
}
@RestController // 文字列やJSON等を直接HTTPレスポンスとして返す
class EchoController {
	private static final String url = "jdbc:mysql://localhost:3306/school_db"
			+ "?useSSL=false&allowPublicKeyRetrieval=true" + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
	private static final String user = "root";
	private static final String pass = "Fmps9632";
	
	///検索
	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String showForm() {
		return """
           <!doctype html>
           <html lang="ja">
             <meta charset="utf-8">
             <title>JDBCメモ（ブラウザ版・最小）</title>
             <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">           
              <h1>シンプルメモアプリ</h1>
              <p>・登録</p>
              <form method="POST" action="/register" style="display:flex; gap:.5rem;">
                 <input type="text" name="text" placeholder="テキストを入力してください。" required style="flex:1; padding:.5rem;">
                 <button type="submit" style="padding:.5rem 1rem;">登録</button>
                 </form>
               <p>・検索</p>
               <form method="POST" action="/search" style="display:flex; gap:.5rem;">
                 <input type="text" name="text" placeholder="IDを入力してください。" required style="flex:1; padding:.5rem;">
                 <button type="submit" style="padding:.5rem 1rem;">表示</button>
               </form> 
               <p>・更新</p>
               <form method="POST" action="/update" style="display:flex; gap:.5rem;">
                 <input type="text" name="id" placeholder="IDを入力してください。" required style="flex:1; padding:.5rem;">
                 <input type="text" name="text" placeholder="テキストを入力してください。" required style="flex:1; padding:.5rem;">
                 <button type="submit" style="padding:.5rem 1rem;">更新</button>
               </form>
               <p>・削除</p>
               <form method="POST" action="/delete" style="display:flex; gap:.5rem;">
                 <input type="text" name="text" placeholder="IDを入力してください。" required style="flex:1; padding:.5rem;">
                 <button type="submit" style="padding:.5rem 1rem;">削除</button>
               </form>     
             </body>
           </html>

				 """;
	}
	@PostMapping(value="/register",
			consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			produces = MediaType.TEXT_HTML_VALUE)
	public String register(
			@RequestParam(name="text",defaultValue="")String input) {	
             insertMemo(input);
             return """
				      <!doctype html>
				      <html lang="ja">
				        <meta charset="utf-8">
				        <title>JDBCメモ（ブラウザ版・最小）</title>
				        <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
				         <p>登録されました</p>
				        </body>
				      </html>
				""";
}
	   private static void insertMemo(String text) {
	       String sql = "INSERT INTO memo(text) VALUES(?)";
	       try (Connection conn = DriverManager.getConnection(url, user, pass);
	            PreparedStatement ps = conn.prepareStatement(sql)) {
	           ps.setString(1, text);
	           int rows = ps.executeUpdate();
	           System.out.println(rows + "件 追加しました。");
	       } catch (SQLException e) {
	           System.err.println("INSERT失敗: " + e.getMessage());
	       }
	   }
	
	@PostMapping(value = "/search",
			// フォームのコンテンツタイプを受け付ける（デフォルトもこれだが明示しておくと親切）
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			// HTMLを返すことを明示
			produces = MediaType.TEXT_HTML_VALUE)
	public String echo2(
			// name="text" で送られてきた値を取得（未指定対策でデフォルト空文字）
			@RequestParam(name = "text", defaultValue = "") String input) {
		int id =Integer.parseInt(input);
		String memo =selectMemoById(id);

		// 結果画面（HTML）を返却
		return """
           <!doctype html>
           <html lang="ja">
             <meta charset="utf-8">
             <title>JDBCメモ（ブラウザ版・最小）</title>
             <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
              <p>%s</p>
             </body>
           </html>

				 """.formatted(memo);
	}

///（DBからSELECTするメソッド）
	private String selectMemoById(int id) {
		int getId=0;
		String text=null;
		Timestamp createdAt=null;
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM memo WHERE id=?");
				) {
           ps.setInt(1,id);
           try (ResultSet rs = ps.executeQuery();) {
   			while (rs.next()) {
   				getId = rs.getInt("id");
   				text = rs.getString("text");
   				createdAt = rs.getTimestamp("created_at");
   			}
           }
		} catch (
		SQLException e) {
			System.err.println("SELECT失敗: " + e.getMessage());
		}
		String returnString = getId + " :" + text + "  " + "[" + createdAt + "]";
		return returnString;
	}
	
	// -------------------------------
	// 更新
	// -------------------------------
	@PostMapping(value = "/update",
			// フォームのコンテンツタイプを受け付ける（デフォルトもこれだが明示しておくと親切）
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			// HTMLを返すことを明示
			produces = MediaType.TEXT_HTML_VALUE)
	public String echo3(
			// name="text" で送られてきた値を取得（未指定対策でデフォルト空文字）
			@RequestParam(name = "id", defaultValue = "") String idInput,
			@RequestParam (name="text",defaultValue="")String textInput) {
		 int id = Integer.parseInt(idInput);
	     updateMemoById(id,textInput);
        
		// 結果画面（HTML）を返却
		return """
				      <!doctype html>
				      <html lang="ja">
				        <meta charset="utf-8">
				        <title>JDBCメモ（ブラウザ版・最小）</title>
				        <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
				         <p>更新されました</p>
				        </body>
				      </html>
				""";
	}
		
	private void updateMemoById(int id,String text) {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn.prepareStatement("UPDATE memo set text=? where id=? ");) {
			ps.setString(1, text);  			
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("UPDATE失敗: " + e.getMessage());
		}
	}

	
	// -------------------------------
	// 削除
	// -------------------------------
	@PostMapping(value = "/delete",
			// フォームのコンテンツタイプを受け付ける（デフォルトもこれだが明示しておくと親切）
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			// HTMLを返すことを明示
			produces = MediaType.TEXT_HTML_VALUE)
	public String echo(
			// name="text" で送られてきた値を取得（未指定対策でデフォルト空文字）
			@RequestParam(name = "text", defaultValue = "") String input) {
		int id =Integer.parseInt(input);
	    deleteMemoById(id);

		// 結果画面（HTML）を返却
		return """
           <!doctype html>
           <html lang="ja">
             <meta charset="utf-8">
             <title>JDBCメモ（ブラウザ版・最小）</title>
             <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
              <p>削除されました。</p>
             </body>
           </html>

				 """;
	}
///（DBからSDELETEするメソッド）
	private void deleteMemoById(int id) {
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement ps = conn.prepareStatement("delete from memo where id=? ");) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("DELETE失敗: " + e.getMessage());
		}
	}
}
