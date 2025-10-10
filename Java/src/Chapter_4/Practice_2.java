package Chapter_4;

public class Practice_2 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
        
        int[] numbers= {21,54,19,68,49,37,3,15,34,6};
        int min=numbers[0];        
        for(int i=1; i<numbers.length;i++) {
        	if(numbers[i]<min) {
        		min=numbers[i];
        		
        	}
        }System.out.println(min);
	}

}
