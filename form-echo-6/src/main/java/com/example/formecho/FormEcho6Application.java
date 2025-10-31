package com.example.formecho;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FormEcho6Application {
	public static void main(String[] args) {
		SpringApplication.run(FormEcho6Application.class, args);
		System.out.println("起動: http://localhost:8080/");
	}
}

// ===============================
// コントローラ（リクエストの受付・レスポンスの返却を担当）
// ===============================
@RestController // 文字列やJSON等を直接HTTPレスポンスとして返す
class EchoController {
	private static final String url = "jdbc:mysql://localhost:3306/school_db"
			+ "?useSSL=false&allowPublicKeyRetrieval=true" + "&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
	private static final String user = "root";
	private static final String pass = "Fmps9632";

	// -------------------------------
	// GET / : 入力フォームの表示
	// -------------------------------
	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String showForm() {
		// 複数行文字列（テキストブロック）でシンプルなHTMLを返す
		// フォームは application/x-www-form-urlencoded 形式で POST /echo へ送信
		return """
           <!doctype html>
           <html lang="ja">
             <meta charset="utf-8">
             <title>JDBCメモ（ブラウザ版・最小）</title>
             <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">           
              <h1>IDからメモを消去</h1>
               <h2>メモのIDを入力</h2>
               <form method="POST" action="/memos" style="display:flex; gap:.5rem;">
                 <input type="text" name="text" placeholder="IDを入力" required style="flex:1; padding:.5rem;">
                 <button type="submit" style="padding:.5rem 1rem;">検索</button>
               </form>  
             </body>
           </html>

				 """;
	}

	// -------------------------------
	// POST /echo : 送信データの受取・加工・結果表示
	// -------------------------------
	@PostMapping(value = "/memos",
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
///（DBからDELETEするメソッド）
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