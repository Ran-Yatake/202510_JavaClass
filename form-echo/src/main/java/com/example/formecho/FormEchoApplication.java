package com.example.formecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

// ===============================
// アプリのエントリポイント
// ===============================
@SpringBootApplication
public class FormEchoApplication {
    public static void main(String[] args) {
        // Spring Bootアプリを起動（内蔵Tomcatが立ち上がる）
        SpringApplication.run(FormEchoApplication.class, args);
        System.out.println("起動: http://localhost:8080/");
    }
}

// ===============================
// コントローラ（リクエストの受付・レスポンスの返却を担当）
// ===============================
@RestController // 文字列やJSON等を直接HTTPレスポンスとして返す
class EchoController {

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
              <title>フォーム送信 → サーバー処理（Spring Boot）</title>
              <body style="font-family:sans-serif; line-height:1.6">
                <h1>文字を送ってサーバーで処理</h1>
                <form method="POST" action="/echo">
                  <label>テキスト：
                    <input type="text" name="text" required>
                  </label>
                  <button type="submit">送信</button>
                </form>
                <p style="color:#666">
                  ※ このフォームは <code>application/x-www-form-urlencoded</code> で送信します。
                </p>
              </body>
            </html>
            """;
    }

    // -------------------------------
    // POST /echo : 送信データの受取・加工・結果表示
    // -------------------------------
    @PostMapping(
        value = "/echo",
        // フォームのコンテンツタイプを受け付ける（デフォルトもこれだが明示しておくと親切）
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        // HTMLを返すことを明示
        produces = MediaType.TEXT_HTML_VALUE
    )
    public String echo(
            // name="text" で送られてきた値を取得（未指定対策でデフォルト空文字）
            @RequestParam(name = "text", defaultValue = "") String text
    ) {
        // --- ここが「サーバー側の処理」例 ---
        String upper = text.toUpperCase();      // 大文字に変換
        String reversed = new StringBuilder(text).reverse().toString(); // ついでに反転も用意

        // XSS対策：入力値を画面に戻す際はHTMLエスケープを必ず行う
 

        // 結果画面（HTML）を返却
        return """
            <!doctype html>
            <html lang="ja">
              <meta charset="utf-8">
              <title>結果</title>
              <body style="font-family:sans-serif; line-height:1.6">
                <h1>処理結果</h1>
                <ul>
                  <li><strong>入力:</strong> %s</li>
                  <li><strong>大文字:</strong> %s</li>
                  <li><strong>逆順:</strong> %s</li>
                </ul>
                <p><a href="/">戻る</a></p>
              </body>
            </html>
            """.formatted(text, upper, reversed);
    }


}
