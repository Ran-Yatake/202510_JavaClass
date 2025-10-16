package chapter_5;

public class Practice_1 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
         Book b = new Book();
        	b.setName("java");	 
		   String x=b.getName();
		System.out.println(x);
		
	}

}


class Book{
  String name;
  
 void setName(String x) {
	 name=x;
 }
  
String getName() {
	return name;
}
}