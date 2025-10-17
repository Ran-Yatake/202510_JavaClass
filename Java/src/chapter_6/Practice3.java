package chapter_6;
import java.util.*;

public class Practice3 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
        Product s1=new Product("A",1000,5);
        Product s2=new Product("B",2000,15);
     	Product s3=new Product("C",3000,25);
     	Product s4=new Product("D",4000,55);
     	Cart cart=new Cart(3);
     	cart.put(s1);
     	cart.put(s2);
     	cart.put(s3);
     	cart.put(s4);
     	cart.take(2); 
     	System.out.println(cart.total());
	} 
}

class Product {
    // TODO: name, price, stock + コンストラクタ + show()
	String name;
	int price;
	int stock;	
	Product(String name,int price,int stock){
	this.name=name;
	this.price=price;
	this.stock=stock;
	}
	public void show(){
		System.out.println(name+" "+price+" "+stock);
	}
}

class Cart {
    // TODO: items, max + コンストラクタ
	ArrayList<Product>items=new ArrayList<Product>();
	int max;
	Cart(int max){
		this.max=max;
	}

    // TODO: put(Product), take(int), total()
	public boolean put(Product p) {
			if(items.size()<max) {
				items.add(p);
				return true;
			}else {
				System.out.println("追加できません。");
				return false;
			}
	}
		public Product take(int index) {
			return items.remove(index);
		}
		public int total(){
				int sum=0;
			for(Product p:items) {
					sum+=p.price;
				}
				return sum;		
	}
}

