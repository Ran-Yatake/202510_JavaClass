package chapter_5;
import java.util.ArrayList;
class Fruit{
	String name;
	String color;
	int price;
	public Fruit(String x, String y,int z) {
		name=x;
		color=y;
		price=z;
	}
		
	     String getName(){
	 	  	return name;
	     }
	 	 String getColor(){
	 		 return color;
	 	 }
	 	int getPrice(){
	 		return price;
	 	}
	 	void show() {
	 		System.out.println("名前："+name+"色:"+color+"価格:"+price);
	 	}
	}

public class Practice_3 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Fruit fruit=new Fruit("みかん","オレンジ",100);
		Fruit fruit2=new Fruit("りんご","赤",50);
		Fruit fruit3=new Fruit("ぶどう","紫",200);
		fruit.show();
		fruit2.show();
		fruit3.show();
		FruitBasket fruitBasket=new FruitBasket(5);
		fruitBasket.put(fruit); 
		fruitBasket.put(fruit2);
		fruitBasket.put(fruit3);
		Fruit fr=fruitBasket.take(0);
		fr.show();					
	}
}
 class FruitBasket{
	 ArrayList<Fruit>list=new ArrayList<Fruit>();
	int max;
	FruitBasket(int x){
		max=x;	
	}
	void put (Fruit y) {
		if(list.size()<max){
			list.add(y);
		} 
	}
	Fruit take (int z) {
		return list.remove(z);
	}
}
