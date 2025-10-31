package com.example.formecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class FormEcho2Application {

	public static void main(String[] args) {
		SpringApplication.run(FormEcho2Application.class, args);
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
              <title>2の倍数チェックアプリ（Spring Boot）</title>
              <body style="font-family:sans-serif; line-height:1.6">
                <h1>2の倍数チェック</h1>
                <!-- method="POST" はサーバーにデータを送信するための指定 -->
                <form method="POST" action="/check">
                  <label>数字を入力：
                    <input type="number" name="number" required>
                  </label>
                  <button type="submit">送信</button>
                </form>
                <hr>
                <p style="color:#666">数字を入力して「送信」すると、2の倍数かどうかを判定します。</p>
              </body>
            </html>
            """;
    }

    // -------------------------------
    // POST /echo : 送信データの受取・加工・結果表示
    // -------------------------------
    @PostMapping(
        value = "/check",
        // フォームのコンテンツタイプを受け付ける（デフォルトもこれだが明示しておくと親切）
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        // HTMLを返すことを明示
        produces = MediaType.TEXT_HTML_VALUE
    )
    public String echo(
            // name="text" で送られてきた値を取得（未指定対策でデフォルト空文字）
            @RequestParam(name = "number", defaultValue = "") String input
    ) {
    	String resultMessage;
    	int number=Integer.parseInt(input);
        if(number%2==0) {
        	resultMessage=number+"は２の倍数です。";
        }else {
        	resultMessage=number+"は２の倍数ではありません。";
        }
    	
        // 結果画面（HTML）を返却
        return """
               <!doctype html>
            <html lang="ja">
              <meta charset="utf-8">
              <title>判定結果</title>
              <body style="font-family:sans-serif; line-height:1.6">
                <h1>判定結果</h1>
                <p><strong>%s</strong></p>
                <hr>
                <p><a href="/">もう一度試す</a></p>
              </body>
            </html>
            """.formatted(resultMessage);
    }
}

