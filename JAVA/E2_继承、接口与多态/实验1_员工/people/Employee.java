
package people;
import people.People;

public class Employee extends People{
    int id;
    protected String test = "子类";
    // @Override
    //  public String getTest(){
    //     return this.test;
    //  }
    public Employee(String name,String gender,int age,int id){
        super(name,gender,age);
        this.id = id;
    }

    public void speak(){
        System.out.println("speak");
    }

    public void eat(){
        System.out.println("eat");
    }

    public void work(){
        System.out.println("work");
    }

    public int getId(){
        return this.id;
    }
    public Employee setId(int id){
        this.id = id;
        return this;
    }


    
}
