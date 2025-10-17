package chapter_6;
import java.util.*;
public class practice4 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Employee s1=new Employee("田中","営業",1000);
		Employee s2=new Employee("鈴木","経理",2000);
		Employee s3=new Employee("松本","企画",3000);
		Department department=new Department();		
		department.add(s1);
		department.add(s2);
		department.add(s3);
		for(Employee e:department.members) {
			e.show();	
		}
		System.out.println(department.avgSaraly());
		System.out.println(department.maxSalaryEmployee().name);
    }
}

class Employee { /* TODO: name, dept, salary, static count, show() */
	String name;
	String dept;
	int salary;
	static int count;
	Employee(String name,String dept,int salary){
		this.name=name;
		this.dept=dept;
		this.salary=salary;
		count++;
	}
	public void show(){
		System.out.println(name+" "+dept+" "+salary);
	}
}
class Department {
    // TODO: members, add(), avgSalary(), maxSalaryEmployee()
	ArrayList<Employee>members=new ArrayList<Employee>();

	  public void add(Employee e) {
		  members.add(e);
	  }
	
	public double avgSaraly() {
		int sum=0;
		for(Employee e:members) {
			sum+=e.salary;
		}
		double avg=sum/members.size();
		return avg;
	}
	public Employee maxSalaryEmployee() {
		int maxSalary=0;
		Employee employee=null; 
		for(Employee e:members)  {
			if(maxSalary<e.salary) {
				maxSalary=e.salary;
				employee=e;
			}
		}
		return employee;
	}
}
