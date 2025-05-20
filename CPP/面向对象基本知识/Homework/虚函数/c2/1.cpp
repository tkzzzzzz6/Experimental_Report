#include <iostream>

using namespace std;

class area_c1{
    protected:
        double height;
        double width;
    public:
        area_c1(double r,double s){
            height = r;
            width = s;
        }
        virtual double area() = 0;
};

// 问题一要求
class retangle:public area_c1{
    public:
        retangle(double r,double s):area_c1(r,s){}
        double area(){return height*width;}
};

class isosceles:public area_c1{
    public:
        isosceles(double r,double s):area_c1(r,s){}
        double area(){return 0.5*height*width;}
};

// 问题二要求
int main(){
    retangle r(10.0,5.0);
    isosceles i(4.0, 6.0);
    cout << "the area of retangle is: " << r.area() << endl;
    cout << "the area of isosceles is: " << i.area() << endl;
    return 0;
}