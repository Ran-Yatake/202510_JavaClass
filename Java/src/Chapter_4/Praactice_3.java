package Chapter_4;

import java.util.ArrayList;

public class Praactice_3 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
         ArrayList<Integer>array=new  ArrayList<Integer>();
         
         array.add(76);
         array.add(8);
         array.add(33);
         
         for(int s:array) {
        	 if(s%2==0) {
        	System.out.print(s+"-");
        	}	 
         }	
	}
	

}
