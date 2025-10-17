package chapter_6;
import java.util.*;
public class Practice_2 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		 // TODO: Book を複数作成 → Library に add → 各種表示
		Book s1=new Book("A","X",1000);
		Book s2=new Book("B","Y",1500);
		Book s3=new Book("C","Z",1250);
		Library library=new Library();
		library.add(s1);
		library.add(s2);
		library.add(s3);
		s1.show();
		s2.show();
		s3.show();
		for(Book b:library.list) {
			b.show();
		}
		System.out.println(library.totalPrice());
		System.out.println(library.avgPrice());
		library.maxPriceBook().show();
    }
	
}

class Book {
    // TODO: title, author, price
	String title;
	String author;
	int price;
    // TODO: コンストラクタ
	Book(String title,String author,int price){
		this.title=title;
		this.author=author;
		this .price=price;
	}
    // TODO: show()
	public void show(){
		System.out.println("タイトル："+title+"著者："+author+ "価格："+price+"円");
	}
}

class Library {
    // TODO: ArrayList<Book> list
	ArrayList<Book>list=new ArrayList<Book>();
    // TODO: add(Book)
	public void add(Book b) {
		list.add(b);
	}		
    // TODO: totalPrice(), avgPrice(), maxPriceBook()
	public int totalPrice(){
		int sum=0;
		for(Book b:list) {
			sum+=b.price;
		}
		return sum;
	}
	public double avgPrice() {
		int sum=0;
		for(Book b:list) {
			sum+=b.price;
		}
		double avg=sum/list.size();
		return avg;
	}
	public Book maxPriceBook() {
		int maxPrice=0;
		Book book=null; 
		for(Book b:list)  {
			if(maxPrice<b.price) {
				maxPrice=b.price;
				book=b;
			}
		}
		return book;
	}
}	

