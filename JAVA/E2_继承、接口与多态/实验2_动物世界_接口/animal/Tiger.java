package animal;

public class Tiger implements Animal {
    private String name;
    private int age;
    private String gender;

    @Override
    public void eat() {
        System.out.println("我是老虎，我吃肉！"); 
    }
}