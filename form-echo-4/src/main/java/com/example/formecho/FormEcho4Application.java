package com.example.formecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FormEcho4Application {
	public static void main(String[] args) {
		SpringApplication.run(FormEcho4Application.class, args);
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
				    <h1>メモ登録 & 一覧（JDBCのみ / SpringはWebだけ）</h1>
				    <h2>新規登録</h2>
				    <form method="POST" action="/memos" style="display:flex; gap:.5rem;">
				      <input type="text" name="text" placeholder="メモ内容を入力" required style="flex:1; padding:.5rem;">
				      <button type="submit" style="padding:.5rem 1rem;">追加</button>
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
            insertMemo(input);
		// 結果画面（HTML）を返却
		return """
				<!doctype html>
				<html lang="ja">
				  <meta charset="utf-8">
				  <title>JDBCメモ（ブラウザ版・最小）</title>
				  <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
				    <h1>登録されました。</h1>
				  </body>
				</html>
				 """;
	}

///insert
	private void insertMemo(String text) {
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
}
