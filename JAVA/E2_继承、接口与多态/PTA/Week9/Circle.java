import java.util.Scanner;
class Circle{
    private int radius,area;
    Circle(){
        radius=2;
        System.out.println("this is a constructor");
    }
     Circle(int radius){
        if(radius<=0)  {this.radius=2;}
         else this.radius=radius;
        System.out.println("this is a constructor with para");
     }
    public int getRadius(){
         return radius;
    }
     public void setRadius(int radius){
       if(radius<=0)  {this.radius=2;}
         else this.radius=radius;
    }
public int getArea(){
    area=(int)((Math.PI)*radius*radius);
    return area;
}
@Override
public String toString(){
    return "Circle [radius=" + radius + "]";
}
    
}
 public class Main{
        public static void main(String args[]){
            Scanner sc=new Scanner(System.in);
            int radius1=sc.nextInt();
            int radius2=sc.nextInt();

            Circle c1=new Circle();
            System.out.println(c1);
            System.out.println("c1:area="+c1.getArea());
            Circle c2=new Circle();
            System.out.println(c2);

            c2.setRadius(radius1);
            System.out.println(c2);
            System.out.println("c2:area="+c2.getArea());

            Circle c3=new Circle(radius2);       
            System.out.println(c3);
            System.out.println("c3:area="+c3.getArea());
        }
 }
            
  
