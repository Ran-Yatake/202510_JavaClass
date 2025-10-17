package chapter_6;

import java.util.*;

public class Practice5 {
	
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Student2 s1=new Student2("田中",80,70,85);
		Student2 s2=new Student2("佐藤",60,85,90);
		Student2 s3=new Student2("鈴木",90,75,80);
		Course course=new Course();
		course.add(s1);
		course.add(s2);
		course.add(s3);
		for(Student2 s:course.members) {
			s.show();
        }
		System.out.println(course.topByLiberal());
		System.out.println(course.topByScience());
		course.avgEach();
   }
}

class Student2 {
	    // TODO: name, japanese, math, english
	    // TODO: total(), liberal(), science(), show()
	String name;
	int japanese;
	int math;
	int english;
	
	Student2(String name,int japanese,int math, int english){
	this.name=name;
	this.japanese=japanese;
	this.math=math;
	this.english=english;
	}
	public void show(){ 
		System.out.println(name+" "+japanese+" "+math+" "+english);
	}
	int total () {
		return japanese+math+english;
	}
	int liberal() {
		return japanese+math;
	}
	int science() {
		return math+english;
	}
}
	
class Course {
	    // TODO: list, add(), topByLiberal(), topByScience(), avgEach()
	ArrayList<Student2>members=new ArrayList<Student2>();
	public void add(Student2 s) {
		members.add(s);
	}
    public int topByLiberal() {
	    String liberalName=null;
	    int liberalTop=0;
	    for(Student2 student:members) {
		   	 if(liberalTop<student.liberal()) {
		   		liberalTop=student.liberal();
		   		 liberalName=student.name;
		   	 }
   	 	}
	    return liberalTop;
    }
	public int topByScience() {
	    String scienceName=null;
	    int scienceTop=0;
	    for(Student2 student:members) {
		   	 if(scienceTop<student.science()) {
		   		scienceTop=student.science();
		   		scienceName=student.name;
		   	  }
	     }
	    return scienceTop;
	}
	public void avgEach() {
		int japSum=0;
		int mathSum=0;
		int engSum=0;
		for(Student2 student:members) {
			japSum+=student.japanese;
			mathSum+=student.math;
			engSum+=student.english;
		}
		int japAve=japSum/members.size();
	    int mathAve=mathSum/members.size();
	    int engAve=engSum/members.size();   
	     System.out.println("国語:"+japAve+"点");
	     System.out.println("数学:"+mathAve+"点");
	     System.out.println("英語："+engAve+"点");
	}
     
     
}