package animal;

public interface Animal {
    // 动物的吃法都是不一样的
    void eat();
    
    // 动物的睡眠方法
    default void sleep() {
        System.out.println("sleep"); 
    }
}

