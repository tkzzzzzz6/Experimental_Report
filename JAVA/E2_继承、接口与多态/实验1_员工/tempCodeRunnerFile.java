import java.util.Scanner;

import people.Employee;


public class Main {
    public static void main(String[] args){
        Employee employee = new Employee("张三","男",18,13000000);
        System.out.println("这是一名员工："); 
        System.out.println("姓名："+employee.getName()); 
        System.out.println("性别："+employee.getGender()); 
        System.out.println("年龄："+employee.getAge()); 
        System.out.println("工号："+employee.getId()); 
        // 父类定影的返回的是父类的指针,setName返回的是People的指针,setName返回的是Employee的指针,父类没有work方法
        // ((Employee)employee).setName("李四").setAge(20);
        // ((Employee)employee.setName("李四").setAge(20)).work();
        // 
        employee.setName("李四").setAge(20);
        employee.eat();
        employee.speak();
        employee.work();
        System.out.println();
        // People people = new People("丽丝","女",16); 
        // System.out.println("这是一个普通的人："); 
        // System.out.println("姓名："+people.getName()); 
        // System.out.println("性别："+people.getGender()); 
        // System.out.println("年龄："+people.getAge()); 
        // people.eat();
        // people.speak();

        // System.out.println(employee.getTest()); 返回的是父类的属性,因为是引用关系
    }
}
