#include <iostream>

using namespace std;

class Container{
    private:
        string name;
    public:
        Container(string name):name(name){}
        virtual double getArea() = 0;
        virtual double getVolume() = 0;
        string getName(){return name;}
        static const double PI;
};

const double Container::PI = 3.1415926;

class Sphere:public Container{
    private:
        double radius;
    public:
        Sphere(string name,double r):Container(name),radius(r){}
        double getArea(){
            return 4*PI*radius*radius;
        }
        double getVolume(){
            return 4.0/3*PI*radius*radius*radius;
        }
};

class Cylinder:public Container{
    private:
        double radius, height;
    public:
        Cylinder(string name, double r, double h) : Container(name), radius(r), height(h) {}
        double getArea(){return 2*PI*radius*(height+radius);}
        double getVolume(){return PI*radius*radius*height;}
};

class Cube:public Container{
    private:
        double side;
    public:
        Cube(string name, double s):Container(name),side(s){}
        double getArea(){return 6*side*side;}
        double getVolume(){return side*side*side;}
};

void display(Container *p){
    cout << "Name: " << p->getName() << endl;
    cout << "Area: " << p->getArea() << endl;
    cout << "Volume: " << p->getVolume() << endl;
}

int main(){
    Container *p;
    Sphere s("Sphere",1);
    Cylinder c("Cylinder",1,1);
    Cube cube("Cube",1);
    p = &s;
    display(p);
    p = &c;
    display(p);
    p = &cube;
    display(p);
    return 0;
}