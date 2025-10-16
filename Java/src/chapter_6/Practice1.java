package chapter_6;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
abstract class Food{
	String name;
	int price;
	String volume;
	Food(String name, int price, String volume){
		this.name=name;
		this.price=price;
		this.volume=volume;
	}
	public abstract void buy();
	// メニュー表示用の共通フォーマット
    public String menuLine() {
        return String.format("%s（%s：%d円）", name, volume, price);
    }	
}


class Gyudon extends Food implements VolumeChange{
	Gyudon(){
		super("牛丼",380,"並");
	}
	@Override
	public void buy (){
	  setVolume();
	  System.out.println(name+"の"+volume+"は"+price+"円です。");
	}
	@Override
	public void setVolume() {
		Scanner sc = new Scanner(System.in);
        System.out.println("サイズを選択して下さい。");
        System.out.println("0 : 並（380円）");
        System.out.println("1 : 大盛り（550円）");
        System.out.println("2 : 特盛り（700円）");
        System.out.print("数字を入力 => ");

        int sel;
        if (sc.hasNextInt()) {
            sel = sc.nextInt();
        } else {
            // 数値以外の入力は既定（並）にフォールバック
            sel = 0;
            sc.next(); // 無効入力を読み捨て
        }

        switch (sel) {
            case 0:
                this.volume = "並";
                this.price = 380;
                break;
            case 1:
                this.volume = "大盛り";
                this.price = 550;
                break;
            case 2:
                this.volume = "特盛り";
                this.price = 700;
                break;
            default:
                System.out.println("不正な入力のため、並にします。");
                this.volume = "並";
                this.price = 380;
        }		
	}
}
class Curry extends Food{
	Curry(){
		super("カレー",500,"並");
	}
    public void buy() {
    	
    }
}
interface VolumeChange{
	public abstract void setVolume();
}


public class Practice1 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Shop shop = new Shop();
        shop.add(new Gyudon()); // デフォルトは「牛丼：並 380円」
        shop.add(new Curry());  // デフォルトは「カレー：並 500円」
        shop.select();          // メニュー表示 → 選択 → buy()
	}

}
class Shop{
	ArrayList<Food>menuList=new ArrayList<Food>();
	public void add(Food food) {
		menuList.add(food);
	}
	public void select(){
		if (menuList.isEmpty()) {
            System.out.println("メニューがありません。");
            return;
        }

        // メニュー一覧表示
        System.out.println("=== メニュー一覧 ====");
        for (int i = 0; i < menuList.size(); i++) {
            System.out.printf("%d: %s%n", i, menuList.get(i).menuLine());
        }
        System.out.println("=====================");
        System.out.print("商品を選んでください => ");

        Scanner sc = new Scanner(System.in);
        int idx;
        if (sc.hasNextInt()) {
            idx = sc.nextInt();
        } else {
            System.out.println("数値で入力してください。");
            return;
        }

        if (idx < 0 || idx >= menuList.size()) {
            System.out.println("その番号の商品はありません。");
            return;
        }

        // 選択した商品の購入フローへ
        Food chosen = menuList.get(idx);
        chosen.buy();
	}
}