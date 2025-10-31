package chapter_6;
import java.util.*;
public class Q1_Easy {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		List<Soundable> list = new ArrayList<>();
        // TODO: Dog("ポチ"), Cat("タマ") を追加
			list.add(new Dog("ポチ"));
			list.add(new Cat("タマ"));
        for (Soundable s : list) {
            // TODO: show() → makeSound() → eat() の順で呼ぶ
            // ヒント: ((Animal) s).show();
            ((Animal) s).show();
            s.makeSound();
            ((Animal) s).eat();   
        }
    }
}

abstract class Animal {
    // TODO: name, コンストラクタ(name), show(), 抽象メソッド eat()
	String name;
	Animal(String name){
		this.name=name;
	}
     public void show() {
    	 System.out.println("名前："+name);
     }
     public abstract void eat();
}

interface Soundable {
    void makeSound();
}

class Dog extends Animal implements Soundable {
    // TODO: コンストラクタ
	Dog(String name){
		super(name);
	}
    @Override public void eat() {
        // 出力：{name}はドッグフードをガツガツ食べた。
    	System.out.println(name+"はドッグフードをガツガツ食べた。");
    }
    @Override public void makeSound() {
        // 出力：[Dog] {name}：ワン！
    	System.out.println("[Dog]"+name+"：ワン！");
    }
}

class Cat extends Animal implements Soundable {
    // TODO: コンストラクタ
	String name;
	Cat(String name){
		super(name);
	}
    @Override public void eat() {
        // 出力：{name}はキャットフードを上品に食べた。
    	System.out.println(name+"はキャットフードを上品に食べた。");
    }
    @Override public void makeSound() {
        // 出力：[Cat] {name}：ニャー！
    	System.out.println("[Cat]"+name+"：ニャー！");
    }
}
