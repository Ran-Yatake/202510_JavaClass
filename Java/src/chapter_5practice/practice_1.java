package chapter_5practice;
import java.util.ArrayList;
class Student{
	   String name;
	   int japanese;
	   int math;
	   int English;
	   static int count;
	   public Student(String a,int b, int c, int d) {
		   name=a;
		   japanese=b;
		   math=c;
		   English=d;
		   count++;
	   }
	   void show() {
		   System.out.println("名前:"+name+"国語:"+japanese+"数学："+math+"英語:"+English);
	   }
	   int all() {
		   return japanese+math+English;
	   }
	   int bunkei(){
		   return japanese+English;
	   }
	   int rikei(){
		   return math+English;
	   }
}

public class practice_1 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
      Student s1=new Student("佐藤",50,20,60);
      Student s2=new Student("中野",60,30,40);
      Student s3=new Student("森下",50,40,50);
      ArrayList<Student>list=new ArrayList<Student>();
      list.add(s1);
      list.add(s2);
      list.add(s3);
      
     int bunkeiTop=0;
     String bunkeiName=null;
     for(Student student:list) {
    	 student.show();
    	 if(bunkeiTop<student.bunkei()) {
    		 bunkeiTop=student.bunkei();
    		 bunkeiName=student.name;
    	 }
     }
     int rikeiTop=0;
     String rikeiName=null;
     for(Student student:list) {
    	 student.show();
    	 if(rikeiTop<student.rikei()) {
    		 rikeiTop=student.rikei();
    		 rikeiName=student.name;
    	 }
     }
     int japSum=0;
     int mathSum=0;
     int EngSum=0;
     for(Student student:list) {
    	 japSum+=student.japanese;
    	 mathSum+=student.math;
    	 EngSum+=student.English;
     }
     double japAve=japSum/Student.count;
     double mathAve=mathSum/Student.count;
     double EngAve=EngSum/Student.count;
      System.out.println(bunkeiName+":"+bunkeiTop+"点");
      
       System.out.println(rikeiName+":"+rikeiTop+"点");
      
       System.out.println("国語:"+japAve+"点");
       System.out.println("数学:"+mathAve+"点");
       System.out.println("英語："+EngAve+"点");
       
      
      
	}

}
