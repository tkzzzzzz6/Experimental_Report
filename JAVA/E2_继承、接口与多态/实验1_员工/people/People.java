package people;
public abstract class People {
    protected String name,gender;
    protected int age;

    protected String test = "父类";

    public String getTest(){
        return this.test;
    }   

    public People(String name,String gender,int age){
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    // 定义抽象方法
    public abstract void speak();
    public abstract void eat();

    public String getName(){
        return this.name;
    }

    public String getGender(){
        return this.gender;
    }

    public int getAge(){
        return this.age;
    }

    // 链式调用,返回this当前对象指针
    public People setName(String name){
        this.name = name;
        return this;
    }

    public People setGender(String gender){
        this.gender = gender;
        return this;
    }

    public People setAge(int age){
        this.age = age;
        return this;
    }
    
}

