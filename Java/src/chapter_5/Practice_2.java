package chapter_5;
class Drink{
	String name;
	int volume;
	public Drink(String x, int y){
		name=x;
		volume=y;
	}
	 void setName(String name){
		  this.name = name;
	}
		  
     String getName(){
	  	return this.name;
	  }
	  void setVolume(String name){
		this.name = name;
		  }
		  
	  String getVolume(){
		 return this.name;
		  }
	  public void show() {
		System.out.println(name+":残り"+volume+"ml");
		 }
	  public void consume(int x) {
	     volume=volume-x;
		 }

}
public class Practice_2 {
	public static void main(String[] args) {
	 Drink drink = new Drink("お茶", 350);
	 System.out.println(drink.name+ drink.volume);
	 drink.show();
	 drink.consume(100);
	 drink.show();
}
}