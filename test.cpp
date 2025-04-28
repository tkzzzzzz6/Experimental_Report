#include <iostream>
#include <string>
using namespace std;

class Computer
{ // 父类
public:
    Computer(string cpu, string gpu, double ram, double storage) : cpu(cpu), gpu(gpu), ram(ram), storage(storage) {}
    void show()
    {
        cout << "Computer：cpu=" << cpu << ",gpu=" << gpu << ",ram=" << ram << ",storage=" << storage << endl;
    }

protected:  // 让子类可以访问
    string cpu;
    string gpu;
    double ram;
    double storage;
};

class Laptop : public Computer
{ // 子类 - 笔记本
public:
    Laptop(string cpu, string gpu, double ram, double storage) : Computer(cpu, gpu, ram, storage) {}
    void show()
    {
        cout << "Laptop：cpu=" << cpu << ",gpu=" << gpu << ",ram=" << ram << ",storage=" << storage << endl;
    }

};
int main()
{
    Computer computer("Intel", "NVIDIA RTX 3060Ti", 16, 512);
    cout << "基类对象：" << endl;
    computer.show();
    Laptop laptop("Intel", "NVIDIA RTX 3060", 16, 512);
    cout << "将派生类对象赋值给基类对象：" << endl;
    computer = laptop; // 将派生类对象赋值给基类对象
    computer.show();
    return 0;
}