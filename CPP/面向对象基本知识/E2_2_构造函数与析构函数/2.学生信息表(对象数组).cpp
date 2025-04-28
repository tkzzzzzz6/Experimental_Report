#include <string>
#include <iostream>
using namespace std;

/********* Begin *********/
class Student
{
    // 在此处声明所需的成员
    private:
        int SID;
        string name;
        float score;
    public:
        Student(int StudentID = 0, string name = "", float score = 0):SID(StudentID),name(name),score(score){}
        ~Student(){};
        void Display();
        int GetSID() { return SID; }
        string GetName() { return name; }
        float GetScore() { return score; }
};

// 学生信息表长度不超过5
const int MAX_STUDENTS = 5;
Student students[MAX_STUDENTS];
int studentCount = 0;

void Add(int sid, string name, float score)
{
    if (studentCount < MAX_STUDENTS) {
        students[studentCount] = Student(sid, name, score);
        studentCount++;
    }
}

void Student::Display()
{
    cout << SID << " " << name << " " << score << endl;
}

void PrintAll()
{
    for (int i = 0; i < studentCount; i++) {
        students[i].Display();
    }
}

void Average()
{
    float sum = 0;
    for (int i = 0; i < studentCount; i++) {
        sum += students[i].GetScore();
    }
    
    if (studentCount > 0) {
        float avg = sum / studentCount;
        cout << "平均成绩 " << avg << endl;
    } else {
        cout << "没有学生记录" << endl;
    }
}
/********* End *********/

