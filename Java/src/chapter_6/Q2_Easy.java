package chapter_6;
import java.util.*;
public class Q2_Easy {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
	        List<Hornable> list = new ArrayList<>();
	        // TODO: Car("プリウス"), Bicycle("ビアンキ") を追加
	        list.add(new Car ("プリウス"));
	        list.add(new Bicycle("ビアンキ"));
	        for (Hornable h : list) {
	            // TODO: show() → honk() → move() の順で呼ぶ
	            // ヒント: ((Vehicle) h).show();
	        	((Vehicle) h).show();
	        	h.honk();
	        	((Vehicle) h).move();
	        }
	    }
	}

	abstract class Vehicle {
	    // TODO: name, コンストラクタ(name), show(), 抽象メソッド move()
		String name;
		Vehicle(String name){
			this.name=name;
		}
		public void show() {
			System.out.println("車両名："+name );
		}
		public abstract void  move();		
		}
	interface Hornable {
	    void honk();
	}

	class Car extends Vehicle implements Hornable {
	    // TODO: コンストラクタ
		Car(String name){
			super(name);
		}
	    @Override public void honk() {
	        // 出力：[Car] {name}：ブロロロ！
	    	System.out.println("[Car]" +name+"：ブロロロ！");
	    }
	    @Override public void move() {
	        // 出力：{name}はエンジンで道路を走る。
	    	System.out.println(name+"はエンジンで道路を走る。");
	    }
	}

	class Bicycle extends Vehicle implements Hornable {
	    // TODO: コンストラクタ
		Bicycle(String name){
			super(name);
		}
	    @Override public void honk() {
	        // 出力：[Bicycle] {name}：チリン！
	    	System.out.println("[Bicycle]"+name+"：チリン！");
	    }
	    @Override public void move() {
	        // 出力：{name}はペダルをこいで進む。
	    	System.out.println(name+"はペダルをこいで進む。");
	    }
	}
