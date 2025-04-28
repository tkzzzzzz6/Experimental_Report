#include <iostream>
#include <string>
#include <cstring>  // Added for strlen function

using namespace std;

/********* Begin *********/
// 在此处实现一个汽车类
class Car
{
    private:
        bool carDoor=false;
        bool carLight=false;
        int speed=0;
    public:
        Car(bool carDoor, bool carLight, int speed);
        void OpenDoor();
        void CloseDoor();
        void OpenLight();
        void CloseLight();
        void Accelerate();
        void Brake();
        void isDisplay();
};

Car::Car(bool carDoor, bool carLight, int speed)
    : carDoor(carDoor), carLight(carLight), speed(speed){};

void Car::OpenDoor()
{
    carDoor = true;
}


void Car::CloseDoor()
{
    carDoor = false;
}

void Car::OpenLight()
{
    carLight = true;
}

void Car::CloseLight()
{
    carLight = false;
}

void Car::Accelerate()
{
    speed += 10;
}

void Car::Brake()
{
    speed -= 10;
}

void Car::isDisplay()
{
    cout << "车门 " << (carDoor ? "ON" : "OFF") << endl;
    cout << "车灯 " << (carLight ? "ON" : "OFF") << endl;
    cout << "速度 " << speed << endl;
}
int main()
{
    /********* Begin *********/
    // 在此处解析执行输出汽车的整体状态
    char cmds[25];
    cin >> cmds;
    Car car(false, false, 0);
    for (int i = 0; i < strlen(cmds); ++i)
    {
        switch (cmds[i])
        {
            // 必须要使用字节的1,2,3,不能使用数字的1,2,3,不然会导致判定错误
            case '1':
                car.OpenDoor();
                break;
            case '2':
                car.CloseDoor();
                break;
            case '3':
                car.OpenLight();
                break;
            case '4':
                car.CloseLight();
                break;
            case '5':
                car.Accelerate();
                break;
            case '6':
                car.Brake();
                break;
        }
    }
    car.isDisplay();
    /********* End *********/
}