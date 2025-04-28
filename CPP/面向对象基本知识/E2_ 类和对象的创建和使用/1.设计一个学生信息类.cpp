#include <iostream>
#include <string>
using namespace std;

class StInfo
{
    /********* Begin *********/
    // 在此处声明StInfo类
    private:
        int studentID;
        string name;
        string className;
        string phoneNumber; 
    public:
        StInfo() {}; // 添加默认构造函数
        StInfo(int studentID, string name, string className, string phoneNumber);
        void SetInfo(int studentID, string name, string className, string phoneNumber); 
        void PrintInfo(); // 重命名display为PrintInfo
        /********* End *********/
};

/********* Begin *********/
// 在此处定义StInfo类
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
    cout << "学号：" << studentID << endl;
    cout << "姓名：" << name << endl;
    cout << "班级：" << className << endl;
    cout << "手机号：" << phoneNumber << endl;
}
/********* End *********/
