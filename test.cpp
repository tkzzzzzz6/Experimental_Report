#include <iostream>
#include <string>
using namespace std;

class Computer
{ // ����
public:
    Computer(string cpu, string gpu, double ram, double storage) : cpu(cpu), gpu(gpu), ram(ram), storage(storage) {}
    void show()
    {
        cout << "Computer��cpu=" << cpu << ",gpu=" << gpu << ",ram=" << ram << ",storage=" << storage << endl;
    }

protected:  // ��������Է���
    string cpu;
    string gpu;
    double ram;
    double storage;
};

class Laptop : public Computer
{ // ���� - �ʼǱ�
public:
    Laptop(string cpu, string gpu, double ram, double storage) : Computer(cpu, gpu, ram, storage) {}
    void show()
    {
        cout << "Laptop��cpu=" << cpu << ",gpu=" << gpu << ",ram=" << ram << ",storage=" << storage << endl;
    }

};
int main()
{
    Computer computer("Intel", "NVIDIA RTX 3060Ti", 16, 512);
    cout << "�������" << endl;
    computer.show();
    Laptop laptop("Intel", "NVIDIA RTX 3060", 16, 512);
    cout << "�����������ֵ���������" << endl;
    computer = laptop; // �����������ֵ���������
    computer.show();
    return 0;
}