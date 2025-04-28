#include <string>
#include <iostream>
using namespace std;

/********* Begin *********/
class User
{
    // 在此处声明所需的成员
    private:
        string name;
        int Books;
    public:
        User(string name,int Books):name(name),Books(Books){
            cout << name << " " << Books << " " << "进入" << endl;
            UserCount++;
            BookCount += Books;
        }
        ~User() {
            cout << name << " " << Books << " " << "离开" << endl;
            UserCount--;
            BookCount -= Books;
        };
        static void GetState();
        static int UserCount;
        static int BookCount;
};

int User::UserCount = 0;
int User::BookCount = 0;

// 在此处定义成员函数
void User::GetState() {
    if(UserCount == 0)
        return;
    cout << "书店人数:" << UserCount << "，" << "书店共享书数量:" << BookCount << "，" << "人均共享数量:" << (UserCount > 0 ? BookCount / UserCount : 0) << endl;
}

/********* End *********/
