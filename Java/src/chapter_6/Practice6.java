package chapter_6;
import java.util.ArrayList;

public class Practice6 {
    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
        Stock stock = new Stock();
        // 在庫に商品を追加（3つ以上）
        stock.add(new Product2("りんご", 120, 3));
        stock.add(new Product2("みかん", 100, 2));
        stock.add(new Product2("バナナ", 150, 1));

        // 在庫一覧
        System.out.println("在庫一覧を表示します。");
        stock.showAll();

        Cart2 cart = new Cart2();

        // カートにいくつか追加（在庫0ケースも試す）
        Product2 apple = stock.findByName("りんご");
        cart.put(apple);
        cart.put(apple);
        cart.put(stock.findByName("バナナ"));
        cart.put(stock.findByName("パイナップル"));

        // カート一覧と合計
        cart.showAll();
        System.out.println("合計金額：" + cart.total() + "円");

        // 取り出し
        Product2 taken = cart.take(1);
        if (taken != null) {
            System.out.print("取り出した商品：");
            taken.show();
        }

        // 最後に在庫再表示（在庫が減っていることを確認）
        stock.showAll();
    }
}

/* -------- Product2 -------- */
class Product2 {
    String name;
    int price;
    int stock;

    Product2(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void show() {
        System.out.println("商品：" + name + "価格：" + price + "在庫：" + stock);
    }
}

/* -------- Stock -------- */
class Stock {
    ArrayList<Product2> list = new ArrayList<Product2>();

    public void add(Product2 p) {
        list.add(p);
    }

    public Product2 findByName(String name) {
        for (Product2 p : list) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void showAll() {
        for (Product2 p : list) {
            p.show();
        }
    }
}

/* -------- Cart2 -------- */
class Cart2 {
    ArrayList<Product2> items = new ArrayList<Product2>();

    public boolean put(Product2 p) {
        if (p == null) {
            return false;
        }
        if (p.stock > 0) {
            items.add(p);
            return true;
        } else {
            return false;
        }
    }

    public Product2 take(int index) {
        Product2 taken = items.remove(index);
        return taken;
    }

    public int total() {
        int sum = 0;
        for (Product2 p : items) {
            sum += p.price;
        }
        return sum;
    }

    public void showAll() {
        for (Product2 p : items) {
            p.show();
        }
    }
}
