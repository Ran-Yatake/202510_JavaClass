package chapter_3;

public class Practice_10 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
     for(int x=1;x<=50;x++)
     if(x%15==0) {
    	 System.out.println("FizzBuzz");
     }else if(x%3==0){
    	 System.out.println("Fizz");
     }else if(x%5==0) {
    	 System.out.println("Buzz");
     }else {
    	 System.out.println(x);
     }
	}

}
