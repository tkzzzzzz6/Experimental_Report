package animal;

public class Rabbit implements Animal {
    private String name;
    private int age;
    private String gender;
    
    @Override
    public void eat() {
        System.out.println("我是兔子，我吃草！"); 
    }
}
