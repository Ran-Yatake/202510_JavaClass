package com.example.formecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class FormEcho3Application {

	public static void main(String[] args) {
		SpringApplication.run(FormEcho3Application.class ,args);
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
              <title>大きい方を返すアプリ</title>
              <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
                <h1>数字を2つ入力してください（大きい方を返します）</h1>
                <form method="POST" action="/max" style="display:flex; gap:.5rem; align-items:center;">
                  <input type="number" name="a" required placeholder="例: 10" style="padding:.5rem;">
                  <span>と</span>
                  <input type="number" name="b" required placeholder="例: 7" style="padding:.5rem;">
                  <button type="submit" style="padding:.5rem 1rem;">送信</button>
                </form>
                <p style="color:#666;">※ 整数入力を想定しています。</p>
              </body>
            </html>
            """;
    }

    // -------------------------------
    // POST /echo : 送信データの受取・加工・結果表示
    // -------------------------------
    @PostMapping(
        value = "/max",
        // フォームのコンテンツタイプを受け付ける（デフォルトもこれだが明示しておくと親切）
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        // HTMLを返すことを明示
        produces = MediaType.TEXT_HTML_VALUE
    )
    public String echo(
            // name="text" で送られてきた値を取得（未指定対策でデフォルト空文字）
            @RequestParam(name = "a", defaultValue = "") String inputa,
            @RequestParam(name="b",defaultValue="")String inputb
                       
    ) {
    	int bigger;
    	int numbera=Integer.parseInt(inputa);
    	int numberb=Integer.parseInt(inputb);
        if(numbera>numberb) {
        	bigger=numbera;
        }else {
        	bigger=numberb;
        }
    	
        // 結果画面（HTML）を返却
        return """
                   <!doctype html>
                <html lang="ja">
                  <meta charset="utf-8">
                  <title>結果</title>
                  <body style="font-family:sans-serif; line-height:1.6; max-width:720px; margin:2rem auto;">
                    <h1>結果</h1>
                    <p>入力: %d と %d</p>
                    <p><strong>大きい方: %d</strong></p>
                    <p><a href="/">戻る</a></p>
                  </body>
                </html>

              </body>
            </html>

            """.formatted(numbera,numberb,bigger);
    }
}
