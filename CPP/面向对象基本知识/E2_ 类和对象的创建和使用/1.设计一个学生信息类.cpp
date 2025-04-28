#include <iostream>
#include <string>
using namespace std;

class StInfo
{
    /********* Begin *********/
    // �ڴ˴�����StInfo��
    private:
        int studentID;
        string name;
        string className;
        string phoneNumber; 
    public:
        StInfo() {}; // ���Ĭ�Ϲ��캯��
        StInfo(int studentID, string name, string className, string phoneNumber);
        void SetInfo(int studentID, string name, string className, string phoneNumber); 
        void PrintInfo(); // ������displayΪPrintInfo
        /********* End *********/
};

/********* Begin *********/
// �ڴ˴�����StInfo��
StInfo::StInfo(int studentID, string name, string className, string phoneNumber)
{
    this->studentID = studentID;
    this->name = name;
    this->className = className;
    this->phoneNumber = phoneNumber;
}

void StInfo::SetInfo(int studentID, string name, string className, string phoneNumber)
{
    this->studentID = studentID;
    this->name = name;
    this->className = className;
    this->phoneNumber = phoneNumber;
}

void StInfo::PrintInfo()
{
    cout << "ѧ�ţ�" << studentID << endl;
    cout << "������" << name << endl;
    cout << "�༶��" << className << endl;
    cout << "�ֻ��ţ�" << phoneNumber << endl;
}
/********* End *********/
