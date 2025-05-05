import animal.Animal;
import animal.Rabbit;
import animal.Tiger;
public class Main{
    public static void main(String[] args) {
            Rabbit r = new Rabbit();
            Tiger t = new Tiger();
            r.eat(); 
            t.eat();
            r.sleep();
            t.sleep();
        }
}

