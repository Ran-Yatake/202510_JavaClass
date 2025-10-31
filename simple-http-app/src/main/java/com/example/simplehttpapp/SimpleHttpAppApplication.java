package com.example.simplehttpapp;

// Spring Bootアプリを起動するためのクラスやアノテーションを読み込む
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Webリクエスト（HTTPリクエスト）を受け取るためのアノテーションを読み込む
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Bootアプリケーションのメインクラス。
 * アプリ全体の起動処理がここから始まる。
 */
@SpringBootApplication  // Spring Bootアプリとして動作することを示すアノテーション
public class SimpleHttpAppApplication {

    public static void main(String[] args) {
        // Spring Bootアプリを起動する。内部で組み込みTomcatサーバーが立ち上がる。
        SpringApplication.run(SimpleHttpAppApplication.class, args);

        // サーバー起動時にメッセージをコンソールに出力
        System.out.println("サーバーが起動しました → http://localhost:8080");
    }
}

/**
 * ブラウザからのリクエストを受け取り、レスポンス（返す内容）を作成するクラス。
 * 通常は「Controller（コントローラー）」と呼ばれる。
 */
@RestController  // このクラスがHTTPリクエストを処理する役割であることを示す
class HelloController {

    /**
     * ブラウザで「/」（ルートURL）にアクセスされたときの処理。
     * 例: http://localhost:8080/
     */
    @GetMapping("/")  // 「GET」リクエストを受け取るURLパスを指定
    public String hello() {
        // ブラウザに返すHTMLの文字列を定義
        // 「"""」はJavaのテキストブロック構文で、複数行の文字列を扱える
        return """
                <h1>Hello from Java Server!</h1>
                <p>ブラウザとJava（Spring Boot）がつながりました 🎉</p>
                """;
    }
}
