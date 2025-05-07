package JAVA.PTA.week10;

// 思考
// 1.你觉得sumAllArea和sumAllPerimeter方法放在哪个类中更合适？
// 答：sumAllArea和sumAllPerimeter方法应该放在Main类中更合适，因为它们是Main类的实例方法，而不是Shape类的实例方法。
// 2.是否应该声明为static?
// 答：应该声明为static，因为它们是Main类的实例方法，而不是Shape类的实例方法。
import java.util.Scanner;

abstract class Shape{
	final double PI=3.14;
	public abstract double getPerimeter();
	public abstract double getArea();
}
class Rectangle extends Shape{
    int width, length;
    
    public Rectangle(int width, int length) {
        this.width = width;
        this.length = length;
    }
    
    @Override
	public double getPerimeter(){
		return (width+length)*2;
	}
    @Override
	public double getArea(){
		return (width*length);
	}
    @Override
    public String toString(){
		return "[width="+width+", length="+length+"]";
	} 
}
class Circle extends Shape{
    int radius;
    
	public Circle(int radius){
		this.radius=radius;
	}
    @Override
	public double getPerimeter()
	{
		return (2*PI*radius);
	}
    @Override
	public double getArea()
	{
		return (PI*radius*radius);
	}
    @Override
    public String toString(){
		return "[radius="+radius+"]";
	} 
}
public class Main{
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		sc.nextLine();
		Shape x[]=new Shape[n];
		double sumAllArea=0,sumAllPerimeter=0;
		for(int i=0;i<n;i++){
			String m=sc.next();
			if(m.equals("rect")){
				int width=sc.nextInt();
                int length=sc.nextInt();
				sc.nextLine();
				x[i]=new Rectangle(width,length);
			}
			else if(m.equals("cir")){
				int radius=sc.nextInt();
				sc.nextLine();
				x[i]=new Circle(radius);
			}
			sumAllArea =sumAllArea + x[i].getArea();
			sumAllPerimeter =sumAllPerimeter + x[i].getPerimeter();
		}
		sc.close();
		System.out.println(sumAllPerimeter);
		System.out.println(sumAllArea);
		System.out.print("[");
		for(int i=0;i<n;i++){
			if(i!=0){
				System.out.print(", ");
			}
			if(x[i] instanceof Rectangle){
				System.out.print("Rectangle ");
				System.out.print(x[i].toString());
			}
			else{
				System.out.print("Circle ");
				System.out.print(x[i].toString());	
			}
		}
		System.out.println("]");
		for(int i=0;i<n;i++){
			if(x[i] instanceof Rectangle){
				System.out.println("class Rectangle,class Shape");
			}
			else{
				System.out.println("class Circle,class Shape");
			}
		}
	}
}
