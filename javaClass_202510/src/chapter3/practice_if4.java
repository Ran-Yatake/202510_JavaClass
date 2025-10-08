package chapter3;

public class practice_if4 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
          int x=9999;
          int y=999999;
          int z=999999999;
        			
    		//xが一番大きい場合
    			if(x>=y & x>=z) {
    				System.out.println(x);
    				if(y>=z) {
    					System.out.println(y);
    					System.out.println(z);
    				} else {
    					System.out.println(z);
    					System.out.println(y);
    				}
    				//yが一番大きい場合
    			} else if (y>=x & y>=z) {
    				System.out.println(y);
    				if (x>=z) {
    					System.out.println(x);
    					System.out.println(z);
    					//ここで残りの二つを並び替える
    				} else {
    					System.out.println(z);
    					System.out.println(x);
    				}
    			} else {
    				System.out.println(z);
    				if(x>=y) {
    					System.out.println(x);
    					System.out.println(y);
    				} else {
    					System.out.println(y);
    					System.out.println(x);
    				}
    			}
		}
	}