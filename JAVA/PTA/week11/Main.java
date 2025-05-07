// package JAVA.PTA.week11;
// 多态

import java.util.*;
 
interface Container {
    public static final double pi = 3.1415926;

    public abstract double area();

    public abstract double volume();

    static double sumofArea(Container c[]) {
        double result = 0.0;
        for (int i = 0; i < c.length; ++i) {
            result += c[i].area();
        }
        return result;
    }

    static double sumofVolume(Container c[]) {
        double result = 0.0;
        for (int i = 0; i < c.length; ++i) {
            result += c[i].volume();
        }
        return result;
    };
}


class Cube implements Container {
    private double length;

    public Cube(double length) {
        this.length = length;
    }

    @Override
    public double area() {
        return 6 * Math.pow(length, 2);
    }

    @Override
    public double volume() {
        return Math.pow(length, 3);
    }
}

class Cylinder implements Container {
    private double radius;
    private double height;

    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double area() {
        return 2 * pi * radius * (radius + height);
    }

    @Override
    public double volume() {
        return pi * Math.pow(radius,2) * height;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Container[] container = new Container[n];
        
        for (int i = 0; i < n; i++) {
            String s = scanner.next();
            if (s.equals("cube")) {
                double length = scanner.nextDouble();
                container[i] = new Cube(length);
            } else if (s.equals("cylinder")) {
                double r, h;
                r = scanner.nextDouble();
                h = scanner.nextDouble();
                container[i] = new Cylinder(r, h);
            }
        }
        System.out.printf("%.2f%n", Container.sumofArea(container));
        System.out.printf("%.2f%n", Container.sumofVolume(container));
        scanner.close();
    }
}