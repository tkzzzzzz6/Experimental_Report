#include <iostream>
#include <string>
#include <cstring>
using namespace std;

class Student
{
    /********* Begin *********/
    private:
        int SID;
        string name;
    public:
        Student();
        Student(int SID, string name) : SID(SID), name(name) {}
        ~Student();
        // 在此处声明所需的成员

        /********* End *********/
};

/********* Begin *********/

// 在此处定义成员函数
Student::Student(){
    SID = 0;
    name = "王小明";
}
Student::~Student(){
    cout << SID << " " << name <<" "<<"退出程序" << endl;
}
/********* End *********/
